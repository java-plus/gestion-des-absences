<%@page import="fr.gda.utils.DateUtils"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.util.Locale"%>
<%@page import="java.time.format.TextStyle"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.Date"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.Year"%>
<%@page import="java.time.Instant"%>
<%@	page
	import="java.util.List, fr.gda.controller.*, fr.gda.filter.*, fr.gda.model.*, fr.gda.dao.*"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<div class="container my-5">

	<script>
		function selectAnnee() {
			document.location.href = "http://localhost:8080/gda/controller/jFerieRttEmp?selectedAn="
					+ document.forms["joursFeries"].anSelect.value;
		}
	</script>

	<h1>Jours feriés et RTT employeurs</h1>
	<%
		int annee = LocalDate.now().getYear();
		if (request.getParameter("selectedAn") != null) {
			annee = Integer.parseInt(request.getParameter("selectedAn"));
		}
		String an = String.valueOf(annee);

		List<Integer> annees = new ArrayList<>();
		annees.add(LocalDate.now().getYear() + 1);
		annees.add(LocalDate.now().getYear());
		annees.add(LocalDate.now().getYear() - 1);
	%>
	<div class="row p-2">
		<form class=" form-inline" name="joursFeries">
			<div class="form-group">
				<label for="inputYear">Année :</label> <select id="inputState"
					class="form-control" name="anSelect" onchange="selectAnnee()">

					<%
						for (int anneeListe : annees) {
							if (anneeListe == annee) {
					%>
					<option selected><%=anneeListe%></option>
					<%
						} else {
					%>
					<option><%=anneeListe%></option>
					<%
						}
						}
					%>
				</select>
			</div>
		</form>
	</div>


	<div class="row p-2 my-1 bg-primary">

		<div class="col-sm-3 d-flex align-items-center mx-auto">
			<div>Date</div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
			</div>

		</div>

		<div class="col-sm-2 d-flex align-items-center mx-auto">
			<div>Type</div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
			</div>
		</div>

		<div class="col-sm-2 d-flex align-items-center mx-auto">
			<div>Jour</div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
			</div>
		</div>

		<div class="col-sm-2 d-flex align-items-center mx-auto">
			<div>Commentaires</div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
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
				typeConge = liste.getTypeAbsence();
				String jour = liste.getDateDebut().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE);
				String jourMaj = jour.substring(0, 1).toUpperCase() + jour.substring(1);
				if ((typeConge.equals("ferié") || typeConge.equals("RTT employeur"))
						&& an.equals(liste.getDateDebut().toString().substring(0, 4))
						&& (liste.getStatut().equals("VALIDEE") || liste.getStatut().equals("INITIALE"))) {
	%>

	<div class="row p-2 my-1">
		<div class="col-sm-3 mx-auto"><%=liste.getDateDebut()%></div>
		<div class="col-sm-2 mx-auto"><%=typeConge%></div>
		<div class="col-sm-2 mx-auto"><%=jourMaj%></div>
		<div class="col-sm-2 mx-auto"><%=liste.getMotif()%></div>
	</div>
	<%
		}
			}
		}
	%>
</div>

<div class="container">
	<!-- 	<button class="btn btn-lg btn-outline-primary" type="button">Ajouter -->
	<!-- 		un jour férié ou un RTT employeur</button> -->
</div>