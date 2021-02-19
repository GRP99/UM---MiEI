var Prizes = require('../models/prizes');

module.exports.list = () => {
    return Prizes.find({}, {
        "_id": 0,
        "year": 1,
        "category": 1
    }).exec()
}

module.exports.consult = id => {
    return Prizes.findOne({_id: id}).exec()
}

module.exports.listCategories = () => {
    return Prizes.distinct("category").exec()
}

module.exports.consultCategory = (c) => {
    return Prizes.find({category: c}).exec()
}

module.exports.consultCategoryYear = (c, y) => {
    return Prizes.find({
        category: c,
        year: {
            $gte: y
        }
    }).exec()
}


module.exports.listLaureates = (c, y) => {
    return Prizes.find({}, {
        "_id": 0,
        "laureates.firstname": 1,
        "laureates.surname": 1,
        "year": 1,
        "category": 1
    }).sort({"laureates.firstname": 1}).exec()
}
