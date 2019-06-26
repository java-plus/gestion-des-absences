<%@page import="javax.xml.bind.ParseConversionEvent"%>
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
	import="java.util.List, fr.gda.controller.*, fr.gda.filter.*, fr.gda.model.*, fr.gda.dao.* "%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<div class="container my-5">

	<script>
		function selectAnnee() {
			console.log(document.forms[1]);
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
					<option selected value="<%=anneeListe%>"><%=anneeListe%></option>
					<%
						} else {
					%>
					<option value="<%=anneeListe%>"><%=anneeListe%></option>
					<%
						}
						}
					%>
				</select>
			</div>
		</form>
	</div>


	<div class="row p-2 my-1 bg-dark font-weight-bold  rounded">

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

		<div class="col-sm-2 d-flex align-items-center mx-auto">
			<div></div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
			</div>
		</div>

	</div>


	<%
		List<AbsenceParPersonne> listeAbsences = (List<AbsenceParPersonne>) request.getAttribute("afficherConge");
		//Integer typeConge = request.getAttribute("afficherTypeConge");
		Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();
		if (listeAbsences != null) {
			for (AbsenceParPersonne liste : listeAbsences) {
				int typeConge = liste.getIdAbsence();
				String jour = liste.getDateDebut().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE);
				String jourMaj = jour.substring(0, 1).toUpperCase() + jour.substring(1);
				if ((typeConge == 6 || typeConge == 5) && an.equals(liste.getDateDebut().toString().substring(0, 4))
						&& (liste.getStatut().equals("VALIDEE") || liste.getStatut().equals("INITIALE"))) {
	%>
	<div
		class="row p-2 my-1 ligneSuppr<%=liste.getIdAbsence()%>_<%=liste.getDateDebut()%>">
		<div class="col-sm-3 mx-auto"><%=liste.afficherDate(liste.getDateDebut())%></div>
		<div class="col-sm-2 mx-auto"><%=liste.typeConge(typeConge)%></div>
		<div class="col-sm-2 mx-auto"><%=jourMaj%></div>
		<div class="col-sm-2 mx-auto"><%=liste.getMotif()%></div>
		<%
			if (liste.getStatut().equals("INITIALE")
								|| (liste.getStatut().equals("VALIDEE") && typeConge == 6)) {
		%>
		<div class="col-sm-2 mx-auto">
			<a href="updateFerie?update=<%=liste.getId()%>"><button
					type="button" class="btn btn-dark btn-modif "
					data-toggle="" data-target="" id="btn-modif">
					<i data-feather="edit-2">modifier</i>
				</button></a>
			<button type="button" class="btn btn-dark btn-supp "
				data-toggle="modal" data-target="#modal"
				id="<%=liste.getIdAbsence()%>_<%=liste.getDateDebut()%>">
				<i data-feather="trash">supprimer</i>
			</button>
		</div>
	</div>
	<%
		} else {
	%>
	<div class="col-sm-2 mx-auto"></div>
</div>
<%
	}
			}
		}
	}
%>
</div>

<div class="container">
	<a href="adminJFerieRttEmp"><button
			class="btn btn-lg btn-dark my-3" type="button">Ajouter
			un jour férié ou un RTT employeur</button></a>
</div>

<!-- 		Modal -->

<div class="modal" tabindex="-1" role="dialog" id="modal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title text-dark">Confirmation suppression</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body text-muted">
				<p>Confirmez-vous la suppression de ces congés?</p>
			</div>
			<form method="DELETE" action="adminJFerieRttEmp" class="formModal"
				id="formModal">
				<div class="modal-footer">
					<a href="jFerieRttEmp"><button type="button"
							class="btn btn-secondary">Annuler</button></a>
					<button type="submit" class="btn btn-success" data-dismiss="modal">Confirmer</button>
				</div>
			</form>

		</div>
	</div>
</div>

<!-- 			fin modal -->