var express = require('express');
var router = express.Router();

var Movies = require('../controllers/movies');


router.get('/filmes', function (req, res, next) {
    if (req.query.by) {
        if (req.query.by == 'ator') { // GET /api/filmes?by=ator => Devolve a lista de obras agrupadas por ator, ou seja, devolve uma lista de atores em que a cada ator esta associada uma lista de filmes
            Movies.consultarPorAtor().then(dados => {
                res.jsonp(dados);
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        } else { // GET /api/filmes?by=genero => Devolve a lista de filmes agrupadas por genero, ou seja, devolve uma lista de generos em que a cada genero esta associada uma lista de filmes
            Movies.consultarPorGenero().then(dados => {
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

// GET /api/atores => Devolve apenas uma lista com os nomes dos atores, sem repeticoes e ordenada alfabeticamente
router.get('/atores', function (req, res, next) {
    Movies.listarAtores().then(dados => {
        res.jsonp(dados.sort());
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

// GET /api/filmesQuantAtor => Devolve uma lista de filmes com os seguintes campos: id, titulo, numero de atores participantes
router.get('/filmesQuantAtor', function (req, res, next) {
    Movies.quantAtor().then(dados => {
        res.jsonp(dados.sort());
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

module.exports = router;
