$(document).ready(function () {
			
    <% --DEBUT --- script pour gerer les requetes envoyées par le filtre-- %>

        $("#form-filtres").on("submit", function (event) {
            event.preventDefault();
            var dataForm = $(this).serialize();

            $.AJAX({
                method: "POST",
                url: "controller/afficherVueDepart?vue=collab",
                data: dataForm,
                datatype: "json"

            }).done(function (result, status) {
                console.log(result + " / " + status);

            }).fail(function (result, status) {

                console.log(result + " / " + status);

            }).always(function () {

                console.log("always");

            });

        });
        
    
<% --FIN --- script pour gerer les requetes envoyées par le filtre-- %>


})
