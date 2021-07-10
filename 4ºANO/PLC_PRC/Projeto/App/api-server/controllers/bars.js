var Bars = require("../models/bars");

module.exports.listBars = () => {
	return Bars.find().exec();
};

module.exports.lookUp = (id) => {
	return Bars.findOne({
		_id: id,
	}).exec();
};

// add visit by user
module.exports.addvisit = (id, idU) => {
	return Bars.updateOne(
		{
			_id: id,
		},
		{
			$push: {
				"visits.authors": idU,
			},
			$inc: {
				"visits.count": 1,
			},
		}
	).exec();
};

// remove visit by user
module.exports.removevisit = (id, idU) => {
	return Bars.updateOne(
		{
			_id: id,
		},
		{
			$pull: {
				"visits.authors": idU,
			},
			$inc: {
				"visits.count": -1,
			},
		}
	).exec();
};

// get bars visited by user
module.exports.getvisits = (idU) => {
    return Bars.find({
        "visits.authors": idU
    });
};