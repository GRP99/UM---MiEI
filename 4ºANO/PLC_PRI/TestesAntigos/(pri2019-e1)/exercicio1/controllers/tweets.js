var Tweets = require('../models/tweets');

module.exports.listar = () => {
    return Tweets.find({}, {}).exec()
}

module.exports.inserir = t => {
    var novo = new Tweets(t);
    return novo.save();
}

module.exports.incrementaGostos = id => {
    return Tweets.updateOne({
        _id: id
    }, {
        $inc: {
            "gostos": 1
        }
    }).exec()
}
