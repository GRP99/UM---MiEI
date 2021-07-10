var express = require("express");
var router = express.Router();

var Location = require("../controllers/locations");

/* get location from mongo */
router.get("/:id", async function (req, res, next) {
	Location.lookUp(req.params.id)
		.then((data) => {
			res.status(200).jsonp(data);
		})
		.catch((err) => {
			res.status(500).jsonp({
				error: err,
			});
		});
});

/* get locations from mongo by user */
router.get('/getvisits/:idUser', async function(req, res, next) {
    Location.getvisits(req.params.idUser).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp({
            error: err
        })
    });
})


// add visit
router.put("/visit/:id", (req, res) => {
	id_user = req.user._id;
	id_location = req.params.id;
	var found = 0;

	Location.lookUp(id_location).then((result) => {
		result.visits.authors.forEach((f) => {
			if (f == id_user) found = 1;
		});
		if (found == 0) {
			Location.addvisit(id_location, id_user)
				.then((data) => {
					res.status(200).jsonp(data);
				})
				.catch((err) => {
					res.status(500).jsonp(err);
				});
		} else {
			Location.removevisit(id_location, id_user)
				.then((data) => {
					res.status(200).jsonp(data);
				})
				.catch((err) => {
					res.status(500).jsonp(err);
				});
		}
	});
});

module.exports = router;
