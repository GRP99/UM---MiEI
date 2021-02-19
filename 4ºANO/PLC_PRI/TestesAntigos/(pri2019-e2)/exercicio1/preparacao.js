var fs = require('fs')

fs.readFile('filmes.json', 'utf8', function (error, data) {
    if (error) 
        throw error
     else {
        file = JSON.parse(data)
        for (let i = 0; i < file.length; i++) {
            file[i]["_id"] = ("f" + (
                i + 1
            ))
        }
        console.log(JSON.stringify(file))
    }
})
