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
 * @author KHARBECHE Bilel
 *
 */
@WebServlet(urlPatterns = "/controller/jFerieRttEmp")
public class FerieRttEmpController extends HttpServlet {

	/** SERVICE_LOG : Logger */
	private static final Logger SERVICE_LOG = LoggerFactory.getLogger(AfficherCongeController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absParPersDao = new AbsenceParPersonneDao();
		UtilisateurDao utilisateurDao = new UtilisateurDao();

		Object userId = session.getAttribute("utilisateurId");
		int utilisateurId = (int) userId;

		List<AbsenceParPersonne> listeAbsences = absParPersDao.afficherAbsencesPersonne(utilisateurId);

		String typeConge = null;

		for (AbsenceParPersonne liste : listeAbsences) {
			int absId = liste.getIdAbsence();

			if (absId == 5 || absId == 6) {
				typeConge = absParPersDao.RecupererTypeConges(liste.getId());
			}
		}

		Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);

		req.setAttribute("afficherConge", listeAbsences);
		req.setAttribute("afficherTypeConge", typeConge);
		req.setAttribute("utilisateur", utilisateur);

		SERVICE_LOG.info("La liste des jours feriés a été affichée");

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jours-feries.jsp");
		dispatcher.forward(req, resp);
	}
}
