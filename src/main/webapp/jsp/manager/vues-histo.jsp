<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="container my-5">



	<p>
		query :
		<%=(String) request.getAttribute("choix")%></p>





	<h1>Histogramme par département et par jour</h1>



	<%-- include des filtres --%>
	<%@ include file="vues-filtres.jsp" %>
	
	

	


	<div class="row p-2 bg-primary">
		<div class="col-sm-2">Nom</div>
		<div class="col-sm-10">Action</div>
	</div>


	<div class="row p-2">

		<div class="col-sm-9">Vue par département par jour et par
			collaborateur</div>
		<div class="col-sm-3">
			<button class="btn btn-sm btn-none" type="button">
				<i data-feather="eye">voir</i>
			</button>
		</div>

	</div>

	<div class="row p-2">

		<div class="col-sm-9">Histogramme par département et par jour</div>
		<div class="col-sm-3">
			<button class="btn btn-sm btn-none" type="button">
				<i data-feather="eye">voir</i>
			</button>
		</div>

	</div>


	<button class="btn btn-outline-primary">retour</button>

	<div class="my-3">légende ... .. . . .</div>


</div>


