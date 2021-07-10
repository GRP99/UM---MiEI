/* SCRIPTS FILE */
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
}

const token = getCookie('token');

function verificaAdmin () {
    return true
}


function showProfile(email, level, registrationDate, lastAccessDate, role, course, department) {
    var profile = $("<pre><b>Email: </b>" + email 
        + "</pre><pre><b>Level: </b>" + level 
        + "</pre><pre><b>Registration Date: </b>" + registrationDate 
        + "</pre><pre><b>Last Access Date: </b>" + lastAccessDate 
        + "</pre><pre><b>Role: </b>" + role 
        + "</pre><pre><b>Course: </b>" + course 
        + "</pre><pre><b>Department: </b>" + department 
        + "</pre>");

    $("#displayProfile").empty();
    $("#displayProfile").append(profile);
    $("#displayProfile").modal();
}


function showComments(idF, idU) {
    var d = new Date().toISOString().substr(0, 16);
    $("#displayComments").empty();
    $("#displayComments").append("<form class=\"w3-container\" method=\"POST\" onSubmit=\"return confirm(&quot;Do you want to add this Comment?&quot;)\" action=\"http://localhost:3001/files/" + idF + "/reviews?token=" + token + "\">" 
        + "<fieldset class=\"w3-container w3-margin\">" 
        + "<legend>New Comment</legend>" 
        + "<input type=\"hidden\" name=\"autor\" value=\"" + idU 
        + "\"/>" 
        + "<input type=\"hidden\" name=\"data\" value=\"" + d 
        + "\"/>" 
        + "<table>" 
        + "<tr>" 
        + "<td> <textarea style=\"resize: none;\" rows=\"3\" cols=\"35\" name=\"descricao\"> </textarea> </td> </tr> </table>" 
        + "<input class=\"w3-btn w3-blue-grey w3-margin\" type=\"submit\" value=\"Add Comment\"/>" 
        + "</fieldset>" 
        + "</form>");
    $("#displayComments").modal();
}


function add() {
    var file = $('<input class="w3-input w3-border w3-light-grey" type="file" name="myFile">');
    $("#addeds").append(file);
}

function classificar(nmr, temp, idF) {
    var atual = temp.split("?;")[1]
    if(parseInt(atual) == nmr) {
        var choice = window.confirm("Do you want remove your classification on this file?");
        if(choice){
            $.ajax({
                url: "http://localhost:3001/files/classificar/" + idF + "?token=" + token + "&class=remove&atual=" + atual,
                type: "PUT",
                success: function () {
                    location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                    $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
                }
            });
        }
        else {
            false;
        }
    }
    else {
        var choice = window.confirm("Do you want to classify this file with rating " + nmr + "✰ ?");
        if(choice){
            $.ajax({
                
                
                url: "http://localhost:3001/files/classificar/" + idF + "?token=" + token + "&class=" + nmr + "&atual=" + atual,
                type: "PUT",
                success: function () {
                    location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                    $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
                }
            });
        }
        else {
            false;
        }
    }
}

function addremoveFavourite(idF) {
    $.ajax({
        url: "http://localhost:3001/files/addremoveFavourite/" + idF + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById("fav " + idF).className == "fa fa-heart")
                document.getElementById("fav "+idF).className = "fa fa-heart fill";
            else
                document.getElementById("fav "+idF).className = "fa fa-heart";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}

function removeFavourite(idF) {
    $.ajax({
        url: "http://localhost:3001/files/removeFavourite/" + idF + "?token=" + token,
        type: "PUT",
        success: function () {
            document.getElementById(idF).remove()
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}

function showFile(subtitle,name,mimetype,size,desc) {
    var file = $("<pre><b>Subtitle: </b>" + subtitle + "</pre>" 
        + "<pre><b>Name of file: </b>" + name + "</pre>" 
        + "<pre><b>Mimetype: </b>" + mimetype + "</pre>"
        + "<pre><b>Size : </b>" + size + " (bytes) </pre>"  
        + '<table><tr><pre><b>Description: </b></td><pre><textarea rows="4" cols="70" style="font-size: 11px; resize: none;" readonly>' + desc 
        + "</textarea>" 
        + "</td></tr></table>");

    $("#display").empty();
    $("#display").append(file);
    $("#display").modal();
}


function validate() {
    mail = document.getElementsByName("_id")[0].value;
    pwd = document.getElementsByName("password")[0].value;
    cpwd = document.getElementsByName("confirm_password")[0].value;

    if (pwd != cpwd) {
        alert("Passwords not matching!");
        return false;
    }

    $.get("http://localhost:3001/users/" + mail + "?token=" + token, function (data) {
        if (data) {
            alert("Email already detailed in use!");
            return false;
        } else {
            return true;
        }
    });
    return true;
}

function openUploadModal(user, token, resourceTypes) {
    var file = $('<form class="w3-container" onSubmit="return confirm(&quot;Do you want to submit?&quot;)" action="http://localhost:3001/files?token=' + token 
        + ' "method="POST" enctype="multipart/form-data" id="myForm"><label class="w3-text-blue-grey"><b>Select file</b></label><!-- #addeds--><input class="w3-input w3-border w3-light-grey" type="file" name="myFile" required="required" accept=".zip"/>' 
        + '<p><b class="w3-text-blue-grey">Acess: <select id="level" name="privacy" type="num" ><option value="0">Public</option><option value="1">Private</option></select></b></p>'
        + "<table>" 
        + "<tr>" 
        + "<td>Description:</td>" 
        + '<td><textarea style=\"resize: none;\" rows="3" cols="30" name="descricao"></textarea></td>' 
        + "</tr>" 
        + '<label class="w3-text-gray"><b>Title</b></label>' 
        + '<input class="w3-input w3-border w3-light-grey" type="text" name="title" required="required" >' 
        + '<label class="w3-text-gray"><b>SubTitle</b></label>' 
        + '<input class="w3-input w3-border w3-light-grey" type="text" name="subtitle">' 
        + '<label class="w3-text-gray"><b>Creation Date</b></label>' 
        + '<input class="w3-input w3-border w3-light-grey" type="date" name="date" required="required">' 
        + '<label class="w3-text-gray"> <b>New Resource type</b> </label> <input class="w3-input w3-border w3-light-grey" type="text" id="newType">'
        + '<p> <button type="button" onclick="addNewType()"> Insert a new resource type </button> </p>'
        + '<p> <b class="w3-text-blue-grey">Resource Type: <select id="resourceType" name="resourceType" required="required"> <option>Book</option><option>Article</option><option>Application</option><option>Student Work</option><option>Monograph</option><option>Report</option><option>Thesis</option><option>Slides</option><option>Test/Exam</option><option>Problem Solved</option> </select></b></p>'
        + '</table><button.w3-btn.w3-teal(type=\'button\' onclick=\'add()\') +--><input type="hidden" name="autor" value="' + user 
        + '" />'
        + '<span><input class="w3-btn w3-blue-grey" type="submit" value="Submit" id="addFile" />'
        + '<a href="https://github.com/GRP99/PRI2020-Projeto/blob/main/_exemplos_/README.md" target="_blank" ><i class="fa fa-question-circle" style="font-size:30px; position: absolute; right: 20px;"></img></a>'
        + "</span></form>");

    $("#display").empty();
    $("#display").append(file);
    $("#display").modal();
}

function editProfile(name, git, course, department){
    var file = $('<form class="w3-container" onSubmit="return confirm(&quot;Do you want to change your profile properties?&quot;)" action="http://localhost:3001/users/edit?token=' + token 
        + ' "method="POST" id="myFormProfile">'
        + '<label class="w3-text-gray"><b>Your Name</b></label>' 
        + '<input class="w3-input w3-border w3-light-grey" type="text" name="name" value="'+name+'" required="required" >' 
        + '<label class="w3-text-gray"><b>Github</b></label>' 
        + '<input class="w3-input w3-border w3-light-grey" type="text" name="git" value="'+git+'" required="required">' 
        + '<label class="w3-text-gray"><b>Course</b></label>' 
        + '<input class="w3-input w3-border w3-light-grey" type="text" name="course" value="'+course+'" required="required">' 
        + '<label class="w3-text-gray"><b>Department</b></label>' 
        + '<input class="w3-input w3-border w3-light-grey" type="text" name="department" value="'+department+'" required="required">' 
        + '<input style="margin: 10px auto" class="w3-btn w3-blue-grey" type="submit" value="Edit Profile" id="editProfile" />' + "</form>");

    $("#display").empty();
    $("#display").append(file);
    $("#display").modal();
}

function editFile(id, title, subtitle, descricao){
    var file = $('<form class="w3-container" onSubmit="return confirm(&quot;Do you want to change your file fields?&quot;)" action="http://localhost:3001/files/edit/' +id+'?token=' + token 
        + ' "method="POST" id="myFormFile">'
        + '<label class="w3-text-gray"><b>Title</b></label>' 
        + '<input class="w3-input w3-border w3-light-grey" type="text" name="title" value="'+title+'" required="required" >' 
        + '<label class="w3-text-gray"><b>Subtitle</b></label>' 
        + '<input class="w3-input w3-border w3-light-grey" type="text" name="subtitle" value="'+subtitle+'" required="required">'
        + "<table>"  
        + "<tr>" 
        + "<td>Description:</td>" 
        + '<td><textarea style=\"resize: none;\" rows="3" cols="30" name="descricao">' + descricao 
        + '</textarea></td>' 
        + "</tr>"
        + "</table>"
        + '<input style="margin: 10px auto" class="w3-btn w3-blue-grey" type="submit" value="Edit File" id="editFile" />' + "</form>");

    $("#display").empty();
    $("#display").append(file);
    $("#display").modal();
}

function addNewType() {
    if (document.getElementById("newType").value != "") {
        var x = document.getElementById("resourceType");
        var option = document.createElement("option");
        option.text = document.getElementById("newType").value;
        x.add(option);
    }
}


function openWarningModal(user) {
    var file = $('<form class="w3-container" onSubmit="return confirm(&quot;Do you want to submit?&quot;)" action="http://localhost:3001/news?token=' + token + '' 
        + ' "method="POST" id="myWarningForm">' 
        + "<table>" 
        + "<tr>" 
        + "<td>Warning:</td>" 
        + '<td><textarea style=\"resize: none;\" rows="3" cols="30" name="descricao"></textarea></td>' 
        + "</tr>"
        + '</table><button.w3-btn.w3-teal(type=\'button\' onclick=\'add()\') +--><input type="hidden" name="autor" value="' + user 
        + '" /><input class="w3-btn w3-blue-grey" type="submit" value="Submit" id="addWarning" />' 
        + "</form>");

    $("#display").empty();
    $("#display").append(file);
    $("#display").modal();
}

window.addEventListener( "pageshow", function ( event ) {
    var historyTraversal = event.persisted ||( typeof window.performance != "undefined" && window.performance.navigation.type === 2 );
    if ( historyTraversal ) {
      // Handle page restore.
      window.location.reload();
    }
});

function changePrivacy(id) {
    $.ajax({
        url: "http://localhost:3001/files/changeprivacy/" + id + "?token=" + token,
        type: "PUT",
        success: function (response) {
            if (document.getElementById("privacy " + id).innerHTML == "Private ") {
                document.getElementById("privacy " + id).innerHTML = "Public ";
                document.getElementById("button " + id).className = "fa fa-unlock";
            } else {
                document.getElementById("privacy " + id).innerHTML = "Private ";
                document.getElementById("button " + id).className = "fa fa-lock";
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}

function deleteFile(id) {
    var choice = window.confirm("Do you want to delete this file?");
    if (choice) {
        $.ajax({
            url: "http://localhost:3001/files/" + id + "?token=" + token,
            type: "DELETE",
            sucess: function () {
                document.getElementById(id).remove();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            document.getElementById(id).remove();
        });
    } else {
        false;
    }
}

function deleteUser(id) {
    var choice = window.confirm("Do you want to delete this user?");
    if (choice) {
        $.ajax({
            url: "http://localhost:3001/users/" + id + "?token=" + token,
            type: "DELETE",
            sucess: function () {
                document.getElementById(id).remove()
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            document.getElementById(id).remove();
        });
    } else {
        false;
    }
}

function deleteComment(idC, idF) {
    var choice = window.confirm("Do you want to delete this comment?");
    if (choice) {
        $.ajax({
            url: "http://localhost:3001/files/" + idF + "/reviews?token=" + token + "&comentario=" + idC,
            type: "DELETE",
            sucess: function () {
                document.getElementById(idC).remove()
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            document.getElementById(idC).remove();
        });
    } else {
        false;
    }
}

/* VALIDADE SEARCH FORM */
function validateSearchForm() {
    var a = document.forms["SearchForm"]["search"].value;
    if (a == null || a == "") {
      alert("Please fill Search Field!");
      return false;
    }
  }

/* TABELAS */

$(document).ready( function () {
    var table1 = $('#example').DataTable( {
      pageLength : 6,
      "lengthChange": false,
      info: false
    } )

    var table2 = $('#example2').DataTable( {
        pageLength : 6,
        "lengthChange": false,
        info: false
    } )

    var table3 = $('#example_user').DataTable( {
        pageLength : 2,
        "lengthChange": false,
        info: false
    } )

    var table4= $('#example2_user').DataTable( {
        pageLength : 2,
        "lengthChange": false,
        info: false
    } )
} );

