var Bartenders = require("../models/bartenders");

module.exports.listBartenders = () => {
    return Bartenders.find().exec();
};

module.exports.lookUp = (id) => {
    return Bartenders.findOne({
        _id: id
    }).exec();
};

module.exports.deleteBartender = (id) => {
    return Bartenders.deleteOne({
        _id: id
    });
};

module.exports.insertBartender = (c) => {
    var newC = new Bartenders(c);
    return newC.save();
};

module.exports.addReview = (idR, review) => {
    return Bartenders.updateOne({
        _id: idR,
    }, {
        $push: {
            reviews: review,
        },
    }).exec();
};

// remove comentary
module.exports.removeReview = (idC, idR) => {
    return Bartenders.updateOne({
        _id: idC,
    }, {
        $pull: {
            reviews: {
                _id: idR,
            },
        },
    }).exec();
};

// add like by user
module.exports.addlike = (id, idU) => {
    return Bartenders.updateOne({
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
    return Bartenders.updateOne({
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
    return Bartenders.updateOne({
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
    return Bartenders.updateOne({
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

// get bartenders liked by user
module.exports.getlikes = (idU) => {
    return Bartenders.find({
        "likes.authors": idU
    });
};

// get bartenders disliked by user
module.exports.getdislikes = (idU) => {
    return Bartenders.find({
        "dislikes.authors": idU
    });
};

// get reviews
module.exports.getreviews = (idU) => {
    return Bartenders.find({
        "reviews.author": idU
    });
};

module.exports.isAuthorofReview= (idB,idR,id_autor) => {
    return Bartenders.findOne({
        "_id": idB,
        "reviews._id": idR,
        "reviews.author": id_autor
    });
};