function preventBack() {
    window.history.forward();
}
setTimeout("preventBack()", 0);
window.onunload = function() {
    null;
};

/* Photo change related */
function mudafoto(idDrink, drinkThumb) {
    var file = $('<form id="formPhoto" onSubmit="return confirm(&quot;Do you want to change this cocktail\'s pic?&quot;)" style="margin:50px; text-align:center" action="http://localhost:7300/cocktails/' + idDrink + '/changedrinkThumb?token=' + token + '" method="POST">' +
        "<h4> Change cocktail Picture </h4>" +
        '<input class="form-control" type="url" id="newdrinkThumb" name="newdrinkThumb"/>' +
        '<input type="hidden" name="idDrink" value="' + idDrink + '"/>' +
        '<btn class="btn btn-secondary btn-sm" type="button" onclick="removeChildPhoto()"> Cancel </btn>' +
        '<input class="btn btn-primary btn-sm" style="margin-left:10px" type="submit" value="Submit"/>' +
        "</form>");

    $("#photo").prepend(file);
    $("#pd").hide();
}

function removeChildPhoto() {
    document.getElementById("formPhoto").remove();
    $("#pd").show();
}

/* Add Ingredient */
function addIngredient() {
    $("#tableIngredients").hide();
    $("#formIngredients").show();
}

function removeChildIngredient() {
    $("#formIngredients").hide();
    $("#tableIngredients").show();
}

/* Add Garnish */
function addGarnish() {
    $("#tableGarnish").hide();
    $("#formGarnish").show();
}

function removeChildGarnish() {
    $("#formGarnish").hide();
    $("#tableGarnish").show();
}

/* Add Cocktail - Bartender */
function addCocktail() {
    $("#tableCocktails").hide();
    $("#formCocktail").show();
}

function removeChildCocktail() {
    $("#formCocktail").hide();
    $("#tableCocktails").show();
}

function deleteCocktail(id) {
    var choice = window.confirm("Do you want to delete this cocktail?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/cocktails/" + id + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = "http://localhost:7301/admin/manage/cocktails?removed=true&id=" + id
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = "http://localhost:7301/admin/manage/cocktails?removed=true&id=" + id
        });
    } else {
        false;
    }
}

function deleteBartender(id) {
    var choice = window.confirm("Do you want to delete this bartender?");
    if (choice) {
        $.ajax({
            url: "http://localhost:7300/bartenders/" + id + "?token=" + token,
            type: "DELETE",
            sucess: function() {
                window.location.href = "http://localhost:7301/admin/bartenders?removed=true&id=" + id
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = "http://localhost:7301/admin/manage/bartenders?removed=true&id=" + id
        });
    } else {
        false;
    }
}

/* delete Cocktail in Bartender */
function deleteCocktailBartender(idBartender, idDrink) {
    var choice = window.confirm("Do you want to delete this cocktail?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/bartenders/" + idBartender + "/cocktail/" + idDrink + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = 'http://localhost:7301/admin/manage/bartenders/' + idBartender + '?cocktail=2'
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = 'http://localhost:7301/admin/manage/bartenders/' + idBartender + '?cocktail=2'
        });
    } else {
        false;
    }
}

/* delete garnish in Cocktail */
function deleteGarnish(idCocktail, idIngredient) {
    var choice = window.confirm("Do you want to delete this ingredient?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/cocktails/" + idCocktail + "/garnish/" + idIngredient + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = 'http://localhost:7301/admin/manage/cocktails/' + idCocktail + '?garnish=2'
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = 'http://localhost:7301/admin/manage/cocktails/' + idCocktail + '?garnish=2'
        });
    } else {
        false;
    }
}

/* delete ingredient in Cocktail */
function deleteIngredient(idCocktail, idIngredient) {
    var choice = window.confirm("Do you want to delete this ingredient?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/cocktails/" + idCocktail + "/ingredient/" + idIngredient + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = 'http://localhost:7301/admin/manage/cocktails/' + idCocktail + '?ingredient=2'
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = 'http://localhost:7301/admin/manage/cocktails/' + idCocktail + '?ingredient=2'
        });
    } else {
        false;
    }
}

/* delete user */
function deleteUser(idUser) {
    var choice = window.confirm("Do you want to delete this user?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/users/" + idUser + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = 'http://localhost:7301/admin/manage/users?removed=true&id=' + idUser
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = 'http://localhost:7301/admin/manage/users?removed=true&id=' + idUser
        });
    } else {
        false;
    }
}

/* delete review - admin */
function deleteAdminReview(id_review, id_cocktail) {
    var choice = window.confirm("Do you want to delete this review?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/dbcocktails/reviews/" + id_cocktail + "/" + id_review + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = "http://localhost:7301/admin/manage/cocktails/" + id_cocktail + '?comment=removed'
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = "http://localhost:7301/admin/manage/cocktails/" + id_cocktail + '?comment=removed'
        });
    } else {
        false;
    }
}

/* delete review - admin */
function deleteAdminBartenderReview(id_review, id_bartender) {
    var choice = window.confirm("Do you want to delete this review?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/dbbartenders/reviews/" + id_bartender + "/" + id_review + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = "http://localhost:7301/admin/manage/bartenders/" + id_bartender + '?comment=removed'
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = "http://localhost:7301/admin/manage/bartenders/" + id_bartender + '?comment=removed'
        });
    } else {
        false;
    }
}

function deleteBar(id) {
    var choice = window.confirm("Do you want to delete this bar?");
    if (choice) {
        $.ajax({
            url: "http://localhost:7300/bars/" + id + "?token=" + token,
            type: "DELETE",
            sucess: function() {
                window.location.href = "http://localhost:7301/admin/bars?removed=true&id=" + id
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = "http://localhost:7301/admin/manage/bars?removed=true&id=" + id
        });
    } else {
        false;
    }
}

function deleteCocktailBar(idBar, idDrink) {
    var choice = window.confirm("Do you want to delete this cocktail?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/bars/" + idBar + "/cocktail/" + idDrink + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = 'http://localhost:7301/admin/manage/bars/' + idBar + '?cocktail=2'
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = 'http://localhost:7301/admin/manage/bars/' + idBar + '?cocktail=2'
        });
    } else {
        false;
    }
}

function deleteCategory(id) {
    var choice = window.confirm("Do you want to delete this category?");
    if (choice) {
        $.ajax({
            url: "http://localhost:7300/categories/" + id + "?token=" + token,
            type: "DELETE",
            sucess: function() {
                window.location.href = "http://localhost:7301/admin/categories?removed=true&id=" + id
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = "http://localhost:7301/admin/manage/categories?removed=true&id=" + id
        });
    } else {
        false;
    }
}

function deleteCocktailCategory(idCategory, idDrink) {
    var choice = window.confirm("Do you want to delete this cocktail?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/categories/" + idCategory + "/cocktail/" + idDrink + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = 'http://localhost:7301/admin/manage/categories/' + idCategory + '?cocktail=2'
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = 'http://localhost:7301/admin/manage/categories/' + idCategory + '?cocktail=2'
        });
    } else {
        false;
    }
}

function deleteGlassware(id) {
    var choice = window.confirm("Do you want to delete this glassware?");
    if (choice) {
        $.ajax({
            url: "http://localhost:7300/glasswares/" + id + "?token=" + token,
            type: "DELETE",
            sucess: function() {
                window.location.href = "http://localhost:7301/admin/glasswares?removed=true&id=" + id
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = "http://localhost:7301/admin/manage/glasswares?removed=true&id=" + id
        });
    } else {
        false;
    }
}

function deleteCocktailGlassware(idGlassware, idDrink) {
    var choice = window.confirm("Do you want to delete this cocktail?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/glasswares/" + idGlassware + "/cocktail/" + idDrink + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = 'http://localhost:7301/admin/manage/glasswares/' + idGlassware + '?cocktail=2'
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = 'http://localhost:7301/admin/manage/glasswares/' + idGlassware + '?cocktail=2'
        });
    } else {
        false;
    }
}

function deleteLocation(id) {
    var choice = window.confirm("Do you want to delete this location?");
    if (choice) {
        $.ajax({
            url: "http://localhost:7300/locations/" + id + "?token=" + token,
            type: "DELETE",
            sucess: function() {
                window.location.href = "http://localhost:7301/admin/locations?removed=true&id=" + id
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = "http://localhost:7301/admin/manage/locations?removed=true&id=" + id
        });
    } else {
        false;
    }
}

function deleteCocktailLocation(idLocation, idDrink) {
    var choice = window.confirm("Do you want to delete this cocktail?");
    if (choice) {
        $.ajax({
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            },
            url: "http://localhost:7300/locations/" + idLocation + "/cocktail/" + idDrink + "?token=" + token,
            crossDomain: true,
            type: "DELETE",
            sucess: function() {
                window.location.href = 'http://localhost:7301/admin/manage/locations/' + idLocation + '?cocktail=2'
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I) for more information!');
                $('#result').html('<p>status code: ' + jqXHR.status + '</p><p>textStatus: ' + textStatus + '</p><p>errorThrown: ' + errorThrown + '</p><p>jqXHR.responseText:</p><div>' + jqXHR.responseText + '</div>');
            }
        }).done(function() {
            window.location.href = 'http://localhost:7301/admin/manage/locations/' + idLocation + '?cocktail=2'
        });
    } else {
        false;
    }
}