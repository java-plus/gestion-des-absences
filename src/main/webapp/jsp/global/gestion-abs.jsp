<%@	page
	import="java.util.List, fr.gda.controller.*, fr.gda.filter.*, fr.gda.model.*, fr.gda.dao.*"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>



<div class="container my-5">

	<!-- Titres du tableau -->

	<h1>Gestion des absences</h1>

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
			Statut
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
		
		Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
	
		if (listeAbsences != null) {
			for (AbsenceParPersonne liste : listeAbsences) {
				if (liste.getIdAbsence() == 6 || liste.getIdAbsence() == 5) {
					continue;
				}
	%>

	<!-- 	Remplissage des cases du tableau avec les infos de la boucle -->

	<div class="row p-2 ligneSuppr<%=liste.getId()%>">
		<div class="col-sm-3"><%=liste.getDateDebut()%></div>
		<div class="col-sm-3"><%=liste.getDateFin()%></div>
		<div class="col-sm-3"><%=liste.typeConge(liste.getIdAbsence())%></div>
		<div class="col-sm-2"><%=liste.getStatut()%></div>
		<div class="col-sm-1 ">

			<!-- 		Affichage des boutons modifier / supprimer en fonction du statut -->

			<%
				if (liste.getStatut().equals("INITIALE")) {
			%>
		<div class="btn-group" role="group">
			<a href="updateConges?update=<%=liste.getId()%>" ><button type="button" class="btn btn-dark btn-modif" id="btn-modif">
				<i data-feather="edit-2">modifier</i>
				</button></a>

			<button type="button" class="btn btn-dark btn-supp"
				data-toggle="modal" data-target="#modal" id="<%=liste.getId()%>">
				<i data-feather="trash">supprimer</i>
			</button>
		</div>
			<%
				} else if (liste.getStatut().equals("EN_ATTENTE_VALIDATION") || liste.getStatut().equals("VALIDEE")) {
			%>
			<button type="button" class="btn btn-dark btn-supp"
				data-toggle="modal" data-target="#modal" id="<%=liste.getId()%>">
				<i data-feather="trash">supprimer</i>
			</button>
			<%
				} else if(liste.getStatut().equals("REJETEE")){
					%>	
				<div class="btn-group" role="group">
			<a href="updateConges?update=<%=liste.getId()%>"> <button type="button" class="btn btn-dark btn-modif" id="btn-modif">
				<i data-feather="edit-2">modifier</i>
				</button></a>
				</div>
				<%
				}
				%>

		</div>
	</div>

	<%
		}
		}
	%>

</div>

<!-- Affichage des congés restants -->

<div class="container">
	Demander une absence
	<a href="ajoutConges?ajout=add">
	<button class="btn btn-lg btn-outline-primary" type="button">créer</button>
	</a>
</div>

<div class="container my-5">
	<div>Soldes des compteurs :</div>
	<div>
		Congés payés :
		<%=utilisateur.getCongeRestant()%></div>
	<div>
		RTT :
		<%=utilisateur.getRttRestant()%></div>
</div>

<!-- 		Modal -->

<div class="modal" tabindex="-1" role="dialog" id="modal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title text-dark">Confirmation suppression</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body text-muted">
				<p>Confirmez-vous la suppression de ces congés?</p>
			</div>
			<form method="DELETE" action="afficherConges" class="formModal"
				id="formModal">
				<div class="modal-footer">
					<button type="submit" class="btn btn-success" data-dismiss="modal">Confirmer</button>
				</div>
			</form>

		</div>
	</div>
</div>

<!-- 			fin modal -->

