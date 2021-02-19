var express = require('express');
var router = express.Router();

var Arqs = require('../controllers/arquivo');


router.get('/obras', function (req, res, next) {
    if (req.query.by) {
        if (req.query.by == 'instrumento') { // GET /api/obras?by=instrumento => Devolve a lista de obras agrupadas por instrumento, ou seja, devolve uma lista de instrumentos em que a cada instrumento esta associada uma lista de obras
            Arqs.consultByInstrument().then(dados => {
                res.jsonp(dados);
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        } else { // GET /api/obras?by=compositor => Devolve a lista de obras agrupadas por compositor, ou seja, devolve uma lista de compositores em que a cada compositor esta associada uma lista de obras
            Arqs.consultByComposers().then(dados => {
                res.jsonp(dados.sort());
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        }
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

/* GET /api/compositores => Devolve apenas uma lista com os nomes dos compositores, sem repeticoes e ordenada alfabeticamente */
router.get('/compositores', function (req, res) {
    Arqs.listComposers().then(dados => {
        res.jsonp(dados.sort());
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
