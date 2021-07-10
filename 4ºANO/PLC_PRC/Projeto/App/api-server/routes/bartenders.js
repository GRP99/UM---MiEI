var express = require("express");
var router = express.Router();
var gdb = require("../utils/graphdb");
var axios = require("axios");
var Bartender = require('../controllers/bartenders');

// #################### GET #####################
// GET ALL BARTENDERS
router.get("/", async function(req, res, next) {
    var myquery = `SELECT ?idbartender ?strBartender (COUNT(?idcocktail) AS ?total) WHERE { 
		?idcocktail a :Cocktail .
		?idcocktail :createdByBartender ?idbartender .
		?idbartender :strBartender ?strBartender .
	} GROUP BY ?idbartender ?strBartender ORDER BY DESC (?total) `;
    var result = await gdb.execQuery(myquery);
    var dados = result.results.bindings.map((bartender) => {
        return {
            idBartender: bartender.idbartender.value.split("#")[1],
            strBartender: bartender.strBartender.value,
            totalOfCocktails: bartender.total.value,
        };
    });
    var myquery = `SELECT (COUNT(?idcocktail) AS ?total) WHERE {
		?idcocktail a :Cocktail .
		MINUS { ?idcocktail :createdByBartender ?idbartender . } 
	}`;
    var result = await gdb.execQuery(myquery);
    var withoutBartender = result.results.bindings.map((bartender) => {
        return {
            idBartender: "withoutbartender",
            strBartender: "Without Bartender",
            totalOfCocktails: bartender.total.value,
        };
    });
    dados.push(withoutBartender[0]);
    res.jsonp(dados);
});

// GET BARTENDER BY ID
router.get("/:id", async function(req, res, next) {
    if (req.params.id == "withoutbartender") {
        var query_cocktails = `SELECT ?drinkid ?drinkname ?strGlass ?idGlass (COUNT(?idIngredient) AS ?totIngredients) WHERE {
			?drinkid a :Cocktail .
			MINUS { ?drinkid :createdByBartender ?idbartender . }
			?drinkid a :Cocktail . 
			?drinkid :drinkName ?drinkname .
			OPTIONAL{ ?drinkid :serveInGlassware ?idglassware . ?idglassware :strGlass ?strGlass . }
			OPTIONAL { ?drinkid :needQuantity ?idQuantity . ?idQuantity :useIngredient ?idIngredient . }.
		} GROUP BY ?drinkid ?idGlass ?drinkname ?strGlass ORDER BY ?drinkid`;
        var result = await gdb.execQuery(query_cocktails);
        var cocktails = result.results.bindings.map((cocktail) => {
            return {
                idDrink: cocktail.drinkid.value.split("#")[1],
                strDrink: cocktail.drinkname.value,
                Glassware: {
                    idGlass: cocktail.idGlass ? cocktail.idGlass.value.split("#")[1] : "",
                    strGlass: cocktail.strGlass ? cocktail.strGlass.value : "",
                },
                totIngredients: cocktail.totIngredients ? cocktail.totIngredients.value : "",
            };
        });
        var bartender = {
            idBartender: req.params.id,
            strBartender: "Without Bartender",
            Cocktails: cocktails,
        };
        res.jsonp(bartender);
    } else {
        var query_cocktails = `SELECT ?drinkid ?drinkname ?strGlass ?idGlass (COUNT(?idIngredient) AS ?totIngredients) WHERE {
			:${req.params.id} a :Bartender .
			:${req.params.id} :createCocktail ?drinkid .
			?drinkid a :Cocktail . 
			?drinkid :drinkName ?drinkname .
			OPTIONAL { ?drinkid :serveInGlassware ?idGlass . ?idGlass :strGlass ?strGlass . }
			OPTIONAL { ?drinkid :needQuantity ?idQuantity . ?idQuantity :useIngredient ?idIngredient . }
		} GROUP BY ?drinkid ?drinkname ?strGlass ?idGlass ORDER BY ?drinkid `;
        var result = await gdb.execQuery(query_cocktails);
        var cocktails = result.results.bindings.map((cocktail) => {
            return {
                idDrink: cocktail.drinkid.value.split("#")[1],
                strDrink: cocktail.drinkname.value,
                Glassware: {
                    idGlass: cocktail.idGlass ? cocktail.idGlass.value.split("#")[1] : "",
                    strGlass: cocktail.strGlass ? cocktail.strGlass.value : "",
                },
                totIngredients: cocktail.totIngredients ? cocktail.totIngredients.value : "",
            };
        });
        var query_strBartender = `SELECT ?strBartender WHERE {
			:${req.params.id} a :Bartender .
			:${req.params.id} :strBartender ?strBartender . 	
		}`;
        var result = await gdb.execQuery(query_strBartender);
        var bartender = result.results.bindings.map((bartender) => {
            return {
                idBartender: req.params.id,
                strBartender: bartender.strBartender.value,
                Cocktails: cocktails,
            };
        });
        res.jsonp(bartender[0]);
    }
});

// #################### POST ####################
// POST NEW BARTENDER
router.post("/", async function(req, res, next) {
    if (req.user.level == "admin") {
        var id = String(req.body.name).replace(/[^A-Za-z0-9-]+/g, "").replace(/-/g, "_").toLowerCase();
        var bartenders = await axios.get("http://localhost:7300/bartenders");
        
        var found = 0
        await axios.all([bartenders]).then(
            axios.spread((...resp) => {
                var c = resp[0].data;
                c.forEach((element) => {
                    if (element.idBartender.toLowerCase() == id) {
                        found = 1
                    }
                });
            })
        );

        if (found == 1) {
            res.redirect("http://localhost:7301/admin/manage/admin_bartender_insert?alert=1");
        }


        var c = {
            _id: id,
            reviews: [],
            likes: {
                count: 0,
                authors: []
            },
            dislikes: {
                count: 0,
                authors: []
            }
        };

        /* create bartender in mongo */
        Bartender.insertBartender(c).then(() => {}).catch((err) => {
            res.status(500).jsonp({
                error: err
            });
        });

        var query = `INSERT DATA {
            :${id} rdf:type owl:NamedIndividual .
            :${id} a :Bartender .
            :${id} :strBartender "${req.body.name}" .
        `;
        
        if (req.body.cocktails != false){
            if (Array.isArray(req.body.cocktails)) {
                let unique = [];
                req.body.cocktails.forEach((cocktail) => {
                    if (!(unique.includes(cocktail))) {
                        unique.push(cocktail);
                        query = query.concat(`:${cocktail} :createdByBartender :${id} . :${id} :createCocktail :${cocktail} . `);
                    }
                });
            } else {
                query = query.concat(`:${req.body.cocktails} :createdByBartender :${id} . :${id} :createCocktail :${req.body.cocktails} .`);
            }
        }

        query = query.concat("\n}\n");
        console.log(query)
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/bartenders?inserted=true");
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// POST A NEW COCKTAIL TO BARTENDER
router.post("/:id/addCocktail", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` INSERT DATA  {
            :${req.body.cocktail} :createdByBartender :${req.params.id} .
            :${req.params.id} :createCocktail :${req.body.cocktail} .          
        }`;
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/bartenders/" + req.params.id + "?cocktail=1");
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// #################### DELETE ##################
// DELETE A BARTENDER
router.delete("/:id", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.id} rdf:type owl:NamedIndividual .
            :${req.params.id} a :Bartender .
            :${req.params.id} :strBartender ?strBartender .
            ?idCocktail :createdByBartender :${req.params.id} .
            :${req.params.id} :createCocktail ?idCocktail .          
        } WHERE {
            :${req.params.id} rdf:type owl:NamedIndividual .
            :${req.params.id} a :Bartender .
            :${req.params.id} :strBartender ?strBartender . 
            OPTIONAL { ?idCocktail :createdByBartender :${req.params.id} . }
            OPTIONAL { :${req.params.id} :createCocktail ?idCocktail . }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).jsonp("Triplos apagados ... " + result);
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// DELETE COCKTAIL FROM A BARTENDER
router.delete("/:id/cocktail/:idDrink", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.idDrink} :createdByBartender :${req.params.id} .
            :${req.params.id} :createCocktail :${req.params.idDrink} .          
        } WHERE {
            OPTIONAL { :${req.params.idDrink} :createdByBartender :${req.params.id} . }
            OPTIONAL { :${req.params.id} :createCocktail :${req.params.idDrink} . }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).json(result);
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

module.exports = router;