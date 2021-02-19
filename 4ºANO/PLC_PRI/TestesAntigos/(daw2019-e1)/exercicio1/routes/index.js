var express = require('express');
var router = express.Router();
var Compositores = require('../controllers/compositores');

router.get('/compositores', function (req, res, next) {
    if (req.query.periodo) {
        if (req.query.data) {
            Compositores.consultarDataPeriodo(req.query.periodo, req.query.data).then(dados => { // GET /api/compositores?data=AAAA-MM-DD&periodo=XXXXX - Devolve a lista de compositores que tenham o campo periodo com o valor "XXXXX" e cuja data de nascimento seja superior a data indicada no fitro data.
                res.jsonp(dados.sort());
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        } else {
            Compositores.consultarPeriodo(req.query.periodo).then(dados => { // GET /api/compositores?periodo=XXXXX => Devolve a lista de compositores que tenham o campo periodo com o valor "XXXXX"
                res.jsonp(dados.sort());
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        }
    } else { // GET /api/compositores => Devolve a lista de compositores com os seguintes campos: id, nome, data-nascimento
        Compositores.listar().then(dados => {
            res.jsonp(dados.sort());
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    }
});

/* GET /api/compositores/:id => Devolve a informacao completa de um composito */
router.get('/compositores/:id', function (req, res) {
    Compositores.consultar(req.params.id).then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

module.exports = router;
