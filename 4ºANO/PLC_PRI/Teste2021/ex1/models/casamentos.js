const mongoose = require('mongoose')

var casamentoSchema = new mongoose.Schema({_id: String, date: String, title: String, href: String});

module.exports = mongoose.model('casamentos', casamentoSchema)
