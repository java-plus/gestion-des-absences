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

	public TraitementDemandes() {
		// TODO Auto-generated constructor stub
	}

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
			// --Long nombreJoursDemandes =
			// ChronoUnit.DAYS.between(abs.getDateDebut(), abs.getDateFin()) +
			// 1;
			// System.out.println(nombreJoursDemandes);

			// Pour les congés payés, congés sans solde et RTT employés:
			if (abs.getTypeAbsence().equals("RTT") || abs.getTypeAbsence().equals("congé payé")
					|| abs.getTypeAbsence().equals("congé sans solde")) {

				if ((abs.getTypeAbsence().equals("RTT") || (abs.getTypeAbsence().equals("congé payé")))) {
					Integer nombreJoursRestants = utilisateurDao.recupererNombreJoursParTypeConge(abs.getIdUtil(),
							abs.getTypeAbsence());
					if (nombreJoursDemandes <= nombreJoursRestants) {
						// * S'il reste assez de jours pour le type d'absence
						// demandé, la demande passe au statut
						// EN_ATTENTE_VALIDATION et le
						abs.setStatut("EN_ATTENTE_VALIDATION");
						absenceParPersonneDao.modifierStatut(abs.getId(), "EN_ATTENTE_VALIDATION");
						utilisateurDao.retirerJoursParTypeConge(abs.getIdUtil(), abs.getTypeAbsence(),
								nombreJoursRestants - nombreJoursDemandes);
						// absenceParPersonneDao.lireDemandesPourMailManager(abs.getId(),
						// nombreJoursDemandes);
						try {
							UtilMessagerie.EnvoyerMailManager(abs.getId(), abs.getIdUtil(), abs.getTypeAbsence(),
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
					// Utils.EnvoyerMailManager(abs.getIdUtil(),
					// abs.getIdAbsence(), nombreJoursDemandes);
					try {
						UtilMessagerie.EnvoyerMailManager(abs.getId(), abs.getIdUtil(), abs.getTypeAbsence(),
								nombreJoursDemandes);
					} catch (Exception e) {

						throw new TechnicalException("Le message n'a pas été envoyé", e);
					}

				}

			} else if (abs.getTypeAbsence().equals("RTT_employeur")) {
				// * Pour les demandes de type RTT employeur alors la demande
				// passe au statut VALIDEE et l'ensemble des employés se voient
				// retirer
				// un jour
				// de RTT.
				abs.setStatut("VALIDEE");
				absenceParPersonneDao.modifierStatut(abs.getId(), "VALIDEE");
				utilisateurDao.retirerJoursParTypeConge(abs.getIdUtil(), "RTT", 1l);

			}

		}
	}
}