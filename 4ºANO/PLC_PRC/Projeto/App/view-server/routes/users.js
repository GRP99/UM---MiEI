var express = require('express');
var router = express.Router();
var axios = require('axios');
const cocktails = require('../../api-server/models/cocktails');
const bartenders = require('../../api-server/models/bartenders');
const locations = require('../../api-server/models/locations');
var users = "http://localhost:7300/users"

router.get('/login', function(req, res) {
    res.render('login', {
        level: req.user.level
    });
});

router.get('/register', function(req, res) {
    res.render('register', {
        level: req.user.level
    });
});

router.post('/register', function(req, res) {
    axios.post(users + '/register', req.body).then(dados => {
        res.redirect('/users/login');
    }).catch(e => {
        res.render('error', {
            text: "registration failed, try again with a new email !",
            error: e
        })
    });
});

/* get my account */
router.get('/myprofile', function(req, res) {

    if (req.cookies.token != null && req.user.level != 'user') {
        res.clearCookie('token');
    }

    var req_dados = axios.get("http://localhost:7300/users/byUsername/" + req.user._id);
    var cocktails = axios.get("http://localhost:7300/dbcocktails/getlikes/" + req.user._id);
    var bartenders = axios.get("http://localhost:7300/dbbartenders/getlikes/" + req.user._id);
    var locations = axios.get("http://localhost:7300/dblocations/getvisits/" + req.user._id);
    var bars = axios.get("http://localhost:7300/dbbars/getvisits/" + req.user._id);

    

    axios.all([req_dados, cocktails, bartenders, locations, bars]).then(axios.spread((...resp) => {
        res.render("myprofile", {
            dados: resp[0].data,
            cocktails: resp[1].data,
            bartenders: resp[2].data,
            locations: resp[3].data,
            bars: resp[4].data,
            level: req.user.level
        });

    })).catch(e => {
        res.render('error', {
            text: "Look like you are tipsy ... Unable to display your request!",
            error: e
        });
    });

})

/* login */
router.post('/login', function(req, res) {
    axios.post(users + '/login', req.body).then(dados => {
        res.cookie('token', dados.data.token)
        res.redirect("/homepage");
    }).catch(e => {
        res.render('error', {
            text: "check your credentials !",
            error: e
        });
    });
});

/* logout */
router.get('/logout', function(req, res) {
    res.clearCookie('token');
    res.redirect('http://localhost:7301/homepage');
})

module.exports = router;