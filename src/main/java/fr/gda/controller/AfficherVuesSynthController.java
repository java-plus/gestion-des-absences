/**
 * 
 */
package fr.gda.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.gda.dao.UtilisateurDao;
import fr.gda.model.Departement;
import fr.gda.service.FormatageListAffichageVues;
import fr.gda.service.UtilExcel;

/**
 * 
 * Controleur qui intercepte les demande d affichage des pages des "vues
 * synthetiques"
 * 
 * @author Eloi
 *
 */

@WebServlet(urlPatterns = "/controller/afficherVueDepart")

public class AfficherVuesSynthController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Integer numeroMois;
		Integer annee;
		Integer idDepartement;

		// recuperation de tous les departements pour les afficher dans le
		// filtre
		UtilisateurDao utilisateurDao = new UtilisateurDao();
		List<Departement> listeDepartements = utilisateurDao.getTousLesDepartements();
		req.setAttribute("listeDepartements", listeDepartements);

		String stringNumeroMois = req.getParameter("inputMois");

		// s'il n'y a pas de parametre passé alors on affiche par défaut le mois
		// en cours de l'annee en cours
		if (stringNumeroMois != null) {
			// récuperation des valeurs indiquées par le filtre
			numeroMois = Integer.parseInt(req.getParameter("inputMois"));
			annee = Integer.parseInt(req.getParameter("inputAnnee"));
			idDepartement = Integer.parseInt(req.getParameter("inputDepartement"));
		} else {
			// valeurs par défaut
			LocalDate maDate = LocalDate.now();
			numeroMois = maDate.getMonthValue();

			annee = maDate.getYear();
			idDepartement = 1;
		}

		FormatageListAffichageVues formatageListVue = new FormatageListAffichageVues();

		String[] ListNoms = formatageListVue.constuireArrayNoms(idDepartement);
		String[][] ListjourMois = formatageListVue.construireArrayJoursMoisAll(numeroMois, annee, idDepartement);

		req.setAttribute("ListjourMois", ListjourMois);
		req.setAttribute("ListNoms", ListNoms);

		req.setAttribute("numeroMois", numeroMois);
		req.setAttribute("annee", annee);
		req.setAttribute("idDepartement", idDepartement);

		// ---------------------------------------------------------------------------------------
		// //

		String choix = req.getParameter("vue");

		// histo = demande d'affichage de la page avec Histo par departement et
		// par jour
		// collab = demande d'affichage de la page de vue par par depart. par
		// jour et par collab.
		// menu = demande d'affichage la page pour choisir

		String urlDirection = "";

		if (choix != null) {

			if (choix.equals("collab")) {
				urlDirection = "/vues-depart.jsp";

			} else if (choix.equals("histo")) {
				urlDirection = "/vues-histo.jsp";
			} else if (choix.equals("menu")) {
				urlDirection = "/vues-synth.jsp";
			}

		} else {

			urlDirection = "/vues-synth.jsp"; // on considere qu'on redirige
												// comme si le choix etait
												// "menu"
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(urlDirection);
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer numeroMois;
		Integer annee;
		Integer idDepartement;

		idDepartement = Integer.parseInt(req.getParameter("inputDepartement"));
		annee = Integer.parseInt(req.getParameter("inputAnnee"));
		numeroMois = Integer.parseInt(req.getParameter("inputMois"));

		UtilExcel utilexcel = new UtilExcel();

		XSSFWorkbook workbook = utilexcel.exportExcelServer(numeroMois, annee, idDepartement);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=ExportExcel.xlsx");
		try (OutputStream out = resp.getOutputStream()) {
			workbook.write(out);
			workbook.close();
		}
		resp.flushBuffer();
	}

}
