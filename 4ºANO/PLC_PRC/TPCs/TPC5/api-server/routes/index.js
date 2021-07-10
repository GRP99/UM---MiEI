var express = require('express');
var router = express.Router();
var axios = require('axios')

// Prefixos obtidos através do seguinte link http://epl.di.uminho.pt:8738/api/rdf4j/repository/A83732-TP5/namespaces
var prefixes = ` PREFIX owl: <http://www.w3.org/2002/07/owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX xml: <http://www.w3.org/XML/1998/namespace>
    PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
    PREFIX : <http://www.di.uminho.pt/prc2021/A83732-TP5#>
`

// Ver id repositório através do seguinte link http://epl.di.uminho.pt:8738/api/rdf4j/management/listRepos
var getLink = "http://epl.di.uminho.pt:8738/api/rdf4j/query/A83732-TP5?query=";
var getLinkUpdate = "http://epl.di.uminho.pt:8738/api/rdf4j/update/A83732-TP5";

router.get('/pubs', function (req, res, next) {
    if (!(req.query.type)) {
        var query = `SELECT ?s ?title WHERE { ?s rdf:type :Bibliography . OPTIONAL{?s :title ?title}. } ORDER BY (?s) `;

        var encoded = encodeURIComponent(prefixes + query);

        axios.get(getLink + encoded).then(dados => {
            pubs = dados.data.results.bindings.map(bind => {
                return({
                    id: bind.s.value.split('#')[1],
                    title: bind.title.value

                })
            });
            res.status(200).jsonp(pubs);
        }).catch(err => {
            res.status(500).jsonp(err);
        });
    } else {
        pubtype = req.query.type
        var query = 'SELECT ?s ?title WHERE { ?s rdf:type :' + pubtype.charAt(0).toUpperCase() + pubtype.slice(1) + ' . OPTIONAL{?s :title ?title}. } ORDER BY (?s)';

        var encoded = encodeURIComponent(prefixes + query);

        axios.get(getLink + encoded).then(dados => {
            pubs = dados.data.results.bindings.map(bind => {
                return({
                    id: bind.s.value.split('#')[1],
                    title: bind.title.value

                })
            });
            res.status(200).jsonp(pubs);
        }).catch(err => {
            res.status(500).jsonp(err);
        });
    }
});

router.get('/pubs/:id', function (req, res, next) {
    var query = 'SELECT ?p ?o { :' + req.params.id + ' ?p ?o.} ORDER BY (?p)';

    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        aux_pub = dados.data.results.bindings.map(bind => {
            return({
                p: bind.p.value.split('#')[1],
                o: (bind.o.type == 'literal') ? bind.o.value : bind.o.value.split('#')[1]
            })
        });

        var pub = []
        var array_authors = []
        var array_editors = []
        var flag_authors = true
        var flag_editors = true
        aux_pub.forEach(p => {
            if (p['p'] != 'type') {
                if (p['p'] != "wasWritten" && p['p'] != "wasEdit") {
                    var obj = {};
                    obj[p['p']] = p['o'];
                    pub.push(obj);
                } else {
                    if (p['p'] != "wasEdit") {
                        var obj = {};
                        array_authors.push(p['o'])
                        obj[p['p']] = array_authors;
                        if (flag_authors) {
                            pub.push(obj);
                            flag_authors = false
                        }
                    } else {
                        var obj = {};
                        array_editors.push(p['o'])
                        obj[p['p']] = array_editors;
                        if (flag_editors) {
                            pub.push(obj);
                            flag_editors = false
                        }
                    }
                }
            } else {
                if (p['o'] != "Bibliography" && p['o'] != "NamedIndividual") {
                    var obj = {};
                    obj[p['p']] = p['o'];
                    pub.push(obj);
                }
            }
        });

        res.status(200).jsonp(pub);
    }).catch(err => {
        res.status(500).jsonp(err);
    });
});

router.get('/authors', function (req, res, next) {
    var query = `SELECT ?s ?name WHERE { ?s rdf:type :Author . OPTIONAL{?s :nome ?name}. } ORDER BY (?s)`;

    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        authors = dados.data.results.bindings.map(bind => {
            return({
                id: bind.s.value.split('#')[1],
                name: bind.name.value

            })
        });
        res.status(200).jsonp(authors);
    }).catch(err => {
        res.status(500).jsonp(err);
    });
});

router.get('/authors/:id', function (req, res, next) {
    var query = 'SELECT DISTINCT ?pub ?title WHERE { :' + req.params.id + ' :write ?pub . ?pub :title ?title . } ORDER BY (?pub)';

    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        authors = dados.data.results.bindings.map(bind => {
            return({
                id_pub: bind.pub.value.split('#')[1],
                title: bind.title.value
            })
        });
        res.status(200).jsonp(authors);
    }).catch(err => {
        res.status(500).jsonp(err);
    });
});

// Erro : Request failed with status code 415 
// Nao sei qual o tipo que ele quer no post, nao funciona nem com json nem com x-www-form-urlencoded
// Codigo = https://github.com/Tibblue/Open-Web-Ontobud/blob/36ac9c78eb45facc00aafdaeef44439bedcfc1b7/backend/routes/api/rdf4j/query.js
// HELP PLEASE ??
router.post('/pubs', function (req, res, next) {
    if (req.body.type && req.body.id && req.body.title) {
        var query = 'INSERT DATA {\n:' + req.body.id + ' rdf:type owl:NamedIndividual ,\n';
        query += '                    :' + req.body.type + ' ;\n';

        var number_line = 0
        for (dados in req.body) {
            if (dados == 'wasWritten') {
                var flag = true
                req.body.wasWritten.forEach(author => {
                    if (flag) {
                        if (number_line > 0) {
                            query += " ;\n           :wasWritten :" + author
                            flag = false
                            number_line = number_line + 1
                        } else {
                            query += "           :wasWritten :" + author
                            flag = false
                            number_line = number_line + 1
                        }
                    } else {
                        query += " ,\n                       :" + author
                        number_line = number_line + 1
                    }
                });
            } else {
                if (dados == 'wasEdit') {
                    var flag = true
                    req.body.wasEdit.forEach(editor => {
                        if (flag) {
                            if (number_line > 0) {
                                query += " ;\n           :wasEdit :" + editor
                                flag = false
                                number_line = number_line + 1
                            } else {
                                query += "           :wasEdit :" + editor
                                flag = false
                                number_line = number_line + 1
                            }
                        } else {
                            query += " ,\n                       :" + editor
                            number_line = number_line + 1
                        }
                    });
                } else {
                    if (number_line == 0) {
                        if (dados != 'id' || dados != 'type') {
                            query += "           :" + dados + " \"" + req.body[dados] + "\""
                            number_line = number_line + 1
                        }
                    } else {
                        if (dados != 'id' || dados != 'type') {
                            query += " ;\n           :" + dados + " \"" + req.body[dados] + "\""
                            number_line = number_line + 1
                        }
                    }
                }
            }
        }
        query += ".\n}"
        console.log(query)
        var encoded = encodeURIComponent(prefixes + query);

        axios({
            method: 'post',
            url: getLinkUpdate,
            headers: {
                "Accept": "application/x-www-form-urlencoded",
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: {
               query: encoded,
            }
        }).then(resp => {
            res.status(200).jsonp(resp);
        }).catch(err => {
            res.status(500).jsonp(err);
        });

    } else {
        res.status(500).jsonp({error: "ERROR: Check that you have at least the id, the type of the bibliography and if it has a title !!!"});
    }

});

// Erro : Request failed with status code 415 
// Nao sei qual o tipo que ele quer no post, nao funciona nem com json nem com x-www-form-urlencoded
// Codigo = https://github.com/Tibblue/Open-Web-Ontobud/blob/36ac9c78eb45facc00aafdaeef44439bedcfc1b7/backend/routes/api/rdf4j/query.js
// HELP PLEASE ??
router.post('/authors', function (req, res, next) {
    if (req.body.name && req.body.id) {
        var queryinsert = 'INSERT DATA { :' + req.body.id + ' rdf:type owl:NamedIndividual . :' + req.body.id + ' rdf:type :Author . :' + req.body.id + ' :nome \"' + req.body.name + '\" .}';
        
        var encoded = encodeURIComponent(prefixes + queryinsert);

        axios({
            method: 'post',
            url: getLinkUpdate,
            headers: {
                "Accept": "application/json",
                'Content-Type': 'application/json'
            },
            data: {
               query: encoded,
            }
        }).then(resp => {
            res.status(200).jsonp(resp);
        }).catch(err => {
            res.status(500).jsonp(err);
        })
    } else {
        res.status(500).jsonp({error: "ERROR: Check that you have at least the id and if it has a name !!!"});
    }
});

router.delete('/pubs/:id', function (req, res, next) {});

router.delete('/authors/:id', function (req, res, next) {});

router.put('/pubs/:id', function (req, res, next) {});

router.put('/authors/:id', function (req, res, next) {});

module.exports = router;
