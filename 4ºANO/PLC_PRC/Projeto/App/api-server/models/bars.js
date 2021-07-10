var mongoose = require("mongoose");

var visitSchema = new mongoose.Schema({
    count: Number,
    authors: [String],
});

var BarSchema = new mongoose.Schema({
    _id: String,
    name: String,
    visits: visitSchema,
});

module.exports = mongoose.model("bars", BarSchema);