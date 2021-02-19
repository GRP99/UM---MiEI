const mongoose = require('mongoose')

var LaureateSchema = new mongoose.Schema({
    id: {
        type: String
    },
    firstname: {
        type: String
    },
    surname: {
        type: String
    },
    motivation: {
        type: String
    },
    share: {
        type: String
    }
})

var prizeSchema = new mongoose.Schema({
    _id: {},
    year: {
        type: String
    },
    category: {
        type: String
    },
    overallMotivation: {
        type: String
    },
    laureates: {
        type: [LaureateSchema]
    }
});

module.exports = mongoose.model('prizes', prizeSchema)