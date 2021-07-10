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


function buildItems(items) {
    list_items = []
    items.forEach(item => {
        var item_construct = {
            namespace: item.namespace,
            objectRef_type: item.type,
            objectRef_id: {
                value: item.id.value
            },
        }
        list_items.push(item_construct)
    })
    return list_items
}


function buildFolder(folders, depth) {
    if (folders.length == 0) {
        return []
    } else {
        var list_folders = []
        folders.forEach(folder => {
            var folder_construct = {
                name: {
                    value: (folder.name) ? (folder.name) : depth + 1,
                    local_terminology_id: 'local'
                },
                archetype_node_id: 'openEHR-EHR-FOLDER.generic.v1',
                uid: {
                    value: uuidv4() + "::EHRManagement.IS2021.com::1",
                },
                items: (folder.items) ? buildItems(folder.items) : [],
                folder: (folder.folders) ? buildFolder(folder.folders, depth + 1) : []
            }
            list_folders.push(folder_construct)
        });
        return list_folders
    }
}


function findFolder(folder, list_paths) {
    if (list_paths.length != 0) {
        var find_folder = {}
        var flag = false
        folder.forEach(subfolder => {
            if (subfolder.name.value == list_paths[0]) {
                if (list_paths.slice(1).length == 0) {
                    find_folder = subfolder
                    flag = true
                } else {
                    find_folder = findFolder(subfolder.folder, list_paths.slice(1))
                }
            }
        });
        return find_folder
    }
}

/**
* @swagger
* tags:
*   name: DIRECTORY
*   description: Management of the directory FOLDER resource
*/

/**
* @swagger
* /ehr/{ehr_id}/directory:
*   get:
*     summary: Get folder in directory.
*     description: Retrieves the version of the directory associated with the EHR identified by ehr_id. If path is supplied, retrieves from the directory only the sub-FOLDER that is associated with that path
*     tags: [DIRECTORY]
*     parameters:
*       - in: path
*         name: ehr_id
*         required: true
*         description: EHR identifier taken from EHR.ehr_id.value
*         schema:
*           type: string
*           format: uuidv4
*           example: "7d44b88c-4199-4bad-97dc-d78268e01398"
*       - in: query
*         name: path
*         description: A path to a sub-folder; consists of slash-separated values of the name attribute of FOLDERs in the directory
*         schema:
*           type: string
*           example: episodes/a/b/c
*     responses:
*       200:
*         description: The requested directory FOLDER is successfully retrieved
*       400:
*          description: The request has invalid content
*       404:
*          description: EHR with ehr_id does not exist or path does not exists within the directory
*/
router.get("/", function(req, res, next) {
    var ehr_id = req.baseUrl.split('/')[2]
    if (isUUID(ehr_id)) {
        EHR.getEHRSummaryByID(ehr_id).then((ehr) => {
            if (ehr) {
                var flag = false
                var find_folder = {}
                ehr.folders.forEach(folder => {
                    if (folder.uid.value == ehr.directory.objectRef_id.value) {
                        flag = true
                        find_folder = folder
                    }
                });
                if (!(flag)) {
                    res.status(404).jsonp("A directory does not exist ! ")
                }
                if (req.query.path) {
                    paths = req.query.path.split('/')

                    if (paths[0] == find_folder.name.value) {
                        if (paths.length > 1) {
                            find_folder_path = findFolder(find_folder.folder, paths.slice(1))
                            if (JSON.stringify(find_folder_path) != '{}') {
                                res.setHeader("Content-Type", "application/json");
                                res.status(200).jsonp({
                                    'uid': find_folder_path.uid.value,
                                    'items': find_folder_path.items,
                                    'folders': find_folder_path.folder
                                })
                            } else {
                                res.status(404).jsonp("The " + req.query.path + ' does not exists within the folders !')
                            }
                        } else {
                            res.setHeader("Content-Type", "application/json");
                            res.status(200).jsonp({
                                'uid': find_folder.uid.value,
                                'items': find_folder.items,
                                'folders': find_folder.folder
                            })
                        }

                    } else {
                        res.status(404).jsonp("The " + req.query.path + ' does not exists within the folders !')
                    }
                } else {
                    res.setHeader("Content-Type", "application/json");
                    res.status(200).jsonp({
                        'uid': find_folder.uid.value,
                        'items': find_folder.items,
                        'folders': find_folder.folder
                    })
                }
            } else {
                res.status(404).jsonp("The EHR with " + ehr_id + " does not exist!")
            }
        }).catch((err) => {
            res.status(400).jsonp("The request has invalid " + ehr_id + " !")
        });
    }
});


/**
* @swagger
* /ehr/{ehr_id}/directory/{version_uid}:
*   get:
*     summary: Get folder in directory version.
*     description: Retrieves a particular version of the directory FOLDER identified by version_uid and associated with the EHR identified by ehr_id. If path is supplied, retrieves from the directory only the sub-FOLDER that is associated with that path
*     tags: [DIRECTORY]
*     parameters:
*       - in: path
*         name: ehr_id
*         required: true
*         description: EHR identifier taken from EHR.ehr_id.value
*         schema:
*           type: string
*           format: uuidv4
*           example: "7d44b88c-4199-4bad-97dc-d78268e01398"
*       - in: path
*         name: version_uid
*         required: true
*         description: VERSION identifier taken from VERSION.uid.value
*         schema:
*           type: string
*           example: "8849182c-82ad-4088-a07f-48ead4180515::openEHRSys.example.com::1"
*       - in: query
*         name: path
*         description: A path to a sub-folder; consists of slash-separated values of the name attribute of FOLDERs in the directory
*         schema:
*           type: string
*           example: episodes/a/b/c
*     responses:
*       200:
*         description: The requested directory FOLDER is successfully retrieved
*       400:
*          description: The request has invalid content
*       404:
*          description: The EHR with ehr_id does not exist or when a directory with version_uid does not exist or path does not exists within the directory
*/
router.get("/:version_uid", function(req, res, next) {
    var ehr_id = req.baseUrl.split('/')[2]
    if (isUUID(ehr_id)) {
        EHR.getEHRSummaryByID(ehr_id).then((ehr) => {
            if (ehr) {
                var flag = false
                var find_folder = {}
                ehr.folders.forEach(folder => {
                    if (folder.uid.value == req.params.version_uid) {
                        flag = true
                        find_folder = folder
                    }
                });
                if (!(flag)) {
                    res.status(404).jsonp("A directory with " + req.params.version_uid + " does not exist!")
                }
                if (req.query.path) {
                    paths = req.query.path.split('/')  
                                  
                    if (paths[0] == find_folder.name.value) {    
                        if (paths.length > 1){
                            find_folder_path = findFolder(find_folder.folder, paths.slice(1))                    
                            if (JSON.stringify(find_folder_path) != '{}') {
                                res.setHeader("Content-Type", "application/json");
                                res.status(200).jsonp({
                                    'uid': find_folder_path.uid.value,
                                    'items': find_folder_path.items,
                                    'folders': find_folder_path.folder
                                })
                            } else {
                                res.status(404).jsonp("The " + req.query.path + ' does not exists within the folders !')
                            }
                        }
                        else{
                            res.setHeader("Content-Type", "application/json");
                                res.status(200).jsonp({
                                    'uid': find_folder.uid.value,
                                    'items': find_folder.items,
                                    'folders': find_folder.folder
                                })
                        }                                                              
                        
                    } else {
                        res.status(404).jsonp("The " + req.query.path + ' does not exists within the folders !')
                    }
                } else {
                    res.setHeader("Content-Type", "application/json");
                    res.status(200).jsonp({
                        'uid': find_folder.uid.value,
                        'items': find_folder.items,
                        'folders': find_folder.folder
                    })
                }
            } else {
                res.status(404).jsonp("The EHR with " + ehr_id + " does not exist!")
            }
        }).catch((err) => {
            res.status(400).jsonp("The request has invalid " + ehr_id + " !")
        });
    } else {
        res.status(400).jsonp("The request has invalid " + ehr_id + " !")
    }
});


/**
* @swagger
* /ehr/{ehr_id}/directory:
*   delete:
*     summary: Delete directory.
*     description: Deletes directory FOLDER associated with the EHR identified by ehr_id
*     tags: [DIRECTORY]
*     parameters:
*       - in: path
*         name: ehr_id
*         required: true
*         description: EHR identifier taken from EHR.ehr_id.value
*         schema:
*           type: string
*           format: uuidv4
*           example: "7d44b88c-4199-4bad-97dc-d78268e01398"
*       - in: header
*         name: If-Match
*         required: true
*         description: The existing latest version_uid of directory FOLDER resource must be specified in the If-Match header
*         schema:
*           type: string
*           example: 8849182c-82ad-4088-a07f-48ead4180515::openEHRSys.example.com::1
*     responses:
*       204:
*         description: The directory was deleted
*       400:
*          description: The request has invalid content
*       404:
*          description: The EHR with ehr_id does not exist
*       412:
*          description: The If-Match request header doesn’t match the latest version on the service side.
*/
router.delete("/", function(req, res, next) {
    var ehr_id = req.baseUrl.split('/')[2]
    if (isUUID(ehr_id)) {
        var folder_id = req.headers["if-match"]
        EHR.getEHRSummaryByID(ehr_id).then((data) => {
            if (data) {
                EHR.removeFolder(ehr_id, folder_id).then((data) => {
                    if (data.nModified != 0) {
                        EHR.getDirectory(ehr_id).then((data) => {
                            if (data[0].directory.objectRef_id.value == folder_id) {
                                EHR.getFolders(ehr_id).then((data) => {
                                    if (data[0].folders.length != 0) {
                                        EHR.updateDirectory(ehr_id, data[0].folders[0].uid.value).then((data) => {}).catch((err) => {
                                            res.status(400).jsonp("The request has invalid content !")
                                        });
                                    } else {
                                        EHR.restoreDirectory(ehr_id).then((data) => {}).catch((err) => {
                                            res.status(400).jsonp("The request has invalid content !")
                                        });
                                    }
                                }).catch((err) => {
                                    res.status(400).jsonp("The request has invalid content ! ")
                                });
                            }
                        }).catch((err) => {
                            res.status(400).jsonp("The request has invalid content !")
                        });
                        res.status(204).jsonp("The directory was deleted ! ")
                    } else {
                        EHR.getFolders(ehr_id).then((data) => {
                            if (data[0].folders.length != 0) {
                                var flag = false
                                var version_uid = ''
                                data[0].folders.forEach(folder => {
                                    if (folder.uid.value.match(folder_id.split("::")[0])) {
                                        flag = true
                                        version_uid = folder.uid.value
                                    }
                                });
                                if (flag) {
                                    res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/directory/" + version_uid);
                                    res.setHeader("ETag", version_uid);
                                    res.status(412).jsonp("The If-Match request header doesn’t match the latest version on the service side !")
                                } else {
                                    res.status(400).jsonp("The If-Match request header doesn’t match !")
                                }
                            } else {
                                res.status(400).jsonp("The If-Match request header doesn’t match !")
                            }
                        }).catch((err) => {
                            res.status(400).jsonp("The request has invalid content ! ")
                        });
                    }
                }).catch((err) => {
                    res.status(400).jsonp("The request has invalid content ! ")
                });
            } else {
                res.status(404).jsonp("The EHR with " + ehr_id + " does not exist !")
            }
        }).catch(() => {
            res.status(400).jsonp("The request has invalid " + ehr_id + " !")
        });
    } else {
        res.status(400).jsonp("The request has invalid " + ehr_id + " !")
    }
});


/**
* @swagger
* /ehr/{ehr_id}/directory:
*   put:
*     summary: Update directory.
*     description: Updates directory FOLDER associated with the EHR identified by ehr_id.
*     tags: [DIRECTORY]
*     parameters:
*       - in: path
*         name: ehr_id
*         required: true
*         description: EHR identifier taken from EHR.ehr_id.value.
*         schema:
*           type: string
*           format: uuidv4
*           example: "7d44b88c-4199-4bad-97dc-d78268e01398"
*       - in: header
*         name: If-Match
*         required: true
*         description: The existing latest version_uid of directory FOLDER resource must be specified in the If-Match header.
*         schema:
*           type: string
*           example: 8849182c-82ad-4088-a07f-48ead4180515::openEHRSys.example.com::1
*       - name: "Prefer"
*         in: "header"
*         required: true
*         description: Content body is only returned when Prefer header has return=representation otherwise only headers are returned.
*         type: string
*         example: "return={representation|minimal}"
*     requestBody:
*       description: Folder structures for this EHR.
*       required: true
*       content:
*         application/json:
*           schema:
*             type: object
*             properties:
*               name:
*                 type: string
*                 example: episodes
*               items:
*                 type: array
*                 items:
*                   type: object
*                   properties:
*                     namespace:
*                       type: string
*                       example: local
*                     type:
*                       type: string
*                       example: ITEM
*                     id:
*                       type: object
*                       properties:
*                         value:
*                           type: string
*                           example: episode1
*               folders:
*                 type: array
*                 items:
*                   type: object
*                   properties:
*                     name:
*                       type: string
*                       example: a
*                     items:
*                       type: array
*                       items:
*                         type: object
*                         properties:
*                           namespace:
*                             type: string
*                             example: local
*                           type:
*                             type: string
*                             example: ITEM
*                           id:
*                             type: object
*                             properties:
*                               value:
*                                type: string
*                                example: a1
*                     folders:
*                       type: array
*                       example: []
*     responses:
*       200:
*         description: The directory is successfully updated and the updated resource is returned in the body when Prefer header value is return=representation.
*       204:
*         description: The directory was updated and Prefer header is missing or is set to return=minimal.
*       400:
*          description: The request has invalid ehr_id or invalid content.
*       404:
*          description: EHR with ehr_id does not exist.
*       412:
*          description: The If-Match request header doesn’t match the latest version on the service side.
*/
router.put("/", function(req, res, next) {
    var ehr_id = req.baseUrl.split('/')[2]
    if (isUUID(ehr_id)) {
        var folder_id = req.headers["if-match"]
        var prefer_return = req.headers['prefer'].split('return=')[1]
        EHR.getEHRSummaryByID(ehr_id).then((ehr) => {
            if (ehr) {
                var version = parseInt(folder_id.split('::')[2]) + 1
                var new_folder_id = folder_id.split('::')[0] + '::' + folder_id.split('::')[1] + '::' + version
                var folderSchema = {
                    name: {
                        value: (req.body.name) ? (req.body.name) : 'root',
                        local_terminology_id: 'local'
                    },
                    archetype_node_id: 'openEHR-EHR-FOLDER.generic.v1',
                    uid: {
                        value: new_folder_id,
                    },
                    items: (req.body.items) ? buildItems(req.body.items) : [],
                    folder: (req.body.folders) ? buildFolder(req.body.folders, 0) : []
                }
                EHR.updateFolder(ehr_id, folder_id, folderSchema).then((updateFolder) => {
                    if (updateFolder.nModified != 0) {
                        EHR.getDirectory(ehr_id).then((directory) => {
                            if (directory[0].directory.objectRef_id.value == folder_id) {
                                EHR.updateDirectory(ehr_id, new_folder_id).then((updateDirectory) => {}).catch((err) => {
                                    res.status(400).jsonp("The request has invalid content !")
                                });
                            }
                        }).catch((err) => {
                            res.status(400).jsonp("The request has invalid content !")
                        });
                        if (prefer_return == 'representation') {
                            res.setHeader("Content-Type", "application/json");
                            res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/directory/" + folderSchema.uid.value);
                            res.setHeader("ETag", folderSchema.uid.value);
                            res.status(200).jsonp({
                                'uid': folderSchema.uid.value,
                                'items': folderSchema.items,
                                'folders': folderSchema.folder
                            })
                        } else {
                            if (prefer_return == 'minimal') {
                                res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/directory/" + new_folder_id);
                                res.setHeader("ETag", new_folder_id);
                                res.status(204).jsonp("The directory was updated ! ")
                            } else {
                                res.status(400).jsonp("The request has invalid content !")
                            }
                        }
                    } else {
                        EHR.getFolders(ehr_id).then((folders) => {
                            if (folders[0].folders.length != 0) {
                                var flag = false
                                var version_uid = ''
                                folders[0].folders.forEach(folder => {
                                    if (folder.uid.value.match(folder_id.split("::")[0])) {
                                        flag = true
                                        version_uid = folder.uid.value
                                    }
                                });
                                if (flag) {
                                    res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/directory/" + version_uid);
                                    res.setHeader("ETag", version_uid);
                                    res.status(412).jsonp("The If-Match request header doesn’t match the latest version on the service side !")
                                } else {
                                    res.status(400).jsonp("The If-Match request header doesn’t match !")
                                }
                            } else {
                                res.status(400).jsonp("The If-Match request header doesn’t match !")
                            }
                        }).catch((err) => {
                            res.status(400).jsonp("The request has invalid content ! ")
                        });
                    }
                }).catch((err) => {
                    res.status(400).jsonp("The request has invalid " + ehr_id + " !")
                });
            } else {
                res.status(404).jsonp("The EHR with " + ehr_id + " does not exist!")
            }
        }).catch((err) => {
            res.status(400).jsonp("The request has invalid " + ehr_id + " !")
        });
    } else {
        res.status(400).jsonp("The request has invalid " + ehr_id + " !")
    }
});

/**
* @swagger
* /ehr/{ehr_id}/directory:
*   post:
*     summary: Create directory.
*     description: Creates a new directory FOLDER associated with the EHR identified by ehr_id.
*     tags: [DIRECTORY]
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
*       description: Folder structures for this EHR.
*       required: true
*       content:
*         application/json:
*           schema:
*             type: object
*             properties:
*               name:
*                 type: string
*                 example: episodes
*               items:
*                 type: array
*                 items:
*                   type: object
*                   properties:
*                     namespace:
*                       type: string
*                       example: local
*                     type:
*                       type: string
*                       example: ITEM
*                     id:
*                       type: object
*                       properties:
*                         value:
*                           type: string
*                           example: episode1
*               folders:
*                 type: array
*                 items:
*                   type: object
*                   properties:
*                     name:
*                       type: string
*                       example: a
*                     items:
*                       type: array
*                       items:
*                         type: object
*                         properties:
*                           namespace:
*                             type: string
*                             example: local
*                           type:
*                             type: string
*                             example: ITEM
*                           id:
*                             type: object
*                             properties:
*                               value:
*                                type: string
*                                example: a1
*                     folders:
*                       type: array
*                       example: []
*     responses:
*       201:
*         description: The new directory was created.
*       400:
*          description: The request has invalid ehr_id or invalid content.
*       404:
*          description: EHR with ehr_id does not exist.
*/
router.post("/", function(req, res, next) {
    var ehr_id = req.baseUrl.split('/')[2]
    var prefer_return = req.headers['prefer'].split('return=')[1]
    if (isUUID(ehr_id)) {
        EHR.getEHRSummaryByID(ehr_id).then((data) => {
            if (data) {
                var folderSchema = {
                    name: {
                        value: (req.body.name) ? (req.body.name) : 'root',
                        local_terminology_id: 'local'
                    },
                    archetype_node_id: 'openEHR-EHR-FOLDER.generic.v1',
                    uid: {
                        value: uuidv4() + "::EHRManagement.IS2021.com::1",
                    },
                    items: (req.body.items) ? buildItems(req.body.items) : [],
                    folder: (req.body.folders) ? buildFolder(req.body.folders, 0) : []
                }
                if (data.folders.length == 0) {
                    var directory = {
                        namespace: "local",
                        objectRef_type: "DIRECTORY",
                        objectRef_id: {
                            value: folderSchema.uid.value
                        }
                    }

                    EHR.addDirectory(ehr_id, directory).then(() => {}).catch((err) => {
                        res.status(400).jsonp("The request has invalid content (e.g. content could not be converted to a valid directory FOLDER) !")
                    });
                }

                EHR.addFolder(ehr_id, folderSchema).then((data) => {
                    if (prefer_return == 'representation') {
                        res.setHeader("Content-Type", "application/json");
                        res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/directory/" + folderSchema.uid.value);
                        res.setHeader("ETag", folderSchema.uid.value);
                        res.status(201).jsonp({
                            'uid': folderSchema.uid.value,
                            'items': folderSchema.items,
                            'folders': folderSchema.folder
                        })
                    } else {
                        if (prefer_return == 'minimal') {
                            res.setHeader("Content-Type", "application/json");
                            res.setHeader("Location", "http://localhost:2021/ehr/" + ehr_id + "/directory/" + folderSchema.uid.value);
                            res.setHeader("ETag", folderSchema.uid.value);
                            res.status(201).jsonp({})
                        }
                    }
                }).catch((err) => {
                    res.status(400).jsonp("The request has invalid content (e.g. content could not be converted to a valid directory FOLDER) !")
                });
            } else {
                res.status(404).jsonp("The EHR with " + ehr_id + " does not exist!")
            }
        }).catch((err) => {
            res.status(400).jsonp("The request has invalid " + ehr_id + " !")
        });
    } else {
        res.status(400).jsonp("The request has invalid " + ehr_id + " !")
    }
});

module.exports = router;