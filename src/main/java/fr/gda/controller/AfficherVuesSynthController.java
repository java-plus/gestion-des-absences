/**
 * 
 */
package fr.gda.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eloi
 *
 */

@WebServlet(urlPatterns = "/controller/afficherVueDepart")
public class AfficherVuesSynthController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String choix = req.getParameter("vue");
		req.setAttribute("choix", choix);

		// histo = demande d'affichage de la page avec Histo par departement et par jour
		// collab = demande d'affichage de la page de vue par par depart. par jour et par collab.
		// menu = demande d'affichage la page pour choisir

		String urlDirection = "";
		if (choix == "collab") {
			urlDirection = "/vues-depart.jsp";
		} else if (choix == "histo") {
			urlDirection = "/vues-histo.jsp";
		} else if (choix == "menu") {
			urlDirection = "/vues-synth.jsp";
		} else {
			urlDirection = "/vues-synth.jsp"; // on considere qu'on redirige comme si le choix etait "menu"

		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/vues-depart.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
