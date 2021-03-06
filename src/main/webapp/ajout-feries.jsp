<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>



<!DOCTYPE html>
<html lang="fr">

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<link
	href="https://fonts.googleapis.com/css?family=Poppins:400,700|Roboto:400,700&display=swap"
	rel="stylesheet">

<link rel="stylesheet" href="../css/font.css">
<link rel="stylesheet" href="../css/global.css">

<title>Nouveau jour ferié / RTT employeur</title>

</head>



<body>



	<%-- include du header manager ou employe
		en fonction du profil de l'utilisateur on charge le menu correspondant
		--%>

	<%
		if (((String) session.getAttribute("profil")).equals("manager")) {
	%>
	<%@ include file="jsp/manager/menu.jsp"%>
	<%
		} else {
	%>
	<%@ include file="jsp/employe/menu.jsp"%>
	<%
		}
	%>

	<%-- si t'utilisateur est Admin, alors on affiche la liste d'action : ajouter, modifier, supprimer --%>
	<%
		if (((Boolean) session.getAttribute("isAdmin")) == true) {
	%>
	<%@ include file="jsp/admin/ajout-feries.jsp"%>
	<%
		} else {
	%>
	<%-- include du contenu --%>
	<%@ include file="jsp/global/jours-feries.jsp"%>
	<%
		}
	%>


	<%-- chargement des js de JQuery et Bootsrap et feather --%>
	<%@ include file="jsp/global/load.jsp"%>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							let texteMotif = '';
							let texteDateDebut = '';
							let dateDuJour1 = new Date();
							dateDuJour1.setDate(dateDuJour1.getDate() + 1);
							dateDuJour1.setHours(0);
							dateDuJour1.setMinutes(0);
							$('#texteMotif').hide();
							$('#texteDateDebut').hide();

							$('#btnValider')
									.click(
											function(event) {

												if (($(
														'#typeJour option:selected')
														.val() == 6)
														&& ($('#commentaire')
																.val() == '')) {
													event.preventDefault();
													$('#texteMotif').show();
												}

												var dateDebutSaisie = new Date(
														$('#date').val());
												if (dateDebutSaisie < dateDuJour1) {
													event.preventDefault();
													$('#texteDateDebut').show();
												}

											});
						});
	</script>

</body>

</html>