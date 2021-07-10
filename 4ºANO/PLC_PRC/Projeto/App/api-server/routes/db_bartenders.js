var express = require("express");
var router = express.Router();

var Bartender = require("../controllers/bartenders");

/* get bartender from mongo */
router.get("/:id", async function (req, res, next) {
	Bartender.lookUp(req.params.id)
		.then((data) => {
			res.status(200).jsonp(data);
		})
		.catch((err) => {
			res.status(500).jsonp({
				error: err,
			});
		});
});

// add or remove userid to favourites of a file
router.put("/like/:id", (req, res) => {
	id_user = req.user._id;
	id_bartender = req.params.id;
	var found = 0;

	Bartender.lookUp(id_bartender).then((result) => {
		result.likes.authors.forEach((f) => {
			if (f == id_user) found = 1;
		});
		if (found == 0) {

			var found_in_dislikes = 0;
			result.dislikes.authors.forEach((f) => {
				if (f == id_user) found_in_dislikes = 1;
			});

			if (found_in_dislikes == 1) {

				Bartender.addlike(id_bartender, id_user)
				.catch((err) => {
					res.status(500).jsonp(err);
				});

				Bartender.removedislike(id_bartender, id_user)
					.then((data) => {
						res.status(200).jsonp(data);
					})
					.catch((err) => {
						res.status(500).jsonp(err);
					});
			}
			else {
				Bartender.addlike(id_bartender, id_user)
				.then((data) => {
					res.status(200).jsonp(data);
				})
				.catch((err) => {
					res.status(500).jsonp(err);
				});
			}
		} else {
			Bartender.removelike(id_bartender, id_user)
				.then((data) => {
					res.status(200).jsonp(data);
				})
				.catch((err) => {
					res.status(500).jsonp(err);
				});
		}
	});
});

// add or remove userid to favourites of a file
router.put("/dislike/:id", (req, res) => {
	id_user = req.user._id;
	id_bartender = req.params.id;

	var found = 0;

	Bartender.lookUp(id_bartender).then((result) => {
		result.dislikes.authors.forEach((f) => {
			if (f == id_user) found = 1;
		});

		if (found == 0) {


			var found_in_likes = 0;
			result.likes.authors.forEach((f) => {
				if (f == id_user) found_in_likes = 1;
			});

			if (found_in_likes == 1) {

				Bartender.adddislike(id_bartender, id_user)
				.catch((err) => {
					res.status(500).jsonp(err);
				});
				
				Bartender.removelike(id_bartender, id_user)
					.then((data) => {
						res.status(200).jsonp(data);
					})
					.catch((err) => {
						res.status(500).jsonp(err);
					});
			}

			else {

				Bartender.adddislike(id_bartender, id_user)
				.then((data) => {
					res.status(200).jsonp(data);
				})
				.catch((err) => {
					res.status(500).jsonp(err);
				});
			}
		} else {
			Bartender.removedislike(id_bartender, id_user)
				.then((data) => {
					res.status(200).jsonp(data);
				})
				.catch((err) => {
					res.status(500).jsonp(err);
				});
		}
	});
});

/* get likes from mongo by user */
router.get('/getlikes/:idUser', async function(req, res, next) {
    Bartender.getlikes(req.params.idUser).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp({
            error: err
        })
    });
})

/* get dislikes from mongo by user */
router.get('/getdislikes/:idUser', async function(req, res, next) {
    Bartender.getdislikes(req.params.idUser).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp({
            error: err
        })
    });
})


/* get reviews from mongo by user */
router.get('/getreviews/:idUser', async function(req, res, next) {
    Bartender.getreviews(req.params.idUser).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp({
            error: err
        })
    });
})

/* post review */
router.post('/reviews/:id/',  function (req, res) {    
	review = {
        publication_date: new Date().toISOString().substr(0, 16),
        author: req.user._id,
        review: req.body.review,
        classification:  req.body.classification,
    }
    
	Bartender.addReview(req.params.id, review).then(data => {
        res.redirect('http://localhost:7301/bartenders/' + req.params.id + '?comment=added')
    }).catch(err => {
        res.status(500).jsonp(err)
    })
});

router.delete("/reviews/:idBartender/:idReview", (req, res) => {
	if (req.user.level == "admin") {
		Bartender.removeReview(req.params.idBartender, req.params.idReview).then((data) => {
			res.status(200).jsonp(data);
		}).catch((err) => {
			res.status(500).jsonp(err);
		});
	} else {
		if (req.user.level == "user") {
			Bartender.isAuthorofReview(req.params.idBartender, req.params.idReview, req.user._id).then((data) => {
				if (data) {
					Bartender.removeReview(req.params.idBartender, req.params.idReview).then((data) => {
						res.status(200).jsonp(data);
					}).catch((err) => {
						res.status(500).jsonp(err);
					});
				} else {
					res.status(500).jsonp(err);
				}
			}).catch((err) => {
				res.status(500).jsonp(err);
			});
		} else {
			res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
		}
	}
});

module.exports = router;
