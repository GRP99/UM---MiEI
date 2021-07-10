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
        var query = `SELECT DISTINCT ?s ?title ?type ?year WHERE { ?type rdfs:subClassOf :Bibliography . ?s rdf:type ?type. OPTIONAL{?s :title ?title} . OPTIONAL{?s :year ?year} .} ORDER BY (?s) `;

        var encoded = encodeURIComponent(prefixes + query);

        axios.get(getLink + encoded).then(dados => {
            pubs = dados.data.results.bindings.map(bind => {
                return({
                    id: bind.s.value.split('#')[1],
                    title: bind.title.value,
                    year: bind.year.value,
                    type: bind.type.value.split('#')[1]
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
    var query = `SELECT ?s ?name (COUNT(?pub) AS ?tot) WHERE { ?s rdf:type :Author . ?s :nome ?name.  ?s :write ?pub. } GROUP BY ?s ?name ORDER BY ?s`;

    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        authors = dados.data.results.bindings.map(bind => {
            return({
                id: bind.s.value.split('#')[1],
                name: bind.name.value,
                tot : bind.tot.value
            })
        });
        res.status(200).jsonp(authors);
    }).catch(err => {
        res.status(500).jsonp(err);
    });
});

router.get('/authors/:id', function (req, res, next) {
    var query = 'SELECT DISTINCT ?pub ?title ?year ?type WHERE { :' + req.params.id + ' :write ?pub . ?pub :title ?title . ?pub :year ?year. ?type rdfs:subClassOf :Bibliography . ?pub rdf:type ?type. } ORDER BY (?pub)';

    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        authors = dados.data.results.bindings.map(bind => {
            return({
                id_pub: bind.pub.value.split('#')[1],
                title: bind.title.value,
                year: bind.year.value,
                type: bind.type.value.split('#')[1]
            })
        });
        res.status(200).jsonp(authors);
    }).catch(err => {
        res.status(500).jsonp(err);
    });
});


module.exports = router;
