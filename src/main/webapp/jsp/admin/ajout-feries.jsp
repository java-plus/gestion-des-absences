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
		</div>

		<div class="form-group row">
			<div class="col-sm-5">
				<label for="typeJour">Type de jour</label>
			</div>
			<div class="col-sm-7">
				<select class="form-control" name="selectedType" id="typeJour">
					<option selected>(Type de jour)</option>
					<option value="ferié">Ferié</option>
					<option value="RTT employeur">RTT employeur</option>
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
		</div>

		<div class="form-group d-flex justify-content-center my-5">
			<a href="jFerieRttEmp"><button
					class="btn btn-lg btn-outline-light border-dark mx-2 bg-danger"
					type="button">Annuler</button></a>
				<button
					class="btn btn-lg btn-outline-light border-dark mx-2 bg-success"
					type="submit">Valider</button>
		</div>
	</form>
</div>