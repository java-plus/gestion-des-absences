* [ ] En tant que collaborateur, quel que soit le profil, j'ai accès à la liste des jours fériés et RTT employeur. 

* [ ] Le tableau des jours fériés et RTT employeurs affichent :
  * la liste des jours feriés pour l'année sélectionnée
  * la liste des RTT employeurs pour l'ennée sélectionnée

![](https://github.com/DiginamicFormation/ressources-atelier/raw/master/gestion-des-absences/Jours.feries.png)

* Une RTT employeur est une demande d'absence qui va impacter l'ensemble des employés de tous les départements. 
  * Une RTT employeur est considérée comme une demande d'absence
  * Une RTT employeur ne dure qu'une seule journée
  * Le worflow d'une RTT employeur est simplifié: INITIALE, VALIDEE
  * le batch de nuit est en charge du changement de statut de cette demande.
  * il n'est pas possible de modifier ou supprimer une RTT employeur validée.

Le tableau affiche les informations suivantes:
* date 
* type (RTT employeur, Ferié)
* Jour
* commentaire

A partir de cette liste l'administrateur uniquement peut effectuer les actions suivantes:
* ajouter un jour ferié ou une RTT employeur
* modifier un jour ferié ou une RTT employeur
* supprimer un jour ferié ou une RTT employeur
