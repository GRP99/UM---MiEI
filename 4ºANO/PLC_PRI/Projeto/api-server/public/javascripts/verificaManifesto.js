var fs = require('fs')

module.exports.verifica = (path) => {
    var manifesto = path + '/manifesto.json'

    try {
        if (fs.existsSync(manifesto)) {
            try{ 
                var file = require(manifesto)
            }
            catch{
                return false;
            }
            if (verificaDir(path + '/data/', file)){
                return true;
            }
    }
    } catch(err) {
        return false;
    }
    return false ;
}

verificaDir = (atual_dir, pasta) => {
    var flag = true;
    
    pasta.ficheiros.forEach((ficheiro) => {
        if (!(verificaFic(atual_dir, ficheiro))) {
            flag = false;
        }
    })

    if (flag) {
        pasta.pastas.forEach((p) => {
            if (!(fs.existsSync(atual_dir + p.titulo))) {
                flag = false
            }
            else {
                if (!(verificaDir(atual_dir + p.titulo + '/', p.conteudo))){
                    flag = false
                } 
            }
        })
    }
    return flag;
}

verificaFic = (atual_path, ficheiro) => {
    try {
        if (fs.existsSync(atual_path + ficheiro.titulo + '.' + ficheiro.tipo)) {
            return true;
        }
        else{
            return false;
        } 
    } catch (err) {
        console.error(err);
    }
    return false;
}