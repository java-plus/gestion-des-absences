<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>



<div class="row mt-5 mb-3"> 

<div class="input-group mb-3 col-sm-4">

  <div class="input-group-prepend">
    <label class="input-group-text" for="inputGroupSelect01">Département</label>
  </div>
  
  <%--A Faire : utiliser méthode Java pour récuperer les années entrées en base afin de proposer un choix d'année cohérent --%>
  <select class="custom-select" id="inputGroupSelect01">
    
    <option selected value="2019">DEV / DVI / Java 1</option>
    
  </select>
  
</div>

<div class="input-group mb-3 col-sm-2">

  <div class="input-group-prepend">
    <label class="input-group-text" for="inputGroupSelect01">Mois</label>
  </div>
  
  <%--A Faire : utiliser méthode Java pour récuperer les mois entrées en base afin de proposer un choix d'année cohérent --%>
  <select class="custom-select" id="inputGroupSelect01">
    <option selected value="Janvier">Janvier</option>
    <option value="Février">Février</option>
    <option value="Mars">Mars</option>
    <option value="Avril">Avril</option>
    <option value="Mai">Mai</option>
    <option value="Juin">Juin</option>
    <option value="Juillet">Juillet</option>
    <option value="Août">Août</option>
    <option value="Septembre">Septembre</option>
    <option value="Octobre">Octobre</option>
    <option value="Novembre">Novembre</option>
    <option value="Décembre">Décembre</option>
  </select>
  
</div>

<div class="input-group mb-3 col-sm-2">

  <div class="input-group-prepend">
    <label class="input-group-text" for="inputGroupSelect01">Année</label>
  </div>
  
  <%--A Faire : utiliser méthode Java pour récuperer les années entrées en base afin de proposer un choix d'année cohérent --%>
  <select class="custom-select" id="inputGroupSelect01">
    <option selected value="2019">2019</option>
    <option value="2018">2018</option>
    <option value="2017">2017</option>
    <option value="2016">2016</option>
  </select>
  
</div>

<div class="col-sm-1"><button class="btn"><i data-feather="search"></i></button></div>

<div class="col-sm-1"><button class="btn btn-outline-light"><i data-feather="file"></i></button></div>


</div>