var express = require("express");
var router = express.Router();
var jwt = require("jsonwebtoken");
var bcrypt = require("bcrypt");
var User = require("../controllers/users");
var axios = require('axios')

/* contrrollers */
var Users = require("../models/users");
var Cocktail = require('../controllers/cocktails');
var Bartender = require('../controllers/bartenders');
var Bar = require("../controllers/bars");
var Location = require("../controllers/locations");

// get all users
router.get("/", function(req, res) {
    if (req.user.level == "admin") {
        User.listUsers().then((data) => {
            res.status(200).jsonp(data);
        }).catch((err) => {
            res.status(500).jsonp(err);
        });
    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

// get one user by username
router.get("/byUsername/:id", function(req, res) {
    id_autor = req.params.id;
    User.lookUpUsername(id_autor).then((data) => {
        user = { _id: data._id, username: data.username, level: data.level };
        res.status(200).jsonp(user);
    }).catch((err) => {
        res.status(401).jsonp(err);
    });
});

// get one user by id
router.get("/:id", function(req, res) {
    id_autor = req.params.id;
    User.lookUp(id_autor).then((data) => {
        user = { _id: data._id, username: data.username, level: data.level };
        res.status(200).jsonp(user);
    }).catch((err) => {
        res.status(401).jsonp(err);
    });
});


// delete user (works)
router.delete("/:id", async function (req, res) {

    if (req.user.level == "admin") {

        /* delete his likes, dislikes, reviews and visits */
        var likes_cocktails = axios.get("http://localhost:7300/dbcocktails/getlikes/" + req.params.id)
        var dislikes_cocktails = axios.get("http://localhost:7300/dbcocktails/getdislikes/" + req.params.id)
        var reviews_cocktails = axios.get("http://localhost:7300/dbcocktails/getreviews/" + req.params.id)
        var likes_bartenders = axios.get("http://localhost:7300/dbbartenders/getlikes/" + req.params.id)
        var dislikes_bartenders = axios.get("http://localhost:7300/dbbartenders/getdislikes/" + req.params.id)
        var reviews_bartenders = axios.get("http://localhost:7300/dbbartenders/getreviews/" + req.params.id)
        var visits_locations = axios.get("http://localhost:7300/dblocations/getvisits/" + req.params.id);
        var visits_bars = axios.get("http://localhost:7300/dbbars/getvisits/" + req.params.id);

        axios.all([likes_cocktails, dislikes_cocktails, reviews_cocktails, likes_bartenders, dislikes_bartenders, reviews_bartenders, visits_bars, visits_locations]).then(axios.spread((...resp) => {
                

                resp[0].data.forEach(element => {
                    Cocktail.removelike(element._id, req.params.id)
                        .catch((err) => {
                            console.log(err)
                            res.status(500).jsonp(err)
                        });
                });

                resp[1].data.forEach(element => {
                    Cocktail.removedislike(element._id, req.params.id)
                        .catch((err) => {
                            console.log(err)
                            res.status(500).jsonp(err)
                        });
                });

                resp[2].data.forEach(element => {
                    element.reviews.forEach(e => {
                        Cocktail.removeReview(element._id, e._id)
                        .catch((err) => {
                            console.log(err)
                            res.status(500).jsonp(err)
                        });
                    });
                });

                resp[3].data.forEach(element => {
                    Bartender.removelike(element._id, req.params.id)
                        .catch((err) => {
                            console.log(err)
                            res.status(500).jsonp(err)
                        });
                });

                resp[4].data.forEach(element => {
                    Bartender.removelike(element._id, req.params.id)
                        .catch((err) => {
                            console.log(err)
                            res.status(500).jsonp(err)
                        });
                });
                
                resp[5].data.forEach(element => {
                    element.reviews.forEach(e => {
                        Bartender.removeReview(element._id, e._id)
                        .catch((err) => {
                            console.log(err)
                            res.status(500).jsonp(err)
                        });
                    });
                });

                resp[6].data.forEach(element => {
                    Bar.removevisit(element._id, req.params.id)
                        .catch((err) => {
                            console.log(err)
                            res.status(500).jsonp(err)
                        });
                });

                resp[7].data.forEach(element => {
                    Location.removevisit(element._id, req.params.id)
                        .catch((err) => {
                            console.log(err)
                            res.status(500).jsonp(err)
                        });
                });

                User.deleteUser(req.params.id).catch((err) => {
                    console.log(err)
                    res.status(500).jsonp(err);
                });
                
                res.status(200).jsonp('Everything OK!'); 
        
        })).catch((err) => {
            console.log(err)
            res.status(500).jsonp(err);
        });

    } else {
        res.status(401).jsonp({ error: "You do not have permissions for such an operation!" });
    }
});

/* login */
router.post("/login", async function(req, res) {
    const user = await Users.findOne({ _id: req.body._id });
    if (user) {
        const validPassword = await bcrypt.compare(req.body.password, user.password);
        if (validPassword) {
            jwt.sign({ _id: user.username, level: user.level }, "PRC2021", { expiresIn: "1d" },
                function(err, token) {
                    if (err) {
                        res.status(400).jsonp({ error: "It wasn't possible to login!" });
                    } else {
                        res.status(200).jsonp({ token: token });
                    }
                }
            );
        } else {
            res.status(400).json({ error: "Invalid Password" });
        }
    } else {
        res.status(401).json({ error: "User does not exist" });
    }
});

/* register */
router.post("/register", async function(req, res) {
    const user = req.body;
    if (!(user._id && user.password)) {
        return res.status(400).send({
            error: "Data not formatted properly"
        });
    }

    /* Encrypt password */
    // generate salt to hash password
    const salt = await bcrypt.genSalt(10);
    // now we set user password to hashed password
    user.password = await bcrypt.hash(user.password, salt);

    user.level = "user";

    User.insertUser(user).then(() => {
        res.status(200).jsonp({ msg: "User created successfully!" });
	}).catch((err) => {
        res.status(500).jsonp({ error: err });
    });
});

/*
    router.post("/logout/:id", function (req, res) {
        var id = req.params.id;
        User.registLastAcess(id).then(() => {
            res.status(200).jsonp({msg: "Last Acess registed!"});
        }).catch((err) => {
            res.status(500).jsonp({error: err})
        });
    }); 
*/

module.exports = router;