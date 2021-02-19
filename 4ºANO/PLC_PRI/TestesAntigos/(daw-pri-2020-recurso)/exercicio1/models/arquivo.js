const mongoose = require('mongoose')

var partituraSchema = new mongoose.Schema({path: String, voz: String, type: String, clave: String});

var instrumentoSchema = new mongoose.Schema({
    designacao: {
        type: String,
        required: true
    },
    partitura: {
        type: partituraSchema,
        required: true
    }
});

// Desnecessário mais fácil seria por instrumentos com os atributos de instrumento
var instrumentosSchema = new mongoose.Schema({instrumento: [instrumentoSchema]});

var videoSchema = new mongoose.Schema({href: String});

var infrelacionadaSchema = new mongoose.Schema({video: {
        videoSchema
    }});

var arquivoSchema = new mongoose.Schema({
    id: {
        type: String,
        required: true
    },
    titulo: {
        type: String,
        required: true
    },
    tipo: String,
    compositor: String,
    arranjo: String,
    editado: String,
    subtitulo: String,
    instrumentos: [instrumentosSchema],
    infrelacionada: {
        infrelacionadaSchema
    }
});

module.exports = mongoose.model('arqs', arquivoSchema)
