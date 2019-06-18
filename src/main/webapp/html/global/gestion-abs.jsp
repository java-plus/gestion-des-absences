<%@	page import="java.util.List, fr.gda.controller.*, fr.gda.filter.*, fr.gda.model.*" %>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>



<div class="container my-5">

	<h1>Gestion des absences</h1>

	<div class="row p-2 bg-primary">
		<div class="col-sm-3 ">
			Date de début
			<div>
				<i data-feather="chevron-up"></i><i data-feather="chevron-down"></i>
			</div>
		</div>
		<div class="col-sm-3">
			Date de fin
			<div>
				<i data-feather="chevron-up"></i><i data-feather="chevron-down"></i>
			</div>
		</div>
		<div class="col-sm-3">
			type
			<div>
				<i data-feather="chevron-up"></i><i data-feather="chevron-down"></i>
			</div>
		</div>
		<div class="col-sm-2">
			statut
			<div>
				<i data-feather="chevron-up"></i><i data-feather="chevron-down"></i>
			</div>
		</div>
		<div class="col-sm-1">
			actions
			<div>
				<i data-feather="chevron-up"></i><i data-feather="archevronrow-down"></i>
			</div>
		</div>
	</div>
	
	<% List<AbsenceParPersonne> listeAbsences  = (List<AbsenceParPersonne>)request.getAttribute("afficherConge");
		String typeConge = (String)request.getAttribute("afficherTypeConge"); 
		Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
		if(listeAbsences != null){
		for(AbsenceParPersonne liste : listeAbsences) {
			
	%>
	

	<div class="row p-2">
		<div class="col-sm-3"><%=liste.getDateDebut() %></div>
		<div class="col-sm-3"><%=liste.getDateFin() %></div>
		<div class="col-sm-3"><%=typeConge %></div>
		<div class="col-sm-2"><%=liste.getStatut() %></div>
		<div class="col-sm-1">
			<i data-feather="trash"> voir / modifier /supprimer</i>
		</div>
	</div>
	
	<%}} %>

</div>

<div class="container">
	Demander une absence
	<button class="btn btn-lg btn-outline-primary" type="button">créer</button>
</div>

<div class="container my-5">
	<div>Soldes des compteurs :</div>
	<div>Congés payés : <%=utilisateur.getCongeRestant() %></div>
	<div>RTT : <%=utilisateur.getRttRestant() %></div>
</div>




