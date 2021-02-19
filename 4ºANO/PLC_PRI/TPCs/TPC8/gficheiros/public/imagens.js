// Image display on client browser
function showImage(name, type) {
    if (type == 'image/png' || type == 'image/jpeg') {
        var file = $('<img src="/fileStore/' + name + '" width="80%"/>')
    } else {
        var file = $('<p>' + name + ', ' + type + '</p>')
    }

    var download = $('<div><a href="/download/' + name + '">Download</a></div>')

    $("#display").empty()
    $("#display").append(file, download)
    $("#display").modal()
}

// Ensures that function is called as soon as all DOM elements (HTML element) of the page are ready to be used ...
// ... without this the append is not performed because it was not loaded
$(function () {
    // Reference to the button with the id "mais1upload" present in the fileForm in html-templates ...
    // ... click event occurs when button +1 is clicked, attaches a append to select another file when a click event occurs
    $("#mais1upload").click(e => {
        // Cancels the event if it is cancelable, without stopping its propagation ... 
        // ...without preventing does not allow to add new file because it disappears when click is triggered
        e.preventDefault()

        // Create a container to append the list of uploads
        var upload = $('<div class="w3-container" id="file"></div>')

        // Create container to add another file (label and input file)
        var file = $('<div class="w3-cell-row" id="myFile"></div>')
        var fileLabel = $('<label class="w3-text-blue"><b>Select file</b></label>')
        var fileupload = $('<input class="w3-input w3-border w3-light-blue" type="file" name="myFile">')

        $("#list").append(upload)
        $("#file").append(file)
        $("#myFile").append(fileLabel, fileupload)
    })
})