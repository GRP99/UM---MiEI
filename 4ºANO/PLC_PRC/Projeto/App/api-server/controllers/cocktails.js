// COCKTAILS CONTROLLER
var Cocktails = require("../models/cocktails");

module.exports.listCocktails = () => {
    return Cocktails.find().exec();
};

module.exports.lookUp = (id) => {
    return Cocktails.findOne({
        _id: id
    }).exec();
};

module.exports.deleteCocktail = (id) => {
    return Cocktails.deleteOne({
        _id: id
    });
};

module.exports.insertCocktail = (c) => {
    var newC = new Cocktails(c);
    return newC.save();
};

module.exports.isAuthorofReview= (idC,idR,id_autor) => {
    return Cocktails.findOne({
        "_id": idC,
        "reviews._id": idR,
        "reviews.author": id_autor
    });
};

module.exports.addReview = (idR, review) => {
    return Cocktails.updateOne({
        _id: idR,
    }, {
        $push: {
            reviews: review,
        },
    }).exec();
};

// remove comentary
module.exports.removeReview = (idC, idR) => {
    return Cocktails.updateOne({
        _id: idC,
    }, {
        $pull: {
            "reviews": {
                "_id": idR,
            },
        },
    }).exec();
};

// add like by user
module.exports.addlike = (id, idU) => {
    return Cocktails.updateOne({
        _id: id,
    }, {
        $push: {
            "likes.authors": idU,
        },
        $inc: {
            "likes.count": 1,
        },
    }).exec();
};

// remove like by user
module.exports.removelike = (id, idU) => {
    return Cocktails.updateOne({
        _id: id,
    }, {
        $pull: {
            "likes.authors": idU,
        },
        $inc: {
            "likes.count": -1,
        },
    }).exec();
};

// add dislike by user
module.exports.adddislike = (id, idU) => {
    return Cocktails.updateOne({
        _id: id,
    }, {
        $push: {
            "dislikes.authors": idU,
        },
        $inc: {
            "dislikes.count": 1,
        },
    }).exec();
};

// remove dislike by user
module.exports.removedislike = (id, idU) => {
    return Cocktails.updateOne({
        _id: id,
    }, {
        $pull: {
            "dislikes.authors": idU,
        },
        $inc: {
            "dislikes.count": -1,
        },
    }).exec();
};

// get cocktails liked by user
module.exports.getlikes = (idU) => {
    return Cocktails.find({
        "likes.authors": idU
    });
};

// get cocktails disliked by user
module.exports.getdislikes = (idU) => {
    return Cocktails.find({
        "dislikes.authors": idU
    });
};

// get reviews
module.exports.getreviews = (idU) => {
    return Cocktails.find({
        "reviews.author": idU
    });
};