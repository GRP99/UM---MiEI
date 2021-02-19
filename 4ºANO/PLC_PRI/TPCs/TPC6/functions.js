function accomplish(value) {
    url = "/realizartarefa?id=" + value;
    $.getJSON(url, function () {
    }
    )
    $('table#TarefasPendentes tr#' + value).remove();
    addToTableNPendent(value, 'realizada')
}


function cancel(value) {
    url = "/cancelartarefa?id=" + value;
    $.getJSON(url, function () {
    }
    )
    $('table#TarefasPendentes tr#' + value).remove();
    addToTableNPendent(value, 'cancelada')

}


function callback() {
    location.reload(true)
}


function addToTableNPendent(value, estado) {
    $.ajax({
        type: 'GET',
        url: "/tarefas/" + value,
        dataType: 'json',
        success: function (data) {
            var responsavel = data.responsavel
            var datalimite = data.datalimite
            var descricao = data.descricao
            var markup = "<tr><td>" + datalimite + "</td><td>" + responsavel + "</td> <td>" + descricao + "</td> <td>" + estado + "</td></tr>"
            $("table#TarefasNPendentes").append(markup);
        }
    });
}