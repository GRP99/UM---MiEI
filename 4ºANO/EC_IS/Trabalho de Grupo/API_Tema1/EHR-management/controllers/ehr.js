var EHR = require("../models/ehr");

module.exports.getEHRSummaryBySubjectID = (subject_id, subject_namespace) => {
    return EHR.findOne({
        "ehr_status.subject.external_ref.objectRef_id.value": subject_id,
        "ehr_status.subject.external_ref.namespace": subject_namespace,
    }).exec();
};

module.exports.getEHRSummaryByID = (ehr_id) => {
    return EHR.findOne({
        "ehr_id.value": ehr_id
    }).exec();
};

module.exports.checkEHR_SubjectIDNamespace = (value, namespace) => {
    return EHR.findOne({
        "ehr_status.subject.external_ref.objectRef_id.value": value,
        "ehr_status.subject.external_ref.namespace": namespace,
    }).exec();
};

module.exports.create = (ehr) => {
    var newEHR = new EHR(ehr);
    return newEHR.save();
};

module.exports.checkContribuidUID = (ehr_id, uid) => {
    return EHR.findOne({
        "ehr_id.value": ehr_id,
        "contributions.uid.value": uid
    }).exec();
};

module.exports.addContribution = (ehr_id, contribution) => {
    return EHR.updateOne({
        "ehr_id.value": ehr_id
    }, {
        $push: {
            "contributions": contribution
        }
    }).exec()
};

module.exports.addDirectory = (ehr_id, directory) => {
    return EHR.updateOne({
        "ehr_id.value": ehr_id
    }, {
        "directory": directory
    }).exec()
};

module.exports.addFolder = (ehr_id, folder) => {
    return EHR.updateOne({
        "ehr_id.value": ehr_id
    }, {
        $push: {
            "folders": folder
        }
    }).exec()
};

module.exports.removeFolder = (ehr_id, folder_id) => {
    return EHR.updateOne({
        "ehr_id.value": ehr_id
    }, {
        $pull: {
            "folders": {
                "uid.value": folder_id
            }
        }
    }).exec()
};

module.exports.getDirectory = (ehr_id) => {
    return EHR.find({
        "ehr_id.value": ehr_id,
    }, {
        "directory": 1,
        "_id": 0
    }).exec()
};

module.exports.getFolders = (ehr_id) => {
    return EHR.find({
        "ehr_id.value": ehr_id,
    }, {
        "folders": 1,
        "_id": 0
    }).exec()
};

module.exports.updateDirectory = (ehr_id, uid_directory) => {
    return EHR.updateOne({
        "ehr_id.value": ehr_id
    }, {
        "directory.objectRef_id.value": uid_directory
    }).exec()
};

module.exports.restoreDirectory = (ehr_id) => {
    return EHR.updateOne({
        "ehr_id.value": ehr_id
    }, {
        "directory": {}
    }).exec()
};

module.exports.updateFolder = (ehr_id, folder_id, folder) => {
    return EHR.updateOne({
        "ehr_id.value": ehr_id,
        'folders.uid.value' : folder_id
    }, {
        '$set': {
            'folders.$': folder
        }
    }).exec()
};