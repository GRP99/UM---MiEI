var express = require("express");
var router = express.Router();
var EHR = require("../controllers/ehr");
var systemID = "d60e2348-b083-48ce-93b9-916cef1d3a5a"

const {
    v4: uuidv4
} = require("uuid");

function isUUID(uuid) {
    let s = "" + uuid;
    s = s.match("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    return (s == null) ? false : true
}



/**
* @swagger
* components:
*   schemas:
*     EHR:
*       type: object
*       properties:
*         system_id:
*           type: object
*           required: true
*           description: The identifier of the logical EHR management system in which this EHR was created.
*           properties:
*             value:
*               type: string
*               format: uuidv4
*               required: true
*         ehr_id:
*           type: object
*           required: true
*           description: The unique identifier of this EHR.
*           properties:
*             value:
*               type: string
*               format: uuidv4
*               required: true
*         contributions:
*           type: array
*           description: List of contributions causing changes to this EHR. Each contribution contains a list of versions, which may include references to any number of VERSION instances.
*           items:
*             type: object
*             properties:
*               uid:
*                 type: object
*                 required: true
*                 description: Unique identifier for this Contribution.
*                 properties:
*                   value:
*                     type: string
*                     required: true
*               versions:
*                 type: array
*                 description: Set of references to Versions causing changes to this EHR. Each contribution contains a list of versions, which may include paths pointing to any number of versionable items.
*                 items:
*                   type: object
*                   properties:
*                     namespace:
*                       type: string
*                       required: true
*                     type:
*                       type: string
*                       required: true
*                     id:
*                       type: object
*                       required: true
*                       properties:
*                         value:
*                           type: string
*                           required: true
*               audit:
*                 type: object
*                 required: true
*                 description: Audit trail corresponding to the committal of this Contribution.
*                 properties:
*                   system_id:
*                     type: string
*                     format: uuidv4
*                     required: true
*                   time_committed:
*                     type: object
*                     required: true
*                     properties:
*                       value:
*                         type: date
*                         required: true
*                   change_type:
*                     type: object
*                     required: true
*                     properties:
*                       defining_code:
*                         type: object
*                         required: true
*                         properties:
*                           terminology_id:
*                             type: object
*                             required: true
*                             properties:
*                               value:
*                                 type: string
*                                 required: true
*                           code_string:
*                             type: string
*                             required: true
*                       local_terminology_id:
*                         type: string
*                         required: true
*                       value:
*                         type: string
*                         required: true
*                   committer:
*                     type: object
*                     required: true
*                     properties:
*                       external_ref:
*                         type: object
*                         properties:
*                           id:
*                             type: object
*                             required: true
*                             properties:
*                               value:
*                                 type: string
*                                 required: true
*                           type:
*                             type: string
*                             required: true
*                           namespace:
*                             type: string
*                             required: true
*                   description:
*                     type: object
*                     properties:
*                       value:
*                         type: string
*                         required: true
*         ehr_status:
*           type: object
*           required: true
*           description: Single object per EHR containing various EHR-wide status flags and settings, including whether this EHR can be queried, modified, etc.
*           properties:
*             name:
*               type: object
*               required: true                           
*               properties:
*                 local_terminology_id:
*                   type: string
*                   required: true
*                 value:
*                   type: string
*                   required: true
*             archetype_node_id:
*               type: string
*               required: true   
*             uid:
*               type: object
*               properties:
*                 value:
*                   type: string
*                   required: true
*             subject:
*               type: object
*               required: true
*               properties:
*                 external_ref:
*                   type: object
*                   properties:
*                     id:
*                       type: object
*                       required: true
*                       properties:
*                         value:
*                           type: string
*                           required: true
*                     type:
*                       type: string
*                       required: true
*                     namespace:
*                       type: string
*                       required: true               
*             is_modifiable:
*               type: boolean
*               required: true
*             is_queryable:
*               type: boolean
*               required: true 
*         ehr_access:
*           type: object
*           required: true
*           description: EHR-wide access control object. All access decisions to data in the EHR must be made in accordance with the policies and rules in this object.
*           properties:
*             namespace:
*               type: string
*               required: true
*             type:
*               type: string
*               required: true
*             id:
*               type: object
*               required: true
*               properties:
*                 value:
*                   type: string
*                   required: true 
*         compositions:
*           type: array
*           description: Master list of all Versioned Composition references in this EHR.
*           items:
*             type: object
*             properties:
*               namespace:
*                 type: string
*                 required: true
*               type:
*                 type: string
*                 required: true
*               id:
*                 type: object
*                 required: true
*                 properties:
*                   value:
*                     type: string
*                     required: true
*         directory:
*           type: object
*           description: Optional directory structure for this EHR. If present, this is a reference to the first member of folders.
*           properties:
*             namespace:
*               type: string
*               required: true
*             type:
*               type: string
*               required: true
*             id:
*               type: object
*               required: true
*               properties:
*                 value:
*                   type: string
*                   required: true
*         time_created:
*           type: object
*           description: Time of creation of the EHR.
*           properties:
*             value:
*               type: date
*               required: true
*         folders:
*           type: array
*           description: Optional additional Folder structures for this EHR. If set, the directory attribute refers to the first member.
*           items:
*             type: object
*             properties:
*               name:
*                 type: object
*                 required: true
*                 properties:
*                   local_terminology_id:
*                     type: string
*                     required: true
*                   value:
*                     type: string
*                     required: true
*               archetype_node_id:
*                 type: string
*                 required: true
*               uid:
*                 type: object
*                 properties:
*                   value:
*                     type: string
*                     required: true
*               items:
*                 type: array
*                 items:
*                   type: object
*                   properties:
*                     namespace:
*                       type: string
*                       required: true
*                     type:
*                       type: string
*                       required: true
*                     id:
*                       type: object
*                       required: true
*                       properties:
*                         value:
*                           type: string
*                           required: true
*               folder:
*                 type: array
*                 items:
*                   $ref: '#/components/schemas/EHR'
*/


/**
* @swagger
* tags:
*   name: EHR
*   description: Management of EHRs.
*/


/**
* @swagger
* /ehr:
*   get:
*     summary: Get EHR summary by subject id.
*     description: Retrieve the EHR with the specified subject_id and subject_namespace.
*     tags: [EHR]
*     parameters:
*       - in: query
*         name: subject_id
*         required: true
*         description: subject id.
*         schema:
*           type: string
*           example: "ins01"
*       - in: query
*         name: subject_namespace
*         required: true
*         description: id namespace.
*         schema:
*           type: string
*           example: "examples"
*     responses:
*       200:
*         description: The requested EHR resource is successfully retrieved.
*       400:
*          description: The request has invalid content.
*       404:
*          description: EHR with supplied subject parameters does not exist.
*/
router.get("/", function(req, res, next) {
    if (req.query.subject_id && req.query.subject_namespace) {
        EHR.getEHRSummaryBySubjectID(req.query.subject_id, req.query.subject_namespace).then((data) => {
            if (data != null) {
                res.setHeader("Content-Type", "application/json");
                res.status(200).jsonp({
                    system_id: data.system_id,
                    ehr_id: data.ehr_id,
                    ehr_status: {
                        id: {
                            _type: "OBJECT_VERSION_ID",
                            value: data.ehr_status.uid.value
                        },
                        namespace: data.ehr_status.subject.external_ref.namespace,
                        type: data.ehr_status.subject.external_ref.objectRef_type,
                    },
                    ehr_access: {
                        id: {
                            _type: "OBJECT_VERSION_ID",
                            value: data.ehr_access.objectRef_id.value
                        },
                        namespace: data.ehr_access.namespace,
                        type: data.ehr_access.objectRef_type,
                    },
                    time_created: data.time_created,
                });
            } else {
                res.status(404).jsonp("EHR with supplied subject parameters does not exist !");
            }
        }).catch(() => {
            res.status(400).jsonp("The request has invalid content !");
        });
    } else {
        res.status(400).jsonp("The request has invalid content !");
    }
});


/**
* @swagger
* /ehr/{ehr_id}:
*   get:
*     summary: Get EHR summary by id.
*     description: Retrieve the EHR with the specified ehr_id.
*     tags: [EHR]
*     parameters:
*       - in: path
*         name: ehr_id
*         required: true
*         description: EHR identifier taken from EHR.ehr_id.value.
*         schema:
*           type: string
*           format: uuidv4
*           example: "7d44b88c-4199-4bad-97dc-d78268e01398"
*     responses:
*       200:
*         description: The requested EHR resource is successfully retrieved.
*       400:
*          description: The request has invalid content.
*       404:
*          description:  EHR with ehr_id does not exist
*/
router.get("/:ehr_id", function(req, res, next) {
    EHR.getEHRSummaryByID(req.params.ehr_id).then((data) => {
        if (data != null) {
            res.setHeader("Content-Type", "application/json");
            res.status(200).jsonp({
                system_id: data.system_id,
                ehr_id: data.ehr_id,
                ehr_status: {
                    id: {
                        _type: "OBJECT_VERSION_ID",
                        value: data.ehr_status.uid.value
                    },
                    namespace: data.ehr_status.subject.external_ref.namespace,
                    type: data.ehr_status.subject.external_ref.objectRef_type,
                },
                ehr_access: {
                    id: {
                        _type: "OBJECT_VERSION_ID",
                        value: data.ehr_access.objectRef_id.value
                    },
                    namespace: data.ehr_access.namespace,
                    type: data.ehr_access.objectRef_type,
                },
                time_created: data.time_created,
            });
        } else {
            res.status(404).jsonp("EHR with ehr_id " + req.params.ehr_id + " does not exist !");
        }
    }).catch(() => {
        res.status(400).jsonp("The request has invalid content !")
    });
});


/**
* @swagger
* /ehr:
*   post:
*     summary: Create EHR.
*     description: Create a new EHR with an auto-generated identifier.
*     tags: [EHR]
*     parameters:
*     - name: "Prefer"
*       in: "header"
*       required: true
*       description: The new EHR resource is returned in the body when the request’s Prefer header value is return=representation, otherwise only headers are returned.
*       type: string
*       example: "return={representation|minimal}"
*     requestBody:
*       description: An EHR_STATUS resource needs to be always created and committed in the new EHR. This resource MAY be also supplied by the client as the request body. If not supplied, a default EHR_STATUS will be used by the service.
*       required: true
*       content:
*         application/json:
*           schema:
*             type: object
*             properties:
*               archetype_node_id:
*                 type: string
*                 example: "openEHR-EHR-EHR_STATUS.generic.v1"
*               name:
*                 type: object
*                 properties:
*                   value:
*                     type: string
*                     example: "EHR Status"
*               subject:
*                 type: object
*                 properties:
*                   external_ref:
*                     type: object
*                     properties:
*                       id:
*                         type: object
*                         properties:
*                           value:
*                             type: string
*                             example: "ins01"
*                           scheme:
*                             type: string
*                             example: "id_scheme"
*                       namespace:
*                         type: string
*                         example: "examples"
*                       type:
*                         type: string
*                         example: "PERSON"
*                   is_modifiable:
*                     type: boolean
*                     example: true
*                   is_queryable:
*                     type: boolean
*                     example: true
*     responses:
*       201:
*         description: The EHR has been successfully created.
*       400:
*          description: The request body (if provided) could not be parsed.
*       409:
*          description:  Unable to create a new EHR due to a conflict with an already existing EHR with the same subject id, namespace pair.
*/
router.post("/", function(req, res, next) {
    var prefer_return = req.headers['prefer'].split('return=')[1]
    if (req.body.subject) {
        if (req.body.subject.external_ref.id && req.body.subject.external_ref.namespace) {
            EHR.checkEHR_SubjectIDNamespace(req.body.subject.external_ref.id.value, req.body.subject.external_ref.namespace).then((data) => {
                if (data == null) {
                    var ehr = {
                        system_id: {
                            value: systemID
                        },
                        ehr_id: {
                            value: uuidv4()
                        },
                        ehr_status: {
                            name: {
                                local_terminology_id: "local",
                                value: (req.body.name) ? req.body.name.value : "EHR Status"
                            },
                            archetype_node_id: (req.body.archetype_node_id) ? req.body.archetype_node_id : "openEHR-EHR-EHR_STATUS.generic.v1",
                            uid: {
                                value: uuidv4() + "::EHRManagement.IS2021.com::1"
                            },
                            subject: {
                                external_ref: {
                                    namespace: req.body.subject.external_ref.namespace,
                                    objectRef_type: req.body.subject.external_ref.type,
                                    objectRef_id: {
                                        value: req.body.subject.external_ref.id.value
                                    },
                                }
                            },
                            is_modifiable: (req.body.is_modifiable) ? req.body.is_modifiable : true,
                            is_queryable: (req.body.is_queryable) ? req.body.is_queryable : true,
                        },
                        ehr_access: {
                            namespace: "local",
                            objectRef_type: "EHR_ACCESS",
                            objectRef_id: {
                                value: uuidv4() + "::EHRManagement.IS2021.com::1"
                            }
                        },
                        time_created: {
                            value: new Date().toISOString()
                        }
                    };
                    EHR.create(ehr).then((data) => {
                        if (prefer_return == 'representation') {
                            res.setHeader("Content-Type", "application/json");
                            res.setHeader("Location", "http://localhost:2021/ehr/" + data.ehr_id.value);
                            res.setHeader("ETag", data.ehr_id.value);
                            res.status(201).jsonp({
                                system_id: data.system_id,
                                ehr_id: data.ehr_id,
                                ehr_status: {
                                    id: {
                                        _type: "OBJECT_VERSION_ID",
                                        value: data.ehr_status.uid.value
                                    },
                                    namespace: data.ehr_status.subject.external_ref.namespace,
                                    type: data.ehr_status.subject.external_ref.objectRef_type,
                                },
                                ehr_access: {
                                    id: {
                                        _type: "OBJECT_VERSION_ID",
                                        value: data.ehr_access.objectRef_id.value
                                    },
                                    namespace: data.ehr_access.namespace,
                                    type: data.ehr_access.objectRef_type,
                                },
                                time_created: data.time_created,
                            });
                        } else {
                            if (prefer_return == 'minimal') {
                                res.setHeader("Content-Type", "application/json");
                                res.setHeader("Location", "http://localhost:2021/ehr/" + data.ehr_id.value);
                                res.setHeader("ETag", data.ehr_id.value);
                                res.status(201).jsonp({});
                            }
                        }

                    }).catch((err) => {
                        res.status(400).jsonp("The request body could not be parsed !");
                    });
                } else {
                    res.status(409).jsonp("Unable to create a new EHR due to a conflict with an already existing EHR with the same subject id, namespace pair !");
                }
            }).catch((err) => {
                res.status(400).jsonp("The body of the request could not be read !");
            });
        } else {
            res.status(400).jsonp("The request has invalid content !");
        }
    } else {
        var ehr = {
            system_id: {
                value: systemID
            },
            ehr_id: {
                value: uuidv4()
            },
            ehr_status: {
                name: {
                    local_terminology_id: "local",
                    value: (req.body.name) ? req.body.name.value : "EHR Status"
                },
                archetype_node_id: (req.body.archetype_node_id) ? req.body.archetype_node_id : "openEHR-EHR-EHR_STATUS.generic.v1",
                uid: {
                    value: uuidv4() + "::EHRManagement.IS2021.com::1"
                },
                subject: {
                    external_ref: {
                        namespace: "defaults",
                        objectRef_type: "PERSON",
                        objectRef_id: {
                            value: "ins" + Date.now()
                        },
                    }
                },
                is_modifiable: true,
                is_queryable: true,
            },
            ehr_access: {
                namespace: "local",
                objectRef_type: "EHR_ACCESS",
                objectRef_id: {
                    value: uuidv4() + "::EHRManagement.IS2021.com::1"
                }
            },
            time_created: {
                value: new Date().toISOString()
            }
        };
        EHR.create(ehr).then((data) => {
            if (prefer_return == 'representation') {
                res.setHeader("Content-Type", "application/json");
                res.setHeader("Location", "http://localhost:2021/ehr/" + data.ehr_id.value);
                res.setHeader("ETag", data.ehr_id.value);
                res.status(201).jsonp({
                    system_id: data.system_id,
                    ehr_id: data.ehr_id,
                    ehr_status: {
                        id: {
                            _type: "OBJECT_VERSION_ID",
                            value: data.ehr_status.uid.value
                        },
                        namespace: data.ehr_status.subject.external_ref.namespace,
                        type: data.ehr_status.subject.external_ref.objectRef_type,
                    },
                    ehr_access: {
                        id: {
                            _type: "OBJECT_VERSION_ID",
                            value: data.ehr_access.objectRef_id.value
                        },
                        namespace: data.ehr_access.namespace,
                        type: data.ehr_access.objectRef_type,
                    },
                    time_created: data.time_created,
                });
            } else {
                if (prefer_return == 'minimal') {
                    res.setHeader("Content-Type", "application/json");
                    res.setHeader("Location", "http://localhost:2021/ehr/" + data.ehr_id.value);
                    res.setHeader("ETag", data.ehr_id.value);
                    res.status(201).jsonp({});
                }
            }
        }).catch((err) => {
            res.status(400).jsonp("The body of the request could not be read, or converted to an EHR_STATUS object !");
        });
    }
});


/**
* @swagger
* /ehr/{ehr_id}:
*   put:
*     summary: Create EHR with id.
*     description: Create a new EHR with the specified ehr_id identifier.
*     tags: [EHR]
*     parameters:
*     - name: "Prefer"
*       in: "header"
*       required: true
*       description: The new EHR resource is returned in the body when the request’s Prefer header value is return=representation, otherwise only headers are returned.
*       type: string
*       example: "return={representation|minimal}"
*     - in: path
*       name: ehr_id
*       required: true
*       description: An UUID as a user specified EHR identifier.
*       schema:
*         type: string
*         format: uuidv4
*         example: "7d44b88c-4199-4bad-97dc-d78268e01398"
*     requestBody:
*       description: An EHR_STATUS resource needs to be always created and committed in the new EHR. This resource MAY be also supplied by the client as the request body. If not supplied, a default EHR_STATUS will be used by the service.
*       required: true
*       content:
*         application/json:
*           schema:
*             type: object
*             properties:
*               archetype_node_id:
*                 type: string
*                 example: "openEHR-EHR-EHR_STATUS.generic.v1"
*               name:
*                 type: object
*                 properties:
*                   value:
*                     type: string
*                     example: "EHR Status"
*               subject:
*                 type: object
*                 properties:
*                   external_ref:
*                     type: object
*                     properties:
*                       id:
*                         type: object
*                         properties:
*                           value:
*                             type: string
*                             example: "ins01"
*                           scheme:
*                             type: string
*                             example: "id_scheme"
*                       namespace:
*                         type: string
*                         example: "examples"
*                       type:
*                         type: string
*                         example: "PERSON"
*                   is_modifiable:
*                     type: boolean
*                     example: true
*                   is_queryable:
*                     type: boolean
*                     example: true
*     responses:
*       201:
*         description: The EHR has been successfully created.
*       400:
*          description: The request has invalid ehr_id format or invalid content.
*       409:
*          description: Unable to create a new EHR due to a conflict with an already existing EHR. Can happen when the supplied ehr_id is already used by an existing EHR.
*/
router.put("/:ehr_id", function(req, res, next) {
    var prefer_return = req.headers['prefer'].split('return=')[1]
    if (isUUID(req.params.ehr_id)) {
        EHR.getEHRSummaryByID(req.params.ehr_id).then((data) => {
            if (data == null) {
                if (req.body.subject) {
                    if (req.body.subject.external_ref.id) {
                        EHR.checkEHR_SubjectIDNamespace(req.body.subject.external_ref.id.value, req.body.subject.external_ref.namespace).then((data) => {
                            if (data == null) {
                                var ehr = {
                                    system_id: {
                                        value: systemID
                                    },
                                    ehr_id: {
                                        value: req.params.ehr_id
                                    },
                                    ehr_status: {
                                        name: {
                                            local_terminology_id: "local",
                                            value: (req.body.name) ? req.body.name.value : "EHR Status"
                                        },
                                        archetype_node_id: (req.body.archetype_node_id) ? req.body.archetype_node_id : "openEHR-EHR-EHR_STATUS.generic.v1",
                                        uid: {
                                            value: uuidv4() + "::EHRManagement.IS2021.com::1"
                                        },
                                        subject: {
                                            external_ref: {
                                                namespace: req.body.subject.external_ref.namespace,
                                                objectRef_type: req.body.subject.external_ref.type,
                                                objectRef_id: {
                                                    value: req.body.subject.external_ref.id.value
                                                },
                                            }
                                        },
                                        is_modifiable: (req.body.is_modifiable) ? req.body.is_modifiable : true,
                                        is_queryable: (req.body.is_queryable) ? req.body.is_queryable : true,
                                    },
                                    ehr_access: {
                                        namespace: "local",
                                        objectRef_type: "EHR_ACCESS",
                                        objectRef_id: {
                                            value: uuidv4() + "::EHRManagement.IS2021.com::1"
                                        }
                                    },
                                    time_created: {
                                        value: new Date().toISOString()
                                    }
                                };
                                EHR.create(ehr).then((data) => {
                                    if (prefer_return == 'representation') {
                                        res.setHeader("Content-Type", "application/json");
                                        res.setHeader("Location", "http://localhost:2021/ehr/" + data.ehr_id.value);
                                        res.setHeader("ETag", data.ehr_id.value);
                                        res.status(201).jsonp({
                                            system_id: data.system_id,
                                            ehr_id: data.ehr_id,
                                            ehr_status: {
                                                id: {
                                                    _type: "OBJECT_VERSION_ID",
                                                    value: data.ehr_status.uid.value
                                                },
                                                namespace: data.ehr_status.subject.external_ref.namespace,
                                                type: data.ehr_status.subject.external_ref.objectRef_type,
                                            },
                                            ehr_access: {
                                                id: {
                                                    _type: "OBJECT_VERSION_ID",
                                                    value: data.ehr_access.objectRef_id.value
                                                },
                                                namespace: data.ehr_access.namespace,
                                                type: data.ehr_access.objectRef_type,
                                            },
                                            time_created: data.time_created,
                                        });
                                    } else {
                                        if (prefer_return == 'minimal') {
                                            res.setHeader("Content-Type", "application/json");
                                            res.setHeader("Location", "http://localhost:2021/ehr/" + data.ehr_id.value);
                                            res.setHeader("ETag", data.ehr_id.value);
                                            res.status(201).jsonp({});
                                        }
                                    }
                                }).catch((err) => {
                                    res.status(400).jsonp("The request body could not be parsed ! ");
                                });
                            } else {
                                res.status(409).jsonp("Unable to create a new EHR due to a conflict with an already existing EHR with the same subject id, namespace pair !");
                            }
                        }).catch((err) => {
                            res.status(400).jsonp("The request body could not be parsed !");
                        });
                    } else {
                        res.status(400).jsonp("The request body could not be parsed !");
                    }
                } else {
                    var ehr = {
                        system_id: {
                            value: systemID
                        },
                        ehr_id: {
                            value: req.params.ehr_id
                        },
                        ehr_status: {
                            name: {
                                local_terminology_id: "local",
                                value: (req.body.name) ? req.body.name.value : "EHR Status"
                            },
                            archetype_node_id: (req.body.archetype_node_id) ? req.body.archetype_node_id : "openEHR-EHR-EHR_STATUS.generic.v1",
                            uid: {
                                value: uuidv4() + "::EHRManagement.IS2021.com::1"
                            },
                            subject: {
                                external_ref: {
                                    namespace: "defaults",
                                    objectRef_type: "PERSON",
                                    objectRef_id: {
                                        value: "ins" + Date.now()
                                    },
                                }
                            },
                            is_modifiable: true,
                            is_queryable: true,
                        },
                        ehr_access: {
                            namespace: "local",
                            objectRef_type: "EHR_ACCESS",
                            objectRef_id: {
                                value: uuidv4() + "::EHRManagement.IS2021.com::1"
                            }
                        },
                        time_created: {
                            value: new Date().toISOString()
                        }
                    };
                    EHR.create(ehr).then((data) => {
                        if (prefer_return == 'representation') {
                            res.setHeader("Content-Type", "application/json");
                            res.setHeader("Location", "http://localhost:2021/ehr/" + data.ehr_id.value);
                            res.setHeader("ETag", data.ehr_id.value);
                            res.status(201).jsonp({
                                system_id: data.system_id,
                                ehr_id: data.ehr_id,
                                ehr_status: {
                                    id: {
                                        _type: "OBJECT_VERSION_ID",
                                        value: data.ehr_status.uid.value
                                    },
                                    namespace: data.ehr_status.subject.external_ref.namespace,
                                    type: data.ehr_status.subject.external_ref.objectRef_type,
                                },
                                ehr_access: {
                                    id: {
                                        _type: "OBJECT_VERSION_ID",
                                        value: data.ehr_access.objectRef_id.value
                                    },
                                    namespace: data.ehr_access.namespace,
                                    type: data.ehr_access.objectRef_type,
                                },
                                time_created: data.time_created,
                            });
                        } else {
                            if (prefer_return == 'minimal') {
                                res.setHeader("Content-Type", "application/json");
                                res.setHeader("Location", "http://localhost:2021/ehr/" + data.ehr_id.value);
                                res.setHeader("ETag", data.ehr_id.value);
                                res.status(201).jsonp({});
                            }
                        }
                    }).catch((err) => {
                        res.status(400).jsonp("The request body could not be parsed !");
                    });
                }
            } else {
                res.status(409).jsonp("Unable to create a new EHR due to a conflict with an already existing EHR. The supplied " + req.params.ehr_id + " is already used by an existing EHR.");
            }
        }).catch((err) => {
            res.status(400).jsonp("The request has invalid ehr_id format !");
        });
    } else {
        res.status(400).jsonp("The request has invalid ehr_id format !");
    }
});



module.exports = router;