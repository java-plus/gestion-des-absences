<%@page import="fr.gda.enumeration.TypeAbsence"%>
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
<%@page import="fr.gda.enumeration.*" %>
<%@	page
	import="java.util.List, fr.gda.controller.*, fr.gda.filter.*, fr.gda.model.*, fr.gda.dao.*"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="container my-5 mx-auto">
	<h1 class="d-flex justify-content-center my-5">Nouveau jour ferié
		/ RTT employeur</h1>

	<form method="post" class=" col-sm-5 mx-auto" action="adminJFerieRttEmp">
		<div class="form-group row">
			<div class="col-sm-5">
				<label for="date">Date</label>
			</div>
			<div class="col-sm-7">
				<input type="date" class="form-control" name="selectedDate" id="date">
			</div>
			<div  class="text-info mx-auto">
				<p id="texteDateDebut">La date de début de congé ne peut être inférieur à demain </p>
				</div>
		</div>

		<div class="form-group row">
			<div class="col-sm-5">
				<label for="typeJour">Type de jour</label>
			</div>
			<div class="col-sm-7">
				<select class="form-control" name="selectedType" id="typeJour" required>
					<option value="">(Type de jour)</option>
					<option value="6"><%=TypeAbsence.FERIE.getTypeAbsence()%></option>
					<option value="5"><%=TypeAbsence.RTT_EMPLOYEUR.getTypeAbsence()%></option>
				</select>
			</div>
		</div>

		<div class="form-group row">
			<div class="col-sm-5">
				<label for="commentaire">Commentaires</label>
			</div>
			<div class="col-sm-7">
				<input type="text" class="form-control" name="selectedMotif" id="commentaire">
			</div>
			<div  class="text-info mx-auto">
				<p id="texteMotif">Le motif est obligatoire pour les jours feriés</p>
				</div>
		</div>

		<div class="form-group d-flex justify-content-center my-5">
			<a href="jFerieRttEmp"><button
					class="btn btn-lg btn-outline-light border-dark mx-2 bg-danger"
					type="button">Annuler</button></a>
				<button
					class="btn btn-lg btn-outline-light border-dark mx-2 bg-success"
					type="submit" id="btnValider">Valider</button>
		</div>
		
		<%
			String erreur = (String) request.getAttribute("erreur");
			if (erreur != null) {
		%>

		<div>
			<p class="text-info text-center">Vos congés se chevauchent avec des congés existants, merci de vérifier les dates !<p>
		</div>

		<%
			}
		%>
	</form>
</div>