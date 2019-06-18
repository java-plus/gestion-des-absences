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

    <link rel="stylesheet" href="css/font.css">
    <link rel="stylesheet" href="css/global.css">

    <title>GDA - Jours fériés et RTT employeur</title>

</head>



<body>



	<%-- include du header manager ou employe
		en fonction du profil de l'utilisateur on charge le menu correspondant
		--%>


	<% 
		if (((String)request.getAttribute("monProfil")).equals("manager")) { 
		%>

	<%@ include file="html/manager/menu.jsp"%>

	<% 
		} else {
		%>
	<%@ include file="html/employe/menu.jsp"%>
	<% } %> 




	<%-- include du contenu --%>
	<%@ include file="global/jours-feries.html"%>
	
	
	<%-- si t'utilisateur est Admin, alors on affiche la liste d'action : ajouter, modifier, supprimer --%>
	<%
	
	if (monProfil isAdmin == true) {
		
	}
	
	%>

	<%-- chargement des js de JQuery et Bootsrap et feather --%>
	<%@ include file="global/load.html"%>

</body>

</html>
