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

@WebServlet(urlPatterns = "/controller/afficherVueDepart/*")
public class AfficherVuesSynthetiques extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String url = req.getRequestURL().toString();

		String uri = req.getRequestURI();

		String scheme = req.getScheme();

		String contextPath = req.getContextPath();
		String servletPath = req.getServletPath();
		String pathInfo = req.getPathInfo();

		req.setAttribute("url", url);
		req.setAttribute("Uri", uri);
		req.setAttribute("pathInfo", pathInfo);
		req.setAttribute("Scheme", scheme);
		req.setAttribute("contextPath", contextPath);
		req.setAttribute("servletPath", servletPath);

		// histo = demande d'affichage de la page avec Histo par departement et par jour
		// collab = demande d'affichage de la page de vue par par depart. par jour et par collab.

		// if (collab) {
		//
		// } else if (histo) {
		//
		// } else {
		//
		// }

		//
		//
		// HttpSession session = req.getSession(false);
		//
		// AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();
		//
		// UtilisateurDao utilisateurDao = new UtilisateurDao();
		//
		// Object userId = session.getAttribute("utilisateurId");
		// int utilisateurId = (int) userId;
		//
		// List<AbsenceParPersonne> listeAbsences = absenceDao.afficherAbsencesPersonne(utilisateurId);
		//
		// String typeConge = null;
		//
		// for (AbsenceParPersonne liste : listeAbsences) {
		// typeConge = absenceDao.RecupererTypeConges(liste.getId());
		// }
		//
		// Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);
		//
		// req.setAttribute("afficherConge", listeAbsences);
		// req.setAttribute("afficherTypeConge", typeConge);
		// req.setAttribute("utilisateur", utilisateur);
		//
		//

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/vues-histo.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
