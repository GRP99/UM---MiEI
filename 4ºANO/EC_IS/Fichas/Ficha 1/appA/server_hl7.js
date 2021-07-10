const hl7 = require('simple-hl7');
const mysql = require("mysql");
const e = require('express');

var app = hl7.tcp();

var mysqlConnection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "12345",
    port: 3306,
    database: "appa"
});

mysqlConnection.connect((err) => {
    if (!err) {
        console.log("Connected to the Database");

        app.use(async (req, res, next) => {
            console.log('******message received*****\n')

            console.log(req.msg.log() + '\n');

            var order_status = 'Desconhecido'
            if (req.msg.getSegment('ORC').getField(5) == 'HD') {
                order_status = 'Por realizar';
            } else {
                if (req.msg.getSegment('ORC').getField(5) == 'CA') {
                    order_status = 'Cancelado';
                } else {
                    if (req.msg.getSegment('ORC').getField(5) == 'CM') {
                        order_status = 'Finalizado';
                    } else {
                        order_status = 'ER';
                    }
                }
            }

            if (!(req.msg.getSegment('OBX'))) {
                atualizarEstado(req.msg, order_status, mysqlConnection);

            } else {
                var segmentos_obx = req.msg.getSegments('OBX');
                var relatorio = '';
                for (let index = 0; index < segmentos_obx.length; index++) {
                    var set_id = segmentos_obx[index].getField(1);
                    if (set_id >= 6) {
                        relatorio += segmentos_obx[index].getField(5).trim() + '\n';
                    }
                }
                registarRelatorio(req.msg, order_status, relatorio, mysqlConnection);
            }
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

        app.start(9696);


    } else {
        console.log("Database Connection Failed");
    }
});

async function atualizarEstado(messagem, order_status, mysqlConnection) {
    await mysqlConnection.query("UPDATE listatrabalho SET estado = \'" + order_status + "\', enviado=1 WHERE idlistatrabalho = " + messagem.header.getField(12), (err, results, fields) => {
        if (!err) {
            console.log('UPDATE successfully ...')
        } else {
            console.log(err);
        }
    });
}

async function registarRelatorio(messagem, order_status, relatorio, mysqlConnection) {
    var sql = "SELECT * FROM listatrabalho WHERE idlistatrabalho=" + messagem.header.getField(12);
    await mysqlConnection.query(sql, (err, results, fields) => {
        if (!err) {
            results.forEach(element => {
                if (element.estado != order_status) {
                    sql = "UPDATE listatrabalho SET estado=\'" + order_status + "\',enviado = 1 WHERE idlistatrabalho = " + messagem.header.getField(12);
                    mysqlConnection.query(sql, (err, results, fields) => {
                        if (!err) {
                            mysqlConnection.query("UPDATE exame SET relatorio = \'" + relatorio + "\' WHERE idexame = " + messagem.getSegment('PID').getField(1), (err, results, fields) => {
                                if (!err) {
                                    console.log('UPDATE successfully ...')
                                } else {
                                    console.log(err);
                                }
                            });
                        } else {
                            console.log(err);
                        }
                    });
                } else {
                    mysqlConnection.query("UPDATE exame SET relatorio = \'" + relatorio + "\' WHERE idexame = " + messagem.getSegment('PID').getField(1), (err, results, fields) => {
                        if (!err) {
                            console.log('UPDATE successfully ...')
                        } else {
                            console.log(err);
                        }
                    });
                }
            });
        } else {
            console.log(err);
        }
    })
};
