var createError = require('http-errors');
var express = require("express");
var router = express.Router();
var axios = require("axios");
var jwt = require("jsonwebtoken");
// var popup = require('popups');
const {request} = require("../app");
var token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiJhZG1pbiIsImxldmVsIjoiYWRtaW4iLCJleHBpcmVzSW4iOiIzbSIsImlhdCI6MTYxMDExODM1OX0.omYqB6hz4vSrRjIBEAi0mg6TNVti0OaqXW6n95JljiM';

// all files library
router.get("/biblioteca", (req, res) => {
    var requestUser = axios.get("http://localhost:3001/users?token=" + token);
    var requestFicheiros = axios.get("http://localhost:3001/files/public?token=" + req.cookies.token);

    axios.all([requestUser, requestFicheiros]).then(axios.spread((...response) => {
        var user = response[0].data;
        var ficheiros = response[1].data;
        res.render("library", {
            lista: ficheiros,
            users: user,
            token: req.cookies.token,
            idUser: req.user._id,
            level: req.user.level
        });
    })).catch(e => {
        res.render('errorAll', {
            text: "it was not possible to present the library with public resources !",
            error: e,
            token: req.cookies.token,
            level: req.user.level
        });
    });
});

// get profile of file
router.get("/:id", (req, res) => {
    var requestUser = axios.get("http://localhost:3001/users?token=" + token);
    var requestFich = axios.get("http://localhost:3001/files/" + req.params.id + "?token=" + req.cookies.token);

    axios.all([requestFich, requestUser]).then(axios.spread((...response) => {
        var fich = response[0].data;
        var users = response[1].data;
        res.render("file", {
            fich: fich,
            idUser: req.user._id,
            users: users,
            token: req.cookies.token,
            level: req.user.level
        });
    })).catch(e => {
        res.render('errorAll', {
            text: "it was not possible to obtain the resource in question !",
            error: e,
            token: req.cookies.token,
            level: req.user.level
        });
    });
});


module.exports = router;
