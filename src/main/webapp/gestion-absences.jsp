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

    <title>GDA - Gestion des absences</title>

</head>

<body>



	<%-- include du header manager 
		en fonction du profil de l'utilisateur on charge le menu correspondant
		--%>


	<% 
		if (monProfil == "manager") { 
		%>
	<%@ include file="WEB-INF/manager/menu.html"%>
	<% 
		} else if (monProfil == "employe") {
		%>
	<%@ include file="WEB-INF/employe/menu.html"%>
	} %>






	<%-- include du contenu --%>
	<%@ include file="WEB-INF/global/gestion-abs.html"%>

	<%-- chargement des js de JQuery et Bootsrap et feather --%>
	<%@ include file="WEB-INF/global/load.html"%>

</body>

</html>

<body>