<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>


<form type="POST" action="">

	<div class="container my-5">

		<h1>Vue par département par jour et par collaborateur</h1>


		<%-- include des filtres --%>
		<%@ include file="vues-filtres.jsp"%>

		<div class="row py-3 bg-primary">
			<div class="col-sm-2">nom</div>
			<div class="col-sm-10 d-flex flex-row ">
				<%
					for (int i = 1; i <= 31; i++) {
				%>
				<div class="cal-vue-dept"><%=i%></div>
				<%
					}
				%>
			</div>
		</div>


		<!-- DEBUT de la boucle pour écrire une ligne par utilisateur -->
		<%
			for (int j = 0; j < 5; j++) {
		%>

		<div class="row row-user py-2">
			<div class="col-sm-2">machin</div>
			<div class="col-sm-10 d-flex flex-row ">
				<%
					for (int i = 1; i <= 31; i++) {
				%>
				<div class="cal-vue-dept">A</div>
				<%
					}
				%>
			</div>
		</div>

		<%
			}
		%>
		<!-- FIN de la boucle pour écrire une ligne par utilisateur  -->





	</div>


	<div class="container my-5">

		<button class="btn btn-outline-primary">retour</button>

		<div>légende ... .. . . .</div>

	</div>

</form>