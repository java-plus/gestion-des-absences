<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
   <h1>GDA</h1> 
   <h3>Gestion des absences</h3>   
   
<form method="post" action="http://localhost:8080/gda/controller/jFerieRttEmp">
  <div class="form-group">
    <label for="exampleInputDate">Date</label>
    <input type="text" class="form-control" id="date" name="selectedDate" placeholder="Enter date">
  </div>
  <div class="form-group">
  <label>Type</label>
    <select id="type" name="selectedType" for="exampleInputPassword1">
    <option>jour ferie</option>
    <option>RTT employeur</option>
    </select>
  </div>
  <div class="form-group">
    <label for="exampleInputCom">Commentaire</label>
    <input type="text" class="form-control" id="commentaire" name="selectedMotif" placeholder="Enter comment">
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
</form>
    
    
</body>
</html>