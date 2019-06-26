<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>



	<%
		List<Departement> listeDepartements = (List<Departement>) request.getAttribute("listeDepartements");
		Integer numeroMois = (Integer) request.getAttribute("numeroMois");
		Integer  annee = (Integer) request.getAttribute("annee");
		Integer idDepartement = (Integer) request.getAttribute("idDepartement");
		LocalDate maDate = LocalDate.now();
	%>

<div> 

<form id="form-filtres" action="afficherVueDepart" method="get">

	<div class="row mt-5 mb-3">

		<input id="vue" name="vue" type="hidden" value="collab">

		<div class="input-group mb-3 col-sm-4">

			<div class="input-group-prepend">
				<label class="input-group-text" for="inputDepartement">Département</label>
			</div>

			
			
			<select class="custom-select" id="inputDepartement" name="inputDepartement">
			
			
				<%
					for (Departement dept : listeDepartements) {
						
						if (dept.getId() == idDepartement) {
							%>
							<option selected value="<%= dept.getId() %>"><%=dept.getNom() %></option>
							<%
						} else {
							%>
							<option value="<%= dept.getId() %>"><%=dept.getNom() %></option>
							<%
						}
						
				
					}
				%>

			</select>

		</div>



		<div class="input-group mb-3 col-sm-2">

			<div class="input-group-prepend">
				<label class="input-group-text" for="inputMois">Mois</label>
			</div>

			<%--A Faire : utiliser méthode Java pour récuperer les mois entrées en base afin de proposer un choix d'année cohérent --%>
			<select class="custom-select" id="inputMois" name="inputMois">
			
			<%
				Calendar cal = Calendar.getInstance();
										
				for (int i = 1; i<=12; i++) {
					
					cal.set(annee, i - 1, 1);

					Date maDate1 = cal.getTime();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMMM");
//		 			simpleDateFormat = new SimpleDateFormat("EEEE");

					String nomMois = simpleDateFormat.format(maDate1);

							
					if(i == numeroMois){
						%>
						<option selected value="<%= i%>"><%= nomMois %></option>
						<% 
					} else {
						%>
						<option value="<%= i%>"><%= nomMois %></option>
						<% 
					}
					
					
				}
				
			%>
			
				
			</select>

		</div>

		<div class="input-group mb-3 col-sm-2">

			<div class="input-group-prepend">
				<label class="input-group-text" for="inputAnnee">Année</label>
			</div>

			<%--A Faire : utiliser méthode Java pour récuperer les années entrées en base afin de proposer un choix d'année cohérent --%>
			<select class="custom-select" id="inputAnnee" name="inputAnnee">
			
			
			
			<%
				int anneeActuelle = maDate.getYear();
				int anneeDebut = anneeActuelle -3;
				int anneeFin = anneeActuelle + 3;
			
			
				for (int i = anneeDebut; i<=anneeFin; i++) {
					
					if(i == annee) {
						%>
						<option selected value="<%= i %>"><%= i %></option>
						<% 
					} else {
						%>
						<option value="<%= i %>"><%= i %></option>
						<% 
					}
					
					
				}
				
			%>
			
			
				
			</select>

		</div>

		<div class="col-sm-1">
			<button id="btn-submit-filtres" type="submit" class="btn">
				<i data-feather="search">filtrer</i>
			</button>
		</div>


		<div class="col-sm-1">
			<button id="export-excel" class="btn btn-outline-light">
				<i data-feather="file">export excel</i>
			</button>
		</div>


	</div>

</form>

