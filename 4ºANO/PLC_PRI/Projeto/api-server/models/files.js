var mongoose = require('mongoose');

var estrelaSchema = new mongoose.Schema({
    numero: {
        type: Number,
        default: 0,
        required: true
    },
    autores: [String]
});

var comentarioSchema = new mongoose.Schema({
    id: String,
    data: {
        type: String,
        default: new Date().getTime(),
        required: true
    },
    autor: {
        type: String,
        required: true
    },
    descricao: {
        type: String,
        required: true
    }
});

var FileSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true
    },
    subtitle: {
        type: String,
        default: ""
    },
    resourceType: String,
    creationDate: String,
    registrationDate: String,
    autor: String,
    name: String,
    mimetype: String,
    size: Number,
    privacy: Number,
    filepath: String,
    descricao: String,
    comentarios: [comentarioSchema],
    estrelas: {
        type: estrelaSchema,
        required: true
    },
    favoritos: {
        type: [String],
        default: [],
        required: true
    }
});

module.exports = mongoose.model('files', FileSchema)
