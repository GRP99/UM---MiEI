var express = require('express');
var router = express.Router();
var Prizes = require('../controllers/prizes');

router.get('/premios', function (req, res, next) {
    if (req.query.categoria) {
        if (req.query.data) { // GET /api/premios?categoria=YYY&data=AAAA - Devolve a lista de premios que tenham o campo "category" com o valor "YYY" e o campo "year" com um valor superior a "AAAA"
            Prizes.consultCategoryYear(req.query.categoria, req.query.data).then(dados => {
                res.jsonp(dados);
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        } else {
            Prizes.consultCategory(req.query.categoria).then(dados => { // GET /api/premios?categoria=YYY - Devolve a lista de prémios que tenham o campo "category" com o valor "YYY"
                res.jsonp(dados);
            }).catch(erro => {
                res.status(500).jsonp(erro);
            });
        }
    } else { // GET /api/premios - Devolve a lista de prémios apenas com os campos "year" e "category";
        Prizes.list().then(dados => {
            res.jsonp(dados);
        }).catch(erro => {
            res.status(500).jsonp(erro);
        });
    }
});

/* GET /api/premios/:id - Devolve a informacao completa de um premio; */
router.get('/premios/:id', function (req, res) {
    Prizes.consult(req.params.id).then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

/* GET /api/categorias - Devolve a lista de categorias, sem repeticoes; */
router.get('/categorias', function (req, res) {
    Prizes.listCategories().then(dados => {
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});

/* GET /api/laureados - Devolve uma lista ordenada alfabeticamente por nome dos laureados com os campos correspondentes ao nome, ano do premio e categoria */
router.get('/laureados', function (req, res) {
    Prizes.listLaureates().then(dados => {
        dados.sort();
        res.jsonp(dados);
    }).catch(erro => {
        res.status(500).jsonp(erro);
    });
});


module.exports = router;
