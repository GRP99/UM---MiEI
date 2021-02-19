const mongoose = require('mongoose')


var compositorSchema = new mongoose.Schema({
    id: {
        type: String
    },
    nome: {
        type: String
    },
    bio: {
        type: String
    },
    dataNasc: {
        type: String
    },
    dataObito: {
        type: String
    },
    periodo: {
        type: String
    }
});

module.exports = mongoose.model('compositores', compositorSchema)
