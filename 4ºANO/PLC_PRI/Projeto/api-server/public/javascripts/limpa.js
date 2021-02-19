var fs = require('fs');

module.exports.eliminaPasta = function (path) {
    eliminaPastas(path)
}

eliminaPastas = function (path) {
    if (fs.existsSync(path)) {
        fs.readdirSync(path).forEach(function (file) {
            var currPath = path + "/" + file;
            if (fs.lstatSync(currPath).isDirectory()) {
                eliminaPastas(currPath);
            } else {
                fs.unlinkSync(currPath);
            }
        });
        fs.rmdirSync(path);
    }
};
