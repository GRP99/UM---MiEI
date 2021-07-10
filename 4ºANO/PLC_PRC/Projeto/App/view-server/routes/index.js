var createError = require('http-errors');
var express = require('express');
var router = express.Router();
var axios = require('axios');
/* var token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiJhZG1pbiIsImxldmVsIjoiYWRtaW4iLCJleHBpcmVzSW4iOiIzbSIsImlhdCI6MTYxMDExODM1OX0.omYqB6hz4vSrRjIBEAi0mg6TNVti0OaqXW6n95JljiM'; */


// #################### GETS ####################
/* Redirect to homepage */
router.get('/', function(req, res, next) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    res.redirect("/homepage").catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get homepage */
router.get('/homepage', function(req, res, next) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    res.render('homepage', {
        level: req.user.level
    });
});

/* Build Cocktail */
router.get('/buildCocktail', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_ingredients = axios.get("http://localhost:7300/ingredients?build=1");

    axios.all([req_ingredients]).then(axios.spread((...resp) => {
        res.render("findCocktail", {
            cocktails: [],
            ingredients: resp[0].data,
            i1: 'false',
            i2: 'false',
            i3: 'false',
            build: false,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get the cocktails */
router.get('/cocktails', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var cocktails = axios.get("http://localhost:7300/cocktails");

    axios.all([cocktails]).then(axios.spread((...resp) => {
        res.render("cocktails", {
            cocktails: resp[0].data,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get cocktail by id */
router.get('/cocktails/:id', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }

    /* add or remove comment */
    var comment = 0
    if (req.query.comment) {
        if (req.query.comment == 'added') {
            comment = 1
        }
        if (req.query.comment == 'removed') {
            comment = 2
        }
    }

    id = req.params.id
    var cocktail = axios.get("http://localhost:7300/cocktails/" + id);
    var cocktail_mongo = axios.get("http://localhost:7300/dbcocktails/" + id)

    axios.all([cocktail, cocktail_mongo]).then(axios.spread((...resp) => {

        /* reviews treatment */
        reviews = resp[1].data.reviews
        var result = new Array(Math.ceil(reviews.length / 4))
            .fill()
            .map(_ => reviews.splice(0, 4))

        first_review = 'none'
        if (result.length != 0) {
            first_review = result[0]
            result.shift();
        }

        /* testing */
        /*
            first_review = [{
                publication_date: '12-12-2020',
                author: 'sheesh',
                review: 'Great! Very good, indeed. Add water to fulfill your dream!!',
                classification:  4,
            },
            {
                publication_date: '12-12-2020',
                author: 'sheesh',
                review: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
                classification:  3,
            },
            {
                publication_date: '12-12-2020',
                author: 'sheesh',
                review: 'Great! Very good, indeed. Add water to fulfill your dream!!',
                classification:  4,
            },
            {
                publication_date: '12-12-2020',
                author: 'sheesh',
                review: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
                classification:  3,
            }]

            result = [[
                {
                    publication_date: '12-12-2020',
                    author: 'sheesh',
                    review: 'Great! Very good, indeed. Add water to fulfill your dream!!',
                    classification:  4,
                },
                {
                    publication_date: '12-12-2020',
                    author: 'sheesh',
                    review: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
                    classification:  3,
                }
            ]]
        */

        res.render("cocktail", {
            cocktail: resp[0].data,
            mongo: resp[1].data,
            level: req.user.level,
            user_id: req.user._id,
            reviews: result,
            first_review: first_review,
            token: req.cookies.token,
            comment: comment
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get the categories */
router.get('/categories', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_categories = axios.get("http://localhost:7300/categories");

    axios.all([req_categories]).then(axios.spread((...resp) => {
        var info_without_category = resp[0].data.slice(-1).pop()
        res.render("categories", {
            categories: resp[0].data,
            no_category: info_without_category,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get category by id */
router.get('/categories/:id', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_category = axios.get("http://localhost:7300/categories/" + req.params.id);

    axios.all([req_category]).then(axios.spread((...resp) => {
        res.render("individual", {
            name: resp[0].data.strCategory,
            tipo: "Category",
            image: "category",
            individual: resp[0].data,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get the glasswares */
router.get('/glasswares', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_glasswares = axios.get("http://localhost:7300/glasswares");

    axios.all([req_glasswares]).then(axios.spread((...resp) => {
        var no_glassware = resp[0].data.slice(-1).pop()

        res.render("glasswares", {
            glasswares: resp[0].data,
            no_glassware: no_glassware,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get glassware by id */
router.get('/glasswares/:id', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_glassware = axios.get("http://localhost:7300/glasswares/" + req.params.id);

    axios.all([req_glassware]).then(axios.spread((...resp) => {
        res.render("individual", {
            name: resp[0].data.strGlass,
            tipo: "Glassware",
            image: "glassware",
            individual: resp[0].data,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get the bartenders */
router.get('/bartenders', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_bartenders = axios.get("http://localhost:7300/bartenders");

    axios.all([req_bartenders]).then(axios.spread((...resp) => {
        var info_without_bartender = resp[0].data.slice(-1).pop()

        res.render("bartenders", {
            bartenders: resp[0].data,
            no_bartender: info_without_bartender,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get bartender by id */
router.get('/bartenders/:id', function(req, res) {

    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }

    /* add or remove comment */
    var comment = 0
    if (req.query.comment) {
        if (req.query.comment == 'added') {
            comment = 1
        }
        if (req.query.comment == 'removed') {
            comment = 2
        }
    }

    var req_bartender = axios.get("http://localhost:7300/bartenders/" + req.params.id);
    var bartender_mongo = axios.get("http://localhost:7300/dbbartenders/" + req.params.id)

    axios.all([req_bartender, bartender_mongo]).then(axios.spread((...resp) => {
        /* reviews treatment */
        reviews = resp[1].data.reviews
        var result = new Array(Math.ceil(reviews.length / 4))
            .fill()
            .map(_ => reviews.splice(0, 4))

        first_review = 'none'
        if (result.length != 0) {
            first_review = result[0]
            result.shift();
        }

        res.render("bartender", {
            name: resp[0].data.strBartender,
            tipo: "Bartender",
            image: "bartender",
            bartender: resp[0].data,
            mongo: resp[1].data,
            level: req.user.level,
            user_id: req.user._id,
            comment: comment,
            first_review: first_review,
            reviews: result,
            token: req.cookies.token
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get the bars */
router.get('/bars', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_bars = axios.get("http://localhost:7300/bars");

    axios.all([req_bars]).then(axios.spread((...resp) => {

        var no_bar = resp[0].data.slice(-1).pop();
        res.render("bars", {
            bars: resp[0].data,
            no_bar: no_bar,
            level: req.user.level

        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get bar by id */
router.get('/bars/:id', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_bar = axios.get("http://localhost:7300/bars/" + req.params.id);
    var bar_mongo = axios.get("http://localhost:7300/dbbars/" + req.params.id)

    axios.all([req_bar, bar_mongo]).then(axios.spread((...resp) => {
        res.render("bar", {
            name: resp[0].data.strBar,
            tipo: "Bar",
            image: "bar",
            mongo: resp[1].data,
            individual: resp[0].data,
            level: req.user.level,
            user_id: req.user._id,
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get the locations */
router.get('/locations', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_locations = axios.get("http://localhost:7300/locations");

    axios.all([req_locations]).then(axios.spread((...resp) => {

        var no_location = resp[0].data.slice(-1).pop();
        res.render("locations", {
            locations: resp[0].data,
            no_location: no_location,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get location by id */
router.get('/locations/:id', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_location = axios.get("http://localhost:7300/locations/" + req.params.id);
    var location_mongo = axios.get("http://localhost:7300/dblocations/" + req.params.id);

    axios.all([req_location, location_mongo]).then(axios.spread((...resp) => {
        res.render("location", {
            name: resp[0].data.strLocation,
            tipo: "Location",
            image: "location",
            individual: resp[0].data,
            mongo: resp[1].data,
            level: req.user.level,
            user_id: req.user._id,
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get the ingredients */
router.get('/ingredients', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_ingredients = axios.get("http://localhost:7300/ingredients");

    axios.all([req_ingredients]).then(axios.spread((...resp) => {
        res.render("ingredients", {
            ingredients: resp[0].data,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });
});

/* Get ingredient by id */
router.get('/ingredients/:id', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var req_ingredient = axios.get("http://localhost:7300/ingredients/" + req.params.id);

    axios.all([req_ingredient]).then(axios.spread((...resp) => {
        res.render("ingredient", {
            ingredient: resp[0].data,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Unable to display your request!",
            error: e
        });
    });
});

// #################### POSTS ####################
/* Post a search in the navigation bar - check if the field is empty in the scripts */
router.post('/search', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var search = axios.get("http://localhost:7300/search?input=" + req.body.search);
    var cocktails_vazio = false
    var bartenders_vazio = false

    axios.all([search]).then(axios.spread((...resp) => {
        if (resp[0].data.cocktails.length === 0) {
            cocktails_vazio = true
        }
        if (resp[0].data.bartenders.length === 0) {
            bartenders_vazio = true
        }
        res.render("search", {
            cocktails: resp[0].data.cocktails,
            cocktails_vazio: cocktails_vazio,
            bartenders: resp[0].data.bartenders,
            bartenders_vazio: bartenders_vazio,
            input: req.body.search,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "unable to display your request!",
            error: e
        });
    });
});

/* Build Cocktail - After inserting 3 ingredients */
router.post('/buildCocktail', function(req, res) {
    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }
    var i1 = req.body.i1
    var i2 = req.body.i2
    var i3 = req.body.i3


    if (i1 != 'false') {
        if (i2 == 'false' && i3 == 'false') {
            var req_build = axios.get("http://localhost:7300/buildCocktail/1?ingredient1=" + i1);
        } else {
            if (i2 == 'false' && i3 != 'false') {
                var req_build = axios.get("http://localhost:7300/buildCocktail/2?ingredient1=" + i1 + '&ingredient2=' + i3);
            } else {
                if (i2 != 'false' && i3 == 'false') {
                    var req_build = axios.get("http://localhost:7300/buildCocktail/2?ingredient1=" + i1 + '&ingredient2=' + i2);
                } else {
                    if (i2 != 'false' && i3 != 'false') {
                        var req_build = axios.get("http://localhost:7300/buildCocktail/3?ingredient1=" + i1 + '&ingredient2=' + i2 + '&ingredient3=' + i3);
                    }
                }
            }
        }
    } else {
        if (i2 == 'false' && i3 != 'false') {
            var req_build = axios.get("http://localhost:7300/buildCocktail/1?ingredient1=" + i3);
        } else {
            if (i2 != 'false' && i3 == 'false') {
                var req_build = axios.get("http://localhost:7300/buildCocktail/1?ingredient1=" + i2);
            } else {
                if (i2 != 'false' && i3 != 'false') {
                    var req_build = axios.get("http://localhost:7300/buildCocktail/2?ingredient1=" + i2 + '&ingredient2=' + i3);
                }
            }
        }
    }

    var req_ingredients = axios.get("http://localhost:7300/ingredients");

    axios.all([req_build, req_ingredients]).then(axios.spread((...resp) => {

        i1 = resp[0].data.ingredient1
        i2 = resp[0].data.ingredient2
        i3 = resp[0].data.ingredient3

        cocktails = resp[0].data.cocktails

        res.render("findCocktail", {
            cocktails: cocktails,
            ingredients: resp[1].data,
            i1: i1,
            i2: i2,
            i3: i3,
            build: true,
            level: req.user.level
        });
    })).catch(e => {
        res.render('error', {
            text: "Unable to display your request!",
            error: e
        });
    });
});


module.exports = router;