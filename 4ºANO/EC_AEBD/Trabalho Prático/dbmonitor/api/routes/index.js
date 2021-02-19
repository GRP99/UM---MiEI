var express = require('express');
var router = express.Router();
var connection = require('../controllers/checkConnection')

router.get('/users', (req, res, next) => {
    connection.getUsers().then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/users/timestamp', (req, res) => {
    connection.getUsersTimeStamps().then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/tablespaces', (req, res) => {
    connection.getTablespaces().then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.post('/timestamp', (req, res) => {
    connection.getTimeStamps(req.body.table).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/tablespacedata/:id/:id2', (req, res) => {
    connection.getTablespaceData(req.params.id, req.params.id2).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/lasttimestamps/:id', (req, res) => {
    connection.getLastTimestamps(req.params.id).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getDataFilesTablespace/:id/:id2', (req, res) => {
    connection.getDataFilesTablespace(req.params.id, req.params.id2).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getDataFiles', (req, res) => {
    connection.getDataFiles().then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getDataFileTimeStamp/:id/:id2', (req, res) => {
    connection.getDataFileTimeStamp(req.params.id, req.params.id2).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getUsersTimeStamps/:id', (req, res) => {
    connection.getUsersTimeStamps(req.params.id).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getUsersTableSpaceTimeStamp/:id/:id2', (req, res) => {
    connection.getUsersTableSpaceTimeStamp(req.params.id, req.params.id2).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getDataFilesBytes/:id/', (req, res) => {
    connection.getDataFilesBytes(req.params.id).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getDataFilesBlocks/:id/', (req, res) => {
    connection.getDataFilesBlocks(req.params.id).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getCPU/:id/', (req, res) => {
    connection.getCPU(req.params.id).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getCPUUsers/', (req, res) => {
    connection.getCPUUsers().then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getSystemGlobalArea/', (req, res) => {
    connection.getSystemGlobalArea().then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getProgramGlobalArea/', (req, res) => {
    connection.getProgramGlobalArea().then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getSessionsTimestampUser/:id', (req, res) => {
    connection.getSessionsTimestampUser(req.params.id).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getSession/:id/:id2', (req, res) => {
    connection.getSession(req.params.id, req.params.id2).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getSessions', (req, res) => {
    connection.getSessions().then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getSessionsUser/:id', (req, res) => {
    connection.getSessionsUser(req.params.id).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getRolesTS/:id/:id2', (req, res) => {
    connection.getRolesTS(req.params.id, req.params.id2).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})

router.get('/getUserNameTS/:id/:id2', (req, res) => {
    connection.getUserNameTS(req.params.id, req.params.id2).then(dados => res.jsonp(dados)).catch(erro => res.jsonp(erro))
})



module.exports = router;