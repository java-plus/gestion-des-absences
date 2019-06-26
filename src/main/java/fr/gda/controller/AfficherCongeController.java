package fr.gda.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.dao.UtilisateurDao;
import fr.gda.model.AbsenceParPersonne;
import fr.gda.model.Utilisateur;

/**
 * classe qui gère l'affichage des congés
 * 
 * @author Cécile Peyras
 *
 */
@WebServlet(urlPatterns = "/controller/afficherConges/*")
public class AfficherCongeController extends HttpServlet {

	/** SERVICE_LOG : Logger */
	private static final Logger SERVICE_LOG = LoggerFactory.getLogger(AfficherCongeController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		UtilisateurDao utilisateurDao = new UtilisateurDao();

		Object userId = session.getAttribute("utilisateurId");
		int utilisateurId = (int) userId;

		List<AbsenceParPersonne> listeAbsences = null;
		// Gestion du tri
		String triColonnes = req.getParameter("Tri");
		if (triColonnes == null) {
			triColonnes = "DateDebutAsc";
		}
		listeAbsences = absenceDao.afficherAbsencesPersonneTrie(utilisateurId, triColonnes);

		Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);

		req.setAttribute("afficherConge", listeAbsences);

		req.setAttribute("utilisateur", utilisateur);

		SERVICE_LOG.info("Affichage des congés de l'utilisateur : " + utilisateur);

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/gestion-absences.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		String idCongeString = req.getParameter("suppr");
		Integer idConge = Integer.parseInt(idCongeString);

		absenceDao.SupprimerConges(idConge);

		SERVICE_LOG.info("Le congé a été bien supprimé : idCongé :  " + idConge);

		resp.getWriter().append("ok");

	}

}
