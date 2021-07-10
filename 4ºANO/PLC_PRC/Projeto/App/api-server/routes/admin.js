var express = require("express");
var router = express.Router();
var jwt = require("jsonwebtoken");
var bcrypt = require("bcrypt");
var fs = require("fs");

var password = "$2b$10$COvJaLnuIVLcFGQPS6whm.IlwfbhONJa6U59wryCJnTgo76Kskf5e";

// give a token to view, so it can make requests!
router.post("/authenticate", async function(req, res, next) {
    if (req.query.key) {
        switch (req.query.key) {
            case "2cf7a71be6dc9665aba1f32451e887442cb5a9a208b29e1598611236e60b490":
                var privateKey = fs.readFileSync("./keys/mykey.pem");
                jwt.sign({ expiresIn: "1d" }, privateKey, { algorithm: "RS256" }, function(err, token) {
                    if (err) {
                        res.status(400).jsonp({ error: "Could not log in !" });
                    } else {
                        res.status(200).jsonp({ token: token });
                    }
                });
                break;
            default:
                res.status(400).jsonp({ error: "Wrong key !" });
                break;
        }
    } else {
        res.status(401).jsonp({ error: "Key not sent !" });
    }
});

// access - get token
router.post("/access", async function(req, res) {
    const validPassword = await bcrypt.compare(req.body.password, password);
    if (req.query.access != null) {
        var publicKey = fs.readFileSync("./keys/pubkey.pem");
        jwt.verify(req.query.access, publicKey, { algorithm: "RS256" }, function(e, payload) {
            if (e) {
                res.status(401).jsonp({ error: e });
            } else {
                if (!validPassword) {
                    res.redirect("http://localhost:7301/homepage");
                } else {
                    jwt.sign({ level: "admin", _id: "admin" }, "PRC2021", { expiresIn: "1d" }, function(err, token) {
                        if (err) {
                            res.status(400).jsonp({ error: "It was not possible to login !" });
                        } else {
                            res.status(200).jsonp({ token: token });
                        }
                    });
                }
            }
        });
    } else {
        res.status(401).jsonp({ error: "It was not possible to login ! No token given !" });
    }
});

module.exports = router;