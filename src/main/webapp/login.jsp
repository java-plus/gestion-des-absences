<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html lang="fr">


<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="css/font.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css?family=Poppins:400,700|Roboto:400,700&display=swap"
	rel="stylesheet">

<title>GDA - Connexion</title>

</head>

<body>


	<form class="form-signin" method="post"
		action="http://localhost:8080/gda/connexion">
		<div class="text-center mb-4">
			<img class="mb-4 logo"
				src="https://pbs.twimg.com/profile_images/810865682564911104/A3CM9RWz_400x400.jpg"
				alt="logo gda">
			<h1 class="h3 mb-3 ">Connexion</h1>
			<p>Gestion des abscences, congés...</p>
		</div>

		<div class="form-label-group">
			<input type="email" id="email" class="form-control"
				placeholder="Adresse email" required="" autofocus="" name="email">
			<label for="email">Adresse email</label>
		</div>

		<div class="form-label-group">
			<input type="password" id="password" class="form-control"
				placeholder="Mot de passe" required="" name="password"> <label
				for="password">Mot de passe</label>
		</div>

		<%
			String erreur = (String) request.getAttribute("erreur");
			if (erreur != null) {
		%>

		<div>
			<p class="text-danger text-center">Vos informations
				d'authentification sont invalides.
			<p>
		</div>

		<%
			}
		%>

		<button id="#btn-login-submit"
			class="btn btn-lg btn-primary btn-block" type="submit">Se
			connecter</button>

	</form>




	<%-- chargement des js de JQuery et Bootsrap et feather --%>

	<%@ include file="jsp/global/load.jsp"%>


</body>

</html>