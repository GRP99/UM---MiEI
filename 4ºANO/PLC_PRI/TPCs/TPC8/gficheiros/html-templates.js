exports.header = header
exports.fileList = fileList
exports.fileForm = fileForm
exports.footer = footer

function header() {
    return `
    <html>
          <head>
              <title>Uploading and Downloading Files</title>
              <meta charset="utf-8"/>
              <link rel="icon" href="/favicon.png"/>
              <link rel="stylesheet" href="/w3.css"/>
              <script src="/jquery-3.5.1.min.js"></script>
              <script src="/imagens.js"></script>
              <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
              <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
          </head>
    `
}

// File Form HTML Page Template ------------------------------------------
function fileForm() {
    return `
        <body>
            <div class="w3-container w3-blue">
                <h2>File Upload</h2>
            </div>
            <form class="w3-container" action="/files" method="POST" enctype="multipart/form-data">
                <!--Create a section/container to add files with id=list-->
                <div class="w3-container" id="list">
                    <!--Create a section/container the first file-->
                    <div class="w3-container">
                        <!--Create a section/container to cell to upload the first file-->
                        <div class="w3-cell-row">
                            <label class="w3-text-blue"><b>Select file</b></label>
                            <input class="w3-input w3-border w3-light-blue" type="file" name="myFile">
                        </div>
                    </div>
                </div>
                <!--Button with id=mais1upload to refer the static function in imagens.js-->
                <button class="w3-btn w3-cyan" id="mais1upload"> + </button>
                <input class="w3-btn w3-cyan" type="submit" value="Submit"/>
            </form>
    `
}

// File List HTML Page Template  -----------------------------------------
function fileList(files) {
    let pagHTML = `
              <div class="w3-card-4 modal" id="display"></div>

              <div class="w3-container w3-blue">
                  <h2>File List</h2>
              </div>
              <table class="w3-table w3-bordered">
                  <tr>
                      <th>Date</th>
                      <th>File</th>
                      <th>Size</th>
                      <th>Type</th>
                  </tr>
    `
    files.forEach(f => {
        pagHTML += `
          <tr onclick='showImage(\"${f.name}", \"${f.mimetype}\");'>
              <td>${f.date}</td>
              <td>${f.name}</td>
              <td>${f.size}</td>
              <td>${f.mimetype}</td>
          </tr>
      `
    })

    pagHTML += `
          </table>
    `
    return pagHTML
}

function footer(){
    return `
            <footer class="w3-container w3-blue w3-center">
                <p> Autor: Gonçalo Rodrigues Pinto </p>
                <p> MiEI - 4ºAno - 1ºSemestre - PRI 20/21 </p> 
                <p><a href="mailto:a83732@alunos.uminho.pt">a83732@alunos.uminho.pt</a></p>
                <a href="https://github.com/GRP99/PRI2020" target="_blank" ><img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Logo.png" alt="Github" style="width:100px;height:50px;"></img></a>
            </footer>
        </body>
    </html>
    `
}

