var express = require('express');
var router = express.Router();
var Pubs = require('../controllers/pubs');

router.get('/pubs', function (req, res, next) {
    if (req.query.type) {
        if (req.query.year) { // GET /api/pubs?type=YYY&year=AAAA - Devolve a lista de publicacoes que tenham o campo "type" com o valor "YYY" e o campo "year" com um valor superior a "AAAA"
            Pubs.consultByTypeAndYear(req.query.type, req.query.year).then(dados => {
                res.jsonp(dados);
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        } else {
            Pubs.consultByType(req.query.type).then(dados => { // GET /api/pubs?type=YYY => Devolve a lista de publicacoes que tenham o campo "type" com o valor "YYY"
                res.jsonp(dados);
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        }
    } else if (req.query.author) { // GET /api/pubs?autor=AAA => Devolve uma lista com as publicacoes do autor
        Pubs.consultAuthorPubs(req.query.author).then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    } else { // GET /api/pubs => Devolve a lista de publicacoes ("id", "title", "year" e "type")
        Pubs.list().then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    }
});

/* GET /api/pubs/:id => Devolve a informacao completa de uma publicacao */
router.get('/pubs/:id', function (req, res) {
    Pubs.consult(req.params.id).then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

/* GET /api/types => Devolve a lista de tipos, sem repeticoes */
router.get('/types', function (req, res) {
    Pubs.listTypes().then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

/* GET /api/authors => Devolve uma lista ordenada alfabeticamente com os nome dos autores */
router.get('/authors', function (req, res) {
    Pubs.listAuthors().then(dados => {
        dados.sort();
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

/* EXTRA => Devolve o numero de autores de cada publicacao */
router.get('/pubsQtAuthors', function (req, res) {
    Pubs.qtAuthors().then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

module.exports = router;
