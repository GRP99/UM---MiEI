var express = require("express");
var router = express.Router();
var multer = require("multer");
var upload = multer({dest: "uploads/"});
var fs = require("fs");
var path = require("path");
var rimraf = require("rimraf");

var FControl = require("../controllers/files");
var NControl = require("../controllers/news");
var User = require("../controllers/users");

var Limpa = require('../public/javascripts/limpa')
var SIP = require('../public/javascripts/unzip_or_zip_SIP')
var Manifesto = require('../public/javascripts/verificaManifesto')

/*
function verificaAutoriadade(autor, usr) {
    return autor == usr;
}
*/

// This route gets all files, but u need to be admin to have access to all files
// First we see if request is sent by a admin, if not we responde with error 401, if it is proceed.
router.get("/", function (req, res, next) {
    if (req.user.level == "admin") {
        FControl.list().then((data) => {
            res.status(200).jsonp(data);
        }).catch((err) => {
            res.status(500).jsonp(err);
        });
    } else {
        res.status(401).jsonp({error: 'Only Admins are allowed!'});
    }
});

// This route gets all public files. If they are public everyone has access to them xD
router.get("/public", function (req, res, next) {
    FControl.publicFiles().then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp(err)
    });
});

// This route gets all files from a user, only the user or the admin can get them.
router.get("/fromUser", function (req, res) {
    var userID = req.user.id;
    FControl.filesbyUser(userID).then((data) => {
        res.status(200).jsonp(data)
    }).catch((err) => {
        res.status(500).jsonp(err)
    });
});

router.get("/resourceTypes", function (req, res) {
    FControl.getResourceTypes().then((data) => {
        res.status(200).jsonp(data)
    }).catch((err) => {
        res.status(500).jsonp(err)
    });
});

/******** HOME - TOP 3  ********/

// Top 3 classified files
router.get("/topClass", function (req, res) {
    FControl.topclassificados().then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp(err);
    });
});

// Top 3 Favourites Files
router.get("/topFavs", function (req, res) {
    FControl.topfavoritos().then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp(err);
    });
});

// Top 3 Authors by number of Uploads
router.get("/topAut", function (req, res) {
    FControl.topautores().then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp(err);
    });
});

// This route gets an file by it's ID, it's only available if it is public, or it's the autor or the admin
router.get("/:id", function (req, res, next) {
    FControl.lookup(req.params.id).then((data) => {
        if (data.autor == req.user._id || req.user.level == "admin" || data.privacy == 0) {
            res.status(200).jsonp(data);
        } else {
            res.status(401).jsonp({error: 'You are not allowed to view this file!'});
        }
    }).catch((err) => res.status(500).jsonp(err));
});

// classify a file
router.put("/classificar/:id", function (req, res, next) { 
    /** variables **/
    id_user = req.user._id
    id_file = req.params.id
    // If user wants to remove
    if(req.query.class == "remove") {
        FControl.lookup(id_file).then((result) => {
            temp = id_user+"?;"+req.query.atual
            result.estrelas.autores.forEach(element => {
                if (element == temp){
                    /** Remove and calculates new media **/
                    var size = result.estrelas.autores.length
                    var oldmedia = result.estrelas.numero
                    var sub = ((oldmedia * size) - parseInt(req.query.atual))
                    var newsize = size - 1
                    if (newsize == 0)
                        var media = 0
                    else 
                        var media = sub / (size - 1)
                    media_n = media.toFixed(2)
                    FControl.removeClassificacao(id_file, temp, media_n).then((data1) => {
                        res.status(200).jsonp({classificacao: data1.numero});
                    }).catch((err) => {
                        res.status(200).jsonp(err);
                    });
                }
            })
        });
    }
    // If user wants to classify with another rating
    else {
        FControl.lookup(id_file).then((result) => {
            temp = id_user+"?;"+req.query.atual
            result.estrelas.autores.forEach(element => {
                if (element == temp){
                    /** Remove and calculates new media **/
                    var size = result.estrelas.autores.length
                    var oldmedia = result.estrelas.numero
                    var sub = ((oldmedia * size) - parseInt(req.query.atual))
                    var newsize = size - 1
                    if (newsize == 0)
                        var media = 0
                    else
                        var media = sub / (size - 1)
                    media_n = media.toFixed(2)
                    FControl.removeClassificacao(id_file, temp, media_n).then((data2) => {
                        res.status(200).jsonp({classificacao: data2.numero});
                    }).catch((err) => {
                        res.status(200).jsonp(err);
                    });
                }
            })
            FControl.classifica(id_file, id_user, req.query.class).then((data) => {
                res.status(200).jsonp({classificacao: data.numero});
            }).catch((err) => {
                res.status(200).jsonp(err);
            });
        });
    }
});

// add or remove userid to favourites of a file
router.put("/addremoveFavourite/:id", (req, res) => {
    id_user = req.user._id;
    id_file = req.params.id;

    var found = 0;

    FControl.lookup(id_file).then((result) => {
        result.favoritos.forEach(f => {
            if (f == id_user)
                found = 1;
        })
        if (found == 0) {
            FControl.addFav(id_file, id_user).then((data) => {
                res.status(200).jsonp(data)
            }).catch((err) => {
                res.status(500).jsonp(err)
            });
        }
        else {
            FControl.removeFav(id_file, id_user).then((data) => {
                res.status(200).jsonp(data)
            }).catch((err) => {
                res.status(500).jsonp(err)
            });
        }
    });
});

// remove userid to favourites of a file
router.put("/removeFavourite/:id", function (req, res, next) { 
    id_user = req.user._id
    id_file = req.params.id;
    FControl.removeFav(id_file, id_user).then((data) => {
        res.status(200).jsonp(data)
    }).catch((err) => {
        res.status(500).jsonp(err)
    });
});

// privacy (works)
router.put("/changeprivacy/:id", (req, res) => { 
    FControl.lookup(req.params.id).then((result) => {
        if (req.user._id == result.autor) {
            FControl.changeprivacy(req.params.id).then((data) => {
                res.status(200).jsonp(data);
            }).catch((err) => {
                res.status(500).jsonp(err);
            });
        } else {
            res.status(401).jsonp({error: 'You are not allowed to do this!'});
        }
    });
});

// download
router.get("/download/:id_autor/:id", function (req, res) {
    FControl.lookup(req.params.id).then((file) => {
        if (req.user.level == "admin" || req.user._id == file.autor || file.privacy == 0) {
            let path = __dirname + '/../public/fileStore/' + req.params.id_autor + "/uploads/" + file.name
            SIP.zip(path);
            let quarantinePath = path + "_dip"
            let dirpath = __dirname + "/../public/fileStore/" + req.params.id_autor + "/downloads"
            fs.mkdirSync(dirpath, {recursive: true});
            let newPath = dirpath + "/" + file.name
            fs.rename(quarantinePath, newPath, function (error) {
                if (error) {
                    res.status(500).jsonp({error: "Error in the downloads folder"});
                }
            })
            res.download(newPath);
        } else {
            res.status(401);
        }
    });
});


// upload file (works)
router.post("/", upload.single("myFile"), (req, res) => {
    if (req.user.level != "consumer") { 
        if (req.file != null) {
            if (req.file.mimetype == 'application/x-zip-compressed') {
                SIP.unzip(req.file.path);
                if (Manifesto.verifica(__dirname + '/../' + req.file.path + '_sip')) {
                    var obj_json = __dirname + '/../' + req.file.path + '_sip' + '/manifesto.json'
                    req.body.manifesto = JSON.stringify(require(obj_json));
                    let quarantinePath = __dirname + '/../' + req.file.path + '_sip'
                    let dirpath = __dirname + "/../public/fileStore/" + req.body.autor + "/uploads/"

                    fs.mkdirSync(dirpath, {recursive: true})

                    let newPath = dirpath + "/" + req.file.originalname
                    var normalizedPath = path.normalize(newPath)
                    var correctedPath = normalizedPath.replace(/\\/g, '/')

                    fs.rename(quarantinePath, newPath, function (error) {
                        if (error) {
                            res.status(500).jsonp({error: "Rename the quarantinePath to newPath !"});
                        }
                    })

                    var d = new Date().toISOString().substr(0, 16);

                    var fD = {
                        title: req.body.title,
                        subtitle: req.body.subtitle,
                        resourceType: req.body.resourceType,
                        creationDate: req.body.date,
                        registrationDate: d,
                        autor: req.body.autor,
                        name: req.file.originalname,
                        mimetype: req.file.mimetype,
                        size: req.file.size,
                        privacy: req.body.privacy,
                        descricao: req.body.descricao
                    }

                    FControl.insert(fD, correctedPath).then((result) => {
                        if (req.body.privacy == 0) {
                            User.lookUp(req.body.autor).then(dados => {
                                var news = {
                                    file: result._id,
                                    date: d,
                                    type: "File",
                                    autorID: req.body.autor,
                                    autor: dados.name,
                                    descricao: 'New submission: Producer ' + dados.name + ' has just released an ' + req.body.resourceType + ' entitled \"' + req.body.title + '\".'
                                }
                                NControl.insert(news)
                            })
                        }
                        res.redirect("http://localhost:3002/users/account")
                    }).catch(err => {
                        res.status(500).jsonp({error: "ERROR: Uploads folder error."});
                    });

                } else {
                    Limpa.eliminaPasta(__dirname + '/../' + req.file.path + '_sip');
                    res.redirect("http://localhost:3002/users/account?alert=1")
                }
            } else {
                Limpa.eliminaPasta(__dirname + '/../' + req.file.path);
                res.redirect("http://localhost:3002/users/account")
            }
        } else {
            res.status(500).jsonp(err);
        }
    }
});

// Edit File Fields
router.post("/edit/:id", (req, res) => {

    
    var id = req.params.id
    var title = req.body.title
    var subtitle = req.body.subtitle
    var descricao = req.body.descricao

    FControl.lookup(req.params.id).then((result) => {
        if (req.user._id == result.autor) {

            FControl.updateFile(id,title,subtitle,descricao).then(() => {
                res.redirect("http://localhost:3002/files/" + id );
            }).catch((err) => {
                res.redirect("http://localhost:3002/files/" + id );
            });
        }
        else {
            res.status(401).jsonp({error: 'You are not allowed to do this!'});
        }
    })
});


// delete file (works)
router.delete("/:id", (req, res) => {
    FControl.lookup(req.params.id).then((result) => {
        if (req.user.level == "admin" || req.user._id == result.autor) { // apagar ficheiro da pasta
            let fpath = "public/fileStore/" + result.autor + "/uploads/" + result.name;

            // Delete folder
            rimraf(fpath, function () {
                // console.log("Filepath deleted.");
            });
            FControl.remove(req.params.id).then((data) => {
                res.status(200).jsonp(data)
            }).catch((err) => {
                res.status(500).jsonp(err)
            });
        } else {
            res.status(401)
        }
    });
});


// all the files from user TO SEE UTILITY
router.get("/autor/:id", function (req, res, next) {
    id_autor = req.params.id;
    FControl.filesbyUser(id_autor).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp(err);
    });
});

// all the files public from user
router.get("/autorP/:id", function (req, res, next) {
    id_autor = req.params.id;
    FControl.filesbyUserP(id_autor).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp(err);
    });
});


// add comment
router.post('/:id/comentarios', function (req, res) {
    FControl.adicionarComentario(req.params.id, req.body).then(dados => {
        res.redirect("http://localhost:3002/files/" + req.params.id )
    }).catch(erro => {
        res.redirect("http://localhost:3002/files/" + req.params.id )
    })
});


// remove comment
router.delete('/:id/comentarios', function (req, res) {
    var temp = ""
    FControl.lookup(req.params.id).then((result) => {
        result.comentarios.forEach(element => {
            if(element._id == req.query.comentario) {
                temp = element.autor
            }
        });
        if (req.user.level == "admin" || req.user._id == result.autor || req.user._id == temp) {
            FControl.removerComentario(req.params.id, req.query.comentario).then(dados => {
                res.jsonp(dados)
            }).catch(e => {
                res.status(500).jsonp(e)
            })
        } else {
                    res.status(401).jsonp({error: 'You are not allowed to do this!'});
        }
    })
});

module.exports = router;