var express = require('express');
var router = express.Router();

const mysqlConnection = require("../utils/database");

/* GET users listing. */
router.get('/', function (req, res, next) {
    res.render('index', {});
});

router.get('/registarrelatorio', function (req, res, next) {
    var sql = "SELECT l.idlistatrabalho, e.idexame, p.data_hora, e.explicacao, te.sigla, pa.nome, pa.numUtente, ep.idepisodio FROM listatrabalho l, pedido p, exame e, tipoexame te, paciente pa, episodio ep WHERE p.fk_exame=e.idexame AND e.relatorio=\'desconhecido\' AND e.fk_tipoExame=te.idtipoExame AND pa.idpaciente=p.fk_paciente AND l.estado!=\'Cancelado\' AND l.estado!=\'Finalizado\' AND e.relatorio=\'desconhecido\' AND l.fk_pedido=p.idpedido AND ep.idepisodio=p.fk_episodio"
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            res.render('novorelatorio', {dados: results});
        } else {
            res.render('error', {error: err});
        }
    });
});

router.get('/verestados', function (req, res, next) {
    res.render('estado', {});
});

router.get('/listatrabalho', function (req, res, next) {
    if (req.query.estado == 'Por_Realizar') {
        mysqlConnection.query("SELECT * FROM listatrabalho l, pedido p WHERE l.estado='Por Realizar' AND l.fk_pedido=p.idpedido ORDER BY idlistatrabalho DESC", (err, results, fields) => {
            if (!err) {
                res.render('listatrabalho', {
                    dados: results,
                    estado: req.query.estado
                });
            } else {
                res.render('error', {error: err});
            }
        });
    } else {
        if (req.query.estado == 'Cancelados') {
            mysqlConnection.query("SELECT * FROM listatrabalho l, pedido p WHERE l.estado='Cancelado' AND l.fk_pedido=p.idpedido ORDER BY idlistatrabalho DESC", (err, results, fields) => {
                if (!err) {
                    res.render('listatrabalho', {
                        dados: results,
                        estado: req.query.estado
                    });
                } else {
                    res.render('error', {error: err});
                }
            });
        } else {
            if (req.query.estado == 'Finalizados') {
                mysqlConnection.query("SELECT * FROM listatrabalho l, pedido p WHERE l.estado='Finalizado' AND l.fk_pedido=p.idpedido ORDER BY idlistatrabalho DESC", (err, results, fields) => {
                    if (!err) {
                        res.render('listatrabalho', {
                            dados: results,
                            estado: req.query.estado
                        });
                    } else {
                        res.render('error', {error: err});
                    }
                });
            } else {
                res.render('error', {error: err});
            }
        }
    }
});


router.get('/exame/:id', function (req, res, next) {
    mysqlConnection.query("SELECT * FROM exame e, tipoexame te WHERE te.idtipoExame=e.fk_tipoExame AND e.idexame=" + req.params.id, (err, results, fields) => {
        if (!err) {
            res.render('exame', {
                dados: results,
                estado: req.query.estado
            });
        } else {
            res.render('error', {error: err});
        }
    });
});

router.get('/paciente/:id', function (req, res, next) {
    mysqlConnection.query("SELECT * FROM paciente WHERE idpaciente=" + req.params.id, (err, results, fields) => {
        if (!err) {
            res.render('paciente', {
                dados: results,
                estado: req.query.estado
            });
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
            mysqlConnection.query("SELECT idpedido, data_hora, estado FROM episodio, pedido, listatrabalho WHERE idepisodio=" + req.params.id + " AND fk_episodio=idepisodio AND idpedido=fk_pedido", (err, results, fields) => {
                if (!err) {
                    res.render('episodio', {
                        episodio: infoepisodio,
                        pedidos: results,
                        estado: req.query.estado
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

router.post('/relatorio', function (req, res, next) {
    var sql = "UPDATE exame SET relatorio = \'" + req.body.relatorio + "\' WHERE idexame = " + req.body.exame + "";
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            var sql = "UPDATE listatrabalho SET enviado = 0 , estado = 'Finalizado' WHERE idlistatrabalho = " + req.body.idlistatrabalho;
            mysqlConnection.query(sql, (err, results, fields) => {
                if (!err) {
                    res.redirect('/verestados');
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
    var sql = "UPDATE listatrabalho SET estado = 'Cancelado', enviado=0  WHERE fk_pedido = " + req.body.id_pedido + " AND idlistatrabalho = " + req.body.idlistatrabalho;
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            res.redirect('/verestados');
        } else {
            res.render('error', {error: err});
        }
    });
});

router.post('/finalizarpedido', function (req, res, next) {
    var sql = "UPDATE listatrabalho SET estado = 'Finalizado', enviado=0 WHERE fk_pedido = " + req.body.id_pedido + " AND idlistatrabalho = " + req.body.idlistatrabalho;
    mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            res.redirect('/verestados');
        } else {
            res.render('error', {error: err});
        }
    });
});

module.exports = router;
