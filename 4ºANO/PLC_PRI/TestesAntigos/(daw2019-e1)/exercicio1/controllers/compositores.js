var Compositores = require('../models/compositores');

module.exports.listar = () => {
    return Compositores.find({}, {
        "_id": 0,
        "id": 1,
        "nome": 1,
        "dataNasc": 1
    }).exec()
}

module.exports.consultar = id => {
    return Compositores.findOne({id: id}).exec()
}

module.exports.consultarPeriodo = (p) => {
    return Compositores.find({
        periodo: p
    }, {
        "_id": 0,
        "id": 1,
        "nome": 1,
        "dataNasc": 1,
        "periodo": 1
    }).exec()
}

module.exports.consultarDataPeriodo = (p, d) => {
    return Compositores.find({
        $and: [
            {
                periodo: p
            }, {
                dataNasc: {
                    $gte: d
                }
            }
        ]
    }, {
        "_id": 0,
        "id": 1,
        "nome": 1,
        "dataNasc": 1
    }).exec()
}
