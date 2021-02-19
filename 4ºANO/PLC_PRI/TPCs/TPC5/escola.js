var http = require('http')
const axios = require('axios');

var servidor = http.createServer(function (req, res) {
    console.log(req.method + ' ' + req.url)

    if (req.method == 'GET') {
        if (req.url == '/') {
            res.writeHead(200, {
                'Content-Type': 'text/html; charset=utf-8'
            })
            res.write('<style> h1{text-align:center;font-size:50px;} h3{margin-left:13px;} body{background-color:LightGray;} footer{text-align:center;padding:3px;position:fixed;left:0;bottom:0;width:100%;} a:link{text-decoration:none;} a:hover{text-decoration:underline;} a:active{text-decoration:underline;} </style>')
            res.write('<head> <title>Arquivo da escola de música</title> </head>')
            res.write('<body>')
            res.write('<h1 style="border: 2px solid white;"> Escola de Música </h1>')
            res.write('<u> <h3> Índice: </h3> </u>')
            res.write('<ul>')
            res.write('<li> <b> <a style="font-weight:bold" href=\"http://localhost:3001/alunos\"> Lista de Alunos </a> </b> </li>')
            res.write('<li> <b> <a style="font-weight:bold" href=\"http://localhost:3001/cursos\"> Lista de Cursos </a> </b> </li>')
            res.write('<li> <b> <a style="font-weight:bold" href=\"http://localhost:3001/instrumentos\"> Lista de Instrumentos </a> </b> </li>')
            res.write('</ul>')
            res.write('<footer>')
            res.write('<p> Autor: Gonçalo Rodrigues Pinto </p>')
            res.write('<p>MiEI - 4ºAno - 1ºSemestre - PRI 20/21</p>')
            res.write('<p> <a href="mailto:a83732@alunos.uminho.pt">a83732@alunos.uminho.pt</a> </p>')
            res.write('<a href="https://github.com/GRP99/PRI2020" target="_blank">')
            res.write('<img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Logo.png" alt="Github" style="width:100px;height:50px;"></img></a>')
            res.write('</footer>')
            res.write('</body>')
            res.end()
        }
        else if (req.url == '/alunos') {
            axios.get('http://localhost:3000/alunos?_sort=id')
                .then(resp => {
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<style> h1{text-align:center;font-size:50px;} h3{margin-left:13px;} body{background-color:LightGray;} footer{text-align:center;padding:3px;position:fixed;left:0;bottom:0;width:100%;} a:link{text-decoration:none;} a:hover{text-decoration:underline;} a:active{text-decoration:underline;} </style>')
                    res.write('<head> <title>Arquivo da escola de música - Alunos </title></head>')
                    res.write('<body>')
                    res.write('<u> <h3> Lista de Alunos: </h3> </u>')
                    alunos = resp.data;
                    res.write('<ul>')
                    alunos.forEach(a => {
                        res.write(`<li> <b> <a style="font-weight:bold" href=\"http://localhost:3001/alunos/${a.id}\"> ${a.id} : ${a.nome} </a> </b> </li>`)
                    });
                    res.write('</ul>')
                    res.write('<footer><address>[<a href=\"http://localhost:3001\">Voltar ao índice </a>]</address></footer>');
                    res.write('</body>')
                    res.end();
                })
                .catch(error => {
                    console.log('ERRO: ' + error);
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<p>Não consegui obter a lista de alunos...</p>')
                    res.end()
                });
        }
        else if (req.url == '/cursos') {
            axios.get('http://localhost:3000/cursos')
                .then(resp => {
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<style> h1{text-align:center;font-size:50px;} h3{margin-left:13px;} body{background-color:LightGray;} footer{text-align:center;padding:3px;} a:link{text-decoration:none;} a:hover{text-decoration:underline;} a:active{text-decoration:underline;} </style>')
                    res.write('<head> <title>Arquivo da escola de música - Cursos </title></head>')
                    res.write('<body>')
                    res.write('<u> <h3> Lista de Cursos: </h3> </u>')
                    cursos = resp.data;
                    res.write('<ul>')
                    cursos.forEach(c => {
                        res.write(`<li> <b> <a style="font-weight:bold" href=\"http://localhost:3001/cursos/${c.id}\"> ${c.id} : ${c.designacao} </b> </li>`)
                    });
                    res.write('</ul>')
                    res.write('<footer><address>[<a href=\"http://localhost:3001\">Voltar ao índice </a>]</address></footer>');
                    res.write('</body>')
                    res.end();
                })
                .catch(error => {
                    console.log('ERRO: ' + error);
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<p>Não consegui obter a lista de cursos...</p>')
                    res.end()
                });
        }
        else if (req.url == '/instrumentos') {
            axios.get('http://localhost:3000/instrumentos')
                .then(resp => {
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<style> h1{text-align:center;font-size:50px;} h3{margin-left:13px;} body{background-color:LightGray;} footer{text-align:center;padding:3px;position:fixed;left:0;bottom:0;width:100%;} a:link{text-decoration:none;} a:hover{text-decoration:underline;} a:active{text-decoration:underline;} </style>')
                    res.write('<head> <title>Arquivo da escola de música - Instrumentos </title></head>')
                    res.write('<body>')
                    res.write('<u> <h3> Lista de Intrumentos: </h3> </u>')
                    instrumentos = resp.data;
                    res.write('<ul>')
                    instrumentos.forEach(i => {
                        res.write(`<li> <b> ${i.id} : ${i.text} </b> </li>`);
                    });
                    res.write('</ul>')
                    res.write('<footer><address>[<a href=\"http://localhost:3001\">Voltar ao índice </a>]</address></footer>');
                    res.write('</body>')
                    res.end();
                })
                .catch(error => {
                    console.log('ERRO: ' + error);
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<p>Não consegui obter a lista de instrumentos...</p>')
                    res.end()
                });
        }
        else if (req.url.match(/\/alunos\/[A]([E][\-])?[0-9]{2,5}\/curso\/[C][BS][0-9]{1,2}/)) {
            var splits = req.url.split("/")
            console.log(splits[4])
            axios.get('http://localhost:3000/cursos?q=' + splits[4] + '&_limit=1')
                .then(resp => {
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<style> h1{text-align:center;font-size:50px;} h3{margin-left:13px;} body{background-color:LightGray;} footer{text-align:center;padding:3px;position:fixed;left:0;bottom:0;width:100%;} a:link{text-decoration:none;} a:hover{text-decoration:underline;} a:active{text-decoration:underline;} </style>')
                    res.write('<head> <title>Arquivo da escola de música - Aluno ' + splits[2] + ' - Curso: ' + splits[4] + '</title></head>')
                    res.write('<meta name="viewport" content="width=device-width, initial-scale=1"/>')
                    res.write('<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"/>')
                    res.write('<body><div class="w3-container">')
                    res.write('<table class="w3-table-all w3-card-4">')
                    cursos = resp.data;
                    res.write('<ul>')
                    cursos.forEach(c => {
                        res.write(`<tr><th>ID</th><td> ${c.id} </td</tr>`);
                        res.write(`<tr><th>DESIGNAÇÃO</th><td> ${c.designacao} </td</tr>`);
                        res.write(`<tr><th>DURAÇÃO</th><td> ${c.duracao} </td</tr>`);
                        res.write(`<tr><th>INSTRUMENTO</th><td> ${c.instrumento.id} : ${c.instrumento.text} </td</tr>`);
                    });
                    res.write('</ul>')
                    res.write('</table></div>')
                    res.write('<footer><address>[<a href=\"http://localhost:3001/alunos/' + splits[2] + '\">Voltar ao aluno</a>]</address></footer>');
                    res.write('</body>')
                    res.end();
                })
                .catch(error => {
                    console.log('ERRO: ' + error);
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<p>Não consegui obter a descrição do curso ' + splits[4] + ' referente ao aluno ' + splits[2] + '...</p>')
                    res.end()
                });
        }
        else if (req.url.match(/\/cursos\/[C][BS][0-9]{1,2}\/listaalunos/)) {
            var splits = req.url.split("/")
            axios.get('http://localhost:3000/alunos?curso=' + splits[2] + '&_sort=anoCurso')
                .then(resp => {
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<style> h1{text-align:center;font-size:50px;} h3{margin-left:13px;} body{background-color:LightGray;} footer{text-align:center;padding:3px;position:fixed;left:0;bottom:0;width:100%;} a:link{text-decoration:none;} a:hover{text-decoration:underline;} a:active{text-decoration:underline;} </style>')
                    res.write('<head> <title>Arquivo da escola de música - Curso ' + splits[2] + ' - Lista de Alunos</title></head>')
                    res.write('<meta name="viewport" content="width=device-width, initial-scale=1"/>')
                    res.write('<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"/>')
                    res.write('<body><div class="w3-container">')
                    res.write('<table class="w3-table-all w3-card-4">')
                    alunos = resp.data;
                    res.write('<ul>')
                    alunos.forEach(a => {
                        res.write(`<li> <b> ${a.id} : ${a.nome} : ANO CURSO = ${a.anoCurso} </b> </li>`)
                    });
                    res.write('</ul>')
                    res.write('</table></div>')
                    res.write('<footer><address>[<a href=\"http://localhost:3001/cursos/' + splits[2] + '\">Voltar ao curso</a>]</address></footer>');
                    res.write('</body>')
                    res.end();
                })
                .catch(error => {
                    console.log('ERRO: ' + error);
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<p>Não consegui obter a descrição do curso ' + splits[4] + ' referente ao aluno ' + splits[2] + '...</p>')
                    res.end()
                });
        }
        else if (req.url.match(/\/alunos\/[A]([E][\-])?[0-9]{2,5}/)) {
            var splits = req.url.split("/")
            axios.get('http://localhost:3000/alunos?q=' + splits[2])
                .then(resp => {
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<style> h1{text-align:center;font-size:50px;} h3{margin-left:13px;} body{background-color:LightGray;} footer{text-align:center;padding:3px;position:fixed;left:0;bottom:0;width:100%;} a:link{text-decoration:none;} a:hover{text-decoration:underline;} a:active{text-decoration:underline;} </style>')
                    res.write('<head> <title>Arquivo da escola de música - Aluno: ' + splits[2] + '</title></head>')
                    res.write('<meta name="viewport" content="width=device-width, initial-scale=1"/>')
                    res.write('<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"/>')
                    res.write('<body><div class="w3-container">')
                    res.write('<table class="w3-table-all w3-card-4">')
                    alunos = resp.data;
                    res.write('<ul>')
                    alunos.forEach(a => {
                        res.write(`<tr><th>ID</th><td> ${a.id} </td</tr>`);
                        res.write(`<tr><th>NOME</th><td> ${a.nome} </td</tr>`);
                        res.write(`<tr><th>DATA NASCIMENTO</th><td> ${a.dataNasc} </td</tr>`);
                        res.write(`<tr><th>CURSO</th><td> <a style="font-weight:bold" href=\"http://localhost:3001/alunos/${a.id}/curso/${a.curso}\"> ${a.curso} </a> </td</tr>`);
                        res.write(`<tr><th>ANO CURSO</th><td> ${a.anoCurso} </td</tr>`);
                        res.write(`<tr><th>INSTRUMENTO</th><td> ${a.instrumento} </td</tr>`);
                    });
                    res.write('</ul>')
                    res.write('</table></div>')
                    res.write('<footer><address>[<a href=\"http://localhost:3001/alunos\">Voltar à lista de alunos</a>]</address></footer>');
                    res.write('</body>')
                    res.end();
                })
                .catch(error => {
                    console.log('ERRO: ' + error);
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<p>Não consegui obter a descrição do aluno...</p>')
                    res.end()
                });
        }
        else if (req.url.match(/\/cursos\/[C][BS][0-9]{1,2}/)) {
            var splits = req.url.split("/")
            axios.get('http://localhost:3000/cursos?q=' + splits[2] + '&_limit=1')
                .then(resp => {
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<style> h1{text-align:center;font-size:50px;} h3{margin-left:13px;} body{background-color:LightGray;} footer{text-align:center;padding:3px;position:fixed;left:0;bottom:0;width:100%;} a:link{text-decoration:none;} a:hover{text-decoration:underline;} a:active{text-decoration:underline;} </style>')
                    res.write('<head> <title>Arquivo da escola de música - Curso: ' + splits[2] + '</title></head>')
                    res.write('<meta name="viewport" content="width=device-width, initial-scale=1"/>')
                    res.write('<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"/>')
                    res.write('<body><div class="w3-container">')
                    res.write('<table class="w3-table-all w3-card-4">')
                    cursos = resp.data;
                    res.write('<ul>')
                    cursos.forEach(c => {
                        res.write(`<tr><th>ID</th><td> ${c.id} </td</tr>`);
                        res.write(`<tr><th>DESIGNAÇÃO</th><td> ${c.designacao} </td</tr>`);
                        res.write(`<tr><th>DURAÇÃO</th><td> ${c.duracao} </td</tr>`);
                        res.write(`<tr><th>INSTRUMENTO</th><td> ${c.instrumento.id} : ${c.instrumento.text} </td</tr>`);
                        res.write(`<tr><th> <a href=\"http://localhost:3001/cursos/${c.id}/listaalunos\"> LISTA DE ALUNOS  </a> </th><td>`)
                    });
                    res.write(`</td</tr>`)
                    res.write('</ul>')
                    res.write('</table></div>')
                    res.write('<footer><address>[<a href=\"http://localhost:3001/cursos\">Voltar à lista de cursos</a>]</address></footer>');
                    res.write('</body>')
                    res.end();
                })
                .catch(error => {
                    console.log('ERRO: ' + error);
                    res.writeHead(200, {
                        'Content-Type': 'text/html; charset=utf-8'
                    })
                    res.write('<p>Não consegui obter a descrição do curso ' + splits[2] + '...</p>')
                    res.end()
                });
        }
        else {
            res.writeHead(200, {
                'Content-Type': 'text/html; charset=utf-8'
            })
            res.write('<p>Pedido não suportado: ' + req.method + '</p>')
            res.end()
        }
    }
})

servidor.listen(3001)
console.log('Servidor à escuta na porta 3001...')