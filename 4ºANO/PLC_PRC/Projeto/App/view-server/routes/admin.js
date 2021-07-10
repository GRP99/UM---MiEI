var express = require('express');
var router = express.Router();
var axios = require('axios');
var authenticate = "http://localhost:7300/admin"
var token;

/* Key before acessing admin rout */
key = {
    key: '2cf7a71be6dc9665aba1f32451e887442cb5a9a208b29e1598611236e60b490'
}

// authenticate admin
router.get('/', function(req, res, next) {
    if (req.query.key) {
        axios.post(authenticate + '/authenticate?key=' + req.query.key).then(t => {
            token = t.data.token
            res.redirect('/admin/access?token=' + token)
        }).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Application authentication went wrong !",
                error: e
            });
        });
    } else {
        res.render('error', {
            text: "Look like you are tipsy ... You did not insert any key !",
            error: "Missing Key",
        });
    }
});

router.get('/access', function(req, res, next) {
    if (req.query.token != null) {
        res.render('admin_password', {
            token: req.query.token
        });
    } else {
        res.render('error', {
            text: "Look like you are tipsy ... Try again !",
            error: "Unable to display your authentication page!"
        });
    }
});

// after enter password
router.post('/access', function(req, res, next) {
    if (req.query.token != null) {
        axios.post(authenticate + '/access?access=' + req.query.token, req.body).then(dados => {
            res.cookie('token', dados.data.token)
            res.redirect("/admin/manage");
        }).catch(e => {
            res.render('error', {
                text: "Look like you are tipsy ... Check your credentials !",
                error: e
            });
        });
    } else {
        res.render('error', {
            text: "Look like you are tipsy ... Try again !",
            error: "Unable to display your authentication page!"
        });
    }
});


// logout
router.get('/logout', function(req, res) {
    res.clearCookie('token');
    res.redirect('http://localhost:7301/homepage');
})

module.exports = router;