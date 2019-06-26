<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <link href="https://fonts.googleapis.com/css?family=Poppins:400,700|Roboto:400,700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="../../css/font.css">
    <link rel="stylesheet" href="../../css/global.css">

    <title>GDA - Validation des demandes</title>

</head>



<body>

    <div class="container-fluid m-0 p-0">

        <header>
            <nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">

                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03"
                    aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <a class="navbar-brand" href="#">
                    <img src="https://pbs.twimg.com/profile_images/810865682564911104/A3CM9RWz_400x400.jpg" width="30"
                        height="30" alt="">
                </a>

                <div class="collapse navbar-collapse" id="navbarNav">

                    <ul class="navbar-nav text-uppercase">
                        <li class="nav-item active">
                            <a class="nav-link" href="#">Accueil<span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Gestion des absences</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Planning des absences</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Validation demandes</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Vues synthÃ©tiques</a>
                        </li>

                    </ul>

                </div>

                <div class="mr-sm-3 text-info"><i data-feather="user"></i>
                    bonjour <span class="user-name">Untel</span>
                </div>
                <button class="btn btn-sm btn-outline-secondary" type="button">log out</button>

            </nav>

        </header>

    </div>



    <div class="container my-5">

        <h1>Validation des demandes</h1>



        <div class="row p-2 bg-primary">
            <div class="col-sm-2 ">Lundi</div>
            <div class="col-sm-2">Mardi</div>
            <div class="col-sm-2">Mercredi</div>
            <div class="col-sm-2">Jeudi</div>
            <div class="col-sm-2">vendredi</div>
            <div class="col-sm-2">Samedi</div>
            <div class="col-sm-2">Dimanche</div>
        </div>

        <div class="row p-2">
            <div class="col-sm-3">valeur debut</div>
            <div class="col-sm-3">valeur de fin</div>
            <div class="col-sm-3">valeur de type</div>
            <div class="col-sm-2">valeur de statut</div>
            <div class="col-sm-1"><i data-feather="trash">supprimer</i></div>
        </div>
        <div class="row p-2">
            <div class="col-sm-3">valeur debut</div>
            <div class="col-sm-3">valeur de fin</div>
            <div class="col-sm-3">valeur de type</div>
            <div class="col-sm-2">valeur de statut</div>
            <div class="col-sm-1"><i data-feather="eye">voir</i></div>
        </div>

        <div class="row p-2">
            <div class="col-sm-3">valeur debut</div>
            <div class="col-sm-3">valeur de fin</div>
            <div class="col-sm-3">valeur de type</div>
            <div class="col-sm-2">valeur de statut</div>
            <div class="col-sm-1"><i data-feather="edit-2">modifier</i></div>
        </div>

        <div class="row p-2">
            <div class="col-sm-3">valeur debut</div>
            <div class="col-sm-3">valeur de fin</div>
            <div class="col-sm-3">valeur de type</div>
            <div class="col-sm-2">valeur de statut</div>
            <div class="col-sm-1"><i data-feather="trash">supprimer</i></div>
        </div>


    </div>



    <div class="container my-5">
        <div>Soldes des compteurs : </div>
        <div>CongÃ©s payÃ©s</div>
        <div>RTT</div>
    </div>


    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>

    <!-- chargement des icons -->
    <script src="https://cdn.jsdelivr.net/npm/feather-icons/dist/feather.min.js"></script>

    <script>
        feather.replace()
    </script>
</body>

</html>


