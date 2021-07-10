var express = require("express");
var router = express.Router();
var gdb = require("../utils/graphdb");

// #################### ABOUT ####################
router.get("/about", async function(req, res) {
    var result_explicit = await gdb.execSize();
    var query_total = `SELECT (COUNT(?s) AS ?triples) WHERE { ?s ?p ?o }`;
    var result_total = await gdb.execQuery(query_total);
    var total = result_total.results.bindings.map((result) => {
        return {
            value: result.triples.value,
        };
    });
    total = parseInt(total[0].value);
    var size = {
        inferred: total - result_explicit,
        total: total,
        explicit: result_explicit,
    };
    res.status(200).jsonp(size);
});

// #################### SEARCH ####################
router.get("/search", async function(req, res, next) {
    var input = req.query.input;

    /* Query Cocktails */
    var query_c = `SELECT ?drinkid ?drinkname ?drinkThumb WHERE { 
        ?drinkid a :Cocktail . 
        ?drinkid :drinkName ?drinkname .
        OPTIONAL { ?drinkid :drinkThumb ?drinkThumb . } 		
		{ FILTER regex(str(?drinkid), '${input}', "i") . }
    	UNION
    	{ FILTER regex(str(?drinkname), '${input}', "i") . }	
	} GROUP BY ?drinkid ?drinkname ?drinkThumb ORDER BY ?drinkid`;
    var result_cocktails = await gdb.execQuery(query_c);
    var cocktails = result_cocktails.results.bindings.map((cocktail) => {
        return {
            idDrink: cocktail.drinkid.value.split("#")[1],
            strDrink: cocktail.drinkname.value,
            drinkThumb: cocktail.drinkThumb ? cocktail.drinkThumb.value : "",
        };
    });

    /* Query Bartenders */
    var query_b = `SELECT ?idbartender ?strBartender WHERE { 
		?idcocktail a :Cocktail .
		?idcocktail :createdByBartender ?idbartender .
		?idbartender :strBartender ?strBartender .
		{ FILTER regex(str(?idbartender), '${input}', "i") . }
    	UNION
    	{ FILTER regex(str(?strBartender), '${input}', "i") . }
	} GROUP BY ?idbartender ?strBartender ORDER BY ?idbartender`;
    var result_bartenders = await gdb.execQuery(query_b);
    var bartenders = result_bartenders.results.bindings.map((bartender) => {
        return {
            idBartender: bartender.idbartender.value.split("#")[1],
            strBartender: bartender.strBartender.value,
        };
    });

    var dados = {
        cocktails: cocktails,
        bartenders: bartenders,
    };

    res.jsonp(dados);
});

// #################### BUILD_COCKTAIL ####################
router.get("/buildCocktail/:quantity", async function(req, res, next) {
    if (req.params.quantity == 1) {
        var query = `SELECT DISTINCT ?idCocktail ?drinkname ?drinkThumb WHERE { 
			?idCocktail a :Cocktail .
			?idCocktail :drinkName ?drinkname .
			OPTIONAL { ?idCocktail :drinkThumb ?drinkThumb . } 
			?idCocktail :needQuantity ?idQuantity1 .
			?idQuantity1 :useIngredient :${req.query.ingredient1} .
		}`;

        var queryIngredient = `SELECT ?ingredient1 WHERE {
            :${req.query.ingredient1} :strIngredient ?ingredient1 .
        }`;
    } else {
        if (req.params.quantity == 2) {
            var query = `SELECT DISTINCT ?idCocktail ?drinkname ?drinkThumb WHERE { 
				?idCocktail a :Cocktail .
				?idCocktail :drinkName ?drinkname .
				OPTIONAL { ?idCocktail :drinkThumb ?drinkThumb . } 
				?idCocktail :needQuantity ?idQuantity1 .
				?idCocktail :needQuantity ?idQuantity2 .
				?idQuantity1 :useIngredient :${req.query.ingredient1} .
				?idQuantity2 :useIngredient :${req.query.ingredient2} .
			}`;

            var queryIngredient = `SELECT ?ingredient1 ?ingredient2 WHERE {
                :${req.query.ingredient1} :strIngredient ?ingredient1 .
                :${req.query.ingredient2} :strIngredient ?ingredient2 .
            }`;
        } else {
            if (req.params.quantity == 3) {
                var query = `SELECT DISTINCT ?idCocktail ?drinkname ?drinkThumb WHERE { 
					?idCocktail a :Cocktail .
					?idCocktail :drinkName ?drinkname .
					OPTIONAL { ?idCocktail :drinkThumb ?drinkThumb . } 
					?idCocktail :needQuantity ?idQuantity1 .
					?idCocktail :needQuantity ?idQuantity2 .
					?idCocktail :needQuantity ?idQuantity3 .
					?idQuantity1 :useIngredient :${req.query.ingredient1} .
					?idQuantity2 :useIngredient :${req.query.ingredient2} .
					?idQuantity3 :useIngredient :${req.query.ingredient3} .
				}`;

                var queryIngredient = `SELECT ?ingredient1 ?ingredient2 ?ingredient3 WHERE {
                    :${req.query.ingredient1} :strIngredient ?ingredient1 .
                    :${req.query.ingredient2} :strIngredient ?ingredient2 .
                    :${req.query.ingredient3} :strIngredient ?ingredient3 .
                }`;
            }
        }
    }
    var result = await gdb.execQuery(query);
    var cocktails = result.results.bindings.map((cocktail) => {
        return {
            idDrink: cocktail.idCocktail.value.split("#")[1],
            strDrink: cocktail.drinkname.value,
            drinkThumb: cocktail.drinkThumb ? cocktail.drinkThumb.value : "",
        };
    });

    var result2 = await gdb.execQuery(queryIngredient);
    var ingredients = result2.results.bindings.map((i) => {
        return {
            ingredient1: i.ingredient1 ? i.ingredient1.value : "false",
            ingredient2: i.ingredient2 ? i.ingredient2.value : "false",
            ingredient3: i.ingredient3 ? i.ingredient3.value : "false",
        };
    });

    var final_result = {};
    final_result["cocktails"] = cocktails;
    final_result["ingredient1"] = ingredients[0].ingredient1;
    final_result["ingredient2"] = ingredients[0].ingredient2;
    final_result["ingredient3"] = ingredients[0].ingredient3;

    res.jsonp(final_result);
});

module.exports = router;