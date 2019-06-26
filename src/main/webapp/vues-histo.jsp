<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" import="java.util.List, java.util.ArrayList, fr.gda.model.*"%>
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

<title>GDA - Vues synthetiques par département par jour et par collaborateur</title>

</head>

<body>

	<%-- include du header manager --%>
	<%@ include file="jsp/manager/menu.jsp"%>
	<%-- ------------------------- --%>

	<%-- include du contenu --%>
	<%@ include file="jsp/manager/vues-histo.jsp"%>
	<%-- ------------------------- --%>

	<%-- chargement des js de JQuery et Bootsrap et feather --%>
	<%@ include file="jsp/global/load.jsp"%>
	<%-- ------------------------- --%>
	
	<%-- chargement de la librairie Chart.js --%>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.bundle.js" integrity="sha256-qSIshlknROr4J8GMHRlW3fGKrPki733tLq+qeMCR05Q=" crossorigin="anonymous"></script>


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
	
			
			
			var ctx = document.getElementById('myChart').getContext('2d');
			var myChart = new Chart(ctx, {
			    type: 'bar',
			    data: {
			        labels: [
			       
			        	
			        	
			        	],
			        datasets: [{
			            label: 'Synthèse par jour',
			            data: [12, 19, 3, 5, 2, 3],
			            backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)',
			                'rgba(255, 206, 86, 0.2)',
			                'rgba(75, 192, 192, 0.2)',
			                'rgba(153, 102, 255, 0.2)',
			                'rgba(255, 159, 64, 0.2)'
			            ],
			            borderColor: [
			                'rgba(255, 99, 132, 1)',
			                'rgba(54, 162, 235, 1)',
			                'rgba(255, 206, 86, 1)',
			                'rgba(75, 192, 192, 1)',
			                'rgba(153, 102, 255, 1)',
			                'rgba(255, 159, 64, 1)'
			            ],
			            borderWidth: 1
			        }]
			    },
			    options: {
			        scales: {
			            yAxes: [{
			                ticks: {
			                    beginAtZero: true
			                }
			            }]
			        }
			    }
			});
			
	
		})
	</script>
	



</body>

</html>