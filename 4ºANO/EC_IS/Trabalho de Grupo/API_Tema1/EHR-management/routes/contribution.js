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
* tags:
*   name: CONTRIBUTION
*   description: Management of CONTRIBUTION resources.
*/

/**
* @swagger
* /ehr/{ehr_id}/contribution/{contribution_uid}:
*   get:
*     summary: Get contribution by id.
*     tags: [CONTRIBUTION]
*     parameters:
*       - in: path
*         name: ehr_id
*         required: true
*         description: EHR identifier taken from EHR.ehr_id.value.
*         schema:
*           type: string
*           format: uuidv4
*           example: "7d44b88c-4199-4bad-97dc-d78268e01398"
*       - in: path
*         name: contribution_uid
*         required: true
*         description: CONTRIBUTION uid.
*         schema:
*           type: string
*           format: uuidv4
*           example: "0826851c-c4c2-4d61-92b9-410fb8275ff0"
*     responses:
*       200:
*         description: The CONTRIBUTION is successfully retrieved.
*       400:
*          description: The request has invalid content.
*       404:
*          description: EHR with ehr_id does not exist or a CONTRIBUTION with contribution_uid does not exist.
*/
router.get("/:contribution_uid", function(req, res, next) {
    var ehr_id = req.baseUrl.split('/')[2]
    EHR.getEHRSummaryByID(ehr_id).then((data) => {
        if (data) {
            EHR.checkContribuidUID(ehr_id, req.params.contribution_uid).then((data) => {
                if (data) {
                    res.setHeader("Content-Type", "application/json");
                    data.contributions.forEach(contribution => {
                        if (contribution.uid.value == req.params.contribution_uid) {
                            var list_versions = contribution.versions.map((v) => {
                                return {
                                    "_type": "OBJECT_REF",
                                    "id": {
                                        "_type": "OBJECT_VERSION_ID",
                                        "value": v.objectRef_id.value
                                    },
                                    "namespace": v.namespace,
                                    "type": v.objectRef_type
                                }
                            });
                            res.status(200).jsonp({
                                _type: "CONTRIBUTION",
                                uid: contribution.uid,
                                versions: list_versions,
                                audit: {
                                    system_id: contribution.audit.system_id,
                                    committer: {
                                        _type: contribution.audit.committer.external_ref.objectRef_type,
                                        id: contribution.audit.committer.external_ref.objectRef_id.value,
                                    },
                                    time_committed: contribution.audit.time_committed,
                                    change_type: {
                                        value: contribution.audit.change_type.value,
                                        defining_code: contribution.audit.change_type.defining_code,
                                    },
                                    description: contribution.audit.description,
                                },
                            });
                        }
                    });
                } else {
                    res.status(404).jsonp("EHR with contribution_uid " + req.params.contribution_uid + " does not exist !");
                }
            }).catch(() => {
                res.status(400).jsonp("The request has invalid content !")
            });
        } else {
            res.status(404).jsonp("EHR with ehr_id " + ehr_id + " does not exist !");
        }
    }).catch(() => {
        res.status(400).jsonp("The request has invalid content !")
    });
});

/**
* @swagger
* /ehr/{ehr_id}/contribution:
*   post:
*     summary: Create contribution.
*     tags: [CONTRIBUTION]
*     parameters:
*       - in: path
*         name: ehr_id
*         required: true
*         description: EHR identifier taken from EHR.ehr_id.value.
*         schema:
*           type: string
*           format: uuidv4
*           example: "7d44b88c-4199-4bad-97dc-d78268e01398"
*       - name: "Prefer"
*         in: "header"
*         required: true
*         description: Content body is only returned when Prefer header has return=representation otherwise only headers are returned.
*         type: string
*         example: "return={representation|minimal}"
*     requestBody:
*       description: Contribution (change set) of one or more versions added to a change-controlled repository.
*       required: true
*       content:
*         application/json:
*           schema:
*             type: object
*             properties:
*               system_id:
*                 type: object
*                 properties:
*                   value:
*                     type: string
*                     format: uuidv4
*                     example: d60e2348-b083-48ce-93b9-916cef1d3a5a
*               uid:
*                 type: object
*                 properties:
*                   value:
*                     type: string
*                     format: uuidv4
*                     example: b5e2cf01-8bb6-4fcd-ad88-0efb611195da
*               versions:
*                 type: array
*                 items:
*                   type: object
*                   properties:
*                     namespace:
*                       type: string
*                       example: local
*                     type:
*                       type: string
*                       example: COMPOSITION
*                     id:
*                       type: object
*                       properties:
*                         value:
*                           type: string
*                           example: fb458d9c-1323-42bc-b7f8-787f3660a0b5::91215053-854b-45b8-bb2a-3b0d255858d1::1
*               audit:
*                 type: object
*                 properties:
*                   committer:
*                     type: object
*                     properties:
*                       external_ref:
*                         type: object
*                         properties:
*                           namespace:
*                             type: string
*                             example: demographic
*                           type:
*                             type: string
*                             example: PERSON
*                           id:
*                             type: object
*                             properties:
*                               value:
*                                 type: string
*                                 example: 9624982A-9F42-41A5-9318-AE13D5F5031
*                   description:
*                     type: object
*                     properties:
*                       value:
*                         type: string
*                         example: comment
*     responses:
*       201:
*         description: The CONTRIBUTION was created.
*       400:
*          description: The request has invalid content.
*       404:
*          description: EHR with ehr_id does not exist.
*/
router.post("/", function(req, res, next) {
    var ehr_id = req.baseUrl.split('/')[2]
    var prefer_return = req.headers['prefer'].split('return=')[1]
    if (isUUID(ehr_id)) {
        EHR.getEHRSummaryByID(ehr_id).then((data) => {
            if (data) {
                if (req.body.uid) {
                    EHR.checkContribuidUID(ehr_id, req.body.uid.value).then((data) => {
                        if (!data) {
                            if (req.body.system_id) {
                                if (req.body.system_id.value == systemID) {
                                    var flag = false
                                    var count_id_version = 0
                                    var id = ""
                                    list_versions = []

                                    req.body.versions.forEach(element => {
                                        if (element.id.value && element.namespace && element.type) {
                                            if (count_id_version == 0) {
                                                id_version = element.id.value.split("::")
                                                if (isUUID(id_version[1]) && isUUID(id_version[0])) {
                                                    if (element.type && element.namespace) {
                                                        id = id_version[1]
                                                        list_versions.push({
                                                            "namespace": element.namespace,
                                                            "objectRef_type": element.type,
                                                            "objectRef_id": {
                                                                "value": element.id.value
                                                            }
                                                        })
                                                    } else {
                                                        flag = true
                                                    }
                                                } else {
                                                    flag = true
                                                }
                                            } else {
                                                id_version = element.id.value.split("::")
                                                if (isUUID(id_version[1]) && isUUID(id_version[0]) && id_version[1] == id) {
                                                    if (element.type && element.namespace) {
                                                        list_versions.push({
                                                            "namespace": element.namespace,
                                                            "objectRef_type": element.type,
                                                            "objectRef_id": {
                                                                "value": element.id.value
                                                            }
                                                        })
                                                    } else {
                                                        flag = true
                                                    }
                                                } else {
                                                    flag = true
                                                }
                                            }
                                        } else {
                                            flag = true
                                        }
                                        count_id_version += 1
                                    });

                                    if (flag) {
                                        return res.status(400).jsonp("The request has invalid ehr_id format !");
                                    }

                                    var contribution = {
                                        uid: {
                                            value: req.body.uid.value
                                        },
                                        versions: list_versions,
                                        audit: {
                                            system_id: req.body.system_id.value,
                                            time_committed: {
                                                value: new Date().toISOString()
                                            },
                                            change_type: {
                                                defining_code: {
                                                    terminology_id: {
                                                        value: "openehr"
                                                    },
                                                    code_string: "249",
                                                },
                                                local_terminology_id: "local",
                                                value: "creation",
                                            },
                                            committer: {
                                                external_ref: {
                                                    namespace: req.body.audit.committer.external_ref.namespace,
                                                    objectRef_type: req.body.audit.committer.external_ref.type,
                                                    objectRef_id: {
                                                        value: req.body.audit.committer.external_ref.id.value,
                                                    },
                                                },
                                            },
                                            description: {
                                                value: req.body.audit.description ? req.body.audit.description.value : null,
                                            },
                                        },
                                    };

                                    EHR.addContribution(ehr_id, contribution).then(() => {
                                        if (prefer_return == 'representation') {
                                            res.setHeader("Content-Type", "application/json");
                                            res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/contribution/" + contribution.uid.value);
                                            res.setHeader("ETag", contribution.uid.value);

                                            var list_versions_send = contribution.versions.map((v) => {
                                                return {
                                                    "_type": "OBJECT_REF",
                                                    "id": {
                                                        "_type": "OBJECT_VERSION_ID",
                                                        "value": v.objectRef_id.value
                                                    },
                                                    "namespace": v.namespace,
                                                    "type": v.objectRef_type
                                                }
                                            });

                                            res.status(201).jsonp({
                                                _type: "CONTRIBUTION",
                                                uid: contribution.uid,
                                                versions: list_versions_send,
                                                audit: {
                                                    system_id: contribution.audit.system_id,
                                                    committer: {
                                                        _type: contribution.audit.committer.external_ref.objectRef_type,
                                                        id: contribution.audit.committer.external_ref.objectRef_id.value,
                                                    },
                                                    time_committed: contribution.audit.time_committed,
                                                    change_type: {
                                                        value: contribution.audit.change_type.value,
                                                        defining_code: contribution.audit.change_type.defining_code,
                                                    },
                                                    description: contribution.audit.description,
                                                },
                                            });
                                        } else {
                                            if (prefer_return == 'minimal') {
                                                res.setHeader("Content-Type", "application/json");
                                                res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/contribution/" + contribution.uid.value);
                                                res.setHeader("ETag", contribution.uid.value);
                                                res.status(201).jsonp({});
                                            }
                                        }
                                    }).catch((err) => {
                                        res.status(400).jsonp("The request body could not be parsed ! ");
                                    });
                                } else {
                                    res.status(400).jsonp("The system_id provided is not valid !");
                                }
                            } else {
                                var flag = false
                                var count_id_version = 0
                                var id = ""
                                list_versions = []

                                req.body.versions.forEach(element => {
                                    if (element.id.value && element.namespace && element.type) {
                                        if (count_id_version == 0) {
                                            id_version = element.id.value.split("::")
                                            if (isUUID(id_version[1]) && isUUID(id_version[0])) {
                                                if (element.type && element.namespace) {
                                                    id = id_version[1]
                                                    list_versions.push({
                                                        "namespace": element.namespace,
                                                        "objectRef_type": element.type,
                                                        "objectRef_id": {
                                                            "value": element.id.value
                                                        }
                                                    })
                                                } else {
                                                    flag = true
                                                }
                                            } else {
                                                flag = true
                                            }
                                        } else {
                                            id_version = element.id.value.split("::")
                                            if (isUUID(id_version[1]) && isUUID(id_version[0]) && id_version[1] == id) {
                                                if (element.type && element.namespace) {
                                                    list_versions.push({
                                                        "namespace": element.namespace,
                                                        "objectRef_type": element.type,
                                                        "objectRef_id": {
                                                            "value": element.id.value
                                                        }
                                                    })
                                                } else {
                                                    flag = true
                                                }
                                            } else {
                                                flag = true
                                            }
                                        }
                                        count_id_version += 1
                                    } else {
                                        flag = true
                                    }
                                });

                                if (flag) {
                                    return res.status(400).jsonp("The request has invalid ehr_id format !");
                                }

                                var contribution = {
                                    uid: {
                                        value: req.body.uid.value
                                    },
                                    versions: list_versions,
                                    audit: {
                                        system_id: systemID,
                                        time_committed: {
                                            value: new Date().toISOString()
                                        },
                                        change_type: {
                                            defining_code: {
                                                terminology_id: {
                                                    value: "openehr"
                                                },
                                                code_string: "249",
                                            },
                                            local_terminology_id: "local",
                                            value: "creation",
                                        },
                                        committer: {
                                            external_ref: {
                                                namespace: req.body.audit.committer.external_ref.namespace,
                                                objectRef_type: req.body.audit.committer.external_ref.type,
                                                objectRef_id: {
                                                    value: req.body.audit.committer.external_ref.id.value,
                                                },
                                            },
                                        },
                                        description: {
                                            value: req.body.audit.description ? req.body.audit.description.value : "",
                                        },
                                    },
                                };

                                EHR.addContribution(ehr_id, contribution).then(() => {
                                    if (prefer_return == 'representation') {
                                        res.setHeader("Content-Type", "application/json");
                                        res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/contribution/" + contribution.uid.value);
                                        res.setHeader("ETag", contribution.uid.value);

                                        var list_versions_send = contribution.versions.map((v) => {
                                            return {
                                                "_type": "OBJECT_REF",
                                                "id": {
                                                    "_type": "OBJECT_VERSION_ID",
                                                    "value": v.objectRef_id.value
                                                },
                                                "namespace": v.namespace,
                                                "type": v.objectRef_type
                                            }
                                        });

                                        res.status(201).jsonp({
                                            _type: "CONTRIBUTION",
                                            uid: contribution.uid,
                                            versions: list_versions_send,
                                            audit: {
                                                system_id: contribution.audit.system_id,
                                                committer: {
                                                    _type: contribution.audit.committer.external_ref.objectRef_type,
                                                    id: contribution.audit.committer.external_ref.objectRef_id.value,
                                                },
                                                time_committed: contribution.audit.time_committed,
                                                change_type: {
                                                    value: contribution.audit.change_type.value,
                                                    defining_code: contribution.audit.change_type.defining_code,
                                                },
                                                description: contribution.audit.description,
                                            },
                                        });
                                    } else {
                                        if (prefer_return == 'minimal') {
                                            res.setHeader("Content-Type", "application/json");
                                            res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/contribution/" + contribution.uid.value);
                                            res.setHeader("ETag", contribution.uid.value);
                                            res.status(201).jsonp({});
                                        }
                                    }
                                }).catch((err) => {
                                    res.status(400).jsonp("The request body could not be parsed ! ");
                                });
                            }
                        } else {
                            res.status(400).jsonp("Contribution with UID " + req.body.uid.value + " already used !");
                        }
                    }).catch((err) => {
                        res.status(400).jsonp("The request has invalid content !" + err);
                    });
                } else {
                    if (req.body.system_id) {
                        if (req.body.system_id.value == systemID) {
                            var flag = false
                            var count_id_version = 0
                            var id = ""
                            list_versions = []

                            req.body.versions.forEach(element => {
                                if (element.id.value && element.namespace && element.type) {
                                    if (count_id_version == 0) {
                                        id_version = element.id.value.split("::")
                                        if (isUUID(id_version[1]) && isUUID(id_version[0])) {
                                            if (element.type && element.namespace) {
                                                id = id_version[1]
                                                list_versions.push({
                                                    "namespace": element.namespace,
                                                    "objectRef_type": element.type,
                                                    "objectRef_id": {
                                                        "value": element.id.value
                                                    }
                                                })
                                            } else {
                                                flag = true
                                            }
                                        } else {
                                            flag = true
                                        }
                                    } else {
                                        id_version = element.id.value.split("::")
                                        if (isUUID(id_version[1]) && isUUID(id_version[0]) && id_version[1] == id) {
                                            if (element.type && element.namespace) {
                                                list_versions.push({
                                                    "namespace": element.namespace,
                                                    "objectRef_type": element.type,
                                                    "objectRef_id": {
                                                        "value": element.id.value
                                                    }
                                                })
                                            } else {
                                                flag = true
                                            }
                                        } else {
                                            flag = true
                                        }
                                    }
                                } else {
                                    flag = true
                                }
                                count_id_version += 1
                            });

                            if (flag) {
                                return res.status(400).jsonp("The request has invalid ehr_id format !");
                            }

                            var contribution = {
                                uid: {
                                    value: uuidv4()
                                },
                                versions: list_versions,
                                audit: {
                                    system_id: req.body.system_id.value,
                                    time_committed: {
                                        value: new Date().toISOString()
                                    },
                                    change_type: {
                                        defining_code: {
                                            terminology_id: {
                                                value: "openehr"
                                            },
                                            code_string: "249"
                                        },
                                        local_terminology_id: "local",
                                        value: "creation",
                                    },
                                    committer: {
                                        external_ref: {
                                            namespace: req.body.audit.committer.external_ref.namespace,
                                            objectRef_type: req.body.audit.committer.external_ref.type,
                                            objectRef_id: {
                                                value: req.body.audit.committer.external_ref.id.value
                                            },
                                        },
                                    },
                                    description: {
                                        value: req.body.audit.description ? req.body.audit.description.value : "",
                                    },
                                },
                            };

                            EHR.addContribution(ehr_id, contribution).then((data) => {
                                if (prefer_return == 'representation') {
                                    res.setHeader("Content-Type", "application/json");
                                    res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/contribution/" + contribution.uid.value);
                                    res.setHeader("ETag", contribution.uid.value);

                                    var list_versions_send = contribution.versions.map((v) => {
                                        return {
                                            "_type": "OBJECT_REF",
                                            "id": {
                                                "_type": "OBJECT_VERSION_ID",
                                                "value": v.objectRef_id.value
                                            },
                                            "namespace": v.namespace,
                                            "type": v.objectRef_type
                                        }
                                    });

                                    res.status(201).jsonp({
                                        _type: "CONTRIBUTION",
                                        uid: contribution.uid,
                                        versions: list_versions_send,
                                        audit: {
                                            system_id: contribution.audit.system_id,
                                            committer: {
                                                _type: contribution.audit.committer.external_ref.objectRef_type,
                                                id: contribution.audit.committer.external_ref.objectRef_id.value,
                                            },
                                            time_committed: contribution.audit.time_committed,
                                            change_type: {
                                                value: contribution.audit.change_type.value,
                                                defining_code: contribution.audit.change_type.defining_code,
                                            },
                                            description: contribution.audit.description,
                                        },
                                    });
                                } else {
                                    if (prefer_return == 'minimal') {
                                        res.setHeader("Content-Type", "application/json");
                                        res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/contribution/" + contribution.uid.value);
                                        res.setHeader("ETag", contribution.uid.value);
                                        res.status(201).jsonp({});
                                    }
                                }
                            }).catch((err) => {
                                res.status(400).jsonp("The request body could not be parsed ! ");
                            });
                        } else {
                            res.status(400).jsonp("The system_id provided is not valid !");
                        }
                    } else {
                        var flag = false
                        var count_id_version = 0
                        var id = ""
                        list_versions = []

                        req.body.versions.forEach(element => {
                            if (element.id.value && element.namespace && element.type) {
                                if (count_id_version == 0) {
                                    id_version = element.id.value.split("::")
                                    if (isUUID(id_version[1]) && isUUID(id_version[0])) {
                                        if (element.type && element.namespace) {
                                            id = id_version[1]
                                            list_versions.push({
                                                "namespace": element.namespace,
                                                "objectRef_type": element.type,
                                                "objectRef_id": {
                                                    "value": element.id.value
                                                }
                                            })
                                        } else {
                                            flag = true
                                        }
                                    } else {
                                        flag = true
                                    }
                                } else {
                                    id_version = element.id.value.split("::")
                                    if (isUUID(id_version[1]) && isUUID(id_version[0]) && id_version[1] == id) {
                                        if (element.type && element.namespace) {
                                            list_versions.push({
                                                "namespace": element.namespace,
                                                "objectRef_type": element.type,
                                                "objectRef_id": {
                                                    "value": element.id.value
                                                }
                                            })
                                        } else {
                                            flag = true
                                        }
                                    } else {
                                        flag = true
                                    }
                                }
                            } else {
                                flag = true
                            }
                            count_id_version += 1
                        });

                        if (flag) {
                            return res.status(400).jsonp("The request has invalid ehr_id format !");
                        }

                        var contribution = {
                            uid: {
                                value: uuidv4()
                            },
                            versions: list_versions,
                            audit: {
                                system_id: systemID,
                                time_committed: {
                                    value: new Date().toISOString()
                                },
                                change_type: {
                                    defining_code: {
                                        terminology_id: {
                                            value: "openehr"
                                        },
                                        code_string: "249"
                                    },
                                    local_terminology_id: "local",
                                    value: "creation",
                                },
                                committer: {
                                    external_ref: {
                                        namespace: req.body.audit.committer.external_ref.namespace,
                                        objectRef_type: req.body.audit.committer.external_ref.type,
                                        objectRef_id: {
                                            value: req.body.audit.committer.external_ref.id.value
                                        },
                                    },
                                },
                                description: {
                                    value: req.body.audit.description ? req.body.audit.description.value : "",
                                },
                            },
                        };

                        EHR.addContribution(ehr_id, contribution).then(() => {
                            if (prefer_return == 'representation') {
                                res.setHeader("Content-Type", "application/json");
                                res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/contribution/" + contribution.uid.value);
                                res.setHeader("ETag", contribution.uid.value);

                                var list_versions_send = contribution.versions.map((v) => {
                                    return {
                                        "_type": "OBJECT_REF",
                                        "id": {
                                            "_type": "OBJECT_VERSION_ID",
                                            "value": v.objectRef_id.value
                                        },
                                        "namespace": v.namespace,
                                        "type": v.objectRef_type
                                    }
                                });

                                res.status(201).jsonp({
                                    _type: "CONTRIBUTION",
                                    uid: contribution.uid,
                                    versions: list_versions_send,
                                    audit: {
                                        system_id: contribution.audit.system_id,
                                        committer: {
                                            _type: contribution.audit.committer.external_ref.objectRef_type,
                                            id: contribution.audit.committer.external_ref.objectRef_id.value,
                                        },
                                        time_committed: contribution.audit.time_committed,
                                        change_type: {
                                            value: contribution.audit.change_type.value,
                                            defining_code: contribution.audit.change_type.defining_code,
                                        },
                                        description: contribution.audit.description,
                                    },
                                });
                            } else {
                                if (prefer_return == 'minimal') {
                                    res.setHeader("Content-Type", "application/json");
                                    res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/contribution/" + contribution.uid.value);
                                    res.setHeader("ETag", contribution.uid.value);
                                    res.status(201).jsonp({});
                                }
                            }
                        }).catch(() => {
                            res.status(400).jsonp("The request body could not be parsed ! ");
                        });
                    }
                }
            } else {
                res.status(404).jsonp("EHR with ehr_id " + ehr_id + " does not exist !");
            }
        }).catch((err) => {
            res.status(400).jsonp("The request has invalid content !" + err);
        });
    } else {
        res.status(400).jsonp("The request has invalid ehr_id format !");
    }
});

module.exports = router;