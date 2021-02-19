var AdmZip = require('adm-zip');
const fs = require('fs');

module.exports.unzip = (ficheiro) => {
    var destino = ficheiro + '_sip'

    fs.mkdirSync(destino);

    var descomprimir = new AdmZip(ficheiro);
    descomprimir.extractAllTo(destino, false);

    fs.unlinkSync(ficheiro);
}

module.exports.zip = (path) => {
    var zip = new AdmZip();

    fs.readdirSync(path).forEach((file) => {
        if (fs.lstatSync(path + "/" + file + "/").isDirectory()) {
            zip.addLocalFolder(path + "/" + file + "/");
        } else {
            zip.addLocalFile(path + "/" + file);
        }
    });

    zip.writeZip(path + "_dip");
}
