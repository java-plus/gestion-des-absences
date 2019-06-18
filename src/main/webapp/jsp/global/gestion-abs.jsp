<%@	page
	import="java.util.List, fr.gda.controller.*, fr.gda.filter.*, fr.gda.model.*, fr.gda.dao.*"%>
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
			Type
			<div>
				<i data-feather="chevron-up"></i><i data-feather="chevron-down"></i>
			</div>
		</div>
		<div class="col-sm-2">
			Statut
			<div>
				<i data-feather="chevron-up"></i><i data-feather="chevron-down"></i>
			</div>
		</div>
		<div class="col-sm-1">
			Actions
			<div>
				<i data-feather="chevron-up"></i><i data-feather="archevronrow-down"></i>
			</div>
		</div>
	</div>

	<%
		List<AbsenceParPersonne> listeAbsences = (List<AbsenceParPersonne>) request.getAttribute("afficherConge");
		String typeConge = (String) request.getAttribute("afficherTypeConge");
		Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();
		if (listeAbsences != null) {
			for (AbsenceParPersonne liste : listeAbsences) {
				typeConge = absenceDao.RecupererTypeConges(liste.getIdAbsence());
				if (typeConge.equals("férié") || typeConge.equals("RTT employeur")) {
					continue;
				}
	%>


	<div class="row p-2">
		<div class="col-sm-3"><%=liste.getDateDebut()%></div>
		<div class="col-sm-3"><%=liste.getDateFin()%></div>
		<div class="col-sm-3"><%=typeConge%></div>
		<div class="col-sm-2"><%=liste.getStatut()%></div>
		<div class="col-sm-1">


			<%
				if (liste.getStatut().equals("INITIALE")) {
			%>
			<a href="#"> <i data-feather="edit-2">modifier</i></a>
			</a> <a href="#"><i data-feather="trash" href="#">supprimer</i> </a>
			<%
				;
						} else if (liste.getStatut().equals("EN_ATTENTE") || liste.getStatut().equals("VALIDEE")) {
			%>
			<a href="#"><i data-feather="trash" href="#">supprimer</i></a>
			<%
				;
						}
			%>
		</div>
	</div>

	<%
		}
		}
	%>

</div>

<div class="container">
	Demander une absence
	<button class="btn btn-lg btn-outline-primary" type="button">créer</button>
</div>

<div class="container my-5">
	<div>Soldes des compteurs :</div>
	<div>
		Congés payés :
		<%=utilisateur.getCongeRestant()%></div>
	<div>
		RTT :
		<%=utilisateur.getRttRestant()%></div>
</div>


