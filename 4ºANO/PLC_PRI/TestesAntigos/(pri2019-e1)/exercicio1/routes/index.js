var express = require('express');
var router = express.Router();

var Tweets = require('../controllers/tweets');

/* GET home page. */
router.get([
    '/', '/myTwitter'
], function (req, res, next) {
    Tweets.listar().then(dados => {
        res.render('index', {tweets: dados});
    }).catch(erro => {
        res.status(err.status || 500);
        res.render('error', {error: erro});
    });
});

router.post('/', function (req, res, next) {
    Tweets.inserir(req.body).then(dados => {
        res.redirect('/');
    }).catch(erro => {
        res.status(err.status || 500);
        res.render('error', {error: erro});
    });
});

router.post('/gostos', function (req, res, next) {
    Tweets.incrementaGostos(req.body.id).then(dados => {
        res.redirect('/');
    }).catch(erro => {
        res.status(err.status || 500);
        res.render('error', {error: erro});
    });
});

module.exports = router;
