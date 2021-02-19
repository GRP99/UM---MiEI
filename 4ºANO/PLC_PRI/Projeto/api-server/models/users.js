var mongoose = require('mongoose');

var UserSchema = new mongoose.Schema({
    _id: String,
    name: String,
    password: String,
    level: String,
    profilepic: Number,
    registrationDate: String,
    lastAccessDate: String,
    git: String,
    role: String,
    course: String,
    department: String
});


module.exports = mongoose.model('users', UserSchema);
