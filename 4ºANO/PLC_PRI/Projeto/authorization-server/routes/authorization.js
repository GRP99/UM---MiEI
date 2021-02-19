var express = require('express');
var router = express.Router();
var fs = require('fs');
var jwt = require('jsonwebtoken');
var User = require('../controllers/users');

/* Let's give a token to view, so it can make requests! */
router.post('/autenticarApp', function (req, res, next) {
    if (req.body.key != undefined) {
        switch (req.body.key) {
            case "2cf7a71be6dc9665aba1f32451e887442cb5a9a208b29e1598611236e60b490":
                var privateKey = fs.readFileSync(__dirname + '/../keys/mykey.pem');
                jwt.sign({
                    expiresIn: "1d"
                }, privateKey, {
                    algorithm: 'RS256'
                }, function (err, token) {
                    if (err) {
                        res.status(400).jsonp({error: "Could not log in"});
                    } else {
                        res.status(200).jsonp({token: token});
                    }
                });
                break;
            default: res.status(400).jsonp({error: 'Wrong key!'});
                break;
        }
    } else {
        res.status(401).jsonp({error: 'Key not sent!'})
    }
});

// login
router.post("/login", function (req, res) {
    User.lookUp(req.body._id).then((dados) => {
        const user = dados;
        if (! user) {
            res.status(404).jsonp({error: "User not found!"});
        } else {
            if (req.body.password == user.password) {
                jwt.sign({
                    _id: user._id,
                    level: user.level
                }, "PRI2020", {
                    expiresIn: "1d"
                }, function (err, token) {
                    if (err) {
                        res.status(400).jsonp({error: "It wasn't possible to login!"});
                    } else {
                        res.status(200).jsonp({token: token});
                    }
                });
            } else {
                res.status(401).jsonp({error: "Wrong password!"});
            }
        }
    });
});

// register
router.post("/registar", function (req, res) {
    var user = req.body;
    user.profilepic = 0;
    var d = new Date().toISOString().substr(0, 16);
    user.registrationDate = d;
    user.lastAccessDate = d;
    User.insereUser(user).then(() => {
        res.status(200).jsonp({msg: "User created successfully!"});
    }).catch((err) => {
        res.status(500).jsonp({error: err})
    });
});

// logout
router.post("/logout/:id", function (req, res) {
    var id = req.params.id;
    User.registLastAcess(id).then(() => {
        res.status(200).jsonp({msg: "Last Acess registed!"});
    }).catch((err) => {
        res.status(500).jsonp({error: err})
    });
});

module.exports = router;
