<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="container my-5">

	<h1>Vues synthétiques</h1>


	<div class="row p-2 bg-primary">
		<div class="col-sm-9 ">Liste des rapports</div>
		<div class="col-sm-3">Action</div>
	</div>

	<div class="row p-2">
		<div class="col-sm-9">Vue par département par jour et par
			collaborateur</div>
		<div class="col-sm-3">
		<a href="afficherVueDepart?vue=collab"><i data-feather="eye">voir</i></a>
		
			
			
		</div>
	</div>
	<div class="row p-2">
		<div class="col-sm-9">Histogramme par département et par jour</div>
		<div class="col-sm-3">
			<a href="afficherVueDepart?vue=histo"><i data-feather="eye">voir</i></a>
		</div>
	</div>



</div>
