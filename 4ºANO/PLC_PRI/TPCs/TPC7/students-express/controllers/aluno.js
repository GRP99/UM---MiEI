var Aluno = require('../models/aluno')

// Listar todos os alunos ordenados pelo seu numero
module.exports.listar = () => {
    return Aluno
        .find({})
        .sort({
            Número: 1
        })
        .exec()
}

// Consultar um determinado aluno pelo seu identificador
module.exports.consultar = id => {
    return Aluno
        .findOne({
            _id: id
        })
        .exec()
}

// Inserir um novo aluno onde a informacao nova desta esta no body da mensagem
module.exports.inserir = a => {
    var novo = new Aluno(a.body)
    return novo.save()
}

// Eliminar um determinado aluno pelo seu identificador
module.exports.eliminar = id => {
    return Aluno
        .deleteOne({
            _id: id
        })
}

// Atualizar um determinado aluno pelo seu identificador com a informacao que vai no aluno
module.exports.atualizar = (id, aluno) => {
    return Aluno
        .updateOne({
            _id: id
        }, aluno)
}

// Adicionar um tpc ao array, ou seja, adicionar um valor 1 apenas se tiver menos que 8 TPCs
module.exports.addTPC = id => {
    return Aluno
        .updateOne({
            $and: [{
                _id: id
            }, {
                $expr: {
                    $lt: [{
                        $size: "$tpc"
                    }, 8]
                }
            }]
        }, {
            $push: {
                "tpc": [1]
            }
        })
}

// Remover um tpc ao array, ou seja, remover um valor 1 
module.exports.removeTPC = id => {
    return Aluno
        .updateOne({
            _id: id
        }, {
            $pop: {
                "tpc": 1
            }
        })
}