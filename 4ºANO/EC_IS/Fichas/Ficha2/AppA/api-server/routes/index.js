var express = require("express");
var axios = require('axios');
var router = express.Router();

const mysqlConnection = require("../utils/database");

// #################### GETS ####################
router.get('/tipoexames', function(req, res, next) {
	var sql = "SELECT * FROM tipoexame"
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
			res.status(201).jsonp({resultados: results})
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.get('/pacientes', function(req, res, next) {
	var sql = "SELECT * FROM paciente"
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
			res.status(201).jsonp({resultados: results})
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.get('/episodios', function(req, res, next) {
	var sql = "SELECT * FROM episodio"
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
			res.status(201).jsonp({resultados: results})
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.get('/pedidos', function(req, res, next) {
	var sql = "SELECT idpedido, data_hora, fk_episodio, sigla, num_utente FROM pedido, tipoexame, paciente WHERE pedido.fk_tipoexame=tipoexame.idtipoexame AND pedido.fk_paciente=paciente.idpaciente AND pedido.estado='Por realizar'"
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
			res.status(201).jsonp({resultados: results})
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.get('/listaepisodios', function(req, res, next) {
	var sql = "SELECT idepisodio, caracterizacao, COUNT(idpedido) AS totalPedidos FROM pedido, episodio WHERE pedido.fk_episodio=episodio.idepisodio GROUP BY episodio.idepisodio ORDER BY idepisodio DESC"
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
			res.status(201).jsonp({resultados: results})
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.get('/episodio/:id', function(req, res, next) {
	var sql = "SELECT idpedido, data_hora, estado, sigla, num_utente FROM pedido, tipoexame, paciente, episodio WHERE pedido.fk_tipoexame=tipoexame.idtipoexame AND pedido.fk_paciente=paciente.idpaciente AND pedido.fk_episodio=episodio.idepisodio AND episodio.idepisodio=" + req.params.id
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
			res.status(201).jsonp({resultados: results})
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.get('/pedido/:id', function(req, res, next) {
	var sql = "SELECT data_hora, estado, explicacao, relatorio, idepisodio, caracterizacao, sigla, descricao, paciente.* FROM pedido, episodio, tipoexame, paciente WHERE pedido.fk_episodio=episodio.idepisodio AND pedido.fk_tipoexame=tipoexame.idtipoexame AND pedido.fk_paciente=paciente.idpaciente AND pedido.idpedido=" + req.params.id
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
			res.status(201).jsonp({resultados: results})
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.get('/paciente/:id', function(req, res, next) {
	var sql = "SELECT * FROM paciente WHERE idpaciente=" + req.params.id
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
			res.status(201).jsonp({resultados: results})
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

// #################### POSTS ####################
router.post("/episodio", function (req, res, next) {
    console.log("\n##################################################")
    console.log("VOU ENVIAR UMA MENSAGEM PARA AppB PARA INSERIR UM NOVO EPISÓDIO")
    console.log(JSON.stringify(req.body))
    console.log("##################################################\n")
  	mysqlConnection.query("INSERT INTO episodio (caracterizacao) VALUES (\'" + req.body.caracterizacao + "\')", (err, results, fields) => {
      	if (!err) {
            axios.post('http://localhost:4000/episodio', req.body).then(dados => {
                res.status(201).jsonp({resultados: results})
            }).catch(e => {
		        res.status(500).jsonp({error: err})
	        });
      	} else {
        	res.status(500).jsonp({error: err})
     	}
    });
});

router.post('/tipoexame', function (req, res, next) {
    console.log("\n##################################################")
    console.log("VOU ENVIAR UMA MENSAGEM PARA AppB PARA REGISTAR UM NOVO TIPO DE EXAME")
    console.log(JSON.stringify(req.body))
    console.log("##################################################\n")
    mysqlConnection.query("INSERT INTO tipoexame (sigla,descricao) VALUES (\'" + req.body.sigla + "\',\'" + req.body.descricao + "\')", (err, results, fields) => {
        if (!err) {
            axios.post('http://localhost:4000/tipoexame', req.body).then(dados => {
                res.status(201).jsonp({resultados: results})
            }).catch(e => {
		        res.status(500).jsonp({error: err})
	        });
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.post('/paciente', function (req, res, next) {
    console.log("\n##################################################")
    console.log("VOU ENVIAR UMA MENSAGEM PARA AppB PARA REGISTAR UM NOVO PACIENTE")
    console.log(JSON.stringify(req.body))
    console.log("##################################################\n")
    mysqlConnection.query("INSERT INTO paciente (sexo,telemovel,num_utente,data_nascimento,email,nome,morada) VALUES (\'" + req.body.sexo + "\',\'" + req.body.telemovel + "\',\'" + req.body.numutente + "\',\'" + req.body.datanascimento + "\',\'" + req.body.email + "\',\'" + req.body.nome + "\',\'" + req.body.morada + "\')", (err, results, fields) => {
        if (!err) {
            axios.post('http://localhost:4000/paciente', req.body).then(dados => {
                res.status(201).jsonp({resultados: results})
            }).catch(e => {
		        res.status(500).jsonp({error: err})
	        });
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.post("/pedido", function (req, res, next) {
    console.log("\n##################################################")
    console.log("VOU ENVIAR UMA MENSAGEM PARA AppB PARA REGISTAR UM NOVO PEDIDO")
    console.log(JSON.stringify(req.body))
    console.log("##################################################\n")
	var sqlquery = "INSERT INTO pedido (data_hora,estado,explicacao,relatorio,fk_episodio,fk_tipoexame,fk_paciente) VALUES (\'" + req.body.datahora + "\',\'Por realizar\',\'" + req.body.explicacao + "\',\'Desconhecido\'," + req.body.episodio + "," + req.body.tipoexame + "," + req.body.paciente + ")"
	mysqlConnection.query(sqlquery, (err, results, fields) => {
		if (!err) {
            axios.post('http://localhost:4000/pedido', req.body).then(dados => {
                res.status(201).jsonp({resultados: results})
            }).catch(e => {
		        res.status(500).jsonp({error: err})
	        });
		} else {
		  res.status(500).jsonp({error: err})
	   }
  });
});

router.post('/enviarcancelar', function (req, res, next) {
    console.log("\n##################################################")
    console.log("VOU ENVIAR UMA MENSAGEM PARA AppB PARA CANCELAR UM PEDIDO")
    console.log(JSON.stringify(req.body))
    console.log("##################################################\n")
    var sql = "UPDATE pedido SET estado = 'Cancelado' WHERE idpedido = " + req.body.idpedido
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            axios.post('http://localhost:4000/recebercancelar', req.body).then(dados => {
                res.status(201).jsonp({resultados: results})
            }).catch(e => {
		        res.status(500).jsonp({error: err})
	        });
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.post('/recebercancelar', function (req, res, next) {
    console.log("\n##################################################")
    console.log("RECEBI UMA MENSAGEM DA AppB PARA CANCELAR O PEDIDO")
    console.log(JSON.stringify(req.body))
    console.log("##################################################\n")
    var sql = "UPDATE pedido SET estado = 'Cancelado' WHERE idpedido = " + req.body.idpedido
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            res.status(201).jsonp({resultados: results})
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

router.post('/relatorio', function (req, res, next) {
    console.log("\n##################################################")
    console.log("RECEBI UMA MENSAGEM DA AppB PARA ADICIONAR UM RELATÓRIO")
    console.log(JSON.stringify(req.body))
    console.log("##################################################\n")
    var sql = "UPDATE pedido SET estado = 'Finalizado', relatorio = \'" + req.body.relatorio + "\' WHERE idpedido=" + req.body.pedido
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            res.status(201).jsonp({resultados: results})
        } else {
            res.status(500).jsonp({error: err})
        }
    });
});

module.exports = router;
