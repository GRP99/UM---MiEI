var express = require("express");
var router = express.Router();
var gdb = require("../utils/graphdb");
var axios = require("axios");

// #################### GET #####################
// GET ALL GLASSWARES
router.get("/", async function(req, res, next) {
    var myquery = `SELECT ?idglassware ?strGlass (COUNT(?idcocktail) AS ?total) WHERE { 
		?idcocktail a :Cocktail .
		?idcocktail :serveInGlassware ?idglassware .
		?idglassware :strGlass ?strGlass .
	} GROUP BY ?idglassware ?strGlass ORDER BY DESC (?total) `;
    var result = await gdb.execQuery(myquery);
    var dados = result.results.bindings.map((glass) => {
        return {
            idGlass: glass.idglassware.value.split("#")[1],
            strGlass: glass.strGlass.value,
            totalOfCocktails: glass.total.value,
        };
    });
    var myquery = `SELECT (COUNT(?idcocktail) AS ?total) WHERE {
		?idcocktail a :Cocktail .
		MINUS { ?idcocktail :serveInGlassware ?idglassware . } 
	}`;
    var result = await gdb.execQuery(myquery);
    var withoutGlass = result.results.bindings.map((glass) => {
        return {
            idGlass: "withoutglass",
            strGlass: "Without Glassware",
            totalOfCocktails: glass.total.value,
        };
    });
    dados.push(withoutGlass[0]);
    res.jsonp(dados);
});

// GET GLASSWARE BY ID
router.get("/:id", async function(req, res, next) {
    if (req.params.id == "withoutglass") {
        var query_cocktails = `SELECT ?drinkid ?drinkname (COUNT(?idIngredient) AS ?totIngredients) WHERE {
			?drinkid a :Cocktail .
			MINUS { ?drinkid :serveInGlassware ?idglassware . }
			?drinkid :drinkName ?drinkname .
			OPTIONAL { ?drinkid :needQuantity ?idQuantity . ?idQuantity :useIngredient ?idIngredient . }
		} GROUP BY ?drinkid ?drinkname ORDER BY ?drinkid`;
        var result = await gdb.execQuery(query_cocktails);
        var cocktails = result.results.bindings.map((cocktail) => {
            return {
                idDrink: cocktail.drinkid.value.split("#")[1],
                strDrink: cocktail.drinkname.value,
                totIngredients: cocktail.totIngredients ? cocktail.totIngredients.value : "",
            };
        });
        var glass = {
            idGlass: req.params.id,
            strGlass: "Without Glassware",
            Cocktails: cocktails,
        };
        res.jsonp(glass);
    } else {
        var query_cocktails = `SELECT ?drinkid ?drinkname (COUNT(?idIngredient) AS ?totIngredients) WHERE {
			:${req.params.id} a :Glassware .
			:${req.params.id} :serveCocktail ?drinkid .
			?drinkid a :Cocktail . 
			?drinkid :drinkName ?drinkname .
			OPTIONAL { ?drinkid :needQuantity ?idQuantity . ?idQuantity :useIngredient ?idIngredient . }
		} GROUP BY ?drinkid ?drinkname ORDER BY ?drinkid`;
        var result = await gdb.execQuery(query_cocktails);
        var cocktails = result.results.bindings.map((cocktail) => {
            return {
                idDrink: cocktail.drinkid.value.split("#")[1],
                strDrink: cocktail.drinkname.value,
                totIngredients: cocktail.totIngredients ? cocktail.totIngredients.value : "",
            };
        });
        var query_strGlass = `SELECT ?strGlass WHERE {
			:${req.params.id} a :Glassware .
			:${req.params.id} :strGlass ?strGlass . 	
		}`;
        var result = await gdb.execQuery(query_strGlass);
        var glassware = result.results.bindings.map((glass) => {
            return {
                idGlass: req.params.id,
                strGlass: glass.strGlass.value,
                Cocktails: cocktails,
            };
        });
        res.jsonp(glassware[0]);
    }
});

// #################### POST ####################
// POST NEW GLASSWARE
router.post("/", async function(req, res, next) {
    if (req.user.level == "admin") {
        var id = String(req.body.name).replace(/[^A-Za-z0-9-]+/g, "").replace(/-/g, "_").toLowerCase();
        var glasswares = await axios.get("http://localhost:7300/glasswares");
        await axios.all([glasswares]).then(
            axios.spread((...resp) => {
                var c = resp[0].data;
                c.forEach((element) => {
                    if (element.idGlass.toLowerCase() == id) {
                        res.redirect("http://localhost:7301/admin/manage/admin_glassware_insert?alert=1");
                    }
                });
            })
        );

        var query = `INSERT DATA {
            :${id} rdf:type owl:NamedIndividual .
            :${id} a :Glassware .
            :${id} :strGlass "${req.body.name}" .
        `;
        if (req.body.cocktails != false){
            if (Array.isArray(req.body.cocktails)) {
                let unique = [];
                req.body.cocktails.forEach((cocktail) => {
                    if (!unique.includes(cocktail)) {
                        unique.push(cocktail);
                        query = query.concat(`:${cocktail} :serveInGlassware :${id} . :${id} :serveCocktail :${cocktail} .`);
                    }
                });
            } else {
                query = query.concat(`:${req.body.cocktails} :serveInGlassware :${id} . :${id} :serveCocktail :${req.body.cocktails} .`);
            }
        }

        query = query.concat("\n}\n");
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/glasswares?inserted=true");
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// POST A NEW COCKTAIL TO GLASSWARE
router.post("/:id/addCocktail", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` INSERT DATA  {
            :${req.body.cocktail} :serveInGlassware :${req.params.id} .
            :${req.params.id} :serveCocktail :${req.body.cocktail} .          
        }`;
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/glasswares/" + req.params.id + "?cocktail=1");
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// #################### DELETE ##################
// DELETE A GLASSWARE
router.delete("/:id", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.id} rdf:type owl:NamedIndividual .
            :${req.params.id} a :Glassware .
            :${req.params.id} :strGlass ?strGlass .
            ?idCocktail :serveInGlassware :${req.params.id} .
            :${req.params.id} :serveCocktail ?idCocktail .          
        } WHERE {
            OPTIONAL { :${req.params.id} rdf:type owl:NamedIndividual . }
            :${req.params.id} a :Glassware .
            :${req.params.id} :strGlass ?strGlass . 
            OPTIONAL { ?idCocktail :serveInGlassware :${req.params.id} . }
            OPTIONAL { :${req.params.id} :serveCocktail ?idCocktail . }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).jsonp("Triplos apagados ... " + result);
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// DELETE COCKTAIL FROM A GLASSWARE
router.delete("/:id/cocktail/:idDrink", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.idDrink} :serveInGlassware :${req.params.id} .
            :${req.params.id} :serveCocktail :${req.params.idDrink} .          
        } WHERE {
            OPTIONAL { :${req.params.idDrink} :serveInGlassware :${req.params.id} . }
            OPTIONAL { :${req.params.id} :serveCocktail :${req.params.idDrink} . }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).json(result);
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

module.exports = router;