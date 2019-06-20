<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"
import="java.util.List, java.util.ArrayList, fr.gda.model.*, fr.gda.dao.*"%>


<%
		List<Utilisateur> utilisateurParDepartement = (List<Utilisateur>) request.getAttribute("utilisateurParDepartement");
		List<AbsenceParPersonne> absenceDepartementMoisAnnee = (List<AbsenceParPersonne>) request.getAttribute("absenceDepartementMoisAnnee");
		int maxDay = (int) request.getAttribute("maxDay");
		
	
	%>



<div class="container my-5">

	<h1>Vue par département par jour et par collaborateur</h1>


	<%-- include des filtres --%>
	<%@ include file="vues-filtres.jsp"%>
	<%-- -------------------- --%>

	<div class="row py-3 bg-primary">
		<div class="col-sm-2">nom<a class="text-light"href=""><i data-feather="chevron-down"></i></a></div>
		<div class="col-sm-10 d-flex flex-row ">
			<%
			for (int i = 1; i < maxDay; i++) {
			%>
			<div class="cal-vue-dept"><%=i%></div>
			<%
				}
			%>
		</div>
	</div>


	<!-- DEBUT de la boucle pour écrire une ligne par utilisateur -->

	
	<%
	for (int i = 0; i < utilisateurParDepartement.size(); i++) {
	%>
	
		<div class="row row-user py-2">
		
			<div class="col-sm-2"><%=utilisateurParDepartement.get(i).getNom()%></div>
			
			<div class="col-sm-10 d-flex flex-row ">
			
		
		
			<%
			
				// Pour chaque jour, si un congé est posé, l'afficher
				for (int k = 1; k < maxDay; k++) {
					
					
					
					for (int m = 0; m < absenceDepartementMoisAnnee.size(); m++) {
						
						
						// Si c'est le même utilisateur
						if (absenceDepartementMoisAnnee.get(m).getIdUtil() == utilisateurParDepartement.get(i).getId()) {
							
							// Si c'est sous les mêmes dates de mois
							if (absenceDepartementMoisAnnee.get(m).getDateDebut().getDayOfMonth() <= k & absenceDepartementMoisAnnee.get(m).getDateFin().getDayOfMonth() >= k) {
								
								String lettre = absenceParPersonneDao.RecupererTypeConges(absenceDepartementMoisAnnee.get(m).getIdAbsence()).toUpperCase().substring(0, 1);
								%>
								<div class="cal-vue-dept"><%= lettre %></div>
								<%
								
							
							} else {
								%>
								<div class="cal-vue-dept">.</div>
								<%
							}

						}
						
						
					}
					
					
					
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

