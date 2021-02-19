var express = require('express');
var router = express.Router();
var Casamentos = require('../controllers/casamentos');

// GET /api/casamentos/noivos - Devolve uma lista de nomes dos noivos, ordenada alfabeticamente, e o id do respetivo registo.
router.get('/casamentos/noivos', function (req, res) {
    Casamentos.consultarNoivos().then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

router.get('/casamentos', function (req, res, next) {
    if (req.query.nome) { // GET /api/casamentos?nome=X - Devolve apenas uma lista com os casamentos onde o nome X aparece incluído no título
        Casamentos.consultarNome(req.query.nome).then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    } else if (req.query.ano) { // GET /api/casamentos?ano=YYYY - Devolve a lista de casamentos realizados no ano YYYY
        Casamentos.consultarAno(req.query.ano).then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    } else if (req.query.byAno) { // GET /api/casamentos?byAno=true - Devolve a lista de casamentos agrupadas por ano, ou seja, devolve uma lista de anos em que a cada ano está associada uma lista de casamentos
        Casamentos.consultarPorAno(req.query.ano).then(dados => {
            res.jsonp(dados.sort());
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    } else { // GET /api/casamentos - Devolve a lista dos casamentos, com os campos: date, title e ref;
        Casamentos.listar().then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    }
});

// GET /api/casamentos/:id - Devolve a informação completa de um casamento
router.get('/casamentos/:id', function (req, res) {
    Casamentos.consultar(req.params.id).then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});


module.exports = router;
