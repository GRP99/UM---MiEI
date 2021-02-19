var express = require('express');
var router = express.Router();

var User = require('../controllers/users');
var File = require('../controllers/files');

/* Search by User */
router.get('/users/:user', function (req, res) {
    User.search(req.params.user).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp(err)
    });
});

/* Search by file */
router.get('/files/:file', function (req, res) {
    File.search(req.params.file).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp(err)
    });
});

/* Search by type */
router.get('/types/:type', function (req, res) {
    File.searchByType(req.params.type).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp(err)
    });
});

/* Search by date */
router.get('/date/:date', function (req, res) {
    File.searchByDate(req.params.date).then((data) => {
        res.status(200).jsonp(data);
    }).catch((err) => {
        res.status(500).jsonp(err)
    });
});

module.exports = router;
