// USERS CONTROLLER
var Users = require('../models/users')

// retorna all the users
module.exports.listUsers = () => {
    return Users.find().sort({"lastAccessDate": -1}).exec();
}

// find that user
module.exports.lookUp = id => {
    return Users.findOne({_id: id}).exec();
}

// delete user
module.exports.deleteUser = id => {
    return Users.deleteOne({_id: id})
}

// insert a new user
module.exports.insereUser = p => {
    var newUser = new Users(p);
    newUser.profilepic = 0
    return newUser.save();
}

// update user profile
module.exports.updateUser = (id, n, g, c, d) => {
    return Users.update({
        _id: id
    }, {
        $set: {
            "name": n,
            "git": g,
            "course": c,
            "department": d
        }
    }).exec();
}

// change the profilepic
module.exports.updatePhoto = (id) => {
    Users.findOne({_id: id}).exec().then((result) => {
        result.profilepic = 1
        Users.findByIdAndUpdate(id, result, {new: true})
        return result.save()
    });
}

// search users
module.exports.search = (text) => {
    return Users.find({
        name: {
            $regex: text,
            "$options": "i"
        }
    }).sort({"name": -1, "lastAccessDate": -1, "role": -1}).exec();
}
