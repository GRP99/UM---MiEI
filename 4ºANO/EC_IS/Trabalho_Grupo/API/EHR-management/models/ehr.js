var mongoose = require("mongoose");

// https://specifications.openehr.org/releases/RM/latest/

// base_types.html#_object_id_class
var objectIDSchema = new mongoose.Schema({
    value: {
        type: String,
        required: true
    }
}, {
    _id: false
});

// base_types.html#_object_ref_class
var objectRefSchema = new mongoose.Schema({
    namespace: {
        type: String,
        required: true
    },
    objectRef_type: {
        type: String,
        required: true
    },
    objectRef_id: {
        type: objectIDSchema,
        required: true
    },
}, {
    _id: false
});

// common.html#_party_proxy_class
var partySelfSchema = new mongoose.Schema({
    external_ref: {
        type: objectRefSchema
    }
}, {
    _id: false
});

// data_types.html#_code_phrase_class
var codePhraseSchema = new mongoose.Schema({
    terminology_id: {
        type: objectIDSchema,
        required: true
    },
    code_string: {
        type: String,
        required: true
    },
}, {
    _id: false
});

// data_types.html#_dv_coded_text_class
var dvCodedTextSchema = new mongoose.Schema({
    defining_code: {
        type: codePhraseSchema,
        required: true
    },
    local_terminology_id: {
        type: String,
        required: true
    },
    value: {
        type: String,
        required: true
    },
}, {
    _id: false
});

// data_types.html#_dv_text_class
var dvTextSchema = new mongoose.Schema({
    local_terminology_id: {
        type: String,
        required: true,
        default: "local"
    },
    value: {
        type: String,
        required: true
    },
}, {
    _id: false
});

// common.html#_audit_details_class
var auditDetailsSchema = new mongoose.Schema({
    system_id: {
        type: String,
        required: true
    },
    time_committed: {
        value: {
            type: Date,
            required: true,
            default: Date.now
        }
    },
    change_type: {
        type: dvCodedTextSchema,
        required: true
    },
    committer: {
        type: partySelfSchema,
        required: true
    },
    description: {
        type: objectIDSchema
    },
}, {
    _id: false
});

// common.html#_contribution_class
var contributionSchema = new mongoose.Schema({
    uid: {
        type: objectIDSchema,
        required: true
    },
    versions: [objectRefSchema],
    audit: {
        type: auditDetailsSchema,
        required: true
    },
}, {
    _id: false
});

// ehr.html#_ehr_status_class
var ehrStatusSchema = new mongoose.Schema({
    name: {
        type: dvTextSchema,
        required: true
    },
    archetype_node_id: {
        type: String,
        required: true
    },
    uid: {
        type: objectIDSchema
    },
    subject: {
        type: partySelfSchema,
        required: true
    },
    is_modifiable: {
        type: Boolean,
        required: true
    },
    is_queryable: {
        type: Boolean,
        required: true
    },
}, {
    _id: false
});

// common.html#_folder_class
var folderSchema = new mongoose.Schema({
    name: {
        type: dvTextSchema,
        required: true
    },
    archetype_node_id: {
        type: String,
        required: true
    },
    uid: {
        type: objectIDSchema
    },
    items: [objectRefSchema],
    folder: [this],
}, {
    _id: false
});

// ehr.html#_ehr_class
var EHRSchema = new mongoose.Schema({
    system_id: {
        type: objectIDSchema,
        required: true
    },
    ehr_id: {
        type: objectIDSchema,
        required: true
    },
    contributions: [contributionSchema],
    ehr_status: {
        type: ehrStatusSchema,
        required: true
    },
    ehr_access: {
        type: objectRefSchema,
        required: true
    },
    compositions: [objectRefSchema],
    directory: {
        type: objectRefSchema
    },
    time_created: {
        value: {
            type: Date,
            required: true,
            default: Date.now
        }
    },
    folders: [folderSchema],
});

module.exports = mongoose.model("ehr", EHRSchema);