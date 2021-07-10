var express = require('express');
var axios = require('axios');

var router = express.Router();

var prefixes = ` PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX owl: <http://www.w3.org/2002/07/owl#>
    PREFIX : <http://www.daml.org/2003/01/periodictable/PeriodicTable#>
    prefix pt: <http://www.daml.org/2003/01/periodictable/PeriodicTable#>
`

var getLink = "http://localhost:7200/repositories/tabelaPeriodica?query=";


/* GET home page. */
router.get([
    '/', '/appPeriodicTable'
], function (req, res, next) {
    res.render('index', {});
});


router.get('/elements', function (req, res, next) {
    var query = `SELECT ?s ?simb ?name ?anumber WHERE { ?s rdf:type pt:Element ; :symbol ?simb ; :name ?name ; :atomicNumber ?anumber.} ORDER BY (?anumber)`;

    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        elements = dados.data.results.bindings.map(bind => {
            return({
                id: bind.s.value.split('#')[1],
                nome: bind.name.value,
                simb: bind.simb.value,
                anumber: bind.anumber.value
            })
        });

        res.render('elements', {elements: elements});

    }).catch(e => {
        res.render('error', {error: e});
    });
});


router.get('/groups', function (req, res, next) {
    var query = `SELECT ?s ?name ?number WHERE { ?s rdf:type pt:Group . OPTIONAL{ ?s :name ?name } . OPTIONAL{ ?s :number ?number } . } ORDER BY (?s)`;

    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        groups = dados.data.results.bindings.map(bind => {
            return({
                id: bind.s.value.split('#')[1],
                number: (bind.number) ? bind.number.value : "---",
                nome: (bind.name) ? bind.name.value : "---"
            })
        });

        res.render('groups', {groups: groups});

    }).catch(e => {
        res.render('error', {error: e});
    });
});


router.get('/periods', function (req, res, next) {
    var query = `SELECT ?s ?number WHERE { ?s rdf:type pt:Period . ?s :number ?number } ORDER BY (?number)`;
    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        periods = dados.data.results.bindings.map(bind => {
            return({
                id: bind.s.value.split('#')[1],
                number: (bind.number) ? "Period " + bind.number.value : "---"
            })
        });

        res.render('periods', {periods: periods});

    }).catch(e => {
        res.render('error', {error: e});
    });
});


router.get('/element/:id', function (req, res, next) {
    var query = 'SELECT DISTINCT ?anumber ?aweight ?casregid ?color ?name ?symbol ?block ?classi ?stastate ?group ?period WHERE {  pt:' + req.params.id +' ?p ?o ; :atomicNumber ?anumber ; :atomicWeight ?aweight ; :casRegistryID ?casregid ; :color ?color ; :name ?name ; :symbol ?symbol ; :block ?block ; :classification ?classi ; :standardState ?stastate ; :group ?group ; :period ?period . }'

    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        element = dados.data.results.bindings.map(bind => {
            return({
                anumber: bind.anumber.value,
                aweight: bind.aweight.value,
                casregid: bind.casregid.value,
                color: bind.color.value,
                name: bind.name.value,
                symbol: bind.symbol.value,
                block: bind.block.value.split('#')[1],
                classi: bind.classi.value.split('#')[1],
                stastate: bind.stastate.value.split('#')[1],
                group: bind.group.value.split('#')[1],
                period: bind.period.value.split('#')[1]
            })
        });
        res.render('element', {
            id: req.params.id,
            element: element
        });

    }).catch(e => {
        res.render('error', {error: e});
    });
});


router.get('/group/:id', function (req, res, next) {
    var query = 'SELECT * WHERE { pt:' + req.params.id + ' ?p ?o . }';

    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        group = dados.data.results.bindings.map(bind => {
            return({
                p: bind.p.value.split('#')[1],
                o: (bind.o.type == 'literal') ? bind.o.value : bind.o.value.split('#')[1]
            })
        });

        var number = '---'
        var name = '---'
        group.forEach(g => {
            if (g.p == 'number') {
                number = g.o
            }
            if (g.p == 'name') {
                name = g.o
            }
        });

        res.render('group', {
            number: number,
            name: name,
            group: group
        });

    }).catch(e => {
        res.render('error', {error: e});
    });
});


router.get('/period/:id', function (req, res, next) {
    var query = 'SELECT distinct ?name WHERE { pt:' + req.params.id + ' ?p ?o . pt:' + req.params.id + ' :element ?name } ORDER BY ?name';

    var encoded = encodeURIComponent(prefixes + query);

    axios.get(getLink + encoded).then(dados => {
        period = dados.data.results.bindings.map(bind => {
            return({
                element: bind.name.value.split('#')[1]
            })
        });

        res.render('period', {
            id: req.params.id.split('_')[1],
            period: period
        });

    }).catch(e => {
        res.render('error', {error: e});
    });
});

module.exports = router;
