var Casamentos = require('../models/casamentos');

module.exports.listar = () => {
    return Casamentos.find({}, {
        "_id": 1,
        "date": 1,
        "title": 1
    }).exec()
}

module.exports.consultar = id => {
    return Casamentos.findOne({_id: id}).exec()
}

module.exports.consultarNome = nome => {
    return Casamentos.find({
        "title": {
            $regex: nome
        }
    }).exec();
}

module.exports.consultarAno = ano => {
    return Casamentos.find({
        "date": {
            $regex: ano
        }
    }).exec();
}

module.exports.consultarPorAno = () => {
    return Casamentos.aggregate([{
            $group: {
                _id: "$date",
                casamentos: {
                    $push: {
                        id: "$_id",
                        title: "$title"
                    }
                }
            }
        }]).sort({_id: -1}).exec();
}

module.exports.consultarNoivos = () => {
    return Casamentos.aggregate([
        {
            $addFields: {
                "lista": {
                    $regexFind: {
                        input: "$title",
                        regex: "(?<=: ).*?(?= c)"
                    }
                }
            }
        }, {
            $group: {
                _id: null,
                noivos: {
                    $push: {
                        id: "$_id",
                        noivo: "$lista.match"
                    }
                }
            }
        }, {
            $project: {
                _id: 0,
                noivos: 1
            }
        }
    ]).exec();
}
