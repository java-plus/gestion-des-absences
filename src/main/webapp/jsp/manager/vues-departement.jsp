<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="container my-5">

	<h1>Vue par département par jour et par collaborateur</h1>


	<%-- include des filtres --%>
	<%@ include file="vues-filtres.jsp" %>

	<div class="row my-2 bg-primary">
		<div class="col-sm-2">nom</div>
		<div class="col-sm-10 d-flex flex-row ">
			<%
			for(int i=1; i<=31; i++) {
			%>
			<div class="cal-vue-dept"><%= i %></div>
			<%
			}
			%>
		</div>
	</div>


	<div class="row py-2">
		<div class="col-sm-2">machin</div>
		<div class="col-sm-10 d-flex flex-row ">
			<%
			for(int i=1; i<=31; i++) {
			%>
			<div class="cal-vue-dept"><%= i %></div>
			<%
			}
			%>
		</div>
	</div>


	

</div>


<div class="container my-5">

	<button class="btn btn-outline-primary">retour</button>
	
	<div>légende ... .. . . .</div>

	</div>