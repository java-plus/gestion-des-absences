En tant qu'administrateur j'ai la possibilité de créer un jour férié ou une RTT employeur.

![](https://github.com/DiginamicFormation/ressources-atelier/raw/master/gestion-des-absences/Nouveau.jour.ferie.png)

Règles métier:
* les dates sont saisies au format DD/MM/YYYY
* un jour férié ne peut pas être saisi dans le passé
* le jour de la semaine (de lundi à dimanche) est calculé en fonction de la date sélectionnée
* le commentaire est obligatoire pour les jours feriés.
* il est interdit de saisir un jour férié à la même date qu'un autre jour férié
* il est autorisé de saisir un jour férié un samedi ou un dimanche
* il est interdit de saisir une RTT employeur un samedi ou un dimanche
* Si une RTT employeur est créée alors le système créé une demande d'abence au statut INITIALE. Cette demande sera traitée lors du passage du batch de nuit.

Je peux effectuer les actions suivantes:
* valider la demande d'absence à condition que toutes les règles métier soient validées
* annuler la demande d'absence auquel cas je reviens à la visualisation des demandes
