<%@	page
	import="java.util.List, fr.gda.controller.*, fr.gda.filter.*, fr.gda.model.*, fr.gda.dao.*"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>


<div class="container my-5">

	<h1 class="text-center">Gestion des absences</h1>

	<div class="col-sm-5 mx-auto mt-5">

		<form method="POST" action="updateConges">

			<div class="form-group row">
				<div class="col-sm-5 col-form-label">
					<label for="dateDebut">Date de début</label>
				</div>
				<div class="col-sm-7 ">
					<input type="date" class="form-control" id="dateDebut"
						aria-describedby="dateDebut" placeholder="Date de début" required>
				</div>
			</div>

			<div class="form-group row">
				<div class="col-sm-5 col-form-label">
					<label for="dateFin">Date de fin</label>
				</div>
				<div class="col-sm-7 ">
					<input type="date" class="form-control" id="dateFin"
						aria-describedby="dateFin" placeholder="Date de fin" required>
				</div>
			</div>

			<div class="form-group row">
				<div class="col-sm-5 col-form-label">
					<label for="motif">Type de congé</label>
				</div>
				<div class="col-sm-7 ">
					<select class="custom-select" id="type" required>
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
					<textarea class="form-control" id="motif" rows="5"></textarea>
				</div>
			</div>

			<div class="form-group row d-flex justify-content-center">
				<a href="afficherConges"
					<button type="button" class="btn btn-danger mr-5 mt-3">Annuler</button></a>
				<button type="submit" class="btn btn-success mr-5 mt-3">Valider</button>
			</div>

		</form>
	</div>
</div>