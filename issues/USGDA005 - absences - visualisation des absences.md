* [ ] En tant qu'employé je peux accéder à la liste de mes demandes d'absence afin de vérifier le solde de mes congés. 
  * Une absence correspond fonctionnellement à un congé (RTT, congé payé, congé sans solde) ou à une mission (formation, déplacement professionel, etc.). Dans cette fonctionnalité seront affichés indifféremment les congés et les missions. 

* [ ] Le tableau des absences affiche :
  * la liste des demandes d'absence de l'utilisateur
  * la liste des missions fournie par l'application "Gestion des Missions"
  
* [ ] Le solde des congés de l'utilisateur est affiché en dessous du tableau des absences:
  * Nombre de jours de congés payés restant
  * Nombre de jours de RTT restants

* [ ] Une demande d'absence est constituée des informations suivantes:
  * date de début
  * date de fin
  * type (congé payé, RTT, congé sans solde, mission)
  * Motif
  * un statut (INITIALE, EN_ATTENTE_VALIDATION, VALIDEE, REJETEE)

* [ ] A partir de cette liste de demandes d'absences je peux effectuer les actions suivantes:
  * ajouter une demande d'absence
  * modifier une demande d'absence au statut INITIALE et qui n'est pas de type mission
  * supprimer une demande d'absence qui n'est pas de type mission
  * visualiser une demande de type mission. Il s'agit d'un lien vers l'application Gestion des missions.

* [ ] Workflow d'une demande d'absence:
  * INITIALE: une demande qui vient d'être créée est au statut INITIALE
  * EN_ATTENTE_VALIDATION: le traitement de nuit passent toutes les demandes du statut INITIALE au statut EN_ATTENTE_VALIDATION (cf. traitement de nuit pour le détail des tâches qu'il réalise).
  * VALIDEE: si le manager a validé la demande
  * REJETEE: si le manager a rejeté la demande

![](https://github.com/DiginamicFormation/ressources-atelier/raw/master/gestion-des-absences/Gestion.des.absences.png)


