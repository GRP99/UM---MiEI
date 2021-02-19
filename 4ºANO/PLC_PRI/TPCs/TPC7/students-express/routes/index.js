var express = require('express');
var router = express.Router();

const Aluno = require('../controllers/aluno');

// Pedidos GET -> utilizou-se window.location.assign para efectuar os pedidos e posteriormente a transicao de paginas
// GET da página principal
router.get(['/alunos', '/'], function (req, res, next) {
    Aluno.listar()
        .then(dados => res.render('index', {
            lista: dados
        }))
        .catch(e => res.render('error', {
            error: e
        }))
})

// GET da página de adição de alunos -> invocado a partir do index.pug
router.get('/alunos/registar', function (req, res, next) {
    res.render('form')
})

// GET da página de um aluno -> invocado a partir do index.pug
router.get('/alunos/:id', function (req, res, next) {
    Aluno.consultar(req.params.id)
        .then(aluno => res.render('aluno', {
            aluno
        }))
        .catch(e => res.render('error', {
            error: e
        }))
})

// GET da página de edição de um aluno -> invocado a partir do index.pug
router.get('/alunos/editar/:id', function (req, res, next) {
    Aluno.consultar(req.params.id)
        .then(aluno => res.render('atualizar', {
            aluno
        }))
        .catch(e => res.render('error', {
            error: e
        }))
})

// Pedido POST de um novo aluno -> efectuado no metodo do form.pug 
router.post('/alunos', function (req, res, next) {
    Aluno.inserir(req)
        .then(dados => {
            console.log("Aluno gravado com sucesso...")
            res.redirect("/alunos/" + dados._id)
        })
        .catch(e => res.render('error', {
            error: e
        }))
})

// Pedido PUT de um aluno atualizado -> efectuado atraves da funcoes em javascripts (atualizar invocado no atualizar.pug)
router.put("/alunos/:id", function (req, res, next) {
    Aluno.atualizar(req.params.id, req.body)
        .then(dados => {
            console.log("Aluno atualizado com sucesso...")
            // O código HTTP 200 OK e a resposta de status de sucesso que indica que a requisicao foi bem sucedida
            res.sendStatus(200)
        })
        .catch(e => res.render('error', {
            error: e
        }))
})

// Pedido PUT de um tpc - adicionar -> efectuado atraves da funcoes em javascripts (adicionarTPC invocado no aluno.pug)
router.put("/alunos/addtpc/:id", function (req, res, next) {
    Aluno.addTPC(req.params.id)
        .then(dados => {
            console.log("Adicionado TPC...")
            res.sendStatus(200)
        })
        .catch(e => res.render('error', {
            error: e
        }))
})

// Pedido PUT de um tpc - remover -> efectuado atraves da funcoes em javascripts (removerTPC invocado no aluno.pug)
router.put("/alunos/remtpc/:id", function (req, res, next) {
    Aluno.removeTPC(req.params.id)
        .then(dados => {
            console.log("Removido TPC...")
            res.sendStatus(200)
        })
        .catch(e => res.render('error', {
            error: e
        }))
})

// Pedido DELETE de um aluno -> efectuado atraves da funcoes em javascripts (eliminar invocado no aluno.pug como tambem em index.pug)
router.delete("/alunos/:id", function (req, res, next) {
    Aluno.eliminar(req.params.id)
        .then(dados => {
            console.log("Aluno eliminado com sucesso...")
            res.sendStatus(200)
        })
        .catch(e => res.render('error', {
            error: e
        }))
})


module.exports = router;