package fr.gda.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.dao.UtilisateurDao;
import fr.gda.model.AbsenceParPersonne;
import fr.gda.model.Utilisateur;

/**
 * Classe d'utilitaire d'export Excel
 * 
 * @author Patrice
 *
 */

public class UtilExcel {

	/** fileName : String */
	private String fileName = "ExportAbsences";

	/** Méthode d'export Excel */
	public void exportExcel(Integer numeroMois, Integer annee, Integer idDepartement) {
		// Récupération des données à exporter
		// Id Département
		// Numéro de Mois
		// Année
		// Récupérer les RTT, les congés et les fériés
		AbsenceParPersonneDao absenceParPersonneDao = new AbsenceParPersonneDao();
		List<AbsenceParPersonne> absenceDepartementMoisAnnee = absenceParPersonneDao
				.afficherAbsencesParDepartementMoisAnnee(numeroMois, annee, idDepartement);

		// Création du workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Absences");

		// Récupération du nombre de jours du mois
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, numeroMois - 1);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// Année bisextile
		if (((annee % 4 == 0) && (annee % 100 != 0)) || (annee % 400 == 0)) {
			if (numeroMois == 2) {
				maxDay += 1;
			}
		}

		// Récupération de la liste des utilisateurs par département
		UtilisateurDao utilisateurDao = new UtilisateurDao();
		List<Utilisateur> utilisateurParDepartement = utilisateurDao.getUtilisateursParDepartement(idDepartement);

		// Création des styles pour changement de couleur de police
		CellStyle couleurRouge = workbook.createCellStyle();
		Font fontRouge = workbook.createFont();
		fontRouge.setColor(IndexedColors.RED.getIndex());
		couleurRouge.setFont(fontRouge);
		CellStyle couleurVerte = workbook.createCellStyle();
		Font fontVerte = workbook.createFont();
		fontVerte.setColor(IndexedColors.GREEN.getIndex());
		couleurVerte.setFont(fontVerte);
		CellStyle couleurGrise = workbook.createCellStyle();
		Font fontGrise = workbook.createFont();
		fontGrise.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
		couleurGrise.setFont(fontGrise);

		// Pour chaque utilisateur du département
		for (int i = 0; i < utilisateurParDepartement.size(); i++) {
			// Ecriture du nom
			Row rowUtilisateur = sheet.createRow(6 + i);
			Cell cellUtilisateur = rowUtilisateur.createCell(0);
			cellUtilisateur.setCellValue(
					utilisateurParDepartement.get(i).getPrenom() + " " + utilisateurParDepartement.get(i).getNom());
			// Pour chaque colonne, si un congé est posé, le mettre
			for (int k = 1; k <= maxDay; k++) {
				rowUtilisateur.createCell(k);
				Cell cellJour = rowUtilisateur.createCell(k);
				sheet.setColumnWidth(k, 1000);
				// Si c'est un Weekend, l'identifier
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
				LocalDate localDate = LocalDate.parse("" + k + "/" + numeroMois + "/" + annee, formatter);
				if ((localDate.getDayOfWeek() == localDate.getDayOfWeek().SATURDAY)
						|| (localDate.getDayOfWeek() == localDate.getDayOfWeek().SUNDAY)) {
					// Création de style pour les Weekends
					CellStyle fondGris = workbook.createCellStyle();
					fondGris.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
					fondGris.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					cellJour.setCellStyle(fondGris);
					// cellJour.setCellValue("WE");
				} else {
					for (int m = 0; m < absenceDepartementMoisAnnee.size(); m++) {
						// Si c'est le même utilisateur
						if (absenceDepartementMoisAnnee.get(m).getIdUtil() == utilisateurParDepartement.get(i)
								.getId()) {
							// Si c'est entre les dates de début et de fin
							LocalDate dateK = LocalDate.of(annee, numeroMois, k);
							if ((absenceDepartementMoisAnnee.get(m).getDateDebut().isBefore(dateK)
									&& absenceDepartementMoisAnnee.get(m).getDateFin().isAfter(dateK))
									|| absenceDepartementMoisAnnee.get(m).getDateDebut().isEqual(dateK)
									|| absenceDepartementMoisAnnee.get(m).getDateFin().isEqual(dateK)) {
								// Changement de couleur si besoin
								if (absenceDepartementMoisAnnee.get(m).getStatut().equals("REJETEE")) {
									cellJour.setCellStyle(couleurRouge);
								} else if (absenceDepartementMoisAnnee.get(m).getStatut().equals("VALIDEE")) {
									cellJour.setCellStyle(couleurVerte);
								} else if (absenceDepartementMoisAnnee.get(m).getStatut()
										.equals("EN_ATTENTE_VALIDATION")) {
									cellJour.setCellStyle(couleurGrise);
								}

								int absence = absenceDepartementMoisAnnee.get(m).getIdAbsence();

								// Insertion de cellule : cas particulier pour
								// le congé sans solde, sinon initiale du mot
								if (absenceDepartementMoisAnnee.get(m).getIdAbsence() == 3) {
									cellJour.setCellValue("S");
								} else
									cellJour.setCellValue(absenceDepartementMoisAnnee.get(m).typeConge(absence)
											.toUpperCase().substring(0, 1));

							}

						}
					}
				}
			}

		}

		// Autosize de première colonne (noms)
		sheet.autoSizeColumn(0);

		// Affichage de la légende
		Row rowLegendeInitiale = sheet.createRow(8 + utilisateurParDepartement.size());
		Cell cellLegendeInitiale = rowLegendeInitiale.createCell(1);
		cellLegendeInitiale.setCellValue("Initiale");
		Row rowLegendeEnAttente = sheet.createRow(9 + utilisateurParDepartement.size());
		Cell cellLegendeEnAttente = rowLegendeEnAttente.createCell(1);
		cellLegendeEnAttente.setCellStyle(couleurGrise);
		cellLegendeEnAttente.setCellValue("En attente de validation");
		Row rowLegendeValidee = sheet.createRow(10 + utilisateurParDepartement.size());
		Cell cellLegendeValidee = rowLegendeValidee.createCell(1);
		cellLegendeValidee.setCellStyle(couleurVerte);
		cellLegendeValidee.setCellValue("Validée");
		Row rowLegendeRejetee = sheet.createRow(11 + utilisateurParDepartement.size());
		Cell cellLegendeRejetee = rowLegendeRejetee.createCell(1);
		cellLegendeRejetee.setCellStyle(couleurRouge);
		cellLegendeRejetee.setCellValue("Rejetée");

		Row rowTypeCongeConge = sheet.createRow(13 + utilisateurParDepartement.size());
		Cell cellTypeCongeConge = rowTypeCongeConge.createCell(1);
		cellTypeCongeConge.setCellValue("C : Congé");
		Row rowTypeCongeFerie = sheet.createRow(14 + utilisateurParDepartement.size());
		Cell cellTypeCongeFerie = rowTypeCongeFerie.createCell(1);
		cellTypeCongeFerie.setCellValue("F : Férié");
		Row rowTypeCongeMission = sheet.createRow(15 + utilisateurParDepartement.size());
		Cell cellTypeCongeMission = rowTypeCongeMission.createCell(1);
		cellTypeCongeMission.setCellValue("M : Mission");
		Row rowTypeCongeRtt = sheet.createRow(16 + utilisateurParDepartement.size());
		Cell cellTypeCongeRTT = rowTypeCongeRtt.createCell(1);
		cellTypeCongeRTT.setCellValue("R : RTT");
		Row rowTypeCongeSansSolde = sheet.createRow(17 + utilisateurParDepartement.size());
		Cell cellTypeCongeSansSolde = rowTypeCongeSansSolde.createCell(1);
		cellTypeCongeSansSolde.setCellValue("S : Sans solde");

		// Affichage du titre
		Row rowTitre = sheet.createRow(1);
		Cell cellTitre = rowTitre.createCell(2);
		cellTitre.setCellValue("Vue par département, par jour et par collaborateur");

		// Affichage des paramètres
		String departement = utilisateurDao.recupererDepartement(idDepartement);
		Row rowParametres = sheet.createRow(3);
		Cell cellDepartement = rowParametres.createCell(2);
		cellDepartement.setCellValue("Département : " + departement);
		Cell cellMois = rowParametres.createCell(8);
		cal.set(annee, numeroMois - 1, 1);
		SimpleDateFormat formater = new SimpleDateFormat("MMMMM");
		String mois = formater.format(cal.getTime());
		cellMois.setCellValue("Mois : " + mois);
		Cell cellAnnee = rowParametres.createCell(14);
		cellAnnee.setCellValue("Année : " + annee);

		// Colonne de travail
		int colNum = 0;
		// Affichage des jours
		Row rowJour = sheet.createRow(5);
		Cell cellNom = rowJour.createCell(colNum++);
		cellNom.setCellValue("Nom");

		for (int j = 0; j < maxDay; j++) {
			Cell cell = rowJour.createCell(colNum++);
			cell.setCellValue(j + 1);
		}

		// Enregistrement du fichier
		try {
			FileOutputStream outputStream = new FileOutputStream(
					new File(fileName + "-" + annee + "-" + mois + "-" + departement + ".xlsx"));
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");

	}

	/** Méthode d'export Excel Server */
	public XSSFWorkbook exportExcelServer(Integer numeroMois, Integer annee, Integer idDepartement) {
		// Récupération des données à exporter
		// Id Département
		// Numéro de Mois
		// Année
		// Récupérer les RTT, les congés et les fériés
		AbsenceParPersonneDao absenceParPersonneDao = new AbsenceParPersonneDao();
		List<AbsenceParPersonne> absenceDepartementMoisAnnee = absenceParPersonneDao
				.afficherAbsencesParDepartementMoisAnnee(numeroMois, annee, idDepartement);

		// Création du workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Absences");

		// Récupération du nombre de jours du mois
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, numeroMois - 1);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// Année bisextile
		if (((annee % 4 == 0) && (annee % 100 != 0)) || (annee % 400 == 0)) {
			if (numeroMois == 2) {
				maxDay += 1;
			}
		}

		// Récupération de la liste des utilisateurs par département
		UtilisateurDao utilisateurDao = new UtilisateurDao();
		List<Utilisateur> utilisateurParDepartement = utilisateurDao.getUtilisateursParDepartement(idDepartement);

		// Création des styles pour changement de couleur de police
		CellStyle couleurRouge = workbook.createCellStyle();
		Font fontRouge = workbook.createFont();
		fontRouge.setColor(IndexedColors.RED.getIndex());
		couleurRouge.setFont(fontRouge);
		CellStyle couleurVerte = workbook.createCellStyle();
		Font fontVerte = workbook.createFont();
		fontVerte.setColor(IndexedColors.GREEN.getIndex());
		couleurVerte.setFont(fontVerte);
		CellStyle couleurGrise = workbook.createCellStyle();
		Font fontGrise = workbook.createFont();
		fontGrise.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
		couleurGrise.setFont(fontGrise);

		// Pour chaque utilisateur du département
		for (int i = 0; i < utilisateurParDepartement.size(); i++) {
			// Ecriture du nom
			Row rowUtilisateur = sheet.createRow(6 + i);
			Cell cellUtilisateur = rowUtilisateur.createCell(0);
			cellUtilisateur.setCellValue(
					utilisateurParDepartement.get(i).getPrenom() + " " + utilisateurParDepartement.get(i).getNom());
			// Pour chaque colonne, si un congé est posé, le mettre
			for (int k = 1; k <= maxDay; k++) {
				rowUtilisateur.createCell(k);
				Cell cellJour = rowUtilisateur.createCell(k);
				sheet.setColumnWidth(k, 1000);
				// Si c'est un Weekend, l'identifier
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
				LocalDate localDate = LocalDate.parse("" + k + "/" + numeroMois + "/" + annee, formatter);
				if ((localDate.getDayOfWeek() == localDate.getDayOfWeek().SATURDAY)
						|| (localDate.getDayOfWeek() == localDate.getDayOfWeek().SUNDAY)) {
					// Création de style pour les Weekends
					CellStyle fondGris = workbook.createCellStyle();
					fondGris.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
					fondGris.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					cellJour.setCellStyle(fondGris);
					// cellJour.setCellValue("WE");
				} else {
					for (int m = 0; m < absenceDepartementMoisAnnee.size(); m++) {
						// Si c'est le même utilisateur
						if (absenceDepartementMoisAnnee.get(m).getIdUtil() == utilisateurParDepartement.get(i)
								.getId()) {
							// Si c'est sous les mêmes dates de mois
							if (absenceDepartementMoisAnnee.get(m).getDateDebut().getDayOfMonth() <= k
									& absenceDepartementMoisAnnee.get(m).getDateFin().getDayOfMonth() >= k) {
								// Changement de couleur si besoin
								if (absenceDepartementMoisAnnee.get(m).getStatut().equals("REJETEE")) {
									cellJour.setCellStyle(couleurRouge);
								} else if (absenceDepartementMoisAnnee.get(m).getStatut().equals("VALIDEE")) {
									cellJour.setCellStyle(couleurVerte);
								} else if (absenceDepartementMoisAnnee.get(m).getStatut()
										.equals("EN_ATTENTE_VALIDATION")) {
									cellJour.setCellStyle(couleurGrise);
								}
								// Insertion de cellule
								cellJour.setCellValue(absenceParPersonneDao
										.RecupererTypeConges(absenceDepartementMoisAnnee.get(m).getIdAbsence())
										.toUpperCase().substring(0, 1));

							}

						}
					}
				}
			}

		}

		// Autosize de première colonne (noms)
		sheet.autoSizeColumn(0);

		// Affichage de la légende
		Row rowLegendeInitiale = sheet.createRow(8 + utilisateurParDepartement.size());
		Cell cellLegendeInitiale = rowLegendeInitiale.createCell(1);
		cellLegendeInitiale.setCellValue("Initiale");
		Row rowLegendeEnAttente = sheet.createRow(9 + utilisateurParDepartement.size());
		Cell cellLegendeEnAttente = rowLegendeEnAttente.createCell(1);
		cellLegendeEnAttente.setCellStyle(couleurGrise);
		cellLegendeEnAttente.setCellValue("En attente de validation");
		Row rowLegendeValidee = sheet.createRow(10 + utilisateurParDepartement.size());
		Cell cellLegendeValidee = rowLegendeValidee.createCell(1);
		cellLegendeValidee.setCellStyle(couleurVerte);
		cellLegendeValidee.setCellValue("Validée");
		Row rowLegendeRejetee = sheet.createRow(11 + utilisateurParDepartement.size());
		Cell cellLegendeRejetee = rowLegendeRejetee.createCell(1);
		cellLegendeRejetee.setCellStyle(couleurRouge);
		cellLegendeRejetee.setCellValue("Rejetée");

		Row rowTypeCongeRtt = sheet.createRow(13 + utilisateurParDepartement.size());
		Cell cellTypeCongeRTT = rowTypeCongeRtt.createCell(1);
		cellTypeCongeRTT.setCellValue("R : RTT");
		Row rowTypeCongeConge = sheet.createRow(14 + utilisateurParDepartement.size());
		Cell cellTypeCongeConge = rowTypeCongeConge.createCell(1);
		cellTypeCongeConge.setCellValue("C : Congé");
		Row rowTypeCongeMission = sheet.createRow(15 + utilisateurParDepartement.size());
		Cell cellTypeCongeMission = rowTypeCongeMission.createCell(1);
		cellTypeCongeMission.setCellValue("M : Mission");
		Row rowTypeCongeFerie = sheet.createRow(16 + utilisateurParDepartement.size());
		Cell cellTypeCongeFerie = rowTypeCongeFerie.createCell(1);
		cellTypeCongeFerie.setCellValue("F : Férié");

		// Affichage du titre
		Row rowTitre = sheet.createRow(1);
		Cell cellTitre = rowTitre.createCell(2);
		cellTitre.setCellValue("Vue par département, par jour et par collaborateur");

		// Affichage des paramètres
		String departement = utilisateurDao.recupererDepartement(idDepartement);
		Row rowParametres = sheet.createRow(3);
		Cell cellDepartement = rowParametres.createCell(2);
		cellDepartement.setCellValue("Département : " + departement);
		Cell cellMois = rowParametres.createCell(8);
		cal.set(annee, numeroMois - 1, 1);
		SimpleDateFormat formater = new SimpleDateFormat("MMMMM");
		String mois = formater.format(cal.getTime());
		cellMois.setCellValue("Mois : " + mois);
		Cell cellAnnee = rowParametres.createCell(14);
		cellAnnee.setCellValue("Année : " + annee);

		// Colonne de travail
		int colNum = 0;
		// Affichage des jours
		Row rowJour = sheet.createRow(5);
		Cell cellNom = rowJour.createCell(colNum++);
		cellNom.setCellValue("Nom");

		for (int j = 0; j < maxDay; j++) {
			Cell cell = rowJour.createCell(colNum++);
			cell.setCellValue(j + 1);
		}

		// Enregistrement du fichier
		// try {
		// FileOutputStream outputStream = new FileOutputStream(
		// new File(fileName + "-" + annee + "-" + mois + "-" + departement +
		// ".xlsx"));
		// workbook.write(outputStream);
		// workbook.close();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		System.out.println("Done");
		return workbook;
	}

	public static void main(String[] args) {

		// Lancement par main pour test
		UtilExcel utilexcel = new UtilExcel();
		utilexcel.exportExcel(6, 2019, 1);

	}
}