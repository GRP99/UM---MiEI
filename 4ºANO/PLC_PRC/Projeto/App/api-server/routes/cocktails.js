var express = require("express");
var router = express.Router();
var axios = require("axios");
var gdb = require("../utils/graphdb");

// TRANSLATE
/* var googleTranslate = require('google-translate')(apiKey, { concurrentLimit: 20 }); 
var apiKey = 'AIzaSyCZm0ALkoiY1Gixh6S_cmtYeoiBkLt2oTg';*/
const {
    translate
} = require("bing-translate-api");

var Cocktail = require('../controllers/cocktails');

// #################### GET ####################
// GET ALL COCKTAILS
router.get("/", async function(req, res) {
    var myquery = `SELECT ?drinkid ?drinkname ?alcoholic
        ?idGlass ?strGlass
        ?idBartender ?strBartender 
        ?idCategory ?strCategory
        (COUNT(?idIngredient) AS ?totIngredients) WHERE { 
            ?drinkid a :Cocktail . 
            ?drinkid :drinkName ?drinkname .
            OPTIONAL {  ?drinkid :serveInGlassware ?idGlass . ?idGlass :strGlass ?strGlass . }
            OPTIONAL{ ?drinkid :alcoholic ?alcoholic . }
            OPTIONAL{ ?drinkid :createdByBartender ?idBartender . ?idBartender :strBartender ?strBartender . }
            OPTIONAL { ?drinkid :associatedWithBar ?idBar . ?idBar :strBar ?strBar .}
            OPTIONAL { ?drinkid :hasCategory ?idCategory . ?idCategory :strCategory ?strCategory . }
            OPTIONAL { :${req.params.id} :createdInLocation ?idLocation . ?idLocation :strLocation ?strLocation .}
            OPTIONAL { ?drinkid :needQuantity ?idQuantity . ?idQuantity :useIngredient ?idIngredient . }
    } GROUP BY ?drinkid ?drinkname ?idGlass ?strGlass ?alcoholic ?idBartender ?strBartender ?idCategory ?strCategory ORDER BY ?drinkid`;

    var result = await gdb.execQuery(myquery);
    var dados = result.results.bindings.map((cocktail) => {
        return {
            idDrink: cocktail.drinkid.value.split("#")[1],
            strDrink: cocktail.drinkname.value,
            Bar_Company: {
                idBar: cocktail.idBar ? cocktail.idBar.value.split("#")[1] : "",
                strBar: cocktail.strBar ? cocktail.strBar.value : "",
            },
            Bartender: {
                idBartender: cocktail.idBartender ? cocktail.idBartender.value.split("#")[1] : "",
                strBartender: cocktail.strBartender ? cocktail.strBartender.value : "",
            },
            Category: {
                idCategory: cocktail.idCategory ? cocktail.idCategory.value.split("#")[1] : "",
                strCategory: cocktail.strCategory ? cocktail.strCategory.value : "",
            },
            Glassware: {
                idGlass: cocktail.idGlass ? cocktail.idGlass.value.split("#")[1] : "",
                strGlass: cocktail.strGlass ? cocktail.strGlass.value : "",
            },            
            Location: {
                idLocation: cocktail.idLocation ? cocktail.idLocation.value.split("#")[1] : "",
                strLocation: cocktail.strLocation ? cocktail.strLocation.value : "",
            },
            totIngredients: cocktail.totIngredients ? cocktail.totIngredients.value : "",
            strAlcoholic: cocktail.alcoholic ? cocktail.alcoholic.value : "",
        };
    });
    res.jsonp(dados);
});

// GET COCKTAIL BY ID
router.get("/:id", async function(req, res) {
    var myquery = `SELECT ?drinkName ?notes ?drinkThumb ?alcoholic 
		?preparationEN ?preparationDE ?preparationIT 
		?idCategory ?strCategory 
		?idGlass ?strGlass 
		?idBartender ?strBartender 
		?idBar ?strBar 
		?idLocation ?strLocation 
		(COUNT(?idIngredient) AS ?totIngredients) WHERE {
				:${req.params.id} a :Cocktail ; 
					:drinkName ?drinkName .
				OPTIONAL { :${req.params.id} :notes ?notes . }
				OPTIONAL { :${req.params.id} :drinkThumb ?drinkThumb . }
				OPTIONAL { :${req.params.id} :alcoholic ?alcoholic . }
				OPTIONAL { :${req.params.id} :preparationEN ?preparationEN . }
				OPTIONAL { :${req.params.id} :preparationDE ?preparationDE . }
				OPTIONAL { :${req.params.id} :preparationIT ?preparationIT . }
				OPTIONAL { :${req.params.id} :hasCategory ?idCategory . ?idCategory :strCategory ?strCategory .}
				OPTIONAL { :${req.params.id} :serveInGlassware ?idGlass . ?idGlass :strGlass ?strGlass .}
				OPTIONAL { :${req.params.id} :createdByBartender ?idBartender . ?idBartender :strBartender ?strBartender .}
				OPTIONAL { :${req.params.id} :associatedWithBar ?idBar . ?idBar :strBar ?strBar .}
				OPTIONAL { :${req.params.id} :createdInLocation ?idLocation . ?idLocation :strLocation ?strLocation .}
				OPTIONAL { :${req.params.id} :needQuantity ?idQuantity . ?idQuantity :useIngredient ?idIngredient . }
	} GROUP BY ?drinkName ?notes ?drinkThumb ?alcoholic ?preparationEN ?preparationDE ?preparationIT ?idCategory ?strCategory ?idGlass ?strGlass ?idBartender ?strBartender ?idBar ?strBar ?idLocation ?strLocation  ORDER BY ?drinkname`;
    var result = await gdb.execQuery(myquery);

    myquery = `SELECT ?idQuantity ?quantity ?measure ?idIngredient ?strIngredient WHERE { 
		:${req.params.id} a :Cocktail ;
			:needQuantity ?idQuantity .
		OPTIONAL { ?idQuantity :quantity ?quantity . }
		OPTIONAL { ?idQuantity :measure ?measure . }
		?idQuantity :useIngredient ?idIngredient .
		?idIngredient :strIngredient ?strIngredient .
	} ORDER BY ?idQuantity`;
    var quantityOfIngredient = await gdb.execQuery(myquery);
    var ingredients = quantityOfIngredient.results.bindings.map((i) => {
        return {
            idQuantity: i.idQuantity.value.split("#")[1],
            quantity: i.quantity ? i.quantity.value : "",
            measure: i.measure ? i.measure.value : "",
            idIngredient: i.idIngredient.value.split("#")[1],
            strIngredient: i.strIngredient.value,
        };
    });

    myquery = `SELECT ?idIngredient ?strIngredient WHERE { 
		:${req.params.id} a :Cocktail ;
			:garnishWithIngredient ?idIngredient .
		?idIngredient :strIngredient ?strIngredient .
	} ORDER BY ?idIngredient`;
    var garnish = await gdb.execQuery(myquery);
    var garnishWith = garnish.results.bindings.map((g) => {
        return {
            strIngredient: g.strIngredient.value,
            idIngredient: g.idIngredient.value.split("#")[1],
        };
    });

    /* isHTML - Check if it has html tags */
    var isHTML = RegExp.prototype.test.bind(/(<([^>]+)>)/i);
    var dados = result.results.bindings.map((cocktail) => {
        return {
            idDrink: req.params.id,
            strDrink: cocktail.drinkName.value,
            notes: cocktail.notes && !isHTML(cocktail.notes.value) ? cocktail.notes.value : "",
            drinkThumb: cocktail.drinkThumb ? cocktail.drinkThumb.value : "",
            strAlcoholic: cocktail.alcoholic ? cocktail.alcoholic.value : "",
            preparationEN: cocktail.preparationEN ? cocktail.preparationEN.value : "",
            preparationDE: cocktail.preparationDE ? cocktail.preparationDE.value : "",
            preparationIT: cocktail.preparationIT ? cocktail.preparationIT.value : "",
            Category: {
                idCategory: cocktail.idCategory ? cocktail.idCategory.value.split("#")[1] : "",
                strCategory: cocktail.strCategory ? cocktail.strCategory.value : "",
            },
            Glassware: {
                idGlass: cocktail.idGlass ? cocktail.idGlass.value.split("#")[1] : "",
                strGlass: cocktail.strGlass ? cocktail.strGlass.value : "",
            },
            Bartender: {
                idBartender: cocktail.idBartender ? cocktail.idBartender.value.split("#")[1] : "",
                strBartender: cocktail.strBartender ? cocktail.strBartender.value : "",
            },
            Bar_Company: {
                idBar: cocktail.idBar ? cocktail.idBar.value.split("#")[1] : "",
                strBar: cocktail.strBar ? cocktail.strBar.value : "",
            },
            Location: {
                idLocation: cocktail.idLocation ? cocktail.idLocation.value.split("#")[1] : "",
                strLocation: cocktail.strLocation ? cocktail.strLocation.value : "",
            },
            GarnishWith: garnishWith,
            Ingredients: ingredients,
            totIngredients: cocktail.totIngredients ? cocktail.totIngredients.value : "",
        };
    });
    res.jsonp(dados[0]);
});

// #################### POST ####################
// POST NEW COCKTAIL
router.post("/", async function(req, res) {
    if (req.user.level == "admin") {
        var id = String(req.body.name).replace(/[^A-Za-z0-9-]+/g, "").replace(/-/g, "_").toLowerCase();

        var found = 0
        var cocktails = await axios.get("http://localhost:7300/cocktails");
        await axios.all([cocktails]).then(
            axios.spread((...resp) => {
                var c = resp[0].data;
                c.forEach((element) => {
                    if (element.idDrink.toLowerCase() == id) {
                        found = 1
                    }
                });
            })
        );

        if (found == 1) {
            res.redirect("http://localhost:7301/admin/manage/insertCocktail?alert=1");
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
            },
        };
        /* create cocktail in mongo */
        Cocktail.insertCocktail(c).then(() => {}).catch((err) => {
            res.status(500).jsonp({
                error: err
            });
        });

        var myquery = `INSERT DATA {
                :${id} rdf:type owl:NamedIndividual .
                :${id} a :Cocktail .
                :${id} :drinkName "${req.body.name}" . `;

        if (req.body.preparation) {
            myquery = myquery.concat(`:${id} :preparationEN "${req.body.preparation}" .`);

            /* translate to italic */
            await translate(`${req.body.preparation}`, null, "it", true)
                .then((res) => {
                    prepItalic = String(res.translation);
                    myquery = myquery.concat(`:${id} :preparationIT "${prepItalic}" .`);
                })
                .catch((err) => {
                    console.error(err);
                });

            /* translate to deutsch */
            await translate(`${req.body.preparation}`, null, "de", true)
                .then((res) => {
                    prepDeutsh = String(res.translation);
                    myquery = myquery.concat(`:${id} :preparationDE "${prepDeutsh}" .`);
                })
                .catch((err) => {
                    console.error(err);
                });
        }
        if (req.body.notes) {
            myquery = myquery.concat(`:${id} :notes "${req.body.notes}" .`);
        }
        if (req.body.alcoholic) {
            myquery = myquery.concat(`:${id} :alcoholic "${req.body.alcoholic}" .`);
        }
        if (req.body.category != "false" && req.body.category) {
            myquery = myquery.concat(
                `:${id} :hasCategory :${req.body.category} . :${req.body.category} :haveCocktail :${id} .`
            );
        }
        if (req.body.glassware != "false" && req.body.glassware) {
            myquery = myquery.concat(
                `:${id} :serveInGlassware :${req.body.glassware} . :${req.body.glassware} :serveCocktail :${id} .`
            );
        }
        if (req.body.bartender != "false" && req.body.bartender) {
            myquery = myquery.concat(
                `:${id} :createdByBartender :${req.body.bartender} . :${req.body.bartender} :createCocktail :${id} .`
            );
        }
        if (req.body.bar != "false" && req.body.bar) {
            myquery = myquery.concat(
                `:${id} :associatedWithBar :${req.body.bar} . :${req.body.bar} :isAssociatedToCocktail :${id} .`
            );
        }
        if (req.body.location != "false" && req.body.location) {
            myquery = myquery.concat(
                `:${id} :createdInLocation :${req.body.location} . :${req.body.location} :originOfCocktail :${id} .`
            );
        }
        myquery = myquery.concat("\n}\n");
        var result = await gdb.execTransaction(myquery);
        res.redirect("http://localhost:7301/admin/manage/cocktails?inserted=true");
    
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// UPDATE COCKTAIL
router.post("/edit", async function(req, res) {
    if (req.user.level == "admin") {
        var query_delete = ` DELETE { 
            :${req.body.idDrink} :alcoholic ?alcoholic .
            ?idGlass :serveCocktail :${req.body.idDrink} .        
            :${req.body.idDrink} :serveInGlassware ?idGlass .
            :${req.body.idDrink} :hasCategory ?idCategory .
            ?idCategory :haveCocktail :${req.body.idDrink} .
            ?idBartender :createCocktail :${req.body.idDrink} .
            :${req.body.idDrink} :createdByBartender ?idBartender .
            :${req.body.idDrink} :createdInLocation ?idLocation .
            ?idLocation :originOfCocktail :${req.body.idDrink} .
            :${req.body.idDrink} :associatedWithBar ?idBar .
            ?idBar :isAssociatedToCocktail :${req.body.idDrink} .
            :${req.body.idDrink} :notes ?notes .
            :${req.body.idDrink} :preparationEN ?preparationEN .
        } WHERE {
            OPTIONAL { :${req.body.idDrink} :alcoholic ?alcoholic . }
            OPTIONAL { ?idGlass :serveCocktail :${req.body.idDrink} . }
            OPTIONAL { :${req.body.idDrink} :serveInGlassware ?idGlass . }
            OPTIONAL { :${req.body.idDrink} :hasCategory ?idCategory . }
            OPTIONAL { ?idCategory :haveCocktail :${req.body.idDrink} . }
            OPTIONAL { ?idBartender :createCocktail :${req.body.idDrink} . }
            OPTIONAL { :${req.body.idDrink} :createdByBartender ?idBartender . }
            OPTIONAL { :${req.body.idDrink} :createdInLocation ?idLocation . }
            OPTIONAL { ?idLocation :originOfCocktail :${req.body.idDrink} . } 
            OPTIONAL { :${req.body.idDrink} :associatedWithBar ?idBar . } 
            OPTIONAL { ?idBar :isAssociatedToCocktail :${req.body.idDrink} . }   
            OPTIONAL { :${req.body.idDrink} :notes ?notes . }
            OPTIONAL { :${req.body.idDrink} :preparationEN ?preparationEN .}   
        }
        `;
        var result_delete = await gdb.execTransaction(query_delete);
        var query_insert = `INSERT DATA { \n`;
        if (req.body.alcoholic) {
            query_insert = query_insert.concat(`:${req.body.idDrink} :alcoholic "${req.body.alcoholic}" .`);
        }
        if (req.body.glassware != "false" && req.body.glassware) {
            query_insert = query_insert.concat(`
                :${req.body.glassware} :serveCocktail :${req.body.idDrink} .
                :${req.body.idDrink} :serveInGlassware :${req.body.glassware} .                 
            `);
        }
        if (req.body.category != "false" && req.body.category) {
            query_insert = query_insert.concat(`
                :${req.body.idDrink} :hasCategory :${req.body.category} . 
                :${req.body.category} :haveCocktail :${req.body.idDrink} .
            `);
        }
        if (req.body.bartender != "false" && req.body.bartender) {
            query_insert = query_insert.concat(`
                :${req.body.bartender} :createCocktail :${req.body.idDrink} .    
                :${req.body.idDrink} :createdByBartender :${req.body.bartender} .                 
            `);
        }
        if (req.body.location != "false" && req.body.location) {
            query_insert = query_insert.concat(`
                :${req.body.idDrink} :createdInLocation :${req.body.location} . 
                :${req.body.location} :originOfCocktail :${req.body.idDrink} .`);
        }
        if (req.body.bar != "false" && req.body.bar) {
            query_insert = query_insert.concat(`
                :${req.body.idDrink} :associatedWithBar :${req.body.bar} . 
                :${req.body.bar} :isAssociatedToCocktail :${req.body.idDrink} .`);
        }
        if (req.body.notes) {
            query_insert = query_insert.concat(`:${req.body.idDrink} :notes "${req.body.notes}" .`);
        }
        if (req.body.preparation) {
            query_insert = query_insert.concat(`:${req.body.idDrink} :preparationEN "${req.body.preparation}" .`);
        }
        query_insert = query_insert.concat("\n}\n");
        var result_insert = await gdb.execTransaction(query_insert);
        res.redirect("http://localhost:7301/admin/manage/cocktails/" + req.body.idDrink + "?edited=true");
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// CHANGE DRINKTHUMB OF COCKTAIL
router.post("/:id/changedrinkThumb", async function(req, res) {
    if (req.user.level == "admin") {
        var query_delete = ` DELETE {
            :${req.params.id} :drinkThumb ?drinkThumb .
        } WHERE {
            :${req.params.id} rdf:type owl:NamedIndividual .
            :${req.params.id} a :Cocktail .
            OPTIONAL { :${req.params.id} :drinkThumb ?drinkThumb  . }
        }
    `;
        var result_delete = await gdb.execTransaction(query_delete);
        var query_insert = `INSERT DATA { :${req.params.id} :drinkThumb "${req.body.newdrinkThumb}" . }`;
        var result_insert = await gdb.execTransaction(query_insert);
        res.redirect("http://localhost:7301/admin/manage/cocktails/" + req.params.id + "?changed_photo=true");
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// POST A NEW INGREDIENT TO A COCKTAIL
router.post("/:id/addIngredient", async function(req, res) {
    if (req.user.level == "admin") {
        var idQuantityOfIngredient = req.body.ingredient;
        if (req.body.measure) {
            idQuantityOfIngredient = req.body.measure.concat(idQuantityOfIngredient);
        }
        if (req.body.quantity) {
            idQuantityOfIngredient = req.body.quantity.concat(idQuantityOfIngredient);
        }
        var query_ask = `ASK { :${idQuantityOfIngredient} a :QuantityOfIngredient . }`;
        var result_ask = await gdb.execQuery(query_ask);
        if (result_ask.boolean) {
            var query_update = `INSERT DATA {
                :${req.params.id} :needQuantity :${idQuantityOfIngredient} .
                :${idQuantityOfIngredient} :isUsedCocktail :${req.params.id} . 
            }`;
            var result_update = await gdb.execTransaction(query_update);
        } else {
            var query_insert = ` INSERT DATA {
                :${idQuantityOfIngredient} rdf:type owl:NamedIndividual .
                :${idQuantityOfIngredient} a :QuantityOfIngredient .
                :${idQuantityOfIngredient} :useIngredient :${req.body.ingredient} .
                :${req.body.ingredient} :haveQuantity :${idQuantityOfIngredient} .
            `;
            if (req.body.measure) {
                query_insert = query_insert.concat(`:${idQuantityOfIngredient} :measure "${req.body.measure}" .`);
            }
            if (req.body.quantity) {
                query_insert = query_insert.concat(`:${idQuantityOfIngredient} :quantity "${req.body.quantity}" .`);
            }
            query_insert = query_insert.concat(` :${req.params.id} :needQuantity :${idQuantityOfIngredient} . :${idQuantityOfIngredient} :isUsedCocktail :${req.params.id} . }`);
            var result_insert = await gdb.execTransaction(query_insert);
        }
        res.redirect("http://localhost:7301/admin/manage/cocktails/" + req.params.id + "?ingredient=1");
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// POST A NEW GARNISH TO A COCKTAIL
router.post("/:id/addGarnish", async function(req, res) {
    if (req.user.level == "admin") {
        var query = `INSERT DATA { 
            :${req.body.ingredient} :asGarnishCocktail :${req.params.id} .
            :${req.params.id} :garnishWithIngredient :${req.body.ingredient} .         
        } `;
        var result = await gdb.execTransaction(query);
        res.redirect("http://localhost:7301/admin/manage/cocktails/" + req.params.id + "?garnish=1");
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// #################### DELETE ####################
// DELETE A COCKTAIL
router.delete("/:id", async function(req, res) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.id} rdf:type owl:NamedIndividual .
            :${req.params.id} a :Cocktail .
            :${req.params.id} :drinkName ?drinkName .
            :${req.params.id} :preparationIT ?preparationIT .
            :${req.params.id} :preparationEN ?preparationEN .
            :${req.params.id} :preparationDE ?preparationDE .
            :${req.params.id} :drinkThumb ?drinkThumb .
            :${req.params.id} :alcoholic ?alcoholic .
            :${req.params.id} :notes ?notes .
            ?idIngredient :asGarnishCocktail :${req.params.id} .
            :${req.params.id} :associatedWithBar ?idBar .            
            ?idBartender :createCocktail :${req.params.id} .
            :${req.params.id} :createdByBartender ?idBartender .
            :${req.params.id} :createdInLocation ?idLocation . 
            :${req.params.id} :garnishWithIngredient ?idIngredient .
            :${req.params.id} :hasCategory ?idCategory .
            ?idCategory :haveCocktail :${req.params.id} .
            ?idBar :isAssociatedToCocktail :${req.params.id} .
            ?needQuantity :isUsedCocktail :${req.params.id} .
            :${req.params.id} :needQuantity ?needQuantity .
            ?idLocation :originOfCocktail :${req.params.id} . 
            ?idGlass :serveCocktail :${req.params.id} .        
            :${req.params.id} :serveInGlassware ?idGlass .                   
        } WHERE {
            :${req.params.id} a :Cocktail .
            :${req.params.id} :drinkName ?drinkName .
            OPTIONAL { :${req.params.id} rdf:type owl:NamedIndividual . }
            OPTIONAL { :${req.params.id} :preparationIT ?preparationIT . }
            OPTIONAL { :${req.params.id} :preparationEN ?preparationEN . }
            OPTIONAL { :${req.params.id} :preparationDE ?preparationDE . }
            OPTIONAL { :${req.params.id} :drinkThumb ?drinkThumb . }
            OPTIONAL { :${req.params.id} :alcoholic ?alcoholic . }
            OPTIONAL { :${req.params.id} :notes ?notes . }
            OPTIONAL { ?idIngredient :asGarnishCocktail :${req.params.id} . }
            OPTIONAL { :${req.params.id} :associatedWithBar ?idBar . }
            OPTIONAL { ?idBartender :createCocktail :${req.params.id} . }
            OPTIONAL { :${req.params.id} :createdByBartender ?idBartender . }
            OPTIONAL { :${req.params.id} :createdInLocation ?idLocation . }
            OPTIONAL { :${req.params.id} :garnishWithIngredient ?idIngredient . }
            OPTIONAL { :${req.params.id} :hasCategory ?idCategory . }
            OPTIONAL { ?idCategory :haveCocktail :${req.params.id} . }
            OPTIONAL { ?idBar :isAssociatedToCocktail :${req.params.id} . }
            OPTIONAL { ?needQuantity :isUsedCocktail :${req.params.id} . }
            OPTIONAL { :${req.params.id} :needQuantity ?needQuantity . }
            OPTIONAL { ?idLocation :originOfCocktail :${req.params.id} . }                                    
            OPTIONAL { ?idGlass :serveCocktail :${req.params.id} . }
            OPTIONAL { :${req.params.id} :serveInGlassware ?idGlass . }
        }`;

        /* delete cocktail from mongo */
        Cocktail.deleteCocktail(req.params.id).then(() => {}).catch((err) => {
            res.status(500).jsonp({ error: err });
        });
        var result = await gdb.execTransaction(query);
        res.status(200).jsonp("Triplos apagados ... " + result);
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// DELETE A INGREDIENT
router.delete("/:id/ingredient/:idIngredient", async function(req, res) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.id} :needQuantity ?idQuantity .
            ?idQuantity :isUsedCocktail :${req.params.id} .        
        } WHERE {
            :${req.params.id} a :Cocktail .
            :${req.params.id} :needQuantity ?idQuantity .
            ?idQuantity :isUsedCocktail :${req.params.id} .
			?idQuantity :useIngredient :${req.params.idIngredient} .
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).jsonp(result);
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

// DELETE A GARNISH
router.delete("/:id/garnish/:idIngredient", async function(req, res) {
    if (req.user.level == "admin") {
        var query = ` DELETE {
            :${req.params.id} :garnishWithIngredient :${req.params.idIngredient} . 
            :${req.params.idIngredient} :asGarnishCocktail :${req.params.id} .        
        } WHERE {
            OPTIONAL { 
                :${req.params.idDrink} :garnishWithIngredient :${req.params.idIngredient} .  
                :${req.params.idIngredient} :asGarnishCocktail :${req.params.idDrink} .
            }
        }`;
        var result = await gdb.execTransaction(query);
        res.status(200).jsonp(result);
    } else {
        res.status(401).jsonp({
            error: "You do not have permissions for such an operation!"
        });
    }
});

module.exports = router;