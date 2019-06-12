En tant qu'employé j'ai la possibilité de modifier une demande d'absence.

* [ ] Règles métier:
  * il n'est pas possible de modifier une demande de type mission
  * On ne peut modifier que les demandes au statut INITIALE et REJETEE
  * les dates sont saisies au format DD/MM/YYYY
  * une demande d'absence ne peut pas débuter le jour même, ni dans le passé
  * la date de fin est supérieure ou égale à la date de début
  * le motif n'est obligatoire que si le type de demande est "congés sans solde"
  * il est interdit de faire une demande qui chevauche une autre demande
  * une fois modifiée la demande est au statut INITIALE

* [ ] Je peux effectuer les actions suivantes:
  * valider la modification d'absence à condition que toutes les règles métier soient validées
  * annuler la modification d'absence auquel cas je reviens à la visualisation des demandes
  
![](https://github.com/DiginamicFormation/ressources-atelier/raw/master/gestion-des-absences/Modification.absence.png)

