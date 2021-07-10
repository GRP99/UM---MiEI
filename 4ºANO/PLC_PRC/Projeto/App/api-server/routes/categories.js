var express = require("express");
var router = express.Router();
var gdb = require("../utils/graphdb");
var axios = require("axios");

// #################### GET #####################
// GET ALL CATEGORIES
router.get("/", async function(req, res, next) {
    var myquery = `SELECT ?idcategory ?strCategory (COUNT(?idcocktail) AS ?total) WHERE { 
		?idcocktail a :Cocktail .
		?idcocktail :hasCategory ?idcategory .
		?idcategory :strCategory ?strCategory .
	} GROUP BY ?idcategory ?strCategory ORDER BY DESC (?total) `;
    var result = await gdb.execQuery(myquery);
    var dados = result.results.bindings.map((category) => {
        return {
            idCategory: category.idcategory.value.split("#")[1],
            strCategory: category.strCategory.value,
            totalOfCocktails: category.total.value,
        };
    });
    var myquery = `SELECT (COUNT(?idcocktail) AS ?total) WHERE {
		?idcocktail a :Cocktail .
		MINUS { ?idcocktail :hasCategory ?idcategory . } 
	}`;
    var result = await gdb.execQuery(myquery);
    var withoutCategory = result.results.bindings.map((category) => {
        return {
            idCategory: "withoutcategory",
            strCategory: "Without Category",
            totalOfCocktails: category.total.value,
        };
    });
    dados.push(withoutCategory[0]);
    res.jsonp(dados);
});

// GET CATEGORY BY ID
router.get("/:id", async function(req, res, next) {
    if (req.params.id == "withoutcategory") {
        var query_cocktails = `SELECT ?drinkid ?drinkname ?strGlass (COUNT(?idIngredient) AS ?totIngredients) WHERE {
			?drinkid a :Cocktail .
			MINUS { ?drinkid :hasCategory ?idcategory . }
			?drinkid :drinkName ?drinkname .
			OPTIONAL {  ?drinkid :serveInGlassware ?idglassware . ?idglassware :strGlass ?strGlass . }
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
        var category = {
            idCategory: req.params.id,
            strCategory: "Without Category",
            Cocktails: cocktails,
        };
        res.jsonp(category);
    } else {
        var query_cocktails = `SELECT ?drinkid ?drinkname ?strGlass (COUNT(?idIngredient) AS ?totIngredients) WHERE {
			:${req.params.id} a :Category .
			:${req.params.id} :haveCocktail ?drinkid .
			?drinkid a :Cocktail . 
			?drinkid :drinkName ?drinkname .
			OPTIONAL {  ?drinkid :serveInGlassware ?idglassware . ?idglassware :strGlass ?strGlass . }
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
        var query_strCategory = `SELECT ?strCategory WHERE {
			:${req.params.id} a :Category .
			:${req.params.id} :strCategory ?strCategory . 	
		}`;
        var result = await gdb.execQuery(query_strCategory);
        var category = result.results.bindings.map((category) => {
            return {
                idCategory: req.params.id,
                strCategory: category.strCategory.value,
                Cocktails: cocktails,
            };
        });
        res.jsonp(category[0]);
    }
});

// #################### POST ####################
// POST NEW CATEGORY
router.post("/", async function(req, res, next) {
    if (req.user.level == "admin") {
        var id = String(req.body.name).replace(/[^A-Za-z0-9-]+/g, "").replace(/-/g, "_").toLowerCase();
        var categories = await axios.get("http://localhost:7300/categories");
        await axios.all([categories]).then(
            axios.spread((...resp) => {
                var c = resp[0].data;
                c.forEach((element) => {
                    if (element.idCategory.toLowerCase() == id) {
                        res.redirect("http://localhost:7301/admin/manage/admin_category_insert?alert=1");
                    }
                });
            })
        );
        var query = `INSERT DATA {
            :${id} rdf:type owl:NamedIndividual .
            :${id} a :Category .
            :${id} :strCategory "${req.body.name}" .
        `;
        if (req.body.cocktails != false){
            if (Array.isArray(req.body.cocktails)) {
                let unique = [];
                req.body.cocktails.forEach((cocktail) => {
                    if (!unique.includes(cocktail)) {
                        unique.push(cocktail);
                        query = query.concat(`:${cocktail} :hasCategory :${id} . :${id} :haveCocktail :${cocktail} .`);
                    }
                });
            } else {
                query = query.concat(`:${req.body.cocktails} :hasCategory :${id} . :${id} :haveCocktail :${req.body.cocktails} .`);
            }
        }
        query = query.concat("\n}\n");
        var result = await gdb.execTransaction(query);
        
        res.redirect("http://localhost:7301/admin/manage/categories?inserted=true");
    } else {        
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// POST A NEW COCKTAIL TO CATEGORY
router.post("/:id/addCocktail", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` INSERT DATA  {
            :${req.body.cocktail} :hasCategory :${req.params.id} .
            :${req.params.id} :haveCocktail :${req.body.cocktail} .          
        }`;
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/categories/" + req.params.id + "?cocktail=1");
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// #################### DELETE ##################
// DELETE A CATEGORY
router.delete("/:id", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.id} rdf:type owl:NamedIndividual .
            :${req.params.id} a :Category .
            :${req.params.id} :strCategory ?strCategory .
            ?idCocktail :hasCategory :${req.params.id} .
            :${req.params.id} :haveCocktail ?idCocktail .          
        } WHERE {
            OPTIONAL { :${req.params.id} rdf:type owl:NamedIndividual . }
            :${req.params.id} a :Category .
            :${req.params.id} :strCategory ?strCategory . 
            OPTIONAL { ?idCocktail :hasCategory :${req.params.id} . }
            OPTIONAL { :${req.params.id} :haveCocktail ?idCocktail . }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).jsonp("Triplos apagados ... " + result);
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// DELETE COCKTAIL FROM A CATEGORY
router.delete("/:id/cocktail/:idDrink", async function(req, res, next) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.idDrink} :hasCategory :${req.params.id} .
            :${req.params.id} :haveCocktail :${req.params.idDrink} .          
        } WHERE {
            OPTIONAL { :${req.params.idDrink} :hasCategory :${req.params.id} . }
            OPTIONAL { :${req.params.id} :haveCocktail :${req.params.idDrink} . }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).json(result);
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});
module.exports = router;