var express = require('express');
var axios = require('axios');
var router = express.Router();

// #################### GETS ####################
router.get('/', function(req, res, next) {
	res.render('index', {});
});

router.get('/relatorio', function (req, res, next) {
    var reqpedido = axios.get('http://localhost:4000/infopedidos')
    axios.all([reqpedido]).then(axios.spread((...response) => {
        res.render('relatorio', {
            pedidos: response[0].data.resultados
        });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/pedidos', function (req, res, next) {
    var reqpedido = axios.get('http://localhost:4000/pedidos?estado=' + req.query.estado)
    axios.all([reqpedido]).then(axios.spread((...response) => {
        res.render('pedidos', {
            pedidos: response[0].data.resultados,
            estado: req.query.estado == 'Por_Realizar' ? 'por realizar' : req.query.estado.toLowerCase()
        });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/episodios', function (req, res, next) {
    var reqepisodio = axios.get('http://localhost:4000/episodios')
    axios.all([reqepisodio]).then(axios.spread((...response) => {
        res.render('episodios', { episodios: response[0].data.resultados });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/episodio/:id', function (req, res, next) {
    var reqepisodio = axios.get('http://localhost:4000/episodio/' + req.params.id)
    axios.all([reqepisodio]).then(axios.spread((...response) => {
        res.render('episodio', { pedidos: response[0].data.resultados, idepisodio: req.params.id });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/pedido/:id', function (req, res, next) {
    var reqpedido = axios.get('http://localhost:4000/pedido/' + req.params.id)
    axios.all([reqpedido]).then(axios.spread((...response) => {
        res.render('pedido', { info: response[0].data.resultados, idpedido: req.params.id });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

router.get('/paciente/:id', function (req, res, next) {
    var reqpaciente = axios.get('http://localhost:4000/paciente/' + req.params.id)
    axios.all([reqpaciente]).then(axios.spread((...response) => {
        res.render('paciente', { info: response[0].data.resultados, idpaciente: req.params.id });
    })).catch(e => {
        res.render('error', {error: e})
    });
});

// #################### POSTS ####################
router.post('/relatorio', function (req, res) {
    axios.post('http://localhost:4000/relatorio', req.body).then(dados => {
        res.redirect('/pedidos?estado=Finalizados');
    }).catch(e => {
		res.render('error', {error: e})
	});
});

router.post('/cancelar', function (req, res, next) {
    axios.post('http://localhost:4000/enviarcancelar', req.body).then(dados => {
        res.redirect('/pedidos?estado=Cancelados');
    }).catch(e => {
		res.render('error', {error: e})
	});
});

module.exports = router;
