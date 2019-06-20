<%@	page
	import="java.util.List, fr.gda.controller.*, fr.gda.filter.*, fr.gda.model.*, fr.gda.dao.*"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>


<div class="container my-5">

	<h1 class="text-center">Demande d'absence</h1>

	<div class="col-sm-6 mx-auto mt-5">

		<form method="POST" action="updateConges?ajout=ok">

			<div class="form-group row">
				<div class="col-sm-5 col-form-label">
					<label for="dateDebut">Date de début</label>
				</div>
				<div class="col-sm-7 ">
					<input type="date" class="form-control" id="dateDebut" name="dateDebut"
						aria-describedby="dateDebut" placeholder="Date de début" required>
				</div>
				<div  class="text-info mx-auto">
				<p id="texteDateDebut">La date de début de congé ne peut être inférieur à demain </p>
				</div>
			</div>

			<div class="form-group row">
				<div class="col-sm-5 col-form-label">
					<label for="dateFin">Date de fin</label>
				</div>
				<div class="col-sm-7 ">
					<input type="date" class="form-control" id="dateFin" name="dateFin"
						aria-describedby="dateFin" placeholder="Date de fin" required>
				</div>
				<div class="text-info mx-auto">
				<p id="texteDateFin" >La date de fin de congé ne peut être inférieur à la date de début </p>
				</div>
			</div>

			<div class="form-group row">
				<div class="col-sm-5 col-form-label">
					<label for="motif">Type de congé</label>
				</div>
				<div class="col-sm-7 ">
					<select class="custom-select" id="type" name="type" required>
						<option value="">Type de congé</option>
						<option value="cp">Congé payé</option>
						<option value="rtt">RTT</option>
						<option id="css" value="css">Congé sans solde</option>
					</select>
				</div>
			</div>

			<div class="form-group row">
				<div class="col-sm-5 col-form-label">
					<label for="motif">Motif</label>
				</div>
				<div class="col-sm-7 ">
					<textarea class="form-control" id="motif" name="motif" rows="5"></textarea>
				</div>
				<div  class="text-info mx-auto">
				<p id="texteMotif">Le motif est obligatoire pour les congés sans solde</p>
				</div>
			</div>

			<div class="form-group row d-flex justify-content-center">
				<a href="afficherConges"
					<button type="button" class="btn btn-danger mr-5 mt-3">Annuler</button></a>
				<button type="submit" id="btnValider" class="btn btn-success mr-5 mt-3">Valider</button>
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
</div>