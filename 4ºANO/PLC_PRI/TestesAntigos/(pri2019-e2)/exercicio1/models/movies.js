const mongoose = require('mongoose')

var movieSchema = new mongoose.Schema({
    _id: {},
    title: {
        type: String
    },
    year: {
        type: Number
    },
    cast: {
        type: [String]
    },
    genres: {
        type: [String]
    }
});

module.exports = mongoose.model('movies', movieSchema)
