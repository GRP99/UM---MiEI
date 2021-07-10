var Locations = require("../models/locations");

module.exports.listLocations = () => {
	return Locations.find().exec();
};

module.exports.lookUp = (id) => {
	return Locations.findOne({
		_id: id,
	}).exec();
};

// add visit by user
module.exports.addvisit = (id, idU) => {
	return Locations.updateOne(
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
	return Locations.updateOne(
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

// get locations visited by user
module.exports.getvisits = (idU) => {
    return Locations.find({
        "visits.authors": idU
    });
};