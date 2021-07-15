const hl7 = require('simple-hl7');
const mysql = require("mysql");

var client = hl7.Server.createTcpClient('localhost', 9696);

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

        async function runClient() {
            var sql = "SELECT * FROM listatrabalho, pedido, episodio, exame, paciente, tipoExame WHERE fk_pedido=idpedido AND fk_episodio=idepisodio AND fk_exame=idexame AND fk_tipoExame=idtipoExame AND fk_paciente=idpaciente AND enviado=0";
            mysqlConnection.query(sql, (err, results, fields) => {
                if (!err) {
                    results.forEach(element => {
                        var order_status = '';
                        if (element.estado == 'Por realizar') {
                            order_status = 'HD';
                        } else {
                            if (element.estado == 'Cancelado') {
                                order_status = 'CA';
                            } else {
                                if (element.estado == 'Finalizado') {
                                    order_status = 'CM';
                                } else {
                                    order_status = 'ER';
                                }
                            }
                        }
                        var message

                        if (element.relatorio == 'desconhecido') {
                            message = criarMensagemPedido(element, order_status);
                        } else {
                            message = criarMensagemRelatorio(element, order_status);
                        }

                        console.log('Sending... message!')

                        client.send(message, async (err, ack) => {
                            if (!err) {
                                var sql = "UPDATE listatrabalho SET enviado = 1 WHERE idlistatrabalho = " + element.idlistatrabalho;
                                mysqlConnection.query(sql, (err, results, fields) => {
                                    if (!err) {
                                        console.log(ack.log());
                                    } else {
                                        console.log(err);
                                    }
                                });
                            } else {
                                console.log(err);
                            }
                        });


                    });
                } else {
                    console.log(err);
                    return;
                }
            });
        }

        setInterval(() => {
            runClient();
        }, 10000);

    } else {
        console.log("Database Connection Failed");
    }
});


function criarMensagemPedido(pedido, order_status) {
    var message = createMSH(pedido);
    createPID(pedido, message);
    createPV1(pedido, message);
    createORC(order_status, message);
    createOBR(pedido, message);

    return message;
}

function criarMensagemRelatorio(pedido, order_status) {
    var message = createMSH(pedido);
    createPID(pedido, message);
    createORC(order_status, message);
    createOBR(pedido, message);
    createOBX(pedido, message);

    return message;
}


function createMSH(pedido) {
    return new hl7.Message( // '^~\&', //MSH.1 - Field Separator	1 [DEFAULT]
            '', // MSH.2 - Encoding Characters	4
            'AppB', // MSH.3 - Sending Application	227
            'AppB', // MSH.4 - Sending Facility	227
            'AppA', // MSH.5 - Receiving Application	227
            'AppA', // MSH.6 - Receiving Facility	227
        Date.now(), // MSH.7 - Date/Time Of Message	26
            '', // MSH.8 - Security	40
            'ORM', // MSH.9 - Message Type	15
    pedido.idpedido, // MSH.10 - Message Control ID	20
            'P', // MSH.11 - Processing ID	3
            '2.5', // MSH.12 - Version ID	60
    pedido.idlistatrabalho, // MSH.13 - Sequence Number	15
            '', // MSH.14 - Continuation Pointer	180
            '', // MSH.15 - Accept Acknowledgment Type	2
            '', // MSH.16 - Application Acknowledgment Type	2
            'PT', // MSH.17 - Country Code	3
            '', // MSH.18 - Character Set	16
            'PT', // MSH.19 - Principal Language Of Message	250
            '', // MSH.20 - Alternate Character Set Handling Scheme	20
            '', // MSH.21 - Message Profile Identifier	427
    )
}

function createPID(pedido, message) {
    message.addSegment('PID', pedido.idexame, // PID.1 - Set ID - PID	4
    pedido.idpaciente, // PID.2 - Patient ID	20
            '', // PID.3 - Patient Identifier List	250
            '', // PID.4 - Alternate Patient ID - PID	20
    pedido.nome, // PID.5 - Patient Name	250
            '', // PID.6 - Mother's Maiden Name	250
            '', // PID.7 - Date/Time of Birth	26
    pedido.genero, // PID.8 - Administrative Sex	1
            '', // PID.9 - Patient Alias	250
            '', // PID.10 - Race	250
    pedido.morada, // PID.11 - Patient Address	250
            'PT', // PID.12 - County Code	4
    pedido.telefone, // PID.13 - Phone Number - Home	250
            '', // PID.14 - Phone Number - Business	250
            'PT', // PID.15 - Primary Language	250
            '', // PID.16 - Marital Status	250
            '', // PID.17 - Religion	250
    pedido.numUtente, // PID.18 - Patient Account Number	250
            '', // PID.19 - SSN Number - Patient	16
            '', // PID.20 - Driver's License Number - Patient	25
            '', // PID.21 - Mother's Identifier	250
            '', // PID.22 - Ethnic Group	250
            '', // PID.23 - Birth Place	250
            '', // PID.24 - Multiple Birth Indicator	1
            '', // PID.25 - Birth Order	2
            'PT', // PID.26 - Citizenship	250
            '', // PID.27 - Veterans Military Status	250
            'PT', // PID.28 - Nationality	250
            '', // PID.29 - Patient Death Date and Time	26
            '', // PID.30 - Patient Death Indicator	1
            '', // PID.31 - Identity Unknown Indicator	1
            '', // PID.32 - Identity Reliability Code	20
            '', // PID.33 - Last Update Date/Time	26
            '', // PID.34 - Last Update Facility	241
            '', // PID.35 - Species Code	250
            '', // PID.36 - Breed Code	250
            '', // PID.37 - Strain	80
            '', // PID.38 - Production Class Code	250
            '', // PID.39 - Tribal Citizenship	250
    )
}

function createPV1(pedido, message) {
    message.addSegment('PV1', '', // PV1.1 - Set ID - PV1	4
            'I', // PV1.2 - Patient Class	1
    pedido.sigla, // PV1.3 - Assigned Patient Location	80
            '', // PV1.4 - Admission Type	2
            '', // PV1.5 - Preadmit Number	250
            '', // PV1.6 - Prior Patient Location	80
            '', // PV1.7 - Attending Doctor	250
            '', // PV1.8 - Referring Doctor	250
            '', // PV1.9 - Consulting Doctor	250
            '', // PV1.10 - Hospital Service	3
            '', // PV1.11 - Temporary Location	80
            '', // PV1.12 - Preadmit Test Indicator	2
            '', // PV1.13 - Re-admission Indicator	2
            '', // PV1.14 - Admit Source	6
            '', // PV1.15 - Ambulatory Status	2
            '', // PV1.16 - VIP Indicator	2
            '', // PV1.17 - Admitting Doctor	250
            '', // PV1.18 - Patient Type	2
    pedido.caracterizacao, // PV1.19 - Visit Number	250
            '', // PV1.20 - Financial Class	50
            '', // PV1.21 - Charge Price Indicator	2
            '', // PV1.22 - Courtesy Code	2
            '', // PV1.23 - Credit Rating	2
            '', // PV1.24 - Contract Code	2
            '', // PV1.25 - Contract Effective Date	8
            '', // PV1.26 - Contract Amount	12
            '', // PV1.27 - Contract Period	3
            '', // PV1.28 - Interest Code	2
            '', // PV1.29 - Transfer to Bad Debt Code	4
            '', // PV1.30 - Transfer to Bad Debt Date	8
            '', // PV1.31 - Bad Debt Agency Code	10
            '', // PV1.32 - Bad Debt Transfer Amount	12
            '', // PV1.33 - Bad Debt Recovery Amount	12
            '', // PV1.34 - Delete Account Indicator	1
            '', // PV1.35 - Delete Account Date	8
            '', // PV1.36 - Discharge Disposition	3
            '', // PV1.37 - Discharged to Location	47
            '', // PV1.38 - Diet Type	250
            '', // PV1.39 - Servicing Facility	2
            '', // PV1.40 - Bed Status	1
            '', // PV1.41 - Account Status	2
            '', // PV1.42 - Pending Location	80
            '', // PV1.43 - Prior Temporary Location	80
            '', // PV1.44 - Admit Date/Time	26
            '', // PV1.45 - Discharge Date/Time	26
            '', // PV1.46 - Current Patient Balance	12
            '', // PV1.47 - Total Charges	12
            '', // PV1.48 - Total Adjustments	12
            '', // PV1.49 - Total Payments	12
    pedido.idepisodio, // PV1.50 - Alternate Visit ID	250
            '', // PV1.51 - Visit Indicator	1
            '', // PV1.52 - Other Healthcare Provider	250
    )
}

function createORC(order_status, message) {
    message.addSegment('ORC', 'NW', // ORC.1 - Order Control	2
            '4727374', // ORC.2 - Placer Order Number	22
            '4727374', // ORC.3 - Filler Order Number	22
            '', // ORC.4 - Placer Group Number	22
    order_status, // ORC.5 - Order Status	2
            '', // ORC.6 - Response Flag	1
            '', // ORC.7 - Quantity/Timing	200
            '', // ORC.8 - Parent Order	200
        Date.now(), // ORC.9 - Date/Time of Transaction	26
            'AppB', // ORC.10 - Entered By	250
            'AppB', // ORC.11 - Verified By	250
            '', // ORC.12 - Ordering Provider	250
            '', // ORC.13 - Enterer's Location	80
            '', // ORC.14 - Call Back Phone Number	250
        Date.now(), // ORC.15 - Order Effective Date/Time	26
            '', // ORC.16 - Order Control Code Reason	250
            '', // ORC.17 - Entering Organization	250
            '', // ORC.18 - Entering Device	250
            'AppA', // ORC.19 - Action By	250
            '', // ORC.20 - Advanced Beneficiary Notice Code	250
            '', // ORC.21 - Ordering Facility Name	250
            '', // ORC.22 - Ordering Facility Address	250
            '', // ORC.23 - Ordering Facility Phone Number	250
            '', // ORC.24 - Ordering Provider Address	250
            '', // ORC.25 - Order Status Modifier	250
            '', // ORC.26 - Advanced Beneficiary Notice Override Reason	60
            '', // ORC.27 - Filler's Expected Availability Date/Time	26
            '', // ORC.28 - Confidentiality Code	250
            '', // ORC.29 - Order Type	250
            '', // ORC.30 - Enterer Authorization Mode	250
    )
}

function createOBR(pedido, message) {
    message.addSegment('OBR', pedido.idtipoExame, // OBR.1 - Set ID - OBR	4
            '4727374', // OBR.2 - Placer Order Number	22
            '4727374', // OBR.3 - Filler Order Number	22
            '', // OBR.4 - Universal Service Identifier	250
            '', // OBR.5 - Priority - OBR	2
        Date.parse(pedido.data_hora), // OBR.6 - Requested Date/Time	26
            '', // OBR.7 - Observation Date/Time	26
            '', // OBR.8 - Observation End Date/Time	26
            '', // OBR.9 - Collection Volume	20
            '', // OBR.10 - Collector Identifier	250
            '', // OBR.11 - Specimen Action Code	1
            '', // OBR.12 - Danger Code	250
    pedido.explicacao, // OBR.13 - Relevant Clinical Information	300
            '', // OBR.14 - Specimen Received Date/Time	26
            '', // OBR.15 - Specimen Source	300
            '', // OBR.16 - Ordering Provider	250
            '', // OBR.17 - Order Callback Phone Number	250
            '', // OBR.18 - Placer Field 1	60
            '', // OBR.19 - Placer Field 2	60
            '', // OBR.20 - Filler Field 1	60
            '', // OBR.21 - Filler Field 2	60
            '', // OBR.22 - Results Rpt/Status Chng - Date/Time	26
            '', // OBR.23 - Charge to Practice	40
            '', // OBR.24 - Diagnostic Serv Sect ID	10
            '', // OBR.25 - Result Status	1
            '', // OBR.26 - Parent Result	400
            '', // OBR.27 - Quantity/Timing	200
            '', // OBR.28 - Result Copies To	250
            '', // OBR.29 - Parent	200
            '', // OBR.30 - Transportation Mode	20
            '', // OBR.31 - Reason for Study	250
            '', // OBR.32 - Principal Result Interpreter	200
            '', // OBR.33 - Assistant Result Interpreter	200
            '', // OBR.34 - Technician	200
            '', // OBR.35 - Transcriptionist	200
            '', // OBR.36 - Scheduled Date/Time	26
            '', // OBR.37 - Number of Sample Containers	4
            '', // OBR.38 - Transport Logistics of Collected Sample	250
            '', // OBR.39 - Collector's Comment	250
            '', // OBR.40 - Transport Arrangement Responsibility	250
            '', // OBR.41 - Transport Arranged	30
            '', // OBR.42 - Escort Required	1
            '', // OBR.43 - Planned Patient Transport Comment	250
    pedido.descricao, // OBR.44 - Procedure Code	250
            '', // OBR.45 - Procedure Code Modifier	250
            '', // OBR.46 - Placer Supplemental Service Information	250
            '', // OBR.47 - Filler Supplemental Service Information	250
            '', // OBR.48 - Medically Necessary Duplicate Procedure Reason.	250
            '', // OBR.49 - Result Handling
    )
}

function createOBX(pedido, message) {
    var relatorio = pedido.relatorio.split('\\n');
    message.addSegment('OBX', '1', // OBX.1 - Set ID - OBX	4	Sequence
            '', // OBX.2 - Value Type	2
            '', // OBX.3 - Observation Identifier	250
            '', // OBX.4 - Observation Sub-ID	20
    pedido.explicacao, // OBX.5 - Observation Value	99999 Inf
            '', // OBX.6  - Units 250
            '', // OBX.7  - References Range 60
            '', // OBX.8  - Abnormal Flags 5 Inf
            '', // OBX.9  - Probability 5
            '', // OBX.10 - Nature of Abnormal Test 2 Inf
            '', // OBX.11 - Observation Result Status 1
            '', // OBX.12 - Effective Date of Reference Range 26
            '', // OBX.13 - User Defined Access Checks 20
            '', // OBX.14 - Date/Time of the Observation 26
            '', // OBX.15 - Producer's ID 250
            '', // OBX.16 - Responsible Observer 250 Inf
            '', // OBX.17 - Observation Method 250 Inf
            '', // OBX.18 - Equipment Instance Identifier 22 Inf
        Date.now(), // OBX.19 - Date/Time of the Analysis 26
            '', // OBX.20 - Reserved for harmonization with V2.6 0
            '', // OBX.21 - Reserved for harmonization with V2.6 0
            '', // OBX.22 - Reserved for harmonization with V2.6 0
            'AppB', // OBX.23 - Performing Organization Name 567
            'Hospital', // OBX.24 - Performing Organization Address 631
            'gr01', // OBX.25 - Performing Organization Medical Director 3002
    );
    message.addSegment('OBX', '2', // OBX.1 - Set ID - OBX	4	Sequence
            '', // OBX.2 - Value Type	2
            '', // OBX.3 - Observation Identifier	250
            '', // OBX.4 - Observation Sub-ID	20
            '', // OBX.5 - Observation Value	99999 Inf
            '', // OBX.6  - Units 250
            '', // OBX.7  - References Range 60
            '', // OBX.8  - Abnormal Flags 5 Inf
            '', // OBX.9  - Probability 5
            '', // OBX.10 - Nature of Abnormal Test 2 Inf
            '', // OBX.11 - Observation Result Status 1
            '', // OBX.12 - Effective Date of Reference Range 26
            '', // OBX.13 - User Defined Access Checks 20
            '', // OBX.14 - Date/Time of the Observation 26
            '', // OBX.15 - Producer's ID 250
            '', // OBX.16 - Responsible Observer 250 Inf
            '', // OBX.17 - Observation Method 250 Inf
            '', // OBX.18 - Equipment Instance Identifier 22 Inf
        Date.now(), // OBX.19 - Date/Time of the Analysis 26
            '', // OBX.20 - Reserved for harmonization with V2.6 0
            '', // OBX.21 - Reserved for harmonization with V2.6 0
            '', // OBX.22 - Reserved for harmonization with V2.6 0
            'AppB', // OBX.23 - Performing Organization Name 567
            'Hospital', // OBX.24 - Performing Organization Address 631
            'gr01', // OBX.25 - Performing Organization Medical Director 3002
    );
    message.addSegment('OBX', '3', // OBX.1 - Set ID - OBX	4	Sequence
            '', // OBX.2 - Value Type	2
            '', // OBX.3 - Observation Identifier	250
            '', // OBX.4 - Observation Sub-ID	20
            '', // OBX.5 - Observation Value	99999 Inf
            '', // OBX.6  - Units 250
            '', // OBX.7  - References Range 60
            '', // OBX.8  - Abnormal Flags 5 Inf
            '', // OBX.9  - Probability 5
            '', // OBX.10 - Nature of Abnormal Test 2 Inf
            '', // OBX.11 - Observation Result Status 1
            '', // OBX.12 - Effective Date of Reference Range 26
            '', // OBX.13 - User Defined Access Checks 20
            '', // OBX.14 - Date/Time of the Observation 26
            '', // OBX.15 - Producer's ID 250
            '', // OBX.16 - Responsible Observer 250 Inf
            '', // OBX.17 - Observation Method 250 Inf
            '', // OBX.18 - Equipment Instance Identifier 22 Inf
        Date.now(), // OBX.19 - Date/Time of the Analysis 26
            '', // OBX.20 - Reserved for harmonization with V2.6 0
            '', // OBX.21 - Reserved for harmonization with V2.6 0
            '', // OBX.22 - Reserved for harmonization with V2.6 0
            'AppB', // OBX.23 - Performing Organization Name 567
            'Hospital', // OBX.24 - Performing Organization Address 631
            'gr01', // OBX.25 - Performing Organization Medical Director 3002
    );
    message.addSegment('OBX', '4', // OBX.1 - Set ID - OBX	4	Sequence
            '', // OBX.2 - Value Type	2
            '', // OBX.3 - Observation Identifier	250
            '', // OBX.4 - Observation Sub-ID	20
            'RELATÓRIO:', // OBX.5 - Observation Value	99999 Inf
            '', // OBX.6  - Units 250
            '', // OBX.7  - References Range 60
            '', // OBX.8  - Abnormal Flags 5 Inf
            '', // OBX.9  - Probability 5
            '', // OBX.10 - Nature of Abnormal Test 2 Inf
            '', // OBX.11 - Observation Result Status 1
            '', // OBX.12 - Effective Date of Reference Range 26
            '', // OBX.13 - User Defined Access Checks 20
            '', // OBX.14 - Date/Time of the Observation 26
            '', // OBX.15 - Producer's ID 250
            '', // OBX.16 - Responsible Observer 250 Inf
            '', // OBX.17 - Observation Method 250 Inf
            '', // OBX.18 - Equipment Instance Identifier 22 Inf
        Date.now(), // OBX.19 - Date/Time of the Analysis 26
            '', // OBX.20 - Reserved for harmonization with V2.6 0
            '', // OBX.21 - Reserved for harmonization with V2.6 0
            '', // OBX.22 - Reserved for harmonization with V2.6 0
            'AppB', // OBX.23 - Performing Organization Name 567
            'Hospital', // OBX.24 - Performing Organization Address 631
            'gr01', // OBX.25 - Performing Organization Medical Director 3002
    );
    message.addSegment('OBX', '5', // OBX.1 - Set ID - OBX	4	Sequence
            '', // OBX.2 - Value Type	2
            '', // OBX.3 - Observation Identifier	250
            '', // OBX.4 - Observation Sub-ID	20
            '', // OBX.5 - Observation Value	99999 Inf
            '', // OBX.6  - Units 250
            '', // OBX.7  - References Range 60
            '', // OBX.8  - Abnormal Flags 5 Inf
            '', // OBX.9  - Probability 5
            '', // OBX.10 - Nature of Abnormal Test 2 Inf
            '', // OBX.11 - Observation Result Status 1
            '', // OBX.12 - Effective Date of Reference Range 26
            '', // OBX.13 - User Defined Access Checks 20
            '', // OBX.14 - Date/Time of the Observation 26
            '', // OBX.15 - Producer's ID 250
            '', // OBX.16 - Responsible Observer 250 Inf
            '', // OBX.17 - Observation Method 250 Inf
            '', // OBX.18 - Equipment Instance Identifier 22 Inf
        Date.now(), // OBX.19 - Date/Time of the Analysis 26
            '', // OBX.20 - Reserved for harmonization with V2.6 0
            '', // OBX.21 - Reserved for harmonization with V2.6 0
            '', // OBX.22 - Reserved for harmonization with V2.6 0
            'AppB', // OBX.23 - Performing Organization Name 567
            'Hospital', // OBX.24 - Performing Organization Address 631
            'gr01', // OBX.25 - Performing Organization Medical Director 3002
    );
    var set_id = 5;
    for (let index = 0; index < relatorio.length; index++) {
        var linha = relatorio[index];
        set_id += 1;
        message.addSegment('OBX', set_id, // OBX.1 - Set ID - OBX	4	Sequence
                '', // OBX.2 - Value Type	2
                '', // OBX.3 - Observation Identifier	250
                '', // OBX.4 - Observation Sub-ID	20
        linha, // OBX.5 - Observation Value	99999 Inf
                '', // OBX.6  - Units 250
                '', // OBX.7  - References Range 60
                '', // OBX.8  - Abnormal Flags 5 Inf
                '', // OBX.9  - Probability 5
                '', // OBX.10 - Nature of Abnormal Test 2 Inf
                '', // OBX.11 - Observation Result Status 1
                '', // OBX.12 - Effective Date of Reference Range 26
                '', // OBX.13 - User Defined Access Checks 20
                '', // OBX.14 - Date/Time of the Observation 26
                '', // OBX.15 - Producer's ID 250
                '', // OBX.16 - Responsible Observer 250 Inf
                '', // OBX.17 - Observation Method 250 Inf
                '', // OBX.18 - Equipment Instance Identifier 22 Inf
            Date.now(), // OBX.19 - Date/Time of the Analysis 26
                '', // OBX.20 - Reserved for harmonization with V2.6 0
                '', // OBX.21 - Reserved for harmonization with V2.6 0
                '', // OBX.22 - Reserved for harmonization with V2.6 0
                'AppB', // OBX.23 - Performing Organization Name 567
                'Hospital', // OBX.24 - Performing Organization Address 631
                'gr01', // OBX.25 - Performing Organization Medical Director 3002
        );
    }
}
