package fr.gda.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.dao.UtilisateurDao;
import fr.gda.model.AbsenceParPersonne;
import fr.gda.model.Utilisateur;

public class FormatageListAffichageVues {

	public String[] constuireArrayNoms(Integer idDepartement) {
		UtilisateurDao utilisateurDao = new UtilisateurDao();
		List<Utilisateur> utilisateurParDepartement = utilisateurDao.getUtilisateursParDepartement(idDepartement);
		String[] listNoms = new String[utilisateurParDepartement.size()];

		for (int i = 0; i < utilisateurParDepartement.size(); i++) {
			// enregistrement du nom

			listNoms[i] = utilisateurParDepartement.get(i).getNom();
		}

		return listNoms;

	}

	/**
	 * 
	 * méthode qui retourne pour tous les employés d'un département
	 * 
	 * @param numeroMois
	 * @param annee
	 * @param idDepartement
	 * @return
	 */
	public String[][] construireArrayJoursMoisAll(Integer numeroMois, Integer annee, Integer idDepartement) {

		AbsenceParPersonneDao absenceParPersonneDao = new AbsenceParPersonneDao();
		List<AbsenceParPersonne> absenceDepartementMoisAnnee = absenceParPersonneDao.afficherAbsencesParDepartementMoisAnnee(numeroMois, annee, idDepartement);

		UtilisateurDao utilisateurDao = new UtilisateurDao();
		List<Utilisateur> utilisateurParDepartement = utilisateurDao.getUtilisateursParDepartement(idDepartement);

		// Récupération du nombre de jours du mois
		RecupererNbreJoursDuMois joursMois = new RecupererNbreJoursDuMois();
		int maxDay = joursMois.getJours(annee, numeroMois);

		String[][] listjourMois = new String[utilisateurParDepartement.size()][maxDay];

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

		// Pour chaque utilisateur du département
		for (int i = 0; i < utilisateurParDepartement.size(); i++) {

			// Pour chaque colonne, si un congé est posé, le mettre
			for (int k = 1; k <= maxDay; k++) {

				// Si c'est un Weekend, l'identifier
				LocalDate localDate = LocalDate.parse("" + k + "/" + numeroMois + "/" + annee, formatter);

				if ((localDate.getDayOfWeek() == localDate.getDayOfWeek().SATURDAY) || (localDate.getDayOfWeek() == localDate.getDayOfWeek().SUNDAY)) {
					// Création de style pour les Weekends
					listjourMois[i][k - 1] = "W";

				} else {

					for (int m = 0; m < absenceDepartementMoisAnnee.size(); m++) {

						// Si c'est le même utilisateur
						if (absenceDepartementMoisAnnee.get(m).getIdUtil() == utilisateurParDepartement.get(i).getId()) {

							// Si c'est sous les mêmes dates de mois
							if (absenceDepartementMoisAnnee.get(m).getDateDebut().getDayOfMonth() <= k & absenceDepartementMoisAnnee.get(m).getDateFin().getDayOfMonth() >= k) {
								// Changement de couleur si besoin
								if (absenceDepartementMoisAnnee.get(m).getStatut().equals("REJETEE")) {
									String monString = absenceParPersonneDao.RecupererTypeConges(absenceDepartementMoisAnnee.get(m).getIdAbsence()).toUpperCase().substring(0, 1);
									monString += "-REJETEE";
									listjourMois[i][k - 1] = monString;
								} else if (absenceDepartementMoisAnnee.get(m).getStatut().equals("VALIDEE")) {
									String monString = absenceParPersonneDao.RecupererTypeConges(absenceDepartementMoisAnnee.get(m).getIdAbsence()).toUpperCase().substring(0, 1);
									monString += "-VALIDEE";
									listjourMois[i][k - 1] = monString;
								} else if (absenceDepartementMoisAnnee.get(m).getStatut().equals("EN_ATTENTE_VALIDATION")) {
									String monString = absenceParPersonneDao.RecupererTypeConges(absenceDepartementMoisAnnee.get(m).getIdAbsence()).toUpperCase().substring(0, 1);
									monString += "-EN_ATTENTE_VALIDATION";
									listjourMois[i][k - 1] = monString;
								}
							} else {
								listjourMois[i][k - 1] = "N";// on donne "N" (comme "NORMAL" comme valeur pour indiquer qu'il n'y a pas de congé ce
								// jour
							}
						}
					}
				}
			}
		}

		// si l utilisateur n'est pas dans la liste des absences du departement car il n'a aucun congé,
		// alors on indique qu'il est présent
		for (int i = 0; i < utilisateurParDepartement.size(); i++) {
			int compteur = 0;

			for (int j = 0; j < absenceDepartementMoisAnnee.size(); j++) {
				if (absenceDepartementMoisAnnee.get(j).getIdUtil() == utilisateurParDepartement.get(i).getId()) {
					break;
				} else {
					compteur++;
				}
			}
			if (compteur == absenceDepartementMoisAnnee.size()) {
				for (int k = 1; k <= maxDay; k++) {
					if (listjourMois[i][k - 1] != "W") {
						listjourMois[i][k - 1] = "N";
					}
				}
			}
		}
		return listjourMois;
	}

	/**
	 * méthode qui retourne les jours pour un employé
	 * 
	 * @param numeroMois
	 * @param annee
	 * @return
	 */
	public String[] construireArrayJoursMois(Integer numeroMois, Integer annee, int utilisateurId) {

		AbsenceParPersonneDao absenceParPersonneDao = new AbsenceParPersonneDao();
		List<AbsenceParPersonne> listAbsenceMoisAnnee = absenceParPersonneDao.afficherAbsencesPersonne(utilisateurId);

		// Récupération du nombre de jours du mois
		RecupererNbreJoursDuMois joursMois = new RecupererNbreJoursDuMois();
		int maxDay = joursMois.getJours(annee, numeroMois);

		String[] listjourMois = new String[maxDay];

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

		// Pour chaque colonne, si un congé est posé, le mettre
		for (int k = 1; k <= maxDay; k++) {

			// Si c'est un Weekend, l'identifier
			LocalDate localDate = LocalDate.parse("" + k + "/" + numeroMois + "/" + annee, formatter);

			if ((localDate.getDayOfWeek() == localDate.getDayOfWeek().SATURDAY) || (localDate.getDayOfWeek() == localDate.getDayOfWeek().SUNDAY)) {
				// Création de style pour les Weekends
				listjourMois[k - 1] = "W";

			} else {

				for (int m = 0; m < listAbsenceMoisAnnee.size(); m++) {

					// Si c'est sous les mêmes dates de mois
					if (listAbsenceMoisAnnee.get(m).getDateDebut().getDayOfMonth() <= k & listAbsenceMoisAnnee.get(m).getDateFin().getDayOfMonth() >= k) {
						// Changement de couleur si besoin
						if (listAbsenceMoisAnnee.get(m).getStatut().equals("REJETEE")) {
							String monString = absenceParPersonneDao.RecupererTypeConges(listAbsenceMoisAnnee.get(m).getIdAbsence()).toUpperCase().substring(0, 1);
							monString += "-REJETEE";
							listjourMois[k - 1] = monString;

						} else if (listAbsenceMoisAnnee.get(m).getStatut().equals("VALIDEE")) {
							String monString = absenceParPersonneDao.RecupererTypeConges(listAbsenceMoisAnnee.get(m).getIdAbsence()).toUpperCase().substring(0, 1);
							monString += "-VALIDEE";
							listjourMois[k - 1] = monString;

						} else if (listAbsenceMoisAnnee.get(m).getStatut().equals("EN_ATTENTE_VALIDATION")) {
							String monString = absenceParPersonneDao.RecupererTypeConges(listAbsenceMoisAnnee.get(m).getIdAbsence()).toUpperCase().substring(0, 1);
							monString += "-EN_ATTENTE_VALIDATION";
							listjourMois[k - 1] = monString;

						}

						break;
					} else {
						listjourMois[k - 1] = "N";// on donne "N" (comme "NORMAL" comme valeur pour indiquer qu'il n'y a pas de congé ce

					}

				}
			}
		}

		return listjourMois;
	}

}
