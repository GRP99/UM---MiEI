var express = require("express");
var router = express.Router();
var gdb = require("../utils/graphdb");
var axios = require("axios");

// #################### GET #####################
// GET ALL BARS
router.get("/", async function(req, res, next) {
    var myquery = `SELECT ?idbar ?strBar (COUNT(?idcocktail) AS ?total) WHERE { 
		?idcocktail a :Cocktail .
		?idcocktail :associatedWithBar ?idbar .
		?idbar :strBar ?strBar .
	} GROUP BY ?idbar ?strBar ORDER BY DESC (?total) `;
    var result = await gdb.execQuery(myquery);
    var dados = result.results.bindings.map((bar) => {
        return {
            idBar: bar.idbar.value.split("#")[1],
            strBar: bar.strBar.value,
            totalOfCocktails: bar.total.value,
        };
    });
    var myquery = `SELECT (COUNT(?idcocktail) AS ?total) WHERE {
		?idcocktail a :Cocktail .
		MINUS { ?idcocktail :associatedWithBar ?idbar . } 
	}`;
    var result = await gdb.execQuery(myquery);
    var withoutBar = result.results.bindings.map((bar) => {
        return {
            idBar: "withoutbar",
            strBar: "Without Bar or Company",
            totalOfCocktails: bar.total.value,
        };
    });
    dados.push(withoutBar[0]);
    res.jsonp(dados);
});

// GET A BAR BY ID
router.get("/:id", async function(req, res, next) {
    if (req.params.id == "withoutbar") {
        var query_cocktails = `SELECT ?drinkid ?drinkname ?strGlass (COUNT(?idIngredient) AS ?totIngredients) WHERE {
			?drinkid a :Cocktail .
			MINUS { ?drinkid :associatedWithBar ?idbar . }
			?drinkid :drinkName ?drinkname .
			OPTIONAL { ?drinkid :serveInGlassware ?idglassware . ?idglassware :strGlass ?strGlass . }
			OPTIONAL { ?drinkid :needQuantity ?idQuantity . ?idQuantity :useIngredient ?idIngredient . }
		} GROUP BY ?drinkid ?drinkname ?strGlass ORDER BY ?drinkid`;
        var result = await gdb.execQuery(query_cocktails);
        var cocktails = result.results.bindings.map((cocktail) => {
            return {
                idDrink: cocktail.drinkid.value.split("#")[1],
                strDrink: cocktail.drinkname.value,
                strGlass: cocktail.strGlass ? cocktail.strGlass.value : "",
                totIngredients: cocktail.totIngredients ? cocktail.totIngredients.value : "",
            };
        });
        var bar = {
            idBar: req.params.id,
            strBar: "Without Bar or Company",
            Cocktails: cocktails,
        };
        res.jsonp(bar);
    } else {
        var query_cocktails = `SELECT ?drinkid ?drinkname ?strGlass (COUNT(?idIngredient) AS ?totIngredients) WHERE {
			:${req.params.id} a :Bar_Company .
			:${req.params.id} :isAssociatedToCocktail ?drinkid .
			?drinkid a :Cocktail . 
			?drinkid :drinkName ?drinkname .
			OPTIONAL { ?drinkid :serveInGlassware ?idglassware . ?idglassware :strGlass ?strGlass .}
            OPTIONAL { ?drinkid :needQuantity ?idQuantity . ?idQuantity :useIngredient ?idIngredient . }
		} GROUP BY ?drinkid ?drinkname ?strGlass ORDER BY ?drinkid`;
        var result = await gdb.execQuery(query_cocktails);
        var cocktails = result.results.bindings.map((cocktail) => {
            return {
                idDrink: cocktail.drinkid.value.split("#")[1],
                strDrink: cocktail.drinkname.value,
                strGlass: cocktail.strGlass ? cocktail.strGlass.value : "",
                totIngredients: cocktail.totIngredients ? cocktail.totIngredients.value : "",
            };
        });
        var query_strBar = `SELECT ?strBar WHERE {
			:${req.params.id} a :Bar_Company .
			:${req.params.id} :strBar ?strBar . 	
		}`;
        var result = await gdb.execQuery(query_strBar);
        var bar = result.results.bindings.map((bar) => {
            return {
                idBar: req.params.id,
                strBar: bar.strBar.value,
                Cocktails: cocktails,
            };
        });
        res.jsonp(bar[0]);
    }
});

// #################### POST ####################
// POST NEW BAR
router.post("/", async function(req, res, next) {
    if (req.user.level == "admin") {
        var id = String(req.body.name).replace(/[^A-Za-z0-9-]+/g, "").replace(/-/g, "_").toLowerCase();
        var bars = await axios.get("http://localhost:7300/bars");
        await axios.all([bars]).then(
            axios.spread((...resp) => {
                var c = resp[0].data;
                c.forEach((element) => {
                    if (element.idBar.toLowerCase() == id) {
                        res.redirect("http://localhost:7301/admin/manage/admin_bar_insert?alert=1");
                    }
                });
            })
        );

        var query = `INSERT DATA {
            :${id} rdf:type owl:NamedIndividual .
            :${id} a :Bar_Company .
            :${id} :strBar "${req.body.name}" .
        `;
        if (req.body.cocktails != false){
            if (Array.isArray(req.body.cocktails)) {
                let unique = [];
                req.body.cocktails.forEach((cocktail) => {
                    if (!unique.includes(cocktail)) {
                        unique.push(cocktail);
                        query = query.concat(`:${cocktail} :associatedWithBar :${id} . :${id} :isAssociatedToCocktail :${cocktail} .`);
                    }
                });
            } else {
                query = query.concat(`:${req.body.cocktails} :associatedWithBar :${id} . :${id} :isAssociatedToCocktail :${req.body.cocktails} .`);
            }
        }

        query = query.concat("\n}\n");
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/bars?inserted=true");
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// POST A NEW COCKTAIL TO BAR
router.post("/:id/addCocktail", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` INSERT DATA  {
            :${req.body.cocktail} :associatedWithBar :${req.params.id} .
            :${req.params.id} :isAssociatedToCocktail :${req.body.cocktail} .          
        }`;
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/bars/" + req.params.id + "?cocktail=1");
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// #################### DELETE ##################
// DELETE A BAR
router.delete("/:id", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.id} rdf:type owl:NamedIndividual .
            :${req.params.id} a :Bar_Company .
            :${req.params.id} :strBar ?strBar .
            ?idCocktail :associatedWithBar :${req.params.id} .
            :${req.params.id} :isAssociatedToCocktail ?idCocktail .          
        } WHERE {
            OPTIONAL { :${req.params.id} rdf:type owl:NamedIndividual . }
            :${req.params.id} a :Bar_Company .
            :${req.params.id} :strBar ?strBar . 
            OPTIONAL { ?idCocktail :associatedWithBar :${req.params.id} . }
            OPTIONAL { :${req.params.id} :isAssociatedToCocktail ?idCocktail . }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).jsonp("Triplos apagados ... " + result);
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// DELETE COCKTAIL FROM A BAR
router.delete("/:id/cocktail/:idDrink", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.idDrink} :associatedWithBar :${req.params.id} .
            :${req.params.id} :isAssociatedToCocktail :${req.params.idDrink} .          
        } WHERE {
            OPTIONAL { :${req.params.idDrink} :associatedWithBar :${req.params.id} . }
            OPTIONAL { :${req.params.id} :isAssociatedToCocktail :${req.params.idDrink} . }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).json(result);
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});


module.exports = router;