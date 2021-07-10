var express = require('express');
var axios = require('axios');
var router = express.Router();

// #################### GETS ####################
router.get('/', function(req, res, next) {
  	res.render('index', {});
});

router.get('/registarepisodio', function (req, res, next) {
    res.render('novoepisodio', {});
});

router.get('/registartipodeexame', function (req, res, next) {
    res.render('novotipoexame', {});
});

router.get('/registarpaciente', function (req, res, next) {
    res.render('novopaciente', {});
});

router.get('/registarpedido', function (req, res, next) {
    var reqtipoexame = axios.get('http://localhost:3000/tipoexames')
    var reqpaciente = axios.get('http://localhost:3000/pacientes')
    var reqepisodio = axios.get('http://localhost:3000/episodios')
    axios.all([reqtipoexame,reqpaciente,reqepisodio]).then(axios.spread((...response) => {
        restipoexame = response[0].data
        respaciente = response[1].data
        resepisodio = response[2].data
        res.render('novopedido', {
            tiposexames: restipoexame.resultados,
            pacientes: respaciente.resultados,
            episodios: resepisodio.resultados
        });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/pedidos', function (req, res, next) {
    var reqpedido = axios.get('http://localhost:3000/pedidos')
    axios.all([reqpedido]).then(axios.spread((...response) => {
        res.render('pedidos', { pedidos: response[0].data.resultados });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/episodios', function (req, res, next) {
    var reqepisodio = axios.get('http://localhost:3000/listaepisodios')
    axios.all([reqepisodio]).then(axios.spread((...response) => {
        res.render('episodios', { episodios: response[0].data.resultados });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/episodio/:id', function (req, res, next) {
    var reqepisodio = axios.get('http://localhost:3000/episodio/' + req.params.id)
    axios.all([reqepisodio]).then(axios.spread((...response) => {
        res.render('episodio', { pedidos: response[0].data.resultados, idepisodio: req.params.id });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/pedido/:id', function (req, res, next) {
    var reqpedido = axios.get('http://localhost:3000/pedido/' + req.params.id)
    axios.all([reqpedido]).then(axios.spread((...response) => {
        res.render('pedido', { info: response[0].data.resultados, idpedido: req.params.id });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/paciente/:id', function (req, res, next) {
    var reqpaciente = axios.get('http://localhost:3000/paciente/' + req.params.id)
    axios.all([reqpaciente]).then(axios.spread((...response) => {
        res.render('paciente', { info: response[0].data.resultados, idpaciente: req.params.id });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/paciente/:id', function (req, res, next) {
    var reqpaciente = axios.get('http://localhost:3000/paciente/' + req.params.id)
    axios.all([reqpaciente]).then(axios.spread((...response) => {
        res.render('paciente', { info: response[0].data.resultados, idpaciente: req.params.id });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

// #################### POSTS ####################
router.post('/episodio', function (req, res) {
    axios.post('http://localhost:3000/episodio', req.body).then(dados => {
        res.redirect('/registarpaciente');
    }).catch(e => {
		res.render('error', {error: e})
	});
});

router.post('/tipoexame', function (req, res) {
    axios.post('http://localhost:3000/tipoexame', req.body).then(dados => {
        res.redirect('/registarpedido');
    }).catch(e => {
		res.render('error', {error: e})
	});
});

router.post('/paciente', function (req, res) {
    axios.post('http://localhost:3000/paciente', req.body).then(dados => {
        res.redirect('/registarpedido');
    }).catch(e => {
		res.render('error', {error: e})
	});
});

router.post('/pedido', function (req, res) {
    axios.post('http://localhost:3000/pedido', req.body).then(dados => {
        res.redirect('/pedidos');
    }).catch(e => {
		res.render('error', {error: e})
	});
});

router.post('/cancelar', function (req, res, next) {
    axios.post('http://localhost:3000/enviarcancelar', req.body).then(dados => {
        res.redirect('/episodios');
    }).catch(e => {
		res.render('error', {error: e})
	});
});

module.exports = router;
