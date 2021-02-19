// FILES CONTROLLER
var Files = require('../models/files');

// return all files
module.exports.list = () => {
    return Files.find().sort({"registrationDate": -1}).exec();
}

// return only public files
module.exports.publicFiles = () => {
    return Files.find({privacy: 0}).exec();
}

// find that file
module.exports.lookup = id => {
    return Files.findOne({_id: id}).exec();
}

// update file fields
module.exports.updateFile = (id, t, s, d) => {
    return Files.update({
        _id: id
    }, {
        $set: {
            "title": t,
            "subtitle": s,
            "descricao": d
        }
    }).exec();
}

// insert a new file
module.exports.insert = (p, path) => { 
    var newFile = new Files(p)
    newFile.filepath = path
    estrelas = {
        type: 0,
        autores: []
    }
    newFile.comentarios = []
    newFile.estrelas = estrelas;
    return newFile.save();
}

// all the files that user
module.exports.filesbyUser = id => {
    return Files.find({autor: id}).exec();
}

// all the files that user
module.exports.filesbyUserP = id => {
    return Files.find({autor: id, privacy:0}).exec();
}

// find a file with a given title
module.exports.findByName = t => {
    return Files.findOne({title: t}).exec()
}


// delete file
module.exports.remove = id => {
    return Files.deleteOne({_id: id})
}

// add a user to favourites list from file
module.exports.addFav = (id, user) => {
    return Files.findOne({_id: id}).exec().then((result) => {
        result.favoritos.push(user);
        return Files.findByIdAndUpdate(id, result, {new: true});
    })
}

// removes a user to favourites list from file
module.exports.removeFav = (id, user) => {
    return Files.findOne({_id: id}).exec().then((result) => {
        var arrFavs = []
        result.favoritos.forEach(a => {
            if (a != user) {
                arrFavs.push(a)
            }
        })
        result.favoritos = arrFavs
        return Files.findByIdAndUpdate(id, result, {new: true});
    })
}

// add classification and calculate the average
module.exports.classifica = (id, user, classi) => {
    return Files.findOne({_id: id}).exec().then((result) => {
        var size = result.estrelas.autores.length
        var oldmedia = result.estrelas.numero
        var added = ((oldmedia * size) + parseInt(classi))
        var media = added / (size + 1)
        result.estrelas.numero = media.toFixed(2);
        var pair = user + "?;" + classi
        result.estrelas.autores.push(pair);
        return Files.findByIdAndUpdate(id, result, {new: true});
    })
}

// remove classification
module.exports.removeClassificacao = (idF, strk, nmr) => {
    return Files.update({
        _id: idF
    }, {
        $pull: {
            "estrelas.autores": strk
        },
        $set: {
            "estrelas.numero": nmr
        }
    }).exec();
}

/* Changes security */
module.exports.changeprivacy = (id) => {
    return Files.findOne({_id: id}).exec().then((result) => {
        if (result.privacy == 0) {
            p = 1;
        } else {
            p = 0;
        }
        return Files.update({
            _id: id
        }, {
            $set: {
                "privacy": p
            }
        });
    })
}

// add a new comentary
module.exports.adicionarComentario = (idR, comentario) => {
    return Files.update({
        _id: idR
    }, {
        $push: {
            comentarios: comentario
        }
    }).exec()
}


// remove comentary
module.exports.removerComentario = (idR, idC) => {
    return Files.update({
        _id: idR
    }, {
        $pull: {
            "comentarios": {
                _id: idC
            }
        }
    }).exec();
}


// get stars
module.exports.getEstrelas = (id) => {
    return Files.find({
        _id: id
    }, {
        _id: 0,
        estrelas: 1
    }).exec();
};


// add star
module.exports.incrementarEstrelas = (idR, idU) => {
    return Files.updateOne({
        _id: idR
    }, {
        $push: {
            "estrelas.autores": idU
        },
        $inc: {
            "estrelas.numero": 1
        }
    }).exec();
}


// remove star
module.exports.decrementarEstrelas = (idR, idU) => {
    return Files.updateOne({
        _id: idR
    }, {
        $pull: {
            "estrelas.autores": idU
        },
        $inc: {
            "estrelas.numero": -1
        }
    }).exec();
}

// Search files by title
module.exports.search = (text) => {
    return Files.find({
        title: {
            $regex: text,
            "$options": "i"
        }
    }).sort({"title": -1, "resourceType": -1, "creationDate": -1}).exec();
}

// Search files by resource type
module.exports.searchByType = (type) => {
    return Files.find({
        resourceType: {
            $regex: type,
            "$options": "i"
        }
    }).sort({"resourceType": -1, "title": -1, "creationDate": -1}).exec();
}

// Search files by creation date
module.exports.searchByDate = (date) => {
    return Files.find({
        creationDate: {
            $regex: date
        }
    }).sort({"creationDate": -1, "title": -1, "resourceType": -1}).exec();
}

// get the resources highest classified
module.exports.topclassificados = () => {
    return Files.find({
        privacy: 0
    }, {}).sort({"estrelas.numero": -1}).limit(3).exec();
}

// get top favourites
module.exports.topfavoritos = () => {
    return Files.aggregate([
        {
            $addFields: {
                nmrFavs: {
                    $size: "$favoritos"
                }
            }
        }, {
            $sort: {
                nmrFavs: -1
            }
        }, {
            $limit: 3
        }, {
            $match: {
                privacy: 0
            }
        }
    ]).exec()
}

// get top producers
module.exports.topautores = () => {
    return Files.aggregate([
        {
            $match: {
                privacy: 0
            }
        },
        {
            $group: {
                _id: "$autor",
                filesbyA: {
                    $push: "$_id"
                }
            }
        },
        {
            $addFields: {
                nmrUploads: {
                    $size: "$filesbyA"
                }
            }
        },
        {
            $sort: {
                nmrUploads: -1
            }
        }, {
            $limit: 3
        }
    ]).exec()
}

// get number of uploads from a given user
module.exports.numberofUploads = (id) => {
    return Files.aggregate([
        {
            $group: {
                _id: "$autor",
                filesbyA: {
                    $push: "$_id"
                }
            }
        }, {
            $addFields: {
                nmrUploads: {
                    $size: "$filesbyA"
                }
            }
        }, {
            $sort: {
                nmrUploads: -1
            }
        }, {
            $match: {
                _id: id
            }
        }
    ]).exec()
}

// get resource types
module.exports.getResourceTypes = () => {
    return Files.distinct("resourceType").exec();
}
