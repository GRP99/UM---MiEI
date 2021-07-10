var mongoose = require("mongoose");

var UserSchema = new mongoose.Schema({
    _id: String,
    username: String,
    password: String,
    level: String,
});

module.exports = mongoose.model("users", UserSchema);