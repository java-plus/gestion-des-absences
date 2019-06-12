En tant qu'employé j'ai la possibilité de faire une demande d'absence.

* [ ] Une demande d'absence est constituée des informations suivantes:
  * date de début
  * date de fin
  * type (congé payé, RTT, congé sans solde)
  * Motif (zone de texte)
  * un statut (INITIALE, EN_ATTENTE_VALIDATION, VALIDEE, REJETEE)


* [ ] Règles métier:
  * il n'est pas possible de faire une demande de type "mission". Ces dernières s'effectuent dans l'application de gestion des missions.
  * les dates sont saisies au format DD/MM/YYYY
  * une demande d'absence débute au plus tôt à partir de J+1
  * la date de fin est supérieure ou égale à la date de début
  * le motif n'est obligatoire que si le type de demande est "congés sans solde"
  * il est interdit de faire une demande qui chevauche une demande existante, sauf si cette dernière est rejetée.
  * une fois créée, ma demande est au statut INITIALE
  * une demande d'absence ne modifie pas le solde des compteurs de congés. Cette opération est effectuée par le traitement de nuit.

* [ ] Je peux effectuer les actions suivantes:
  * valider la demande d'absence à condition que toutes les règles métier soient validées
  * annuler la demande d'absence auquel cas je reviens à la visualisation des demandes

![](https://github.com/DiginamicFormation/ressources-atelier/raw/master/gestion-des-absences/Demande.absence.png)

