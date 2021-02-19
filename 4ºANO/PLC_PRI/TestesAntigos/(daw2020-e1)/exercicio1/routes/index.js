var express = require('express');
var router = express.Router();

var Arqs = require('../controllers/arqs');


router.get('/obras', function (req, res, next) {
    if(req.query.instrumento){ // GET /api/obras?instrumento=III => Devolve a lista de obras que tenham uma ou mais partituras para o instrumento III
        Arqs.consultInstrObr(req.query.instrumento).then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    }
    else if (req.query.compositor) { // GET /api/obras?compositor=XXX => Devolve a lista de obras que tenham o campo "compositor" com o valor "XXX"
        Arqs.consultCompObr(req.query.compositor).then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    } else { // GET /api/obras => Devolve a lista de obras musicais ("id", "titulo", "tipo" e "compositor")
        Arqs.list().then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    }
});

/* GET /api/obras/:id => Devolve a informacao completa de uma obra */
router.get('/obras/:id', function (req, res) {
    Arqs.consult(req.params.id).then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

/* GET /api/tipos => Devolve a lista de tipos, sem repeticos; */
router.get('/tipos', function (req, res) {
    Arqs.listTypes().then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

// GET /api/obrasQuant => Devolve uma lista de obras musicais com os seguintes campos: id, titulo, partituras (numero de partituras disponiveis);
router.get('/obrasQuant', function (req, res) {
    Arqs.listQuant().then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

module.exports = router;
