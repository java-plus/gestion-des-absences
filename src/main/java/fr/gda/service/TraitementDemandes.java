package fr.gda.service;

import java.util.List;

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.dao.UtilisateurDao;
import fr.gda.exception.TechnicalException;
import fr.gda.model.AbsenceParPersonne;

/**
 * Classe de traiement des demandes (dit traitement de nuit)
 * 
 * @author Patrice
 *
 */
public class TraitementDemandes {

	public static void main(String[] args) {
		// Création de Dao pour les demandes de congés au statut Initiale
		AbsenceParPersonneDao absenceParPersonneDao = new AbsenceParPersonneDao();
		// Récupération des demandes dans l'ordre des dates des demandes
		List<AbsenceParPersonne> absenceAuStatutInitiale = absenceParPersonneDao.lireDemandesEnStatutInitiale();

		// Création de Dao pour utilisateur
		UtilisateurDao utilisateurDao = new UtilisateurDao();

		// Pour chaque demande de congé au statut Initiale
		for (AbsenceParPersonne abs : absenceAuStatutInitiale) {
			// Nombre de jours = Ecart entre les dates en jours (dates incluses)
			Long nombreJoursDemandes = abs.getNombreJoursDemandesSansWE();

			// Pour les congés payés, congés sans solde et RTT employés:
			if (abs.getIdAbsence() == 1 || abs.getIdAbsence() == 2 || abs.getIdAbsence() == 3) {

				if ((abs.getIdAbsence() == 1 || (abs.getIdAbsence() == 2))) {
					Integer nombreJoursRestants = utilisateurDao.recupererNombreJoursParTypeConge(abs.getIdUtil(),
							abs.getIdAbsence());
					if (nombreJoursDemandes <= nombreJoursRestants) {
						// * S'il reste assez de jours pour le type d'absence
						// demandé, la demande passe au statut
						// EN_ATTENTE_VALIDATION et le
						abs.setStatut("EN_ATTENTE_VALIDATION");
						absenceParPersonneDao.modifierStatut(abs.getId(), "EN_ATTENTE_VALIDATION");
						utilisateurDao.ajouterRetirerJoursParTypeConge(abs.getIdUtil(), abs.getIdAbsence(),
								nombreJoursRestants - nombreJoursDemandes);

						try {
							UtilMessagerie.EnvoyerMailManager(abs.getId(), abs.getIdUtil(), abs.getIdAbsence(),
									nombreJoursDemandes);
						} catch (Exception e) {

							throw new TechnicalException("Le message n'a pas été envoyé", e);
						}
						// compteur
						// du
						// collaborateur est décrémenté du nb de jours
						// correspondant. Un
						// mail de
						// demande de validation est envoyé au manager de
						// l'employé.
					} else {
						// Pas assez de jours donc REJETEE
						abs.setStatut("REJETEE");
						absenceParPersonneDao.modifierStatut(abs.getId(), "REJETEE");
					}
				} else {
					// Congé sans solde
					abs.setStatut("EN_ATTENTE_VALIDATION");
					absenceParPersonneDao.modifierStatut(abs.getId(), "EN_ATTENTE_VALIDATION");
					try {
						UtilMessagerie.EnvoyerMailManager(abs.getId(), abs.getIdUtil(), abs.getIdAbsence(),
								nombreJoursDemandes);
					} catch (Exception e) {

						throw new TechnicalException("Le message n'a pas été envoyé", e);
					}

				}

			} else if (abs.getIdAbsence() == 5) {
				// * Pour les demandes de type RTT employeur alors la demande
				// passe au statut VALIDEE et l'ensemble des employés se voient
				// retirer
				// un jour
				// de RTT.
				abs.setStatut("VALIDEE");
				absenceParPersonneDao.modifierStatut(abs.getId(), "VALIDEE");
				Integer nombreJoursRestantsRTTPourEmployeur = utilisateurDao
						.recupererNombreJoursParTypeConge(abs.getIdUtil(), 1);
				utilisateurDao.ajouterRetirerJoursParTypeConge(abs.getIdUtil(), 1,
						(Integer) nombreJoursRestantsRTTPourEmployeur - 1l);

			}

		}
	}
}