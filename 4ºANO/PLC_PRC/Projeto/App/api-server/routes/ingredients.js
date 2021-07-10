var express = require("express");
var router = express.Router();
var axios = require("axios");
var gdb = require("../utils/graphdb");

// #################### GET ####################
// GET ALL INGREDIENTS THAT HAVE COCKTAILS ASSOCIATED
router.get("/", async function(req, res, next) {
    if (req.query.build) {
        var myquery = `SELECT ?idIngredient ?strIngredient (COUNT(?idCocktail) AS ?total) WHERE { 
			?idCocktail a :Cocktail .
			?idCocktail :needQuantity ?idQuantity .
			?idQuantity :useIngredient ?idIngredient .
			?idIngredient :strIngredient ?strIngredient .
		} GROUP BY ?idIngredient ?strIngredient ORDER BY ?strIngredient`;
    } else {
        var myquery = `SELECT ?idIngredient ?strIngredient (COUNT(?idCocktail) AS ?total) WHERE { 
			?idCocktail a :Cocktail .
			?idCocktail :needQuantity ?idQuantity .
			?idQuantity :useIngredient ?idIngredient .
			?idIngredient :strIngredient ?strIngredient .
		} GROUP BY ?idIngredient ?strIngredient ORDER BY DESC (?total)`;
    }
    var result = await gdb.execQuery(myquery);
    var dados = result.results.bindings.map((ingredient) => {
        return {
            idIngredient: ingredient.idIngredient.value.split("#")[1],
            strIngredient: ingredient.strIngredient.value,
            totalOfCocktails: ingredient.total.value,
        };
    });
    res.jsonp(dados);
});

// GET ALL INGREDIENTS
router.get("/all", async function(req, res, next) {
    var myquery = `SELECT ?idIngredient ?strIngredient WHERE {
        ?idIngredient a :Ingredient ;
            :strIngredient  ?strIngredient .    
    } ORDER BY ?strIngredient `;    
    var result = await gdb.execQuery(myquery);
    var dados = result.results.bindings.map((ingredient) => {
        return {
            idIngredient: ingredient.idIngredient.value.split("#")[1],
            strIngredient: ingredient.strIngredient.value
        };
    });    
    res.jsonp(dados);
});

// GET INGREDIENT BY ID
router.get("/:id", async function(req, res, next) {
    var query_garnish = `SELECT ?idCocktail ?drinkname ?alcoholic WHERE {
		:${req.params.id} a :Ingredient .
		:${req.params.id} :asGarnishCocktail ?idCocktail .
		?idCocktail a :Cocktail .
		?idCocktail :drinkName ?drinkname .
		OPTIONAL { ?idCocktail :alcoholic ?alcoholic . }
	}`;
    var result = await gdb.execQuery(query_garnish);
    var garnish = result.results.bindings.map((garnish) => {
        return {
            idDrink: garnish.idCocktail.value.split("#")[1],
            strDrink: garnish.drinkname.value,
            strAlcoholic: garnish.alcoholic ? garnish.alcoholic.value : "",
        };
    });

    var query_qtd = `SELECT ?idQuaOfIng ?quantity ?measure WHERE {
		:${req.params.id} a :Ingredient .
		:${req.params.id} :haveQuantity ?idQuaOfIng .
		?idQuaOfIng a :QuantityOfIngredient .
		OPTIONAL { ?idQuaOfIng :quantity ?quantity . }
		OPTIONAL { ?idQuaOfIng :measure ?measure . }
	}`;
    var result = await gdb.execQuery(query_qtd);
    var quaOfIng = result.results.bindings.map((quaOfIng) => {
        return {
            idQuantity: quaOfIng.idQuaOfIng.value.split("#")[1],
            quantity: quaOfIng.quantity ? quaOfIng.quantity.value : "",
            measure: quaOfIng.measure ? quaOfIng.measure.value : "",
        };
    });

    var query_cocktails = `SELECT ?drinkid ?drinkname ?alcoholic WHERE {
		:${req.params.id} a :Ingredient .
		:${req.params.id} :haveQuantity ?idQuaOfIng .
		?idQuaOfIng :isUsedCocktail ?drinkid .
		?drinkid a :Cocktail . 
		?drinkid :drinkName ?drinkname .
		OPTIONAL { ?drinkid :alcoholic ?alcoholic . }
	} `;
    var result = await gdb.execQuery(query_cocktails);
    var cocktails = result.results.bindings.map((cocktail) => {
        return {
            idDrink: cocktail.drinkid.value.split("#")[1],
            strDrink: cocktail.drinkname.value,
            strAlcoholic: cocktail.alcoholic ? cocktail.alcoholic.value : "",
        };
    });

    var query_strIngredient = `SELECT ?strIngredient WHERE {
		:${req.params.id} a :Ingredient ;
			:strIngredient ?strIngredient .
	}`;
    var result = await gdb.execQuery(query_strIngredient);
    var ingredient = result.results.bindings.map((ingredient) => {
        return {
            idIngredient: req.params.id,
            strIngredient: ingredient.strIngredient.value,
            Garnish: garnish,
            QuantityOfIngredient: quaOfIng,
            Cocktails: cocktails,
        };
    });
    res.jsonp(ingredient[0]);
});

// #################### POST ###################
// POST A NEW INGREDIENT
router.post("/", async function(req, res, next) {
    if (req.user.level == "admin") {
        var id = String(req.body.name).replace(/[^A-Za-z0-9-]+/g, "").replace(/-/g, "_").toLowerCase();
        var ingredients = await axios.get("http://localhost:7300/ingredients/all");
        await axios.all([ingredients]).then(
            axios.spread((...resp) => {
                var c = resp[0].data;
                c.forEach((element) => {
                    if (element.idIngredient.toLowerCase() == id) {
                        res.redirect("http://localhost:7301/admin/manage/admin_ingredient_insert?alert=1");
                    }
                });
            })
        );

        var query = `INSERT DATA {
            :${id} rdf:type owl:NamedIndividual .
            :${id} a :Ingredient .
            :${id} :strIngredient "${req.body.name}" .
        }`;
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/ingredients?inserted=true");
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

module.exports = router;