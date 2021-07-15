const hl7 = require('simple-hl7');
const mysql = require("mysql");

var app = hl7.tcp();

var mysqlConnection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "12345",
    port: 3306,
    database: "appb"
});

mysqlConnection.connect((err) => {
    if (!err) {
        console.log("Connected to the Database");

        app.use(async (req, res, next) => {
            console.log('******message received*****\n')

            console.log(req.msg.log() + '\n');

            registaPedido(req.msg, mysqlConnection);
            next();
        });

        app.use((req, res, next) => {
            console.log('******sending ack*****')
            res.end();
        })

        app.use(function (err, req, res, next) {
            console.log('******ERROR*****')
            console.log(err);
            res.ack.addSegment('ERR', err.message);
            res.end();
        });

        app.start(6969);


    } else {
        console.log("Database Connection Failed");
    }
});


async function registaPedido(messagem, mysqlConnection) {
    var order_status = 'Desconhecido';
    if (messagem.getSegment('ORC').getField(5) == 'HD') {
        order_status = 'Por realizar';
    } else {
        if (messagem.getSegment('ORC').getField(5) == 'CA') {
            order_status = 'Cancelado';
        } else {
            if (messagem.getSegment('ORC').getField(5) == 'CM') {
                order_status = 'Finalizado';
            } else {
                order_status = 'ER';
            }
        }
    }
    
    var sql = "SELECT * FROM listatrabalho WHERE idlistatrabalho=" + messagem.header.getField(12);
    await mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            if (results.length > 0) {
                var idlistatrabalho = messagem.header.getField(12);

                var sql = "UPDATE listatrabalho SET estado=\'" + order_status + "\',enviado = 1 WHERE idlistatrabalho = " + idlistatrabalho;
                mysqlConnection.query(sql, (err, results, fields) => {
                    if (!err) {
                        console.log('UPDATE successfully ...')
                    } else {
                        console.log(err);
                    }
                });
                
            } else {
                sql = "SELECT * FROM episodio WHERE idepisodio=" + messagem.getSegment('PV1').getField(50);
                mysqlConnection.query(sql, (err, results, fields) => {
                    if (!err) {
                        if (results.length > 0) {
                            var idepisodio = messagem.getSegment('PV1').getField(50);

                            sql = "SELECT * FROM tipoexame WHERE idtipoexame=" + messagem.getSegment('OBR').getField(1);
                            mysqlConnection.query(sql, (err, results, fields) => {
                                if (!err) {
                                    if (results.length > 0) {
                                        var idtipoexame = messagem.getSegment('OBR').getField(1);

                                        sql = "SELECT * FROM paciente WHERE idpaciente=" + messagem.getSegment('PID').getField(2);
                                        mysqlConnection.query(sql, (err, results, fields) => {
                                            if (!err) {
                                                if (results.length > 0) {
                                                    var idpaciente = messagem.getSegment('PID').getField(2);

                                                    sql = "SELECT * FROM exame WHERE idexame=" + messagem.getSegment('PID').getField(1);
                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                        if (!err) {
                                                            if (results.length > 0) {
                                                                var idexame = messagem.getSegment('PID').getField(1);

                                                                sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                    if (!err) {
                                                                        if (results.length > 0) {
                                                                            var idpedido = messagem.header.getField(9);

                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                if (!err) {
                                                                                    console.log('INSERT\'s successfully ...')
                                                                                } else {
                                                                                    console.log(err);
                                                                                }
                                                                            });

                                                                        } else {
                                                                            sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                if (!err) {
                                                                                    var idpedido = results.insertId;

                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                        if (!err) {
                                                                                            console.log('INSERT\'s successfully ...')
                                                                                        } else {
                                                                                            console.log(err);
                                                                                        }
                                                                                    });
                                                                                } else {
                                                                                    console.log(err);
                                                                                }
                                                                            });
                                                                        }
                                                                    } else {
                                                                        console.log(err);
                                                                    }
                                                                });

                                                            } else {
                                                                sql = "INSERT INTO exame (idexame,explicacao,relatorio,fk_tipoExame) VALUES (" + messagem.getSegment('PID').getField(1) + ",\'" + messagem.getSegment('OBR').getField(13) + "\',\'desconhecido\'," + idtipoexame + ")";
                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                    if (!err) {
                                                                        var idexame = results.insertId;

                                                                        sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                            if (!err) {
                                                                                if (results.length > 0) {
                                                                                    var idpedido = messagem.header.getField(9);
                                                                                    
                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                        if (!err) {
                                                                                            console.log('INSERT\'s successfully ...')
                                                                                        } else {
                                                                                            console.log(err);
                                                                                        }
                                                                                    });

                                                                                } else {
                                                                                    sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                        if (!err) {
                                                                                            var idpedido = results.insertId;

                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });
                                                                                        } else {
                                                                                            console.log(err);
                                                                                        }
                                                                                    });
                                                                                }
                                                                            } else {
                                                                                console.log(err);
                                                                            }
                                                                        });
                                                                    } else {
                                                                        console.log(err);
                                                                    }
                                                                });
                                                            }
                                                        } else {
                                                            console.log(err);
                                                        }
                                                    });

                                                } else {
                                                    sql = "INSERT INTO paciente (idpaciente,nome,telefone,numUtente,morada,genero) VALUES (" + messagem.getSegment('PID').getField(2) + ",\'" + messagem.getSegment('PID').getField(5) + "\'," + messagem.getSegment('PID').getField(13) + "," + messagem.getSegment('PID').getField(18) + ",\'" + messagem.getSegment('PID').getField(11) + "\',\'" + messagem.getSegment('PID').getField(8) + "\')"
                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                        if (!err) {
                                                            var idpaciente = results.insertId;
                                                            sql = "SELECT * FROM exame WHERE idexame=" + messagem.getSegment('PID').getField(1);
                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                if (!err) {
                                                                    if (results.length > 0) {
                                                                        var idexame = messagem.getSegment('PID').getField(1);

                                                                        sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                            if (!err) {
                                                                                if (results.length > 0) {
                                                                                    var idpedido = messagem.header.getField(9);
                                                                                    
                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                        if (!err) {
                                                                                            console.log('INSERT\'s successfully ...')
                                                                                        } else {
                                                                                            console.log(err);
                                                                                        }
                                                                                    });

                                                                                } else {
                                                                                    sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                        if (!err) {
                                                                                            var idpedido = results.insertId;

                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });

                                                                                        } else {
                                                                                            console.log(err);
                                                                                        }
                                                                                    });
                                                                                }
                                                                            } else {
                                                                                console.log(err);
                                                                            }
                                                                        });

                                                                    } else {
                                                                        sql = "INSERT INTO exame (idexame,explicacao,relatorio,fk_tipoExame) VALUES (" + messagem.getSegment('PID').getField(1) + ",\'" + messagem.getSegment('OBR').getField(13) + "\',\'desconhecido\'," + idtipoexame + ")";
                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                            if (!err) {
                                                                                var idexame = results.insertId;

                                                                                sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                                    if (!err) {
                                                                                        if (results.length > 0) {
                                                                                            var idpedido = messagem.header.getField(9);
                                                                                            
                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });

                                                                                        } else {
                                                                                            sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    var idpedido = results.insertId;

                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    } else {
                                                                                        console.log(err);
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                console.log(err);
                                                                            }
                                                                        });
                                                                    }
                                                                } else {
                                                                    console.log(err);
                                                                }
                                                            });

                                                        } else {
                                                            console.log(err);
                                                        }
                                                    });
                                                }
                                            } else {
                                                console.log(err);
                                            }
                                        });
                                    } else {
                                        sql = "INSERT INTO tipoexame (idtipoexame,sigla,descricao) VALUES (" + messagem.getSegment('OBR').getField(1) + ",\'" + messagem.getSegment('PV1').getField(3) + "\',\'" + messagem.getSegment('OBR').getField(44) + "\')";
                                        mysqlConnection.query(sql, (err, results, fields) => {
                                            if (!err) {
                                                var idtipoexame = results.insertId;
                                                sql = "SELECT * FROM paciente WHERE idpaciente=" + messagem.getSegment('PID').getField(2);
                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                    if (!err) {
                                                        if (results.length > 0) {
                                                            var idpaciente = messagem.getSegment('PID').getField(2);

                                                            sql = "SELECT * FROM exame WHERE idexame=" + messagem.getSegment('PID').getField(1);
                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                if (!err) {
                                                                    if (results.length > 0) {
                                                                        var idexame = messagem.getSegment('PID').getField(1);

                                                                        sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                            if (!err) {
                                                                                if (results.length > 0) {
                                                                                    var idpedido = messagem.header.getField(9);
                                                                                    
                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                        if (!err) {
                                                                                            console.log('INSERT\'s successfully ...')
                                                                                        } else {
                                                                                            console.log(err);
                                                                                        }
                                                                                    });

                                                                                } else {
                                                                                    sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                        if (!err) {
                                                                                            var idpedido = results.insertId;

                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });
                                                                                        } else {
                                                                                            console.log(err);
                                                                                        }
                                                                                    });
                                                                                }
                                                                            } else {
                                                                                console.log(err);
                                                                            }
                                                                        });

                                                                    } else {
                                                                        sql = "INSERT INTO exame (idexame,explicacao,relatorio,fk_tipoExame) VALUES (" + messagem.getSegment('PID').getField(1) + ",\'" + messagem.getSegment('OBR').getField(13) + "\',\'desconhecido\'," + idtipoexame + ")";
                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                            if (!err) {
                                                                                var idexame = results.insertId;

                                                                                sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                                    if (!err) {
                                                                                        if (results.length > 0) {
                                                                                            var idpedido = messagem.header.getField(9);
                                                                                            
                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });

                                                                                        } else {
                                                                                            sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    var idpedido = results.insertId;

                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    } else {
                                                                                        console.log(err);
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                console.log(err);
                                                                            }
                                                                        });
                                                                    }
                                                                } else {
                                                                    console.log(err);
                                                                }
                                                            });

                                                        } else {
                                                            sql = "INSERT INTO paciente (idpaciente,nome,telefone,numUtente,morada,genero) VALUES (" + messagem.getSegment('PID').getField(2) + ",\'" + messagem.getSegment('PID').getField(5) + "\'," + messagem.getSegment('PID').getField(13) + "," + messagem.getSegment('PID').getField(18) + ",\'" + messagem.getSegment('PID').getField(11) + "\',\'" + messagem.getSegment('PID').getField(8) + "\')"
                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                if (!err) {
                                                                    var idpaciente = results.insertId;

                                                                    sql = "SELECT * FROM exame WHERE idexame=" + messagem.getSegment('PID').getField(1);
                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                        if (!err) {
                                                                            if (results.length > 0) {
                                                                                var idexame = messagem.getSegment('PID').getField(1);

                                                                                sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                                    if (!err) {
                                                                                        if (results.length > 0) {
                                                                                            var idpedido = messagem.header.getField(9);
                                                                                            
                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });

                                                                                        } else {
                                                                                            sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    var idpedido = results.insertId;

                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    } else {
                                                                                        console.log(err);
                                                                                    }
                                                                                });

                                                                            } else {
                                                                                sql = "INSERT INTO exame (idexame,explicacao,relatorio,fk_tipoExame) VALUES (" + messagem.getSegment('PID').getField(1) + ",\'" + messagem.getSegment('OBR').getField(13) + "\',\'desconhecido\'," + idtipoexame + ")";
                                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                                    if (!err) {
                                                                                        var idexame = results.insertId;

                                                                                        sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                                            if (!err) {
                                                                                                if (results.length > 0) {
                                                                                                    var idpedido = messagem.header.getField(9);
                                                                                                    
                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });

                                                                                                } else {
                                                                                                    sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            var idpedido = results.insertId;

                                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                                if (!err) {
                                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                                } else {
                                                                                                                    console.log(err);
                                                                                                                }
                                                                                                            });
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            } else {
                                                                                                console.log(err);
                                                                                            }
                                                                                        });
                                                                                    } else {
                                                                                        console.log(err);
                                                                                    }
                                                                                });
                                                                            }
                                                                        } else {
                                                                            console.log(err);
                                                                        }
                                                                    });

                                                                } else {
                                                                    console.log(err);
                                                                }
                                                            });
                                                        }
                                                    } else {
                                                        console.log(err);
                                                    }
                                                });

                                            } else {
                                                console.log(err);
                                            }
                                        });
                                    }
                                } else {
                                    console.log(err);
                                }
                            });
                        } else {
                            sql = "INSERT INTO episodio (idepisodio,caracterizacao) VALUES (" + messagem.getSegment('PV1').getField(50) + ",\'" + messagem.getSegment('PV1').getField(19) + "\')";
                            mysqlConnection.query(sql, (err, results, fields) => {
                                if (!err) {
                                    var idepisodio = results.insertId;

                                    sql = "SELECT * FROM tipoexame WHERE idtipoexame=" + messagem.getSegment('OBR').getField(1);
                                    mysqlConnection.query(sql, (err, results, fields) => {
                                        if (!err) {
                                            if (results.length > 0) {
                                                var idtipoexame = messagem.getSegment('OBR').getField(1);
                                                
                                                sql = "SELECT * FROM paciente WHERE idpaciente=" + messagem.getSegment('PID').getField(2);
                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                    if (!err) {
                                                        if (results.length > 0) {
                                                            var idpaciente = messagem.getSegment('PID').getField(2);

                                                            sql = "SELECT * FROM exame WHERE idexame=" + messagem.getSegment('PID').getField(1);
                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                if (!err) {
                                                                    if (results.length > 0) {
                                                                        var idexame = messagem.getSegment('PID').getField(1);

                                                                        sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                            if (!err) {
                                                                                if (results.length > 0) {
                                                                                    var idpedido = messagem.header.getField(9);
                                                                                    
                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                        if (!err) {
                                                                                            console.log('INSERT\'s successfully ...')
                                                                                        } else {
                                                                                            console.log(err);
                                                                                        }
                                                                                    });

                                                                                } else {
                                                                                    sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                        if (!err) {
                                                                                            var idpedido = results.insertId;
                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });
                                                                                        } else {
                                                                                            console.log(err);
                                                                                        }
                                                                                    });
                                                                                }
                                                                            } else {
                                                                                console.log(err);
                                                                            }
                                                                        });

                                                                    } else {
                                                                        sql = "INSERT INTO exame (idexame,explicacao,relatorio,fk_tipoExame) VALUES (" + messagem.getSegment('PID').getField(1) + ",\'" + messagem.getSegment('OBR').getField(13) + "\',\'desconhecido\'," + idtipoexame + ")";
                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                            if (!err) {
                                                                                var idexame = results.insertId;

                                                                                sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                                    if (!err) {
                                                                                        if (results.length > 0) {
                                                                                            var idpedido = messagem.header.getField(9);
                                                                                            
                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });

                                                                                        } else {
                                                                                            sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    var idpedido = results.insertId;
                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    } else {
                                                                                        console.log(err);
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                console.log(err);
                                                                            }
                                                                        });
                                                                    }
                                                                } else {
                                                                    console.log(err);
                                                                }
                                                            });

                                                        } else {
                                                            sql = "INSERT INTO paciente (idpaciente,nome,telefone,numUtente,morada,genero) VALUES (" + messagem.getSegment('PID').getField(2) + ",\'" + messagem.getSegment('PID').getField(5) + "\'," + messagem.getSegment('PID').getField(13) + "," + messagem.getSegment('PID').getField(18) + ",\'" + messagem.getSegment('PID').getField(11) + "\',\'" + messagem.getSegment('PID').getField(8) + "\')"
                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                if (!err) {
                                                                    var idpaciente = results.insertId;
                                                                    sql = "SELECT * FROM exame WHERE idexame=" + messagem.getSegment('PID').getField(1);
                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                        if (!err) {
                                                                            if (results.length > 0) {
                                                                                var idexame = messagem.getSegment('PID').getField(1);

                                                                                sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                                    if (!err) {
                                                                                        if (results.length > 0) {
                                                                                            var idpedido = messagem.header.getField(9);
                                                                                            
                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });

                                                                                        } else {
                                                                                            sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    var idpedido = results.insertId;
                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    } else {
                                                                                        console.log(err);
                                                                                    }
                                                                                });

                                                                            } else {
                                                                                sql = "INSERT INTO exame (idexame,explicacao,relatorio,fk_tipoExame) VALUES (" + messagem.getSegment('PID').getField(1) + ",\'" + messagem.getSegment('OBR').getField(13) + "\',\'desconhecido\'," + idtipoexame + ")";
                                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                                    if (!err) {
                                                                                        var idexame = results.insertId;

                                                                                        sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                                            if (!err) {
                                                                                                if (results.length > 0) {
                                                                                                    var idpedido = messagem.header.getField(9);
                                                                                                    
                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });

                                                                                                } else {
                                                                                                    sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            var idpedido = results.insertId;
                                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                                if (!err) {
                                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                                } else {
                                                                                                                    console.log(err);
                                                                                                                }
                                                                                                            });
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            } else {
                                                                                                console.log(err);
                                                                                            }
                                                                                        });
                                                                                    } else {
                                                                                        console.log(err);
                                                                                    }
                                                                                });
                                                                            }
                                                                        } else {
                                                                            console.log(err);
                                                                        }
                                                                    });

                                                                } else {
                                                                    console.log(err);
                                                                }
                                                            });
                                                        }
                                                    } else {
                                                        console.log(err);
                                                    }
                                                });
                                            } else {
                                                sql = "INSERT INTO tipoexame (idtipoexame,sigla,descricao) VALUES (" + messagem.getSegment('OBR').getField(1) + ",\'" + messagem.getSegment('PV1').getField(3) + "\',\'" + messagem.getSegment('OBR').getField(44) + "\')";
                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                    if (!err) {
                                                        var idtipoexame = results.insertId;

                                                        sql = "SELECT * FROM paciente WHERE idpaciente=" + messagem.getSegment('PID').getField(2);
                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                            if (!err) {
                                                                if (results.length > 0) {
                                                                    var idpaciente;
                                                                    

                                                                    sql = "SELECT * FROM exame WHERE idexame=" + messagem.getSegment('PID').getField(1);
                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                        if (!err) {
                                                                            if (results.length > 0) {
                                                                                var idexame = messagem.getSegment('PID').getField(1);

                                                                                sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                                    if (!err) {
                                                                                        if (results.length > 0) {
                                                                                            var idpedido = messagem.header.getField(9);
                                                                                            
                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });

                                                                                        } else {
                                                                                            sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                if (!err) {
                                                                                                    var idpedido = results.insertId;
                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });
                                                                                                } else {
                                                                                                    console.log(err);
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    } else {
                                                                                        console.log(err);
                                                                                    }
                                                                                });

                                                                            } else {
                                                                                sql = "INSERT INTO exame (idexame,explicacao,relatorio,fk_tipoExame) VALUES (" + messagem.getSegment('PID').getField(1) + ",\'" + messagem.getSegment('OBR').getField(13) + "\',\'desconhecido\'," + idtipoexame + ")";
                                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                                    if (!err) {
                                                                                        var idexame = results.insertId;

                                                                                        sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                                            if (!err) {
                                                                                                if (results.length > 0) {
                                                                                                    var idpedido = messagem.header.getField(9);
                                                                                                    
                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });

                                                                                                } else {
                                                                                                    sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            var idpedido = results.insertId;
                                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                                if (!err) {
                                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                                } else {
                                                                                                                    console.log(err);
                                                                                                                }
                                                                                                            });
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            } else {
                                                                                                console.log(err);
                                                                                            }
                                                                                        });
                                                                                    } else {
                                                                                        console.log(err);
                                                                                    }
                                                                                });
                                                                            }
                                                                        } else {
                                                                            console.log(err);
                                                                        }
                                                                    });

                                                                } else {
                                                                    sql = "INSERT INTO paciente (idpaciente,nome,telefone,numUtente,morada,genero) VALUES (" + messagem.getSegment('PID').getField(2) + ",\'" + messagem.getSegment('PID').getField(5) + "\'," + messagem.getSegment('PID').getField(13) + "," + messagem.getSegment('PID').getField(18) + ",\'" + messagem.getSegment('PID').getField(11) + "\',\'" + messagem.getSegment('PID').getField(8) + "\')"
                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                        if (!err) {
                                                                            var idpaciente = results.insertId;
                                                                            sql = "SELECT * FROM exame WHERE idexame=" + messagem.getSegment('PID').getField(1);
                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                if (!err) {
                                                                                    if (results.length > 0) {
                                                                                        var idexame = messagem.getSegment('PID').getField(1);

                                                                                        sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                                            if (!err) {
                                                                                                if (results.length > 0) {
                                                                                                    var idpedido = messagem.header.getField(9);
                                                                                                    
                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });

                                                                                                } else {
                                                                                                    sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                        if (!err) {
                                                                                                            var idpedido = results.insertId;
                                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                                if (!err) {
                                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                                } else {
                                                                                                                    console.log(err);
                                                                                                                }
                                                                                                            });
                                                                                                        } else {
                                                                                                            console.log(err);
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            } else {
                                                                                                console.log(err);
                                                                                            }
                                                                                        });

                                                                                    } else {
                                                                                        sql = "INSERT INTO exame (idexame,explicacao,relatorio,fk_tipoExame) VALUES (" + messagem.getSegment('PID').getField(1) + ",\'" + messagem.getSegment('OBR').getField(13) + "\',\'desconhecido\'," + idtipoexame + ")";
                                                                                        mysqlConnection.query(sql, (err, results, fields) => {
                                                                                            if (!err) {
                                                                                                var idexame = results.insertId;

                                                                                                sql = "SELECT * FROM pedido WHERE idpedido=" + messagem.header.getField(9);
                                                                                                mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                    if (!err) {
                                                                                                        if (results.length > 0) {
                                                                                                            var idpedido = messagem.header.getField(9);
                                                                                                            
                                                                                                            sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                                if (!err) {
                                                                                                                    console.log('INSERT\'s successfully ...')
                                                                                                                } else {
                                                                                                                    console.log(err);
                                                                                                                }
                                                                                                            });

                                                                                                        } else {
                                                                                                            sql = "INSERT INTO pedido (idpedido,fk_exame,fk_paciente,data_hora,fk_episodio) VALUES (" + messagem.header.getField(9) + "," + idexame + "," + idpaciente + ",\'" + messagem.getSegment('OBR').getField(6).toString() + "\'," + idepisodio + ")"
                                                                                                            mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                                if (!err) {
                                                                                                                    var idpedido = results.insertId;
                                                                                                                    sql = "INSERT INTO listatrabalho (idlistatrabalho,fk_pedido,estado,enviado) VALUES (" + messagem.header.getField(12) + "," + idpedido + ",\'" + order_status + "\',1)";
                                                                                                                    mysqlConnection.query(sql, (err, results, fields) => {
                                                                                                                        if (!err) {
                                                                                                                            console.log('INSERT\'s successfully ...')
                                                                                                                        } else {
                                                                                                                            console.log(err);
                                                                                                                        }
                                                                                                                    });
                                                                                                                } else {
                                                                                                                    console.log(err);
                                                                                                                }
                                                                                                            });
                                                                                                        }
                                                                                                    } else {
                                                                                                        console.log(err);
                                                                                                    }
                                                                                                });
                                                                                            } else {
                                                                                                console.log(err);
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                } else {
                                                                                    console.log(err);
                                                                                }
                                                                            });

                                                                        } else {
                                                                            console.log(err);
                                                                        }
                                                                    });
                                                                }
                                                            } else {
                                                                console.log(err);
                                                            }
                                                        });

                                                    } else {
                                                        console.log(err);
                                                    }
                                                });
                                            }
                                        } else {
                                            console.log(err);
                                        }
                                    });

                                } else {
                                    console.log(err);
                                }
                            });
                        }
                    } else {
                        console.log(err);
                    }
                });
            }
        } else {
            console.log(err);
        }
    });
}
