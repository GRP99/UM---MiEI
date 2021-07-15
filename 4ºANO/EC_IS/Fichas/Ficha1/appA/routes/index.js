var express = require('express');
var router = express.Router();

const mysqlConnection = require("../utils/database");

/* GET users listing. */
router.get('/', function (req, res, next) {
    res.render('index', {});
});

router.get('/registarepisodio', function (req, res, next) {
    res.render('novoepisodio', {});
});

router.get('/registartipodeexame', function (req, res, next) {
    res.render('novotipodeexame', {});
});

router.get('/registarpaciente', function (req, res, next) {
    res.render('novopaciente', {});
});

router.get('/registarpedido', function (req, res, next) {
    mysqlConnection.query("SELECT * FROM tipoexame", (err, results, fields) => {
        if (!err) {
            tipoexame = results;
            mysqlConnection.query("SELECT * FROM paciente", (err, results, fields) => {
                if (!err) {
                    paciente = results;
                    mysqlConnection.query("SELECT * FROM episodio", (err, results, fields) => {
                        if (!err) {
                            res.render('novopedido', {
                                tiposexames: tipoexame,
                                pacientes: paciente,
                                episodios: results
                            });
                        } else {
                            res.render('error', {error: err});
                        }
                    });
                } else {
                    res.render('error', {error: err});
                }
            });
        } else {
            res.render('error', {error: err});
        }
    });
});

router.get('/listatrabalho', function (req, res, next) {
    var sql = "SELECT * FROM listatrabalho l, pedido p WHERE l.fk_pedido=p.idpedido AND l.estado='Por Realizar' ORDER BY idlistatrabalho DESC"
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            res.render('listatrabalho', {dados: results});
        } else {
            res.render('error', {error: err});
        }
    });
});

router.get('/listaepisodios', function (req, res, next) {
    var sql = "SELECT idepisodio, caracterizacao, COUNT(idpedido) AS totalPedidos FROM episodio, pedido WHERE fk_episodio=idepisodio GROUP BY idepisodio ORDER BY idepisodio DESC"
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            res.render('listaepisodios', {dados: results});
        } else {
            res.render('error', {error: err});
        }
    });
});

router.get('/exame/:id', function (req, res, next) {
    mysqlConnection.query("SELECT idexame,e.explicacao as descricao_exame,relatorio,fk_tipoExame,idtipoExame,sigla,te.descricao as descricao_tipoexame FROM exame e, tipoexame te WHERE te.idtipoExame=e.fk_tipoExame AND e.idexame=" + req.params.id, (err, results, fields) => {
        if (!err) {
            res.render('exame', {dados: results});
        } else {
            res.render('error', {error: err});
        }
    });
});

router.get('/paciente/:id', function (req, res, next) {
    mysqlConnection.query("SELECT * FROM paciente WHERE idpaciente=" + req.params.id, (err, results, fields) => {
        if (!err) {
            res.render('paciente', {dados: results});
        } else {
            res.render('error', {error: err});
        }
    });
});

router.get('/episodio/:id', function (req, res, next) {
    var sql = ""
    mysqlConnection.query("SELECT * FROM episodio WHERE idepisodio=" + req.params.id, (err, results, fields) => {
        infoepisodio = results
        if (!err) {
            mysqlConnection.query("SELECT idpedido, data_hora, estado, fk_exame FROM episodio, pedido, listatrabalho WHERE idepisodio=" + req.params.id + " AND fk_episodio=idepisodio AND idpedido=fk_pedido", (err, results, fields) => {
                if (!err) {
                    res.render('episodio', {
                        episodio: infoepisodio,
                        pedidos: results
                    });
                } else {
                    res.render('error', {error: err});
                }
            });
        } else {
            res.render('error', {error: err});
        }
    });
});

router.post('/episodio', function (req, res, next) {
    mysqlConnection.query("INSERT INTO episodio (caracterizacao) VALUES (\'" + req.body.caracterizacao + "\')", (err, results, fields) => {
        if (!err) {
            res.redirect('/');
        } else {
            res.render('error', {error: err});
        }
    });
});

router.post('/tipoexame', function (req, res, next) {
    mysqlConnection.query("INSERT INTO tipoexame (sigla,descricao) VALUES (\'" + req.body.sigla + "\',\'" + req.body.descricao + "\')", (err, results, fields) => {
        if (!err) {
            res.redirect('/');
        } else {
            res.render('error', {error: err});
        }
    });
});

router.post('/paciente', function (req, res, next) {
    mysqlConnection.query("INSERT INTO paciente (nome,telefone,numUtente,morada,genero) VALUES (\'" + req.body.nome + "\'," + req.body.telefone + "," + req.body.numUtente + ",\'" + req.body.morada + "\',\'" + req.body.genero + "\')", (err, results, fields) => {
        if (!err) {
            res.redirect('/');
        } else {
            res.render('error', {error: err});
        }
    });
});

router.post('/pedido', function (req, res, next) {
    mysqlConnection.query("INSERT INTO exame (explicacao,relatorio,fk_tipoExame) VALUES (\'" + req.body.descricao + "\',\'desconhecido\'," + req.body.tipoexame + ")", (err, results, fields) => {
        if (!err) {
            mysqlConnection.query("INSERT INTO pedido (fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + results.insertId + "," + req.body.paciente + ",\'" + req.body.data_hora + "\'," + req.body.episodio + ")", (err, results, fields) => {
                if (!err) {
                    mysqlConnection.query("INSERT INTO listatrabalho (fk_pedido,estado) VALUES (" + results.insertId + ",\'Por realizar\')", (err, results, fields) => {
                        if (!err) {
                            res.redirect('/');
                        } else {
                            res.render('error', {error: err});
                        }
                    });
                } else {
                    res.render('error', {error: err});
                }
            });
        } else {
            res.render('error', {error: err});
        }
    });
});

router.post('/cancelarpedido', function (req, res, next) {
    var sql = "UPDATE listatrabalho SET estado = 'Cancelado', enviado=0 WHERE fk_pedido = " + req.body.id_pedido + " AND idlistatrabalho = " + req.body.idlistatrabalho;
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            res.redirect('/listatrabalho');
        } else {
            res.render('error', {error: err});
        }
    });
});

module.exports = router;
