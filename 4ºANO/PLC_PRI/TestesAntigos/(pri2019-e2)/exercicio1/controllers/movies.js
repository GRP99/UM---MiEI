var Movies = require('../models/movies');

module.exports.listar = () => {
    return Movies.find({}, {
        "_id": 0,
        "title": 1,
        "year": 1
    }).exec();
}

module.exports.consultar = id => {
    return Movies.findOne({"_id": id}).exec();
}

module.exports.consultarGenero = g => {
    return Movies.find({genres: g}).exec()
}

module.exports.consultarGeneroData = (g, d) => {
    return Movies.find({
        genres: g,
        year: {
            $gte: d
        }
    }).exec()
}

module.exports.listarGeneros = () => {
    return Movies.distinct("genres").exec();
}

module.exports.listarAtores = () => {
    return Movies.distinct("cast").exec();
}
