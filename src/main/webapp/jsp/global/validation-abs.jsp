<%@	page
	import="java.util.List, fr.gda.controller.*, fr.gda.filter.*, fr.gda.model.*, fr.gda.dao.*"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>



<div class="container my-5">

	<!-- Titres du tableau -->

	<h1>Validation des absences</h1>

	<div class="row p-2 bg-primary">
		<div class="col-sm-3 ">
			Date de début
<!-- 			<div> -->
<!-- 								</i><i data-feather="chevron-down"></i> -->
<!-- 			</div> -->
		</div>
		<div class="col-sm-3">
			Date de fin
<!-- 			<div> -->
<!-- 								<i data-feather="chevron-up"></i><i data-feather="chevron-down"></i> -->
								
<!-- 			</div> -->
		</div>
		<div class="col-sm-3">
			Type
		</div>
		<div class="col-sm-2">
			Nom
<!-- 			<div> -->
<!-- 								<i data-feather="chevron-up"></i><i data-feather="chevron-down"></i> -->
<!-- 			</div> -->
		</div>
		<div class="col-sm-1">
			Actions
		</div>
	</div>

	<!-- contenu du tableau -->

	<!-- 	boucle qui retourne les résultats pour le tableau -->

	<%
		List<AbsenceParPersonne> listeAbsences = (List<AbsenceParPersonne>) request.getAttribute("afficherConge");
	
		List<Utilisateur> groupeUtilisateurs = (List<Utilisateur>) request.getAttribute("groupeUtilisateurs");
		
		Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
	
		if (listeAbsences != null) {
			for (AbsenceParPersonne liste : listeAbsences) {
				if (liste.getIdAbsence() == 5 || liste.getIdAbsence() == 6) {
					continue;
				}
	%>

	<!-- 	Remplissage des cases du tableau avec les infos de la boucle -->

	<div class="row p-2 ligneSuppr<%=liste.getId()%>">
		<div class="col-sm-3"><%=liste.afficherDate(liste.getDateDebut())%></div>
		<div class="col-sm-3"><%=liste.afficherDate(liste.getDateFin())%></div>
		<div class="col-sm-3"><%=liste.typeConge(liste.getIdAbsence())%></div>
		<% for (Utilisateur util : groupeUtilisateurs) {
			if (liste.getIdUtil() == util.getId()) {
				%>
				<div class="col-sm-2"><%=util.getPrenom() + " " + util.getNom()%></div>
				<%
			}
		}
		%>
		
		<div class="col-sm-1 ">

			<!-- 		Affichage des boutons valider / rejeter -->

		<div class="btn-group" role="group">
			<a href="validerDemande?demande=<%=liste.getId()%>&typeAbsence=<%=liste.getIdAbsence()%>" <button type="button" class="btn btn-dark btn-modif" id="btn-modif">
				<i data-feather="check">valider</i>
				</button></a>

			<a href="rejeterDemande?demande=<%=liste.getId()%>&typeAbsence=<%=liste.getIdAbsence()%>" <button type="button" class="btn btn-dark btn-modif" id="btn-modif">
				<i data-feather="x">rejeter</i>
				</button></a>		
		</div>



		</div>
	</div>

	<%
		}
		}
	%>

</div>

