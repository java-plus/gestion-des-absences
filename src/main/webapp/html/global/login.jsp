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

    <link rel="stylesheet" href="../../css/main.css">

    <link rel="stylesheet" href="../../css/font.css">

    <title>GDA - Connexion</title>

</head>

<body>







    <form class="form-signin">
        <div class="text-center mb-4">
            <img class="mb-4 logo" src="https://pbs.twimg.com/profile_images/810865682564911104/A3CM9RWz_400x400.jpg"
                alt="logo gda">
            <h1 class="h3 mb-3 ">Connexion</h1>
            <p>Gestion des abscences, congÃ©s...</p>
        </div>

        <div class="form-label-group">
            <input type="email" id="email" class="form-control" placeholder="Adresse email" required="" autofocus=""
                name="email">
            <label for="email">Adresse email</label>
        </div>

        <div class="form-label-group">
            <input type="password" id="password" class="form-control" placeholder="Mot de passe" required=""
                name="password">
            <label for="password">Mot de passe</label>
        </div>


        <button id="#btn-login-submit" class="btn btn-lg btn-primary btn-block" type="submit">Se connecter</button>

    </form>



    <!-- Modal -->
    <div class="modal fade" id="modal-Login" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Erreur de connexion</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    texte dynamique
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>

                </div>
            </div>
        </div>
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

    <script src="../js/login.js"></script>
</body>


</html>