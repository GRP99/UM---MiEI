var mongoose = require('mongoose')

// Esquema de um aluno
var avalSchema = new mongoose.Schema({
    Número: String,
    Nome: String,
    Git: String,
    tpc: [Number]
});

// coleccao a exportar 
module.exports = mongoose.model('PRI2020', avalSchema, 'alunos')