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

<title>GDA - Gestion des absences</title>

</head>

<body>



	<%-- include du header manager 
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






	<%-- include du contenu --%>
	<%@ include file="jsp/global/gestion-abs.jsp"%>

	<%-- chargement des js de JQuery et Bootsrap et feather --%>
	<%@ include file="jsp/global/load.jsp"%>



<script type="text/javascript">
$(document).ready(function(){
	
var id; 
	
 	$(".btn-supp").click(function() {
					
		url = "afficherConges?suppr=" + this.id; 
		id = this.id; 
		$('.btn-success').attr('id', url );
	});
 	
	$(".btn-success").click(function() {
		
 		$.ajax({
		    url: this.id,
		    type: 'DELETE',
		    success: function(result) {
		        console.log(result);
		        $( ".ligneSuppr" + id ).remove();
		    }
		});
 		
});		
	});

		

</script>

</body>

</html>

