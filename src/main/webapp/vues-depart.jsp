<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" import="java.util.Date, java.time.LocalDate, java.util.Calendar, java.text.SimpleDateFormat"%>

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
	
	
	
	<!-- Small modal -->
	
     
    
	<div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	    
	     <div class="modal-header">
        <h4 class="modal-title text-dark" id="mySmallModalLabel">Export Excel</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">×</span>
        </button>
      </div>
      
      <div class="modal-body text-dark">
       L'export excel est fait !
      </div>
      
      
	    
	    </div>
	  </div>
	</div>

	<script>
	
		$(document).ready(function() {
			
			<%-- DEBUT ---  script pour gerer les requetes envoyées par le filtre --%>			
	
			$( "#export-excel" ).on( "click", function(event) {
				  event.preventDefault();
				  var dataForm  = $("#form-filtres").serialize();
				  
				  
				  console.log(dataForm);
				  
				  $.ajax({
	 					method : "POST",
	 					url : "afficherVueDepart?vue=collab",
	 					data : dataForm,
	 					dataType : "text"
	 					
	 				}).done(function( result, status ) {
	 					$('.bd-example-modal-sm').modal('toggle');
	 					
	 					console.log(result + " / " + status);
	 				
	 				  }).fail(function(result, status) {
	 					  
	 					 console.log("fail : " + result + " / "+ status);
	 					 
					  });
	  	  
	 				  
			});
			
			$('#trier').on( "click", function(event) {
				if($('#list-e').hasClass('flex-column-reverse')){
					$('#list-e').removeClass( "flex-column-reverse" ).addClass( "flex-column" );
					//$(".svg.feather.feather-chevron-up").replaceWith(feather.icons.chevron-down.toSvg());
				} else {
					$('#list-e').removeClass( "flex-column" ).addClass( "flex-column-reverse" );
				//	$(".svg.feather.feather-chevron-up").replaceWith(feather.icons.chevron-down.toSvg());
				}
				

			});
			
				
			
			<%-- FIN ---  script pour gerer les requetes envoyées par le filtre --%>
	
	
		})
	</script>

</body>



</html>