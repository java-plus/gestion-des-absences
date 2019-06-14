package fr.gda.service;

import java.util.List;

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.model.AbsenceParPersonne;

/**
 * Classe de traiement des demandes (dit traitement de nuit)
 * 
 * @author Patrice
 *
 */
public class TraitementDemandes {

	public TraitementDemandes() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// Création de Dao pour les demandes de congés au statut Initiale
		AbsenceParPersonneDao absenceParPersonneDao = new AbsenceParPersonneDao();
		// Récupération des demandes dans l'ordre des dates des demandes
		List<AbsenceParPersonne> absenceAuStatutInitiale = absenceParPersonneDao.lireDemandesEnStatutInitiale();

		// * Pour les congés payés, congés sans solde et RTT employés:

		// * S'il reste assez de jours pour le type d'absence demandé, la
		// demande passe au statut EN_ATTENTE_VALIDATION et le compteur du
		// collaborateur est décrémenté du nb de jours correspondant. Un mail de
		// demande de validation est envoyé au manager de l'employé.
		// * S'il ne reste pas assez de jours alors la demande est rejetée: elle
		// passe au statut REJETEE.
		// * Pour les demandes de type RTT employeur alors la demande passe au
		// statut VALIDEE et l'ensemble des employés se voient retirer un jour
		// de RTT.

		for (AbsenceParPersonne abs : absenceAuStatutInitiale)
			System.out.println(abs.getStatut());

	}

}
