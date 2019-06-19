package fr.gda.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
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

	private static final String FILE_NAME = "ExportAbsences.xlsx";

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

		// Colonne et ligne de travail
		int rowNum = 0;
		int colNum = 1;

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

		UtilisateurDao utilisateurDao = new UtilisateurDao();
		List<Utilisateur> utilisateurParDepartement = utilisateurDao.getUtilisateursParDepartement(idDepartement);

		for (int i = 0; i < utilisateurParDepartement.size(); i++) {
			Row rowUtilisateur = sheet.createRow(6 + i);
			Cell cellUtilisateur = rowUtilisateur.createCell(0);
			cellUtilisateur.setCellValue(
					utilisateurParDepartement.get(i).getPrenom() + " " + utilisateurParDepartement.get(i).getNom());
			// Pour chaque colonne, si un congé est posé, le mettre
			for (int k = 1; k < maxDay; k++) {
				for (int m = 0; m < absenceDepartementMoisAnnee.size(); m++) {
					// Si c'est le même utilisateur
					if (absenceDepartementMoisAnnee.get(m).getIdUtil() == utilisateurParDepartement.get(i).getId()) {
						// Si c'est sous les mêmes dates de mois
						if (absenceDepartementMoisAnnee.get(m).getDateDebut().getDayOfMonth() <= k
								& absenceDepartementMoisAnnee.get(m).getDateFin().getDayOfMonth() >= k) {
							rowUtilisateur.createCell(k);
							Cell cellJour = rowUtilisateur.createCell(k);
							cellJour.setCellValue(absenceParPersonneDao
									.RecupererTypeConges(absenceDepartementMoisAnnee.get(m).getIdAbsence())
									.toUpperCase().substring(0, 1));
						}

					}
				}
			}

		}

		// Affichage du titre
		Row rowTitre = sheet.createRow(1);
		Cell cellTitre = rowTitre.createCell(2);
		cellTitre.setCellValue("Vue par département, par jour et par collaborateur");

		// Affichage des paramètres
		String departement = utilisateurDao.recupererDepartement(idDepartement);
		Row rowParametres = sheet.createRow(3);
		Cell cellDepartement = rowParametres.createCell(2);
		cellDepartement.setCellValue("Département : " + departement);
		Cell cellMois = rowParametres.createCell(4);
		cal.set(annee, numeroMois - 1, 1);
		SimpleDateFormat formater = new SimpleDateFormat("MMMMM");
		cellMois.setCellValue("Mois : " + formater.format(cal.getTime()));
		Cell cellAnnee = rowParametres.createCell(6);
		cellAnnee.setCellValue("Année : " + annee);

		// Affichage des jours
		Row row = sheet.createRow(5);
		for (int j = 0; j < maxDay; j++) {
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(j + 1);
		}

		// Enregistrement du fichier
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(FILE_NAME));
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");

	}

	public static void main(String[] args) {

		UtilExcel utilexcel = new UtilExcel();
		utilexcel.exportExcel(6, 2019, 1);

		/*
		 * XSSFWorkbook workbook = new XSSFWorkbook(); XSSFSheet sheet =
		 * workbook.createSheet("Datatypes in Java"); Object[][] datatypes = { {
		 * "Datatype", "Type", "Size(in bytes)" }, { "int", "Primitive", 2 }, {
		 * "float", "Primitive", 4 }, { "double", "Primitive", 8 }, { "char",
		 * "Primitive", 1 }, { "String", "Non-Primitive", "No fixed size" } };
		 * 
		 * int rowNum = 0; System.out.println("Creating excel");
		 * 
		 * for (Object[] datatype : datatypes) { Row row =
		 * sheet.createRow(rowNum++); int colNum = 0; for (Object field :
		 * datatype) { Cell cell = row.createCell(colNum++); if (field
		 * instanceof String) { cell.setCellValue((String) field); } else if
		 * (field instanceof Integer) { cell.setCellValue((Integer) field); } }
		 * }
		 * 
		 * try { FileOutputStream outputStream = new FileOutputStream(new
		 * File(FILE_NAME)); workbook.write(outputStream); workbook.close(); }
		 * catch (FileNotFoundException e) { e.printStackTrace(); } catch
		 * (IOException e) { e.printStackTrace(); }
		 * 
		 * System.out.println("Done");
		 */
	}
}