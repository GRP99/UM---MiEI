var express = require('express');
var router = express.Router();

var axios = require('axios');

const apiKey = "apikey=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmZWJjZTA2ZGUzODcyMzBmYmY0ZjAyYyIsImlhdCI6MTYwOTI4OTIyMiwiZXhwIjoxNjExODgxMjIyfQ.By8mFyHeIQSshDo1iN6HaDewA0sGf29PfnJP0GujprPTvNY_a9N7G0jF5c364S66PBEnV6FB6qfiFlfgMpLBw6vgZJlsY9XfQ_ZU3Lj93NNX67qO68BfxdR5EJFFq75b38ZPYwOkCnOTv2feZYpEH_PZd4ccdH1DspGkCzuBy_1hJov3Go0ct40juKt9d_LqR2ohsgD0pV4C77L8MWs87mV-Ark5lXGsyQv_t8JS2DuKgS1LTUy78JjOGL4QSQrG0ss3r-hvWXpt-MM6rJMqVemN8v7KeA9V7km8y5wLsZ5GW8JStXkVtGwtZfZimgc5Uj-CGQwuQuRf7gz7I3cZrg";
const clav = "http://clav-api.di.uminho.pt/v2/";

/* GET home page. */
router.get([
    '/', '/classes'
], function (req, res, next) {
    var request = clav + "classes?nivel=1&"+ apiKey;
    axios.get(request).then(dados => {
        res.render('index', {classes: dados.data});
    }).catch(e => {
        res.render('error', {error: e});
    })
});


router.get('/classes/:id', function (req, res, next) {
    const requestOne = axios.get(clav + "classes/" + req.params.id + "?" + apiKey);
    axios.all([requestOne]).then(axios.spread((...responses) => {
        var base = responses[0].data;
        res.render('classe', {
            data: {
                classe1: req.params.id,
                base: base
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

router.get('/classes\/c[0-9]+\/:id', function (req, res, next) {
    var splits = req.url.split("/");
    var classe1 = splits[2];
    const requestOne = axios.get(clav + "classes/" + req.params.id + "?" + apiKey);
    axios.all([requestOne]).then(axios.spread((...responses) => {
        var base = responses[0].data;
        res.render('classe2', {
            data: {
                classe1: classe1,
                classe2: req.params.id,
                base: base
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

router.get('/classes\/c[0-9]+\/c[0-9\.]+\/:id', function (req, res, next) {
    var splits = req.url.split("/");
    var classe1 = splits[2];
    var classe2 = splits[3];
    const requestOne = axios.get(clav + "classes/" + req.params.id + "?" + apiKey);
    axios.all([requestOne]).then(axios.spread((...responses) => {
        var base = responses[0].data;
        res.render('classe3', {
            data: {
                classe1: classe1,
                classe2: classe2,
                base: base
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

module.exports = router;
