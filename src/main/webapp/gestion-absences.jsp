<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>


<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <link href="https://fonts.googleapis.com/css?family=Poppins:400,700|Roboto:400,700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="../css/font.css">
    <link rel="stylesheet" href="../css/global.css">

    <title>GDA - Gestion des absences</title>

</head>

<body>



	<%-- include du header manager 
		en fonction du profil de l'utilisateur on charge le menu correspondant
		--%>



	<% 
		if (((String)session.getAttribute("profil")).equals("manager")) { 
		%>

	<%@ include file="jsp/manager/menu.jsp"%>
	<% 
		} else {
		%>
	<%@ include file="jsp/employe/menu.jsp"%>
	<% } %>



	<%  String delete = (String)session.getAttribute("delete");
	
		if (delete != null) {
			
			%>
			
			
			<div><%= delete %></div>
			
			
			<% 
			
		}
	
	%>


	<%-- include du contenu --%>
	<%@ include file="jsp/global/gestion-abs.jsp"%>

	<%-- chargement des js de JQuery et Bootsrap et feather --%>
	<%@ include file="jsp/global/load.jsp"%>


</body>

</html>

<script type="text/javascript">

$(document).ready(function(){
	

// 	$(".btn-supp").click(function() {
		
// 		monBouton = "afficherConges?suppr=" + this.id; 
// 		console.log(monBouton); 
// 		$('.formModal').attr('action', monBouton );
// 	});

	$(".btn-supp").click(function() {
		
		monBouton = "afficherConges?suppr=" + this.id; 
		console.log(monBouton); 
		$('.btn-success').attr('id', monBouton );
	});

	
	$(".btn-success").click(function() {
		

		$.ajax({
		    url: this.id,
		    type: 'DELETE',
		    success: function(result) {
		        console.log(result);
		        $( ".row" ).remove();
		    }
		});
		
		/* 
		console.log(this.id); 
		$(location).attr('href', this.id); */
		
	});
	
});

</script>

<body>