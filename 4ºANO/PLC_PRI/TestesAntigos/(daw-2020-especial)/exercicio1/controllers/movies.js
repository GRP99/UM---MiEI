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

module.exports.listarAtores = () => {
    return Movies.distinct("cast").exec();
}

module.exports.consultarPorAtor = () => {
    return Movies.aggregate([
        {
            $unwind: "$cast"
        }, {
            $group: {
                _id: "$cast",
                filmes: {
                    $push: {
                        id: "$_id",
                        title: "$title"
                    }
                }
            }
        }
    ]).exec();
}

module.exports.consultarPorGenero = () => {
    return Movies.aggregate([
        {
            $unwind: "$genres"
        }, {
            $group: {
                _id: "$genres",
                filmes: {
                    $push: {
                        id: "$_id",
                        title: "$title"
                    }
                }
            }
        }
    ]).exec();
}


module.exports.quantAtor = () => {
    return Movies.aggregate([{
            $project: {
                "_id": 1,
                "titulo": 1,
                numeroAtoresParticpantes: {
                    $cond: {
                        if  : {
                            $isArray: "$cast"
                        },
                        then: {
                            $size: "$cast"
                        },
                        else  : "0"
                    }
                }
            }
        }]).exec();
}
