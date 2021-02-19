var Arqs = require('../models/arqs');

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

module.exports.listTypes = () => {
    return Arqs.distinct("tipo").exec()
}

module.exports.consultCompObr = compositor => {
    return Arqs.aggregate(([{
            $match: {
                compositor: compositor
            }
        }])).exec()
}

module.exports.consultInstrObr = instr => {
    return Arqs.find({
        instrumentos: {
            $elemMatch: {
                designacao: instr
            }
        }
    }).exec()
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
