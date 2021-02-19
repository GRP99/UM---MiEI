var express = require('express');
var router = express.Router();

var Movies = require('../controllers/movies');


router.get('/filmes', function (req, res, next) {
    if (req.query.genero) {
        if (req.query.genero == 'Action') { // GET /api/filmes?genro=Action - Devolve a lista de filmes cujo campo "genres" contem o valor "Action"
            Movies.consultarGenero(req.query.genro).then(dados => {
                res.jsonp(dados);
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        }
    } else if (req.query.categoria && req.query.data) { // GET /api/filmes?categoria=Action&data=AAAA - Devolve a lista de premios cujo campo "genres" contenha o valor "Action" e o campo "year" com um valor superior a "AAAA"
        if (req.query.categoria == 'Action') {
            Movies.consultarGeneroData(req.query.categoria, req.query.data).then(dados => {
                res.jsonp(dados);
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        }
    } else { // GET /api/filmes => Devolve a lista de filmes apenas com os campos "title", e "year"
        Movies.listar().then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    }
});

// GET /api/filmes/:id => Devolve a informacao completa de um filme
router.get('/filmes/:id', function (req, res, next) {
    Movies.consultar(req.params.id).then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

// GET /api/generos - Devolve a lista de generos em que os filmes se encontram classificados, sem repeticoes
router.get('/generos', function (req, res, next) {
    Movies.listarGeneros().then(dados => {
        res.jsonp(dados.sort());
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

// GET /api/atores - Devolve uma lista ordenada alfabeticamente por nome dos atores
router.get('/atores', function (req, res, next) {
    Movies.listarAtores().then(dados => {
        res.jsonp(dados.sort());
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

module.exports = router;
