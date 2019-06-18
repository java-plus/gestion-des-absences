<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<div class="container my-5">

	<h1>Jours fÃ©riÃ©s et RTT employeurs</h1>

	<div class="row p-2">
		<form class=" form-inline">
			<div class="form-group">
				<label for="inputPassword6">AnnÃ©e :</label> <select id="inputState" class="form-control">
					<option>2019</option>
					<option>2018</option>
					<option>2017</option>
				</select>
			</div>
		</form>
	</div>


	<div class="row p-2 my-1 bg-primary ">

		<div class="col-sm-3 d-flex align-items-center">
			<div>Date de dÃ©but</div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
				<button type="button" class="btn btn-secondary">
					<i data-feather="chevron-up"></i>
				</button>
				<button type="button" class="btn btn-secondary">
					<i data-feather="chevron-down"></i>
				</button>
			</div>

		</div>

		<div class="col-sm-3 d-flex align-items-center">
			<div>Date de fin</div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
				<button type="button" class="btn btn-secondary">
					<i data-feather="chevron-up"></i>
				</button>
				<button type="button" class="btn btn-secondary">
					<i data-feather="chevron-down"></i>
				</button>
			</div>
		</div>

		<div class="col-sm-2 d-flex align-items-center">
			<div>type</div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
				<button type="button" class="btn btn-secondary">
					<i data-feather="chevron-up"></i>
				</button>
				<button type="button" class="btn btn-secondary">
					<i data-feather="chevron-down"></i>
				</button>
			</div>
		</div>

		<div class="col-sm-2 d-flex align-items-center">
			<div>statut</div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
				<button type="button" class="btn btn-secondary">
					<i data-feather="chevron-up"></i>
				</button>
				<button type="button" class="btn btn-secondary">
					<i data-feather="chevron-down"></i>
				</button>
			</div>
		</div>

		<div class="col-sm-2 d-flex align-items-center">
			<div>actions</div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
				<button type="button" class="btn btn-secondary">
					<i data-feather="chevron-up"></i>
				</button>
				<button type="button" class="btn btn-secondary">
					<i data-feather="chevron-down"></i>
				</button>
			</div>
		</div>

	</div>




	<div class="row p-2">
		<div class="col-sm-3">valeur debut</div>
		<div class="col-sm-3">valeur de fin</div>
		<div class="col-sm-2">valeur de type</div>
		<div class="col-sm-2">valeur de statut</div>
		<div class="col-sm-2">
			<button type="button" class="btn btn-none">
				<i data-feather="trash">supprimer</i>
			</button>
		</div>
	</div>

	<div class="row p-2">
		<div class="col-sm-3">valeur debut</div>
		<div class="col-sm-3">valeur de fin</div>
		<div class="col-sm-2">valeur de type</div>
		<div class="col-sm-2">valeur de statut</div>
		<div class="col-sm-2">
			<i data-feather="eye">voir</i>
		</div>
	</div>

	<div class="row p-2">
		<div class="col-sm-3">valeur debut</div>
		<div class="col-sm-3">valeur de fin</div>
		<div class="col-sm-2">valeur de type</div>
		<div class="col-sm-2">valeur de statut</div>
		<div class="col-sm-2">
			<i data-feather="edit-2">modifier</i>
		</div>
	</div>

	<div class="row p-2">
		<div class="col-sm-3">valeur debut</div>
		<div class="col-sm-3">valeur de fin</div>
		<div class="col-sm-2">valeur de type</div>
		<div class="col-sm-2">valeur de statut</div>
		<div class="col-sm-2">
			<i data-feather="trash">supprimer</i>
		</div>
	</div>

</div>

<div class="container">
	<button class="btn btn-lg btn-outline-primary" type="button">Ajouter
		un jour fÃ©riÃ© ou un RTT employeur</button>
</div>