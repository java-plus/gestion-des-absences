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

<title>GDA - Vues synthetiques par département par jour et par
	collaborateur</title>

</head>

<body>


	


	<%-- include du header manager --%>
	<%@ include file="jsp/manager/menu.jsp"%>
	<%-- ------------------------- --%>



	


	<%-- include du contenu --%>
	<%@ include file="jsp/manager/vues-departement.jsp"%>
	<%-- ------------------------- --%>

	<%-- chargement des js de JQuery et Bootsrap et feather --%>
	<%@ include file="jsp/global/load.jsp"%>
	<%-- ------------------------- --%>

	<script>
	
		$(document).ready(function() {
			
			<%-- DEBUT ---  script pour gerer les requetes envoyées par le filtre --%>			
	
			$( "#form-filtres" ).on( "submit", function(event) {
				  event.preventDefault();
				  var dataForm  = $(this).serialize();
				  
				  $.ajax({
	 					method : "POST",
	 					url : "afficherVueDepart?vue=collab",
	 					data : dataForm,
	 					dataType : "json"
	 					
	 				}).done(function( result, status ) {
	 					
	 					
	 					console.log("departeemnt : " + result[0].departement);
	 					console.log("mois = " + result[0].mois);
	 					console.log("annee = " + result[0].annee);
	 				  }).fail(function(result, status) {
	 					  
	 					 console.log("fail : " + result + " / "+ status);
	 					 
					  });
				  
				  
				  
	 				  
			});
				
			
			<%-- FIN ---  script pour gerer les requetes envoyées par le filtre --%>
	
	
		})
	</script>

</body>



</html>