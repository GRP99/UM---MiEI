var express = require("express");
var router = express.Router();

var Bar = require("../controllers/bars");

/* get bar from mongo */
router.get("/:id", async function (req, res, next) {
	Bar.lookUp(req.params.id)
		.then((data) => {
			res.status(200).jsonp(data);
		})
		.catch((err) => {
			res.status(500).jsonp({
				error: err,
			});
		});
});

/* get bars from mongo by user */
router.get('/getvisits/:idUser', async function(req, res, next) {
    Bar.getvisits(req.params.idUser).then((data) => {
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
	id_bar = req.params.id;
	var found = 0;

	Bar.lookUp(id_bar).then((result) => {
		result.visits.authors.forEach((f) => {
			if (f == id_user) found = 1;
		});
		if (found == 0) {
			Bar.addvisit(id_bar, id_user)
				.then((data) => {
					res.status(200).jsonp(data);
				})
				.catch((err) => {
					res.status(500).jsonp(err);
				});
		} else {
			Bar.removevisit(id_bar, id_user)
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
