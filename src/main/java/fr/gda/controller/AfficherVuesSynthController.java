/**
 * 
 */
package fr.gda.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gda.dao.UtilisateurDao;
import fr.gda.model.Departement;
import fr.gda.service.FormatageListAffichageVues;

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

		// recuperation de tous les departements pour les afficher dans le filtre
		UtilisateurDao utilisateurDao = new UtilisateurDao();
		List<Departement> listeDepartements = utilisateurDao.getTousLesDepartements();
		req.setAttribute("listeDepartements", listeDepartements);

		// récuperationd des valeurs indiquées par le filtre
		Integer numeroMois = 8;
		Integer annee = 2019;
		Integer idDepartement = 1;

		FormatageListAffichageVues formatageListVue = new FormatageListAffichageVues();

		String[] ListNoms = formatageListVue.constuireArrayNoms(idDepartement);
		String[][] ListjourMois = formatageListVue.construireArrayJoursMois(numeroMois, annee, idDepartement);

		req.setAttribute("ListjourMois", ListjourMois);
		req.setAttribute("ListNoms", ListNoms);

		// --------------------------------------------------------------------------------------- //

		String choix = req.getParameter("vue");

		// histo = demande d'affichage de la page avec Histo par departement et par jour
		// collab = demande d'affichage de la page de vue par par depart. par jour et par collab.
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
			urlDirection = "/vues-synth.jsp"; // on considere qu'on redirige comme si le choix etait "menu"
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(urlDirection);
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// récupération des valeurs des input selectionnées
		String paramDepartement = req.getParameter("inputDepartement");
		String paramMois = req.getParameter("inputMois");
		String paramAnnee = req.getParameter("inputAnnee");

		// En fonction des valeurs récupérées via le fitre, on

		// json
		StringBuilder strJson = new StringBuilder();
		strJson.append("[{\"departement\" : \"").append(paramDepartement).append("\", \"mois\" : \"").append(paramMois).append("\", \"annee\" : \"").append(paramAnnee).append("\"}]");

		resp.getWriter().append(strJson);

		// RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/vues-depart.jsp");
		// dispatcher.forward(req, resp);

	}

}
