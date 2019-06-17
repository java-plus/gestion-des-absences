$(document).ready(function () {


    document.getElementById('#btn-login-submit').addEventListener('click', function (event) {
        event.preventDefault();
        $('#modal-Login').modal('show');


        // $.ajax({
        //     method: "POST",
        //     url: "http://localhost:8080/gestion-absences/connexion",
        //     dataType: "text"
        // })

        //     .done(function () {
        //         alert("success");
        //     })
        //     .fail(function (error) {
        //         ouvrirModal(error);

        //     });
    })







});


