const mongoose = require('mongoose')

var instrSchema = new mongoose.Schema({
    designacao: String,
    afinacao: String,
    voz: String,
    partitura:{
        path: String,
        voz: String,
        clave: String
    }
})

var arqsSchema = new mongoose.Schema({
    id: String,
    titulo: String,
    tipo: String,
    compositor: String,
    instrumentos: [instrSchema]
});

module.exports = mongoose.model('arqs', arqsSchema)
