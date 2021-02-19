var Sports = require('../models/sports');

module.exports.list = () => {
    return Sports.find({}, {
        "_id": 0,
        "start_date": 1,
        "type": 1,
        "distance": 1,
        "moving_time": 1
    }).exec()
}

module.exports.listByType = (t) => {
    return Sports.aggregate([
        {
            $match: {
                type: t
            }
        }, {
            $group: {
                _id: "$type",
                atividades: {
                    $push: {
                        name: "$name"
                    }
                }
            }
        }
    ]).exec();
}

module.exports.longer = (t) => {
    return Sports.find({}, {
        "_id": 0,
        "start_date": 1,
        "type": 1,
        "distance": 1,
        "moving_time": 1,
        "name": 1
    }).sort({distance: -1}).limit(1).exec();
}

module.exports.longerType = (t) => {
    return Sports.find({
        type: t
    }, {
        "_id": 0,
        "start_date": 1,
        "type": 1,
        "distance": 1,
        "moving_time": 1,
        "name": 1
    }).sort({distance: -1}).limit(1).exec();
}
