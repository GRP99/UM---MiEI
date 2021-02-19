var createError = require('http-errors');
var express = require('express');
var router = express.Router();
var axios = require('axios');
var token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiJhZG1pbiIsImxldmVsIjoiYWRtaW4iLCJleHBpcmVzSW4iOiIzbSIsImlhdCI6MTYxMDExODM1OX0.omYqB6hz4vSrRjIBEAi0mg6TNVti0OaqXW6n95JljiM';

/* Router get homepage */
router.get('/homepage', function (req, res, next) {
    var requestNews = axios.get("http://localhost:3001/news?token=" + req.cookies.token);
    var rUser = axios.get("http://localhost:3001/users?token=" + token);
    var requestTopClassi = axios.get("http://localhost:3001/files/topClass?token=" + req.cookies.token);
    var requestTopFavs = axios.get("http://localhost:3001/files/topFavs?token=" + req.cookies.token);
    var requestTopAut = axios.get("http://localhost:3001/files/topAut?token=" + req.cookies.token);
    axios.all([
        requestNews,
        requestTopClassi,
        requestTopFavs,
        requestTopAut,
        rUser
    ]).then(axios.spread((...response) => {
        news = response[0].data
        classified = response[1].data
        favourites = response[2].data
        authors = response[3].data
        users = response[4].data
        res.render('home', {
            token: req.cookies.token,
            level: req.user.level,
            noticias: news,
            classified: classified,
            favourites: favourites,
            authors: authors,
            users: users
        });
    })).catch(e => {
        res.render('errorAll', {
            text: "unable to display your main page !",
            error: e,
            token: req.cookies.token
        });
    });
});

/* Post a search in nav bar - checks if field is empty on scripts */
router.post('/search', function (req, res) {
    var rUser = axios.get("http://localhost:3001/users?token=" + token);
    var rSearch = axios.get('http://localhost:3001/search/' + req.body.type + '/' + req.body.search + '?token=' + req.cookies.token);

    axios.all([rSearch, rUser]).then(axios.spread((...resposta) => {
        var search;
        if (req.body.type == 'users') {
            authors = 1
        } else {
            authors = 0
        }
        res.render("search", {
            lista: resposta[0].data,
            users: resposta[1].data,
            autor: authors,
            token: req.cookies.token,
            level: req.user.level,
            idUser: req.user._id
        });
    })).catch(e => {
        res.render('errorAll', {
            text: "search again !",
            error: e,
            token: req.cookies.token
        });
    });
});

module.exports = router;
