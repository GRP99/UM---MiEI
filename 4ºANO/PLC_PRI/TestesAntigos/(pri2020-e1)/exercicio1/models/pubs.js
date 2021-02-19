const mongoose = require('mongoose')


var pubsSchema = new mongoose.Schema({
    type: String,
    id: String,
    authors: [String],
    title: String,
    booktitle: String,
    address: String,
    year: String,
    month: String,
    doi: String
});

module.exports = mongoose.model('pubs', pubsSchema)
