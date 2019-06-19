/**
 * 
 */
package fr.gda.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.dao.UtilisateurDao;
import fr.gda.model.AbsenceParPersonne;
import fr.gda.model.Departement;
import fr.gda.model.Utilisateur;

/**
 * 
 * Controleur qui intercepte les demande d affichage des pages des vues synthetiques
 * 
 * @author Eloi
 *
 */

@WebServlet(urlPatterns = "/controller/afficherVueDepart")
public class AfficherVuesSynthController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// ---------------- A recuperer du filtre de la page ----------------- //
		Integer numeroMois = 6;
		Integer annee = 2019;
		Integer idDepartement = 1;

		AbsenceParPersonneDao absenceParPersonneDao = new AbsenceParPersonneDao();
		List<AbsenceParPersonne> absenceDepartementMoisAnnee = absenceParPersonneDao.afficherAbsencesParDepartementMoisAnnee(numeroMois, annee, idDepartement);
		req.setAttribute("absenceDepartementMoisAnnee", absenceDepartementMoisAnnee);

		UtilisateurDao utilisateurDao = new UtilisateurDao();
		List<Utilisateur> utilisateurParDepartement = utilisateurDao.getUtilisateursParDepartement(idDepartement);
		req.setAttribute("utilisateurParDepartement", utilisateurParDepartement);

		List<Departement> listeDepartements = utilisateurDao.getTousLesDepartements();
		req.setAttribute("listeDepartements", listeDepartements);

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
		req.setAttribute("maxDay", maxDay);

		String choix = req.getParameter("vue");

		// histo = demande d'affichage de la page avec Histo par departement et par jour
		// collab = demande d'affichage de la page de vue par par depart. par jour et par collab.
		// menu = demande d'affichage la page pour choisir

		String urlDirection = "";

		if (choix.equals("collab")) {
			urlDirection = "/vues-depart.jsp";

		} else if (choix.equals("histo")) {
			urlDirection = "/vues-histo.jsp";
		} else if (choix.equals("menu")) {
			urlDirection = "/vues-synth.jsp";
		} else {
			urlDirection = "/vues-synth.jsp"; // on considere qu'on redirige comme si le choix etait "menu"
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(urlDirection);
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
