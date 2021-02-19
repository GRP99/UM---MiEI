var express = require('express');
var router = express.Router();

var axios = require('axios');

const apiKey = "?apikey=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmZWJjZTA2ZGUzODcyMzBmYmY0ZjAyYyIsImlhdCI6MTYwOTI4OTIyMiwiZXhwIjoxNjExODgxMjIyfQ.By8mFyHeIQSshDo1iN6HaDewA0sGf29PfnJP0GujprPTvNY_a9N7G0jF5c364S66PBEnV6FB6qfiFlfgMpLBw6vgZJlsY9XfQ_ZU3Lj93NNX67qO68BfxdR5EJFFq75b38ZPYwOkCnOTv2feZYpEH_PZd4ccdH1DspGkCzuBy_1hJov3Go0ct40juKt9d_LqR2ohsgD0pV4C77L8MWs87mV-Ark5lXGsyQv_t8JS2DuKgS1LTUy78JjOGL4QSQrG0ss3r-hvWXpt-MM6rJMqVemN8v7KeA9V7km8y5wLsZ5GW8JStXkVtGwtZfZimgc5Uj-CGQwuQuRf7gz7I3cZrg";
const clav = "http://clav-api.di.uminho.pt/v2/";

/* GET home page. */
router.get([
    '/', '/tipologias'
], function (req, res, next) {
    var request = clav + "tipologias" + apiKey;
    axios.get(request).then(dados => {
        res.render('index', {tipologias: dados.data});
    }).catch(e => {
        res.render('error', {error: e});
    })
});


router.get('/tipologia/:id', function (req, res, next) {
    const requestOne = axios.get(clav + "tipologias/" + req.params.id + apiKey);
    const requestTwo = axios.get(clav + "tipologias/" + req.params.id + "/elementos" + apiKey);
    const requestThree = axios.get(clav + "tipologias/" + req.params.id + "/intervencao/dono" + apiKey);
    const requestFour = axios.get(clav + "tipologias/" + req.params.id + "/intervencao/participante" + apiKey);
    axios.all([requestOne, requestTwo, requestThree, requestFour]).then(axios.spread((...responses) => {
        var base = responses[0].data;
        var elementos = responses[1].data;
        var dono = responses[2].data;
        var participante = responses[3].data;
        res.render('tipologia', {
            data: {
                idt: req.params.id,
                base: base,
                elementos: elementos,
                dono: dono,
                participante: participante
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

router.get('/tipologia\/tip_[A-Z]*\/entidade/:id', function (req, res, next) {
    var splits = req.url.split("/");
    var tipologia = splits[2];
    const requestOne = axios.get(clav + "entidades/" + req.params.id + apiKey);
    const requestTwo = axios.get(clav + "entidades/" + req.params.id + "/tipologias" + apiKey);
    axios.all([requestOne, requestTwo]).then(axios.spread((...responses) => {
        var base = responses[0].data;
        var tipologias = responses[1].data;
        res.render('entidade', {
            data: {
                idt: tipologia,
                ide: req.params.id,
                base: base,
                tipologias: tipologias
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

router.get('/tipologia\/tip_[A-Z]+\/entidade\/ent_[A-Z]+\/tipologia/:id', function (req, res, next) {
    var splits = req.url.split("/");
    var tipologia = splits[2];
    var entidade = splits[4];
    const requestOne = axios.get(clav + "tipologias/" + req.params.id + apiKey);
    const requestTwo = axios.get(clav + "tipologias/" + req.params.id + "/elementos" + apiKey);
    const requestThree = axios.get(clav + "tipologias/" + req.params.id + "/intervencao/dono" + apiKey);
    const requestFour = axios.get(clav + "tipologias/" + req.params.id + "/intervencao/participante" + apiKey);
    axios.all([requestOne, requestTwo, requestThree, requestFour]).then(axios.spread((...responses) => {
        var base = responses[0].data;
        var elementos = responses[1].data;
        var dono = responses[2].data;
        var participante = responses[3].data;
        res.render('tipologiaEntidade', {
            data: {
                idt: tipologia,
                ide: entidade,
                base: base,
                elementos: elementos,
                dono: dono,
                participante: participante
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

module.exports = router;
