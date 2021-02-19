var Users = require('../models/users')

// return all the users
module.exports.listUsers = () => {
    return Users.find().exec();
}

// find that user
module.exports.lookUp = id => {
    return Users.findOne({_id: id}).exec();
}

// insert a new user
module.exports.insereUser = p => {
    var newUser = new Users(p);
    newUser.profilepic = 0
    return newUser.save();
}

// change the profile pic
module.exports.updatePhoto = (id) => {
    Users.findOne({_id: id}).exec().then((result) => {
        result.profilepic = 1
        Users.findByIdAndUpdate(id, result, {new: true})
        return result.save()
    });
}

// register last access
module.exports.registLastAcess = (id) => {
    var d = new Date().toISOString().substr(0, 16);
    return Users.updateOne({
        "_id": id
    }, {
        $set: {
            "lastAccessDate": d
        }
    }).exec();
}
