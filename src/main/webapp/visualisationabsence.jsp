<%@page
	import="java.util.List, fr.gda.model.*, fr.gda.controllers.*"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Document</title>
</head>
<body>

<form method="get" action="controller/afficherConges">
<input type="submit" name="afficher" value="afficher">
</form>

	<h1>Gestion des absences</h1>
	<% List<AbsenceParPersonne> listeAbsence = (List<AbsenceParPersonne>)request.getAttribute("afficherConge");
	   	if(listeAbsence != null ){ for (AbsenceParPersonne liste :listeAbsence) { %>

	<p>date de début :<%=liste.getDateDebut() %> </p>
	<p>date de fin :<%= liste.getDateFin() %></p>
	<p>type : <%=request.getAttribute("afficherTypeConge") %></p>
	<p>status : <%= liste.getStatut() %></p>
	<p>action :</p>
	<%}} %>

	<p>
		Demander une absence : <input type="button" value="ajouterAbsence">
	</p>

	<div></div>

	<% Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur"); %>
	<p>Solde des compteurs :</p>
	<p>congés payés : <%=utilisateur.getCongeRestant() %></p>
	<p>RTT : <%=utilisateur.getRttRestant() %></p> 


</body>
</html>