var Arqs = require('../models/arquivo');

module.exports.list = () => {
    return Arqs.find({}, {
        "_id": 0,
        "id": 1,
        "titulo": 1,
        "tipo": 1,
        "compositor": 1
    }).exec()
}

module.exports.consult = id => {
    return Arqs.findOne({id: id}).exec()
}

module.exports.listComposers = () => {
    return Arqs.distinct("compositor").exec()
}

module.exports.consultByComposers = () => {
    return Arqs.aggregate([
        {
            $unwind: "$compositor"
        }, {
            $group: {
                _id: "$compositor",
                obras: {
                    $push: {
                        id: "$id",
                        titulo: "$titulo"
                    }
                }
            }
        }
    ]).exec()
}

module.exports.consultByInstrument = () => {
    // Correto contudo a insercao na bd foi complicada 
    return Arqs.aggregate([
        {
            $unwind: "$instrumentos.designacao"
        }, {
            $group: {
                _id: "$instrumentos.designacao",
                obras: {
                    $push: {
                        id: "$id",
                        titulo: "$titulo"
                    }
                }
            }
        }
    ]).exec()
}

module.exports.listQuant = () => {
    return Arqs.aggregate([
            {
                $project: {
                    id: 1,
                    titulo: 1,
                    _id: 0,
                    numeroPartituras: {
                        $cond: {
                            if  : {
                                $isArray: "$instrumentos.partitura"
                            },
                            then: {
                                $size: "$instrumentos"
                            },
                            else  : "0"
                            }
                        }
                    }
                }
            ]
        ).exec()}
