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
<link rel="stylesheet" href="css/font.css">
<link rel="stylesheet" href="css/global.css">

<title>GDA - Vues synthetiques</title>

</head>

<body>

	<%-- include du header manager --%>
	<%@ include file="html/manager/menu.html"%>

	<div class="container my-5">

        <h1>Vues synthétiques</h1>

        <div class="row p-2 bg-primary">
            <div class="col-sm-9 ">Liste des rapports</div>
            <div class="col-sm-3">Action</div>
        </div>

        <div class="row p-2">
            <div class="col-sm-9">Vue par département par jour et par collaborateur</div>
            <div class="col-sm-3">
             	<button class="btn btn-sm btn-none" type="button"><i data-feather="eye">voir</i></button>  
            </div>
        </div>
        <div class="row p-2">
            <div class="col-sm-9">Histogramme par département et par jour</div>
            <div class="col-sm-3">
           <button class="btn btn-sm btn-none" type="button"><i data-feather="eye">voir</i></button>  
            </div>
        </div>



    </div>

	<%-- chargement des js de JQuery et Bootsrap et feather --%>
	<%@ include file="html/global/load.html"%>


</body>

</html>