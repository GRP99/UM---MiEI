const mongoose = require('mongoose')


var tweetSchema = new mongoose.Schema({
    autor: String,
    palavrachave: String,
    texto: String,
    gostos: {
        type: Number,
        default: 0
    }
});

module.exports = mongoose.model('tweets', tweetSchema)
