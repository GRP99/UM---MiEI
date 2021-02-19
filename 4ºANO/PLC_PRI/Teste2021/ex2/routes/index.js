var express = require('express');
var router = express.Router();

var axios = require('axios');

const apiKey = "token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjExMTExMjIyMyIsImxldmVsIjoyLCJlbnRpZGFkZSI6ImVudF9BM0VTIiwiZW1haWwiOiJwcmkyMDIwQHRlc3RlLnVtaW5oby5wdCIsImlhdCI6MTYxMDk4MTUyNywiZXhwIjoxNjExMDEwMzI3fQ.euae7D-D3Fbf4vAkVJJyMW1tHVqdBo4ArZX1iGo_O4umjWtRGR55VMCeHgWNXMf4FFxsr5tFDzjmTon79WSJPNsscbAvLn6n4FC4F7ifGOQ8i700a4TvMzBaeJ8Y1cmFXoZ-H49AeEBvtu1cwAacnSz139jMcN6FqO4fNO02sbv_Mbd1pbFNTMQxO1bQLkzcUIhD2RpSBDYiweq3fW2Wx6r6e9LCPJzUheGxP98Js60HPDx4Ji3CYuTHEJny091J010en_T6GujvJVAOQkYVtkRmCMHqEo9P-c0qXIgy44etmTc5CFCBArfLFmMHapYV1BwEr466jI2lW5EUbHMViQ";
const clav = "http://clav-api.di.uminho.pt/v2/";

/* GET home page. */
router.get([
    '/', '/classes'
], function (req, res, next) {
    var request = clav + "classes?nivel=1&" + apiKey;
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
                classe3: req.params.id,
                base: base
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

router.get('/classes\/c[0-9]+\/c[0-9\.]+\/c[0-9\.]+\/relacionado/:id', function (req, res, next) {
    var splits = req.url.split("/");
    var classe1 = splits[2];
    var classe2 = splits[3];
    var classe3 = splits[4];
    var processo3 = classe3.replace('c','');
    const requestOne = axios.get(clav + "classes/" + req.params.id + "?" + apiKey);
    axios.all([requestOne]).then(axios.spread((...responses) => {
        var base = responses[0].data;
        res.render('processorelacionado', {
            data: {
                classe1: classe1,
                classe2: classe2,
                classe3: classe3,
                processo3: processo3,
                base: base
            }
        });
    })).catch(e => {
        res.render('error', {error: e});
    })
});

module.exports = router;
