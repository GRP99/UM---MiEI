var express = require("express");
var router = express.Router();

var Cocktail = require('../controllers/cocktails');

/* get cocktail from mongo */
router.get('/:id', async function(req, res, next) {
    Cocktail.lookUp(req.params.id).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp({
            error: err
        })
    });
})

// add or remove userid to favourites of a cocktail
router.put("/like/:id", (req, res) => {
    id_user = req.user._id;
    id_cocktail = req.params.id
    var found = 0;

    Cocktail.lookUp(id_cocktail).then((result) => {
        result.likes.authors.forEach(f => {
            if (f == id_user)
                found = 1;
        })
        if (found == 0) {

            var found_in_dislikes = 0
            result.dislikes.authors.forEach(f => {
                if (f == id_user)
                    found_in_dislikes = 1;
            })

            if (found_in_dislikes == 1) {
                
                Cocktail.addlike(id_cocktail, id_user)
                .catch((err) => {
                    res.status(500).jsonp(err)
                });

                Cocktail.removedislike(id_cocktail, id_user).then((data) => {
                    res.status(200).jsonp(data)
                }).catch((err) => {
                    res.status(500).jsonp(err)
                });
            }
            
            else {
                Cocktail.addlike(id_cocktail, id_user).then((data) => {
                    res.status(200).jsonp(data)
                })
                .catch((err) => {
                    res.status(500).jsonp(err)
                });
            }
        } else {
            Cocktail.removelike(id_cocktail, id_user).then((data) => {
                res.status(200).jsonp(data)
            }).catch((err) => {
                res.status(500).jsonp(err)
            });
        }
    });
});

// add or remove userid to favourites of a cocktail
router.put("/dislike/:id", (req, res) => {
    id_user = req.user._id;
    id_cocktail = req.params.id

    var found = 0;

    Cocktail.lookUp(id_cocktail).then((result) => {
        result.dislikes.authors.forEach(f => {
            if (f == id_user)
                found = 1;
        })

        if (found == 0) {

            var found_in_likes = 0
            result.likes.authors.forEach(f => {
                if (f == id_user)
                    found_in_likes = 1;
            })

            if (found_in_likes == 1) {
                Cocktail.adddislike(id_cocktail, id_user)
                .catch((err) => {
                    res.status(500).jsonp(err)
                });

                Cocktail.removelike(id_cocktail, id_user).then((data) => {
                    res.status(200).jsonp(data)
                }).catch((err) => {
                    res.status(500).jsonp(err)
                });
            }

            else {
                Cocktail.adddislike(id_cocktail, id_user)
                .then((data) => {
                    res.status(200).jsonp(data)})
                .catch((err) => {
                    res.status(500).jsonp(err)
                });
                
            }
        } else {
            Cocktail.removedislike(id_cocktail, id_user).then((data) => {
                res.status(200).jsonp(data)
            }).catch((err) => {
                res.status(500).jsonp(err)
            });
        }
    });
});

/* get likes from mongo by user */
router.get('/getlikes/:idUser', async function(req, res, next) {
    Cocktail.getlikes(req.params.idUser).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp({
            error: err
        })
    });
})

/* get dislikes from mongo by user */
router.get('/getdislikes/:idUser', async function(req, res, next) {
    Cocktail.getdislikes(req.params.idUser).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp({
            error: err
        })
    });
})

/* get reviews from mongo by user */
router.get('/getreviews/:idUser', async function(req, res, next) {
    Cocktail.getreviews(req.params.idUser).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp({
            error: err
        })
    });
})


/* post review */
router.post('/reviews/:id',  function (req, res) {

    var dateobj =  new Date();
    var string_date = dateobj.toISOString();
    
    review = {
        publication_date: new Date().toISOString().substr(0, 16),
        author: req.user._id,
        review: req.body.review,
        classification:  req.body.classification,
    }
    Cocktail.addReview(req.params.id, review).then(data => {
        res.redirect('http://localhost:7301/cocktails/' + req.params.id + '?comment=added')
    }).catch(err => {
        res.status(500).jsonp(err)
    })
});

// delete review
router.delete("/reviews/:idCocktail/:idReview", (req, res) => {
    if (req.user.level == "admin") { 
        Cocktail.removeReview(req.params.idCocktail,req.params.idReview).then((data) => { 
            res.status(200).jsonp(data); 
        }).catch((err) => {
            res.status(500).jsonp(err);
        });
    }
    else {
        if (req.user.level == "user") { 
            Cocktail.isAuthorofReview(req.params.idCocktail, req.params.idReview, req.user._id).then((data) => { 
                if (data){
                    Cocktail.removeReview(req.params.idCocktail, req.params.idReview).then((data) => { 
                        res.status(200).jsonp(data); 
                    }).catch((err) => {
                        res.status(500).jsonp(err);
                    });
                }
                else{
                    res.status(500).jsonp(err);
                }
            }).catch((err) => {
                res.status(500).jsonp(err);
            });
        }
        else {
            res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
        }
    }
});

module.exports = router;