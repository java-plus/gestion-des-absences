<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"
import="java.util.List, java.util.ArrayList, fr.gda.model.*, fr.gda.dao.*"%>


<%
		//List<Utilisateur> utilisateurParDepartement = (List<Utilisateur>) request.getAttribute("utilisateurParDepartement");
	//	List<AbsenceParPersonne> absenceDepartementMoisAnnee = (List<AbsenceParPersonne>) request.getAttribute("absenceDepartementMoisAnnee");
		//int maxDay = (int) request.getAttribute("maxDay");
		
		String[] ListNoms = (String[]) request.getAttribute("ListNoms");
		String[][] ListjourMois = (String[][]) request.getAttribute("ListjourMois");
		
		
	%>



<div class="container my-5">

	<h1>Vue par département par jour et par collaborateur</h1>


	<%-- include des filtres --%>
	<%@ include file="vues-filtres.jsp"%>
	<%-- -------------------- --%>

	<div class="row py-3 bg-primary">
		<div class="col-sm-2 d-flex">nom<a class="text-light ml-auto"href=""><i data-feather="chevron-down"></i></a></div>
		<div class="col-sm-10 d-flex flex-row p-0">
			<%
			for (int i = 1; i <= ListjourMois[0].length; i++) {
			%>
			<div class="cal-vue-dept text-center"><%=i%></div>
			<%
				}
			%>
		</div>
	</div>


	<!-- DEBUT de la boucle pour écrire une ligne par utilisateur -->

	
	<%
	for (int i = 0; i < ListNoms.length; i++) {
	%>
	
		<div class="row row-user">
		
			<div class="col-sm-2 nom"><%=ListNoms[i]%></div>
			
			<div class="col-sm-10 d-flex flex-row p-0">
			
		
			<%
			
			for (int k = 0; k <ListjourMois[0].length; k++) {
				
				
				String valeur = ListjourMois[i][k];
				String congeEtat ="";
				String maClasse = "cal-vue-dept text-center py-2 ";
				
				String classeAttente = "attente";
				String classeValidee = "validee";
				String classeRefusee = "refusee";
				String classeWeekEnd = "weekend";
				String classeNormal = "base";
				
				
				if (valeur.contains("-")) {
					String[] parts = valeur.split("-");
					valeur = parts[0];
					congeEtat = parts[1];
					
					
					
					
					if (congeEtat.equals("REJETEE")) {
						maClasse += classeRefusee;
					} else if (congeEtat.equals("VALIDEE")) {
						maClasse += classeValidee;
					} else if (congeEtat.equals("EN_ATTENTE_VALIDATION")) {
						maClasse += classeAttente;
					}
					
					
				} else {
					if (valeur.equals("N")) {
						maClasse += classeNormal;
						valeur = "N";
					} else if (valeur.equals("W")) {
						maClasse += classeWeekEnd;
						valeur = "W";
					}
				}
				
				
				%>
				<div class="<%= maClasse %>"><%= valeur %></div>
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

	<div class=" my-2"><h6>légende :</h6><p>C : congés, F : férié, M : mission, R : RTT, S : Sans solde</p></div>

</div>

