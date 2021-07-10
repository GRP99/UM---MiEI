window.addEventListener( "pageshow", function ( event ) {
    var historyTraversal = event.persisted || 
                           ( typeof window.performance != "undefined" && 
                                window.performance.navigation.type === 2 );
    if ( historyTraversal ) {
      // Handle page restore.
      window.location.reload();
    }
  });

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

/* VALIDADE SEARCH FORM */
function validateSearchForm() {
    var a = document.forms["SearchForm"]["search"].value;
    if (a == null || a == "") {
      alert("Please fill Search Field!");
      return false;
    }
  }

/* VALIDADE INGREDIENT FORM */
function validateIngredientForm() {
    var i1 = document.forms["ChooseIngredients"]["i1"].value;
    var i2 = document.forms["ChooseIngredients"]["i2"].value;
    var i3 = document.forms["ChooseIngredients"]["i3"].value;


    if (i1 == "false" && i2 == "false" && i3 == "false") {
      alert("Please choose at least one Ingredient!");
      return false;
    }
}

/* 
    let timesAttempted = 0
    function validatePassword() {
        pwd = document.getElementsByName("password")[0].value;

        if (pwd != 'admin123') {
            if (timesAttempted < 2) {
                timesAttempted += 1
                alert("Password Incorrect! You still got " + (3-timesAttempted) + " out of 3 Attempts. ");
                return false;
            }
            else {
                timesAttempted = 0
                alert("Password Incorrect!");
                window.location = 'http://localhost:7301/homepage';
            }
        }

        return true;
    }
*/

function validate() {
    mail = document.getElementsByName("_id")[0].value;
    cmail = document.getElementsByName("confirm_id")[0].value;
    pwd = document.getElementsByName("password")[0].value;
    cpwd = document.getElementsByName("confirm_password")[0].value;
    username = document.getElementsByName("username")[0].value;

    if (pwd != cpwd) {
        alert("Passwords not matching!");
        return false;
    }

    if (mail != cmail) {
        alert("Emails not matching!");
        return false;
    }

    /* mail must be unique */
    $.get("http://localhost:7300/users/" + mail, function (data) {
        if (data) {
            alert("Email detailed already in use!");
            return false;
        } else {
            return true;
        }
    });

    /* username must be unique */
    $.get("http://localhost:7300/users/byUsername/" + username, function (data) {
        if (data || username == 'admin') {
            alert("Username detailed already in use!");
            return false;
        } else {
            return true;
        }
    });

    return true;
}

/* validate name of inserted cocktail */
/* function validateNameCocktail(cocktails) {
    var n = document.forms["Cocktails"]["name"].value;
    var name = String(n).trim().toLowerCase();

    cocktails.forEach(element => {
        if (element.strDrink.trim().toLowerCase() == name) {
            alert('The name you inserted is already associated with other cocktail.');
            return false;
        } 
    });
    alert('Não existe nome')
    return true;
} */

var s = 0
function add() {
    var up = document.getElementById('cocktail_bartender').innerHTML;
    var newcontent = document.createElement('div');
    newcontent.className = 'row'
    newcontent.id = 's' + s
    newcontent.innerHTML = up;
    
    let div = document.createElement("div");
    div.className = 'col';
    let btn = document.createElement("button");
    btn.type = "button";
    btn.className = "button-transparent fa fa-trash";
    btn.style.color = 'red';
    btn.style.fontSize = '20px';
    btn.style.padding = '7px';

    btn.addEventListener("click", function(){
        document.getElementById(newcontent.id).remove()
    });
    s = s + 1;
    div.appendChild(btn)
    newcontent.appendChild(div)

    var myspan = document.getElementById("addeds");
    myspan.appendChild(newcontent)
}

function remove(n) {
    id = 's' + n
    document.getElementById(id).remove();
}


/* TABELAS */
$(document).ready( function () {
    var table1 = $('#example').DataTable( {
      pageLength : 6,
      "lengthChange": false,
      info: false
    } )

    var table2 = $('#example2').DataTable( {
        pageLength : 3,
        "lengthChange": false,
        info: false
      } )
    
    var table3 = $('#example3').DataTable( {
        pageLength : 2,
        "lengthChange": false,
        info: false
    } )

    var table4 = $('#individual').DataTable( {
        pageLength : 4,
        "lengthChange": false,
        info: false
    } )

    var a1 = $('#admin1').DataTable( {
        pageLength : 6,
        "lengthChange": false,
        info: false
      } )

    var a2 = $('#admin2').DataTable( {
      pageLength : 6,
      "lengthChange": false,
      info: false
    } )

    
    var table4 = $('#individual1').DataTable( {
        pageLength : 4,
        "lengthChange": false,
        info: false
    } )

    var table4 = $('#individual2').DataTable( {
        pageLength : 4,
        "lengthChange": false,
        info: false
    } )

    var table4 = $('#individual3').DataTable( {
        pageLength : 4,
        "lengthChange": false,
        info: false
    } )
} );

/* Scripts template */
var navToggled = false;

/* Menu Bar */
        $(document).ready(function() {
            $("#sidebar").mCustomScrollbar({
                theme: "minimal"
            });

            $('#dismiss, .overlay').on('click', function() {
                $('#sidebar').removeClass('active');
                $('.overlay').removeClass('active');
            });

            $('#sidebarCollapse').on('click', function() {
                if (navToggled == true){
                    navToggled = false;
                    $('#sidebar').removeClass('active');
                    $('.overlay').removeClass('active');
                }   
                else{
                    navToggled = true;
                    $('#sidebar').addClass('active');
                    $('.overlay').addClass('active');
                    $('.collapse.in').toggleClass('in');
                    $('a[aria-expanded=true]').attr('aria-expanded', 'false');
                }
            });
        });



/* Carousel */
$(document).ready(function() {

    /* ***** HOMEPAGE ***** */
    var owl1 = $('.owl-carousel.homepage');
    owl1.owlCarousel({
        margin: 20,
        loop: true,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 2
            },
            1000: {
                items: 5
            }
        }
    })

    /* ***** COCKTAIL ***** */
    var owl = $('.owl-carousel.cocktail');
    owl.on('changed.owl.carousel', function (e) {
            $('.current').text(e.relatedTarget.relative(e.relatedTarget.current()) + 1);
            $('.allitems').text(e.item.count);
    })

    owl.owlCarousel({
        margin: 20,
        center: true,
        loop: true,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 2
            },
            1000: {
                items: 5
            }
        }
    })

    $(".next-button-cocktail").click(function() {
        owl.trigger('next.owl.carousel');
    });

    $(".prev-button-cocktail").click(function() {
        owl.trigger('prev.owl.carousel');
    });

    /* ***** BARTENDER ***** */
    var owl2 = $('.owl-carousel.bartender');

    owl2.on('changed.owl.carousel', function (e) {
            $('.current-bartender').text(e.relatedTarget.relative(e.relatedTarget.current()) + 1);
            $('.allitems-bartender').text(e.item.count);
    })

    owl2.owlCarousel({
        margin: 20,
        center: true,
        loop: true,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 2
            },
            1000: {
                items: 5
            }
        }
    })

    $(".next-button-bartender").click(function() {
        owl2.trigger('next.owl.carousel');
    });

    $(".prev-button-bartender").click(function() {
        owl2.trigger('prev.owl.carousel');
    });
})


/* ---- COCKTAILS ---- */

/* like e dislike */
function like(id_cocktail) {
    $.ajax({
        url: "http://localhost:7300/dbcocktails/like/" + id_cocktail + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById("like").className == "fa fa-thumbs-up") {
                document.getElementById("like").className = "fa fa-thumbs-up fill";

                if (document.getElementById("dislike").className == "fa fa-thumbs-down fill") {
                    document.getElementById("dislike").className = "fa fa-thumbs-down";
                    document.getElementById('count_dislike').innerHTML = String(parseInt(document.getElementById('count_dislike').innerHTML) - 1)
                }
                
                document.getElementById('count_like').innerHTML = String(parseInt(document.getElementById('count_like').innerHTML) + 1)
            }
            
            else {
                document.getElementById("like").className = "fa fa-thumbs-up";
                document.getElementById('count_like').innerHTML = String(parseInt(document.getElementById('count_like').innerHTML) - 1)
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}

function dislike(id_cocktail) {
    $.ajax({
        url: "http://localhost:7300/dbcocktails/dislike/" + id_cocktail + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById("dislike").className == "fa fa-thumbs-down") {
                document.getElementById("dislike").className = "fa fa-thumbs-down fill";
                
                if (document.getElementById("like").className == "fa fa-thumbs-up fill") {
                    document.getElementById("like").className = "fa fa-thumbs-up";
                    document.getElementById('count_like').innerHTML = String(parseInt(document.getElementById('count_like').innerHTML) - 1)
                }
                
                document.getElementById('count_dislike').innerHTML = String(parseInt(document.getElementById('count_dislike').innerHTML) + 1)

            }
            else {
                document.getElementById("dislike").className = "fa fa-thumbs-down";
                document.getElementById('count_dislike').innerHTML = String(parseInt(document.getElementById('count_dislike').innerHTML) - 1)
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}
/* -------------------- */

/* ---- COMMENTS ---- */
function openComment() {
    $("#commentSection").hide();
    $("#formComment").show();
}

function removeComment() {
    $("#formComment").hide();
    $("#commentSection").show();
}

function deleteReview(id_review, id_cocktail) {
    var choice = window.confirm("Do you want to delete this review?");
    if (choice) {
            $.ajax({
                headers: {  'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Headers':'Origin, X-Requested-With, Content-Type, Accept' },
                url: "http://localhost:7300/dbcocktails/reviews/" + id_cocktail + "/" + id_review + "?token=" + token,
                crossDomain: true,
                type: "DELETE",
                sucess: function () {
                    window.location.href  = "http://localhost:7301/cocktails/" + id_cocktail + '?comment=removed'
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                    $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
                }
            }).done(function() {
                window.location.href = "http://localhost:7301/cocktails/" + id_cocktail + '?comment=removed'
            });
    } else {
        false;
    }  
}
/* -------------------- */

/* ---- BARTENDERS ---- */

/* like e dislike */
function likeBartender(id_bartender) {
    $.ajax({
        url: "http://localhost:7300/dbbartenders/like/" + id_bartender + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById("like").className == "fa fa-thumbs-up") {
                document.getElementById("like").className = "fa fa-thumbs-up fill";

                if (document.getElementById("dislike").className == "fa fa-thumbs-down fill") {
                    document.getElementById("dislike").className = "fa fa-thumbs-down";
                    document.getElementById('count_dislike').innerHTML = String(parseInt(document.getElementById('count_dislike').innerHTML) - 1)
                }
                
                document.getElementById('count_like').innerHTML = String(parseInt(document.getElementById('count_like').innerHTML) + 1)
            }
            
            else {
                document.getElementById("like").className = "fa fa-thumbs-up";
                document.getElementById('count_like').innerHTML = String(parseInt(document.getElementById('count_like').innerHTML) - 1)
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}

function dislikeBartender(id_bartender) {
    $.ajax({
        url: "http://localhost:7300/dbbartenders/dislike/" + id_bartender + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById("dislike").className == "fa fa-thumbs-down") {
                document.getElementById("dislike").className = "fa fa-thumbs-down fill";
                
                if (document.getElementById("like").className == "fa fa-thumbs-up fill") {
                    document.getElementById("like").className = "fa fa-thumbs-up";
                    document.getElementById('count_like').innerHTML = String(parseInt(document.getElementById('count_like').innerHTML) - 1)
                }
                
                document.getElementById('count_dislike').innerHTML = String(parseInt(document.getElementById('count_dislike').innerHTML) + 1)

            }
            else {
                document.getElementById("dislike").className = "fa fa-thumbs-down";
                document.getElementById('count_dislike').innerHTML = String(parseInt(document.getElementById('count_dislike').innerHTML) - 1)
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}

function deleteReviewBartender(id_review, id_bartender) {
    var choice = window.confirm("Do you want to delete this review?");
    if (choice) {
            $.ajax({
                headers: {  'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Headers':'Origin, X-Requested-With, Content-Type, Accept' },
                url: "http://localhost:7300/dbbartenders/reviews/" + id_bartender + "/" + id_review + "?token=" + token,
                crossDomain: true,
                type: "DELETE",
                sucess: function () {
                    window.location.href  = "http://localhost:7301/bartenders/" + id_bartender + '?comment=removed'
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                    $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
                }
            }).done(function() {
                window.location.href = "http://localhost:7301/bartenders/" + id_bartender + '?comment=removed'
            });
    } else {
        false;
    }  
}
/* -------------------- */

/* ---- LOCATIONS & BARS ---- */
function visitLocation(id_location) {
    $.ajax({
        url: "http://localhost:7300/dblocations/visit/" + id_location + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById("visit").className == "fa fa-thumb-tack") {
                document.getElementById("visit").className = "fa fa-thumb-tack fill";
                
                document.getElementById('count_visit').innerHTML = String(parseInt(document.getElementById('count_visit').innerHTML) + 1)
            }
            
            else {
                document.getElementById("visit").className = "fa fa-thumb-tack";
                document.getElementById('count_visit').innerHTML = String(parseInt(document.getElementById('count_visit').innerHTML) - 1)
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}


function visitBar(id_bar) {
    $.ajax({
        url: "http://localhost:7300/dbbars/visit/" + id_bar + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById("visit").className == "fa fa-thumb-tack") {
                document.getElementById("visit").className = "fa fa-thumb-tack fill";
                
                document.getElementById('count_visit').innerHTML = String(parseInt(document.getElementById('count_visit').innerHTML) + 1)
            }
            
            else {
                document.getElementById("visit").className = "fa fa-thumb-tack";
                document.getElementById('count_visit').innerHTML = String(parseInt(document.getElementById('count_visit').innerHTML) - 1)
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}
/* -------------------- */


/* ---- MY PROFILE ---- */
function visit_location(id_location) {
    $.ajax({
        url: "http://localhost:7300/dblocations/visit/" + id_location + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById(id_location).className == "fa fa-thumb-tack") {
                document.getElementById(id_location).className = "fa fa-thumb-tack fill";
                
            }
            
            else {
                document.getElementById(id_location).className = "fa fa-thumb-tack";
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}

function visit_bar(id_bar) {
    $.ajax({
        url: "http://localhost:7300/dbbars/visit/" + id_bar + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById(id_bar).className == "fa fa-thumb-tack") {
                document.getElementById(id_bar).className = "fa fa-thumb-tack fill";
                
            }
            
            else {
                document.getElementById(id_bar).className = "fa fa-thumb-tack";
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}

function like_cocktail(id_cocktail) {
    $.ajax({
        url: "http://localhost:7300/dbcocktails/like/" + id_cocktail + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById(id_cocktail).className == "fa fa-thumbs-up") {
                document.getElementById(id_cocktail).className = "fa fa-thumbs-up fill";
            }
            else {
                document.getElementById(id_cocktail).className = "fa fa-thumbs-up";
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}

function like_bartender(id_bartender) {
    $.ajax({
        url: "http://localhost:7300/dbbartenders/like/" + id_bartender + "?token=" + token,
        type: "PUT",
        success: function () {
            if (document.getElementById(id_bartender).className == "fa fa-thumbs-up") {
                document.getElementById(id_bartender).className = "fa fa-thumbs-up fill";
            }
            
            else {
                document.getElementById(id_bartender).className = "fa fa-thumbs-up";
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
            $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
        }
    });
}