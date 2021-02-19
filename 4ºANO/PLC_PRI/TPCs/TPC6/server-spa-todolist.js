var http = require('http')
var axios = require('axios')
var fs = require('fs')

// Importar o ficheiro static.js
var static = require('./static')
// Apenas utilizar a funcao parse da biblioteca querystring
var { parse } = require('querystring')


// Aux. Functions
// Retrieves student info from request body --------------------------------
function recuperaInfo(request, callback) {
    if (request.headers['content-type'] == 'application/x-www-form-urlencoded') {
        let body = ''
        request.on('data', bloco => {
            body += bloco.toString()
        })
        request.on('end', () => {
            console.log(body)
            callback(parse(body))
        })
    }
}

function geraPagTarefasNPendentes(responseTarefasNPendentes, d) {
    let pagHTML = `
              <div class="w3-container w3-blue-grey">
                  <h2>Tarefas Realizadas / Canceladas</h2>
              </div>
              <table id="TarefasNPendentes" class="w3-table w3-bordered">
                  <tr>
                      <th>Data Limite</th>
                      <th>Responsável</th>
                      <th>Descrição</th>
                      <th>Estado</th>
                  </tr>
    `
    responseTarefasNPendentes.forEach(t => {
        pagHTML +=
            ` <tr>
                  <td>${t.datalimite}</td>
                  <td>${t.responsavel}</td>
                  <td>${t.descricao}</td>
                  <td>${t.estado}</td>
              </tr>`

    });

    pagHTML += `
            </table>
            <div class="w3-container w3-blue-grey">
                <address> Gerado por Gonçalo Pinto::PRI2020 em ${d} --------------</address>
            </div>
        </body>
        </html>
      `
    return pagHTML
}


function geraPagTarefasPendentes(responseTarefasPendentes) {
    let pagHTML = `
            <div class="w3-container w3-blue-grey">
                <h2>Lista de Tarefas Pendentes</h2>
            </div>
            <table id="TarefasPendentes" class="w3-table w3-bordered">
                <tr>
                    <th> Data Limite </th>
                    <th> Responsável </th>
                    <th> Descrição </th>
                    <th> Marcar como Realizada </th>
                    <th> Marcar como Cancelada </th>
                </tr>
  `
    responseTarefasPendentes.forEach(t => {
        pagHTML += `
        <tr id=${t.id}>
            <td>${t.datalimite}</td>
            <td>${t.responsavel}</td>
            <td>${t.descricao}</td>
            <td style="text-align:center">
            <button class="w3-button w3-teal w3-circle" onclick="accomplish(${t.id})" type="submit" value="Realizar">&#10003</button>
            </td>
            <td style="text-align:center">
            <button class="w3-button w3-red w3-circle" onclick="cancel(${t.id})" type="submit" value="Cancelar">&#10008</button>
            </td>
        </tr>`
    })
    pagHTML += ` </table>`
    return pagHTML
}

function geraFormNovaTarefa() {
    let pagHTML = `<html>
    <head>
        <title>Tarefas</title>
        <meta charset="utf-8"/>
        <link rel="icon" href="favicon.png"/>
        <link rel="stylesheet" href="w3.css"/>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
        <script type="text/javascript" src="functions.js" ></script>
    </head>
    <body>
        <div class="w3-container w3-blue-grey">
            <h2>Registar Nova Tarefa</h2>
        </div>

        <form class="w3-container" action="/tarefas" method="POST">

            <label class="w3-text-gray"><b>Descrição</b></label>
            <input class="w3-input w3-border w3-light-grey" type="text" name="descricao">

            <label class="w3-text-gray"><b>Responsável</b></label>
            <input class="w3-input w3-border w3-light-grey" type="text" name="responsavel">

            <label class="w3-text-gray"><b>Data Limite [AAAA-MM-DD]</b></label>
            <input class="w3-input w3-border w3-light-grey" type="text" name="datalimite">

            <input type="hidden" name="estado" value="arealizar"/>

            <input class="w3-btn w3-blue-grey" type="submit" value="Registar"/>
            <input class="w3-btn w3-blue-grey" type="reset" value="Limpar valores"/> 
        </form>`
    return pagHTML
}


function geraPagPrincipal(res, d) {
    // GET tarefas arealizar
    var requestTarefasPendentes = axios.get("http://localhost:3000/tarefas?estado=arealizar&_sort=datalimite,responsavel&_order=asc,desc")
    // GET tarefas that the estado is not equal(_ne) arealizar  
    var requestTarefasNPendentes = axios.get("http://localhost:3000/tarefas?estado_ne=arealizar&_sort=datalimite,responsavel&_order=asc,desc")

    // Send multiple requests
    axios.all([requestTarefasPendentes, requestTarefasNPendentes])
        .then(axios.spread((...responses) => {
            var responseTarefasPendentes = responses[0].data
            var responseTarefasNPendentes = responses[1].data

            res.writeHead(200, { 'Content-Type': 'text/html;charset=utf-8' })
            res.write(geraFormNovaTarefa())
            res.write(geraPagTarefasPendentes(responseTarefasPendentes))
            res.write(geraPagTarefasNPendentes(responseTarefasNPendentes, d))
            res.end()
        }))
        .catch(function (erro) {
            res.writeHead(200, { 'Content-Type': 'text/html;charset=utf-8' })
            res.write("<p>Não foi possível obter a lista de tarefas...")
            res.end()
        })
}

// Server setup
var todolistServer = http.createServer(function (req, res) {
    var d = new Date().toISOString().substr(0, 16)
    console.log(req.method + " " + req.url + " " + d)

    // Request processing
    // Tests if a static resource is requested
    if (static.recursoEstatico(req)) {
        static.sirvoRecursoEstatico(req, res)
    }
    else {
        // Normal request
        switch (req.method) {
            case "GET":
                if ((req.url == "/") || (req.url == "/tarefas")) {
                    geraPagPrincipal(res, d)
                }
                else if (/\/tarefas\/*/.test(req.url)) {
                    var idt = req.url.split("/")[2]
                    axios.get("http://localhost:3000/tarefas/" + idt).
                        then(response => {
                            res.writeHead(200, { 'Content-Type': 'application/json' });
                            res.end(JSON.stringify(response.data));
                        })
                        .catch(function (erro) {
                            console.log(erro)
                        })
                }
                else if (/\/realizartarefa/.test(req.url)) {
                    var idt = req.url.split("=")[1]
                    axios.get("http://localhost:3000/tarefas/" + idt)
                        .then(response => {
                            requestget = response.data

                            axios.put("http://localhost:3000/tarefas/" + idt, {
                                descricao: requestget.descricao,
                                responsavel: requestget.responsavel,
                                datalimite: requestget.datalimite,
                                estado: 'realizada'
                            })
                                .then(response => {
                                    res.end();
                                })
                                .catch(err => {
                                    console.log(err);
                                });
                        })
                        .catch(err => {
                            console.log(err);
                        });
                }
                else if (/\/cancelartarefa/.test(req.url)) {
                    var idt = req.url.split("=")[1]
                    axios.get("http://localhost:3000/tarefas/" + idt)
                        .then(response => {
                            requestget = response.data

                            axios.put("http://localhost:3000/tarefas/" + idt, {
                                descricao: requestget.descricao,
                                responsavel: requestget.responsavel,
                                datalimite: requestget.datalimite,
                                estado: 'cancelada'
                            })
                                .then(response => {
                                    res.end()
                                })
                                .catch(err => {
                                    console.log(err);
                                });
                        })
                        .catch(err => {
                            console.log(err);
                        });
                }
                else if (req.url == "/functions.js") {
                    fs.readFile("functions.js", function (erro, dados) {
                        if (!erro) {
                            res.writeHead(200, { 'Content-Type': 'text/css;charset=utf-8' })
                            res.write(dados)
                            res.end()
                        }
                    })
                }
                else {
                    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' })
                    res.write("<p>" + req.method + " " + req.url + " não suportado neste serviço. </p>")
                    res.end()
                }
                break
            case "POST":
                if (req.url == '/tarefas') {
                    recuperaInfo(req, resultado => {
                        console.log('POST de tarefas:' + JSON.stringify(resultado))
                        if (resultado.estado == 'arealizar') {
                            axios.post('http://localhost:3000/tarefas', resultado)
                                .then(response => {
                                    res.writeHead(200, { 'Content-Type': 'text/html;charset=utf-8' })
                                    res.writeHead(302, { Location: "/" })
                                    res.end()
                                })
                                .catch(function (erro) {
                                    res.writeHead(200, { 'Content-Type': 'text/html;charset=utf-8' })
                                    res.write("<p>Não foi possível adicionar a tarefa...")
                                    res.end()
                                })
                        }
                    })
                }
                else {
                    res.writeHead(200, { 'Content-Type': 'text/html;charset=utf-8' })
                    res.write("<p> POST " + req.url + " não suportado neste serviço.</p>")
                    res.end()
                }
                break
            default:
                res.writeHead(200, { 'Content-Type': 'text/html;charset=utf-8' })
                res.write("<p>" + req.method + " não suportado neste serviço.</p>")
                res.end()
        }
    }
})

todolistServer.listen(7777)
console.log('Servidor à escuta na porta 7777...')