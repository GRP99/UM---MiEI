var Pubs = require('../models/pubs');

module.exports.list = () => {
    return Pubs.find({}, {
        "_id": 0,
        "id": 1,
        "title": 1,
        "year": 1,
        "type": 1
    }).exec()
}

module.exports.consult = id => {
    return Pubs.findOne({id: id}).exec()
}

module.exports.listTypes = () => {
    return Pubs.distinct("type").exec()
}

module.exports.listAuthors = () => {
    return Pubs.distinct("authors").sort().exec()
}

module.exports.consultByType = type => {
    return Pubs.find({
        type: type
    }, {
        "_id": 0,
        "id": 1,
        "title": 1,
        "year": 1,
        "type": 1
    }).exec()
}

module.exports.consultByTypeAndYear = (type, year) => {
    return Pubs.find({
        $and: [
            {
                type: type
            }, {
                year: {
                    $gte: year
                }
            }
        ]
    }, {
        "_id": 0,
        "id": 1,
        "title": 1,
        "year": 1,
        "type": 1
    }).exec()
}

module.exports.consultAuthorPubs = author => {
    return Pubs.aggregate(([
        {
            $unwind: "$authors"
        }, {
            $match: {
                authors: author
            }
        }
    ])).exec()
}

module.exports.qtAuthors = () => {
    return Pubs.aggregate([{
            $project: {
                "_id": 0,
                "id": 1,
                "title": 1,
                "year": 1,
                "type": 1,
                NumberOfAuthors: {
                    $size: "$authors"
                }
            }
        }]).exec()
}
