<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>



<%
	String[] listJours = (String[]) request.getAttribute("listJours");
	int decalage = (int) request.getAttribute("decalage");
	Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
%>


<div class="container my-5">

        <h1>Planning des absences</h1>



	<%-- include des filtres --%>
<%-- 	<%@ include file="vues-filtres.jsp"%> --%>
	<%-- -------------------- --%>


        <div class="row p-2 bg-primary text-center">
            <div class="col-sm-2">Lundi</div>
            <div class="col-sm-2">Mardi</div>
            <div class="col-sm-2">Mercredi</div>
            <div class="col-sm-2">Jeudi</div>
            <div class="col-sm-2">vendredi</div>
            <div class="col-sm-1">Samedi</div>
            <div class="col-sm-1">Dimanche</div>
        </div>


 	<%-- construction des jours --%>
	<div class="row py-3 ">
		
		<div class="col-sm-12 d-flex flex-row flex-wrap p-0">
			<%
		
			
			
			for (int i = 0; i < decalage; i++) {
				%>
				<div class="col-sm-2 text-center py-2 "></div>
				<%
			}
			
			
			
			for (int i = 0; i < listJours.length; i++) {
				
				
				String valeur = listJours[i];
				String congeEtat = "";
				String maClasse = "col-sm-2 py-2 text-right ";
				
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
						maClasse = "col-sm-1 text-right py-2 ";
						maClasse += classeWeekEnd;
						valeur = "W";
					}
				}
				

				%>
				<div class="<%= maClasse %>"><%= valeur %> | <%= i+1  %></div>
				<%
			
			}
			
			%>
			
		</div>
		
	</div>



</div>

<div class="container my-5">
	<h6>Soldes des compteurs :</h6>
	<div>Congés payés :<%=utilisateur.getCongeRestant()%></div>
	<div>RTT : <%=utilisateur.getRttRestant()%></div>
</div>

<div class="container my-5">
	<h6>légende :</h6>
	<p>C : congés, F : férié, M : mission, R : RTT, S : Sans solde</p>
</div>