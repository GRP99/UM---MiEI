var http = require('http')
var fs = require('fs')

http.createServer(function (req, res) {
    var splits = req.url.split("/")

    if (splits.length == 3) {
        var arq = splits[splits.length - 2]
        var npage = splits[splits.length - 1]

        if (arq === 'arqs') {
            if (npage === '*') {
                fs.readFile('arqWeb/index.html', function (err, data) {
                    if (err) {
                        console.log('ERROR: Reading the file! ' + err)
                        res.writeHead(200, { 'Content-Type': 'text/html' })
                        res.write("<p> Nonexistent File ! <p>")
                        res.end()
                    }
                    else {
                        res.writeHead(200, { 'Content-Type': 'text/html' })
                        res.write(data)
                        res.end()
                    }
                })
            }
            else {
                fs.readFile('arqWeb/arq' + npage + '.html', function (err, data) {
                    if (err) {
                        console.log('ERROR: Reading the file! ' + err)
                        res.writeHead(200, { 'Content-Type': 'text/html' })
                        res.write("<p> Nonexistent File ! <p>")
                        res.end()
                    }
                    else {
                        res.writeHead(200, { 'Content-Type': 'text/html' })
                        res.write(data)
                        res.end()
                    }
                })
            }
        }
        else {
            console.log('ERROR: Request of folder incorrect!!! ')
            res.writeHead(200, { 'Content-Type': 'text/html' })
            res.write("<p> Folder incorrect ! <p>")
            res.end()
        }
    }
    else {
        console.log('ERROR: Request of path incorrect!!! ')
        res.writeHead(200, { 'Content-Type': 'text/html' })
        res.write("<p> Path incorrect ! <p>")
        res.end()
    }
}).listen(7777)

console.log('Servidor à escuta na porta 7777...')