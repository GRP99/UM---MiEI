var express = require('express');
var router = express.Router();
var jwt = require('jsonwebtoken');
var axios = require('axios');

// manage room
router.get('/', function(req, res) {
    // req.user.level
    if (req.user.level == 'admin') {
        res.render("admin_manage", {});
    }
});

// manage room - bars
router.get('/bars', function(req, res) {
    var alert = 0
    id_removed = ''
    if (String(req.query.removed) == 'true') {
        alert = 1
        id_removed = req.query.id
    }

    var add = 0
    if (String(req.query.inserted) == 'true') {
        add = 1
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var bars = axios.get("http://localhost:7300/bars");

        axios.all([bars]).then(axios.spread((...resp) => {
            res.render("admin_manage_bars", {
                bars: resp[0].data,
                alert: alert,
                inserted: add,
                id_removed: id_removed
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// get bar by id
router.get('/bars/:id', function(req, res) {
    var req_bar = axios.get("http://localhost:7300/bars/" + req.params.id);
    var cocktails = axios.get("http://localhost:7300/cocktails");

    var alert_cocktail = ''
    if (req.query.cocktail) {
        alert_cocktail = req.query.cocktail
    }

    axios.all([req_bar, cocktails]).then(axios.spread((...resp) => {
        res.render("admin_bar", {
            name: resp[0].data.strBar,
            bar: resp[0].data,
            cocktails: resp[1].data,
            alert_cocktail: alert_cocktail,
            token: req.cookies.token
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

// insert bar 
router.get('/admin_bar_insert', function(req, res) {
    var alert = 0
    if (String(req.query.alert) == "1") {
        alert = 1
    }

    if (req.cookies.token !== null) {
        // authentication
        jwt.verify(req.cookies.token, 'PRC2021', function(e, payload) {
            if (e) {
                res.redirect('http://localhost:7301/homepage');
            } else {
                req.user = {
                    level: payload.level
                }
            }
        })
    } else {
        res.redirect('http://localhost:7301/homepage');
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var cocktails = axios.get("http://localhost:7300/cocktails");

        axios.all([cocktails]).then(axios.spread((...resp) => {
            res.render("admin_bar_insert", {
                cocktails: resp[0].data,
                alert: alert,
                token: req.cookies.token
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// manage room - bartenders
router.get('/bartenders', function(req, res) {
    var alert = 0
    id_removed = ''
    if (String(req.query.removed) == 'true') {
        alert = 1
        id_removed = req.query.id
    }

    var add = 0
    if (String(req.query.inserted) == 'true') {
        add = 1
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var bartenders = axios.get("http://localhost:7300/bartenders");

        axios.all([bartenders]).then(axios.spread((...resp) => {
            res.render("admin_manage_bartenders", {
                bartenders: resp[0].data,
                alert: alert,
                inserted: add,
                id_removed: id_removed
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// get bartender by id
router.get('/bartenders/:id', function(req, res) {
    var req_bartender = axios.get("http://localhost:7300/bartenders/" + req.params.id);
    var cocktails = axios.get("http://localhost:7300/cocktails");
    var bartender_mongo = axios.get("http://localhost:7300/dbbartenders/" + req.params.id)

    var alert_cocktail = ''
    if (req.query.cocktail) {
        alert_cocktail = req.query.cocktail
    }

    var comment = 0
    if (req.query.comment) {
        if (req.query.comment == 'removed') {
            comment = 1
        }
    }

    axios.all([req_bartender, cocktails, bartender_mongo]).then(axios.spread((...resp) => {
        already_cocktails = []
        cocktails = []

        resp[0].data.Cocktails.forEach(element => {
            already_cocktails.push(element.idDrink)
        });

        resp[1].data.forEach(element => {
            if (!(already_cocktails.includes(element.idDrink))) {
                cocktails.push(element)
            }
        });

        /* reviews treatment */
        reviews = resp[2].data.reviews
        var result = new Array(Math.ceil(reviews.length / 4))
            .fill()
            .map(_ => reviews.splice(0, 4))

        first_review = 'none'
        if (result.length != 0) {
            first_review = result[0]
            result.shift();
        }

        res.render("admin_bartender", {
            name: resp[0].data.strBartender,
            bartender: resp[0].data,
            cocktails: cocktails,
            alert_cocktail: alert_cocktail,
            comment: comment,
            reviews: result,
            first_review: first_review,
            token: req.cookies.token
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

// insert bartender 
router.get('/admin_bartender_insert', function(req, res) {
    var alert = 0
    if (String(req.query.alert) == "1") {
        alert = 1
    }

    if (req.cookies.token !== null) {
        // authentication
        jwt.verify(req.cookies.token, 'PRC2021', function(e, payload) {
            if (e) {
                res.redirect('http://localhost:7301/homepage');
            } else {
                req.user = {
                    level: payload.level
                }
            }
        })
    } else {
        res.redirect('http://localhost:7301/homepage');
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var cocktails = axios.get("http://localhost:7300/cocktails");

        axios.all([cocktails]).then(axios.spread((...resp) => {
            res.render("admin_bartender_insert", {
                cocktails: resp[0].data,
                alert: alert,
                token: req.cookies.token
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// manage room - categories
router.get('/categories', function(req, res) {
    var alert = 0
    id_removed = ''
    if (String(req.query.removed) == 'true') {
        alert = 1
        id_removed = req.query.id
    }

    var add = 0
    if (String(req.query.inserted) == 'true') {
        add = 1
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var categories = axios.get("http://localhost:7300/categories");

        axios.all([categories]).then(axios.spread((...resp) => {
            res.render("admin_manage_categories", {
                categories: resp[0].data,
                alert: alert,
                inserted: add,
                id_removed: id_removed
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// get category by id
router.get('/categories/:id', function(req, res) {
    var req_category = axios.get("http://localhost:7300/categories/" + req.params.id);
    var cocktails = axios.get("http://localhost:7300/cocktails");

    var alert_cocktail = ''
    if (req.query.cocktail) {
        alert_cocktail = req.query.cocktail
    }

    axios.all([req_category, cocktails]).then(axios.spread((...resp) => {
        res.render("admin_category", {
            name: resp[0].data.strCategory,
            category: resp[0].data,
            cocktails: resp[1].data,
            alert_cocktail: alert_cocktail,
            token: req.cookies.token
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

// insert category 
router.get('/admin_category_insert', function(req, res) {
    var alert = 0
    if (String(req.query.alert) == "1") {
        alert = 1
    }

    if (req.cookies.token !== null) {
        // authentication
        jwt.verify(req.cookies.token, 'PRC2021', function(e, payload) {
            if (e) {
                res.redirect('http://localhost:7301/homepage');
            } else {
                req.user = {
                    level: payload.level
                }
            }
        })
    } else {
        res.redirect('http://localhost:7301/homepage');
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var cocktails = axios.get("http://localhost:7300/cocktails");

        axios.all([cocktails]).then(axios.spread((...resp) => {
            res.render("admin_category_insert", {
                cocktails: resp[0].data,
                alert: alert,
                token: req.cookies.token
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// manage room - cocktails
router.get('/cocktails', function(req, res) {

    var alert = 0
    id_removed = ''
    if (String(req.query.removed) == 'true') {
        alert = 1
        id_removed = req.query.id
    }

    var add = 0
    if (String(req.query.inserted) == 'true') {
        add = 1
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var cocktails = axios.get("http://localhost:7300/cocktails");
        axios.all([cocktails]).then(axios.spread((...resp) => {
            res.render("admin_manage_cocktails", {
                cocktails: resp[0].data,
                alert: alert,
                inserted: add,
                id_removed: id_removed
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// get cocktail by id
router.get('/cocktails/:id', function(req, res) {

    id = req.params.id
    var cocktail = axios.get("http://localhost:7300/cocktails/" + id);
    var ingredients = axios.get("http://localhost:7300/ingredients/all");
    var cocktail_mongo = axios.get("http://localhost:7300/dbcocktails/" + id);

    var alert_garnish = ''
    var alert_edited = ''
    var alert_ingredient = ''
    var changed_photo = ''
    var comment = 0

    if (req.query.garnish) {
        alert_garnish = req.query.garnish
    }
    if (req.query.ingredient) {
        alert_ingredient = req.query.ingredient
    }
    if (req.query.edited) {
        alert_edited = req.query.edited
    }
    if (req.query.changed_photo) {
        changed_photo = req.query.changed_photo
    }
    if (req.query.comment) {
        if (req.query.comment == 'removed') {
            comment = 1
        }
    }


    axios.all([cocktail, ingredients, cocktail_mongo]).then(axios.spread((...resp) => {
        already_garnish = []
        already_ingredient = []
        ingredients = []
        garnish = []

        resp[0].data.GarnishWith.forEach(element => {
            already_garnish.push(element.idIngredient)
        });

        resp[0].data.Ingredients.forEach(element => {
            already_ingredient.push(element.idIngredient)
        });

        resp[1].data.forEach(element => {
            if (!(already_garnish.includes(element.idIngredient))) {
                garnish.push(element)
            }
            if (!(already_ingredient.includes(element.idIngredient))) {
                ingredients.push(element)
            }
        });

        /* reviews treatment */
        reviews = resp[2].data.reviews
        var result = new Array(Math.ceil(reviews.length / 4))
            .fill()
            .map(_ => reviews.splice(0, 4))

        first_review = 'none'
        if (result.length != 0) {
            first_review = result[0]
            result.shift();
        }


        res.render("admin_cocktail", {
            cocktail: resp[0].data,
            ingredients: ingredients,
            garnish: garnish,
            alert_edited: alert_edited,
            alert_garnish: alert_garnish,
            alert_ingredient: alert_ingredient,
            changed_photo: changed_photo,
            comment: comment,
            reviews: result,
            first_review: first_review,
            token: req.cookies.token
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

// insert cocktail
router.get('/insertCocktail', function(req, res) {
    var alert = 0
    if (req.query.alert == "1") {
        alert = 1
    }
    if (req.cookies.token) {
        // authentication
        jwt.verify(req.cookies.token, 'PRC2021', function(e, payload) {
            if (e) {
                res.redirect('http://localhost:7301/homepage');
            } else {
                req.user = {
                    level: payload.level
                }
            }
        })
    } else {
        res.redirect('http://localhost:7301/homepage');
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var bartenders = axios.get("http://localhost:7300/bartenders");
        var locations = axios.get("http://localhost:7300/locations");
        var glasswares = axios.get("http://localhost:7300/glasswares");
        var ingredients = axios.get("http://localhost:7300/ingredients");
        var bars = axios.get("http://localhost:7300/bars");
        var categories = axios.get("http://localhost:7300/categories");

        axios.all([bartenders, locations, glasswares, ingredients, bars, categories]).then(axios.spread((...resp) => {
            res.render("admin_cocktail_insert", {
                bartenders: resp[0].data,
                locations: resp[1].data,
                glasswares: resp[2].data,
                ingredients: resp[3].data,
                bars: resp[4].data,
                categories: resp[5].data,
                alert: alert,
                token: req.cookies.token
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }

});

// edit cocktail
router.get('/admin_cocktail_edit/:id', function(req, res) {

    if (req.cookies.token) {
        // authentication
        jwt.verify(req.cookies.token, 'PRC2021', function(e, payload) {
            if (e) {
                res.redirect('http://localhost:7301/homepage');
            } else {
                req.user = {
                    level: payload.level
                }
            }
        })
    } else {
        res.redirect('http://localhost:7301/homepage');
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var cocktail = axios.get("http://localhost:7300/cocktails/" + req.params.id)
        var bartenders = axios.get("http://localhost:7300/bartenders");
        var locations = axios.get("http://localhost:7300/locations");
        var glasswares = axios.get("http://localhost:7300/glasswares");
        var ingredients = axios.get("http://localhost:7300/ingredients");
        var bars = axios.get("http://localhost:7300/bars");
        var categories = axios.get("http://localhost:7300/categories");

        axios.all([cocktail, bartenders, locations, glasswares, ingredients, bars, categories]).then(axios.spread((...resp) => {
            res.render("admin_cocktail_edit", {
                cocktail: resp[0].data,
                bartenders: resp[1].data,
                locations: resp[2].data,
                glasswares: resp[3].data,
                ingredients: resp[4].data,
                bars: resp[5].data,
                categories: resp[6].data,
                token: req.cookies.token
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }

});

// manage room - glasswares
router.get('/glasswares', function(req, res) {
    var alert = 0
    id_removed = ''
    if (String(req.query.removed) == 'true') {
        alert = 1
        id_removed = req.query.id
    }

    var add = 0
    if (String(req.query.inserted) == 'true') {
        add = 1
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var glasswares = axios.get("http://localhost:7300/glasswares");

        axios.all([glasswares]).then(axios.spread((...resp) => {
            res.render("admin_manage_glasswares", {
                glasswares: resp[0].data,
                alert: alert,
                inserted: add,
                id_removed: id_removed
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// get glassware by id
router.get('/glasswares/:id', function(req, res) {
    var req_glassware = axios.get("http://localhost:7300/glasswares/" + req.params.id);
    var cocktails = axios.get("http://localhost:7300/cocktails");

    var alert_cocktail = ''
    if (req.query.cocktail) {
        alert_cocktail = req.query.cocktail
    }

    axios.all([req_glassware, cocktails]).then(axios.spread((...resp) => {
        res.render("admin_glassware", {
            name: resp[0].data.strGlass,
            glassware: resp[0].data,
            cocktails: resp[1].data,
            alert_cocktail: alert_cocktail,
            token: req.cookies.token
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

// insert glassware 
router.get('/admin_glassware_insert', function(req, res) {
    var alert = 0
    if (String(req.query.alert) == "1") {
        alert = 1
    }

    if (req.cookies.token !== null) {
        // authentication
        jwt.verify(req.cookies.token, 'PRC2021', function(e, payload) {
            if (e) {
                res.redirect('http://localhost:7301/homepage');
            } else {
                req.user = {
                    level: payload.level
                }
            }
        })
    } else {
        res.redirect('http://localhost:7301/homepage');
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var cocktails = axios.get("http://localhost:7300/cocktails");

        axios.all([cocktails]).then(axios.spread((...resp) => {
            res.render("admin_glassware_insert", {
                cocktails: resp[0].data,
                alert: alert,
                token: req.cookies.token
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// manage room - ingredients
router.get('/ingredients', function(req, res) {
    var alert = 0
    id_removed = ''
    if (String(req.query.removed) == 'true') {
        alert = 1
        id_removed = req.query.id
    }

    var add = 0
    if (String(req.query.inserted) == 'true') {
        add = 1
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var ingredients = axios.get("http://localhost:7300/ingredients");

        axios.all([ingredients]).then(axios.spread((...resp) => {
            res.render("admin_manage_ingredients", {
                ingredients: resp[0].data,
                alert: alert,
                inserted: add,
                id_removed: id_removed
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// insert ingredient 
router.get('/admin_ingredient_insert', function(req, res) {
    var alert = 0
    if (String(req.query.alert) == "1") {
        alert = 1
    }

    if (req.cookies.token !== null) {
        // authentication
        jwt.verify(req.cookies.token, 'PRC2021', function(e, payload) {
            if (e) {
                res.redirect('http://localhost:7301/homepage');
            } else {
                req.user = {
                    level: payload.level
                }
            }
        })
    } else {
        res.redirect('http://localhost:7301/homepage');
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var cocktails = axios.get("http://localhost:7300/cocktails");

        axios.all([cocktails]).then(axios.spread((...resp) => {
            res.render("admin_ingredient_insert", {
                cocktails: resp[0].data,
                alert: alert,
                token: req.cookies.token
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// manage room - locations
router.get('/locations', function(req, res) {
    var alert = 0
    id_removed = ''
    if (String(req.query.removed) == 'true') {
        alert = 1
        id_removed = req.query.id
    }

    var add = 0
    if (String(req.query.inserted) == 'true') {
        add = 1
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var locations = axios.get("http://localhost:7300/locations");

        axios.all([locations]).then(axios.spread((...resp) => {
            res.render("admin_manage_locations", {
                locations: resp[0].data,
                alert: alert,
                inserted: add,
                id_removed: id_removed
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// get location by id
router.get('/locations/:id', function(req, res) {
    var req_location = axios.get("http://localhost:7300/locations/" + req.params.id);
    var cocktails = axios.get("http://localhost:7300/cocktails");

    var alert_cocktail = ''
    if (req.query.cocktail) {
        alert_cocktail = req.query.cocktail
    }

    axios.all([req_location, cocktails]).then(axios.spread((...resp) => {
        res.render("admin_location", {
            name: resp[0].data.strLocation,
            location: resp[0].data,
            cocktails: resp[1].data,
            alert_cocktail: alert_cocktail,
            token: req.cookies.token
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

// insert location 
router.get('/admin_location_insert', function(req, res) {
    var alert = 0
    if (String(req.query.alert) == "1") {
        alert = 1
    }

    if (req.cookies.token !== null) {
        // authentication
        jwt.verify(req.cookies.token, 'PRC2021', function(e, payload) {
            if (e) {
                res.redirect('http://localhost:7301/homepage');
            } else {
                req.user = {
                    level: payload.level
                }
            }
        })
    } else {
        res.redirect('http://localhost:7301/homepage');
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var cocktails = axios.get("http://localhost:7300/cocktails");

        axios.all([cocktails]).then(axios.spread((...resp) => {
            res.render("admin_location_insert", {
                cocktails: resp[0].data,
                alert: alert,
                token: req.cookies.token
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});

// manage room - users
router.get('/users', function(req, res) {

    var alert = 0
    id_removed = ''
    if (String(req.query.removed) == 'true') {
        alert = 1
        id_removed = req.query.id
    }

    // req.user.level
    if (req.user.level == 'admin') {
        var users = axios.get("http://localhost:7300/users?token=" + req.cookies.token);

        axios.all([users]).then(axios.spread((...resp) => {
            res.render("admin_manage_users", {
                users: resp[0].data,
                alert: alert,
                id_removed: id_removed
            });
        })).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Unable to display your request!",
                error: e
            });
        });
    } else {
        res.redirect('http://localhost:7301/homepage');
    }
});


module.exports = router;