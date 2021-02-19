var express = require('express');
var router = express.Router();

var Sports = require('../controllers/sports');

// GET /api/atividades => lista as atividades no dataset com os seguintes campos: data, tipo de atividade, distância em Km e tempo
router.get('/atividades', function (req, res, next) {
    if (req.query.filtro) {
        if (req.query.filtro == 'maislonga') { // GET /api/atividades?filtro=maislonga => devolve a atividade com a duracao mais longa
            Sports.longer().then(dados => {
                res.jsonp(dados);
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        }
    } else {
        Sports.list().then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    }
});

// GET /api/atividades/:tipo => lista as atividades que pertencem ao tipo indicado
router.get('/atividades/:tipo', function (req, res, next) {
    if (req.query.filtro) {
        if (req.query.filtro == 'maislonga') { // GET /api/atividades/:tipo?filtro=maislonga - devolve a atividade mais longa do tipo indicado
            Sports.longerType(req.params.tipo).then(dados => {
                res.jsonp(dados);
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        }
    } else {
        Sports.listByType(req.params.tipo).then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    }
});


module.exports = router;
