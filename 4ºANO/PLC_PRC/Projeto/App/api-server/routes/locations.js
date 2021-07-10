var express = require("express");
var router = express.Router();
var gdb = require("../utils/graphdb");
var axios = require("axios");

// #################### GET ####################
// GET ALL LOCATIONS
router.get("/", async function(req, res, next) {
    var myquery = `SELECT ?idlocation ?strLocation (COUNT(?idcocktail) AS ?total) WHERE { 
		?idcocktail a :Cocktail .
		?idcocktail :createdInLocation ?idlocation .
		?idlocation :strLocation ?strLocation .
	} GROUP BY ?idlocation ?strLocation ORDER BY DESC (?total) `;
    var result = await gdb.execQuery(myquery);
    var dados = result.results.bindings.map((location) => {
        return {
            idLocation: location.idlocation.value.split("#")[1],
            strLocation: location.strLocation.value,
            totalOfCocktails: location.total.value,
        };
    });
    var myquery = `SELECT (COUNT(?idcocktail) AS ?total) WHERE {
		?idcocktail a :Cocktail .
		MINUS { ?idcocktail :createdInLocation ?idlocation . } 
	}`;
    var result = await gdb.execQuery(myquery);
    var withoutLocation = result.results.bindings.map((location) => {
        return {
            idLocation: "withoutlocation",
            strLocation: "Without Location",
            totalOfCocktails: location.total.value,
        };
    });
    dados.push(withoutLocation[0]);
    res.jsonp(dados);
});

// GET ALL LOCATION BY ID
router.get("/:id", async function(req, res, next) {
    if (req.params.id == "withoutlocation") {
        var query_cocktails = `SELECT ?drinkid ?drinkname ?strGlass (COUNT(?idIngredient) AS ?totIngredients) WHERE {
			?drinkid a :Cocktail .
			MINUS { ?drinkid :createdInLocation ?idlocation . }			
			?drinkid :drinkName ?drinkname .
			OPTIONAL{ ?drinkid :serveInGlassware ?idglassware . ?idglassware :strGlass ?strGlass . }
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
        var location = {
            idLocation: req.params.id,
            strLocation: "Without Location",
            Cocktails: cocktails,
        };
        res.jsonp(location);
    } else {
        var query_cocktails = `SELECT ?drinkid ?drinkname ?strGlass (COUNT(?idIngredient) AS ?totIngredients) WHERE {
			:${req.params.id} a :Location .
			:${req.params.id} :originOfCocktail ?drinkid .
			?drinkid a :Cocktail . 
			?drinkid :drinkName ?drinkname .
			OPTIONAL{ ?drinkid :serveInGlassware ?idglassware . ?idglassware :strGlass ?strGlass . }			
			OPTIONAL { ?drinkid :needQuantity ?idQuantity . ?idQuantity :useIngredient ?idIngredient . }
		} GROUP BY ?drinkid ?drinkname ?strGlass ORDER BY ?drinkid `;
        var result = await gdb.execQuery(query_cocktails);
        var cocktails = result.results.bindings.map((cocktail) => {
            return {
                idDrink: cocktail.drinkid.value.split("#")[1],
                strDrink: cocktail.drinkname.value,
                strGlass: cocktail.strGlass ? cocktail.strGlass.value : "",
                totIngredients: cocktail.totIngredients ? cocktail.totIngredients.value : "",
            };
        });
        var query_strLocation = `SELECT ?strLocation WHERE {
			:${req.params.id} a :Location .
			:${req.params.id} :strLocation ?strLocation . 	
		}`;
        var result = await gdb.execQuery(query_strLocation);
        var location = result.results.bindings.map((location) => {
            return {
                idLocation: req.params.id,
                strLocation: location.strLocation.value,
                Cocktails: cocktails,
            };
        });
        res.jsonp(location[0]);
    }
});

// #################### POST ####################
// POST NEW LOCATION
router.post("/", async function(req, res, next) {
    if (req.user.level == "admin") {
        var id = String(req.body.name).replace(/[^A-Za-z0-9-]+/g, "").replace(/-/g, "_").toLowerCase();
        console.log(id)
        var locations = await axios.get("http://localhost:7300/locations");
        await axios.all([locations]).then(
            axios.spread((...resp) => {
                var c = resp[0].data;
                c.forEach((element) => {
                    if (element.idLocation.toLowerCase() == id) {
                        res.redirect("http://localhost:7301/admin/manage/admin_location_insert?alert=1");
                    }
                });
            })
        );

        var query = `INSERT DATA {
            :${id} rdf:type owl:NamedIndividual .
            :${id} a :Location .
            :${id} :strLocation "${req.body.name}" .
        `;
        if (req.body.cocktails != false){
            if (Array.isArray(req.body.cocktails)) {
                let unique = [];
                req.body.cocktails.forEach((cocktail) => {
                    if (!unique.includes(cocktail)) {
                        unique.push(cocktail);
                        query = query.concat(`:${cocktail} :createdInLocation :${id} . :${id} :originOfCocktail :${cocktail} .`);
                    }
                });
            } else {
                query = query.concat(`:${req.body.cocktails} :createdInLocation :${id} . :${id} :originOfCocktail :${req.body.cocktails} .`);
            }
        }

        query = query.concat("\n}\n");
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/locations?inserted=true");
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// POST A NEW COCKTAIL TO LOCATION
router.post("/:id/addCocktail", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` INSERT DATA  {
            :${req.body.cocktail} :createdInLocation :${req.params.id} .
            :${req.params.id} :originOfCocktail :${req.body.cocktail} .          
        }`;
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/locations/" + req.params.id + "?cocktail=1");
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// #################### DELETE ##################
// DELETE A LOCATION
router.delete("/:id", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.id} rdf:type owl:NamedIndividual .
            :${req.params.id} a :Location .
            :${req.params.id} :strLocation ?strLocation .
            ?idCocktail :createdInLocation :${req.params.id} .
            :${req.params.id} :originOfCocktail ?idCocktail .          
        } WHERE {
            OPTIONAL { :${req.params.id} rdf:type owl:NamedIndividual . }
            :${req.params.id} a :Location .
            :${req.params.id} :strLocation ?strLocation . 
            OPTIONAL { ?idCocktail :createdInLocation :${req.params.id} . }
            OPTIONAL { :${req.params.id} :originOfCocktail ?idCocktail . }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).jsonp("Triplos apagados ... " + result);
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// DELETE COCKTAIL FROM A LOCATION
router.delete("/:id/cocktail/:idDrink", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.idDrink} :createdInLocation :${req.params.id} .
            :${req.params.id} :originOfCocktail :${req.params.idDrink} .          
        } WHERE {
            OPTIONAL { :${req.params.idDrink} :createdInLocation :${req.params.id} . }
            OPTIONAL { :${req.params.id} :originOfCocktail :${req.params.idDrink} . }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).json(result);
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

module.exports = router;