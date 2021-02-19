var express = require('express');
var router = express.Router();

var axios = require('axios');

const apiKey = "?apikey=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmZWJjZTA2ZGUzODcyMzBmYmY0ZjAyYyIsImlhdCI6MTYwOTI4OTIyMiwiZXhwIjoxNjExODgxMjIyfQ.By8mFyHeIQSshDo1iN6HaDewA0sGf29PfnJP0GujprPTvNY_a9N7G0jF5c364S66PBEnV6FB6qfiFlfgMpLBw6vgZJlsY9XfQ_ZU3Lj93NNX67qO68BfxdR5EJFFq75b38ZPYwOkCnOTv2feZYpEH_PZd4ccdH1DspGkCzuBy_1hJov3Go0ct40juKt9d_LqR2ohsgD0pV4C77L8MWs87mV-Ark5lXGsyQv_t8JS2DuKgS1LTUy78JjOGL4QSQrG0ss3r-hvWXpt-MM6rJMqVemN8v7KeA9V7km8y5wLsZ5GW8JStXkVtGwtZfZimgc5Uj-CGQwuQuRf7gz7I3cZrg";
const clav = "http://clav-api.di.uminho.pt/v2/";

/* GET home page. */
router.get([
    '/', '/legislacoes'
], function (req, res, next) {
    var request = clav + "legislacao" + apiKey;
    axios.get(request).then(dados => {
        res.render('index', {legislacao: dados.data.sort()});
    }).catch(e => {
        res.render('error', {error: e});
    })
});


router.get('/legislacao/:id', function (req, res, next) {
    const requestOne = axios.get(clav + "legislacao/" + req.params.id + apiKey);
    const requestTwo = axios.get(clav + "legislacao/" + req.params.id + "/processos" + apiKey);
    axios.all([requestOne, requestTwo]).then(axios.spread((...responses) => {
        var base = responses[0].data;
        var processos = responses[1].data.sort();
        res.render('legislacao', {
            data: {
                idl: req.params.id,
                base: base,
                processos: processos
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

router.get('/legislacao\/leg_[A-Z]*\/entidade/:id', function (req, res, next) {
    var splits = req.url.split("/");
    var legislacao = splits[2];
    const requestOne = axios.get(clav + "entidades/" + req.params.id + apiKey);
    axios.all([requestOne]).then(axios.spread((...responses) => {
        var base = responses[0].data;
        res.render('entidade', {
            data: {
                idd: legislacao,
                ide: req.params.id,
                base: base
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

router.get('/legislacao\/leg_[A-Z]*\/processo/:id', function (req, res, next) {
    var splits = req.url.split("/");
    var legislacao = splits[2];
    console.log(legislacao);
    console.log(req.params.id);
    const requestOne = axios.get(clav + "classes/" + req.params.id + apiKey);
    axios.all([requestOne]).then(axios.spread((...responses) => {
        var base = responses[0].data;
        res.render('processo', {
            data: {
                idd: legislacao,
                base: base
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

module.exports = router;
