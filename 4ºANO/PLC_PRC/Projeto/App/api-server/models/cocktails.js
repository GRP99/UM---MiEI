var mongoose = require("mongoose");

var reviewschema = new mongoose.Schema({
    publication_date: {
        type: String,
        required: true,
    },
    author: {
        type: String,
        required: true,
    },
    review: {
        type: String,
        required: true,
    },
    classification: Number,
});

var likeSchema = new mongoose.Schema({
    count: Number,
    authors: [String],
});

var CocktailSchema = new mongoose.Schema({
    _id: String,
    name: String,
    reviews: [reviewschema],
    likes: likeSchema,
    dislikes: likeSchema,
});

module.exports = mongoose.model("cocktails", CocktailSchema);