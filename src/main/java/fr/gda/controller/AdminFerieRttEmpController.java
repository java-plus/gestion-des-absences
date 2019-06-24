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

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.dao.UtilisateurDao;
import fr.gda.model.AbsenceParPersonne;
import fr.gda.model.Utilisateur;

/**
 * @author KHARBECHE Bilel
 *
 */
@WebServlet(urlPatterns = "/controller/adminJFerieRttEmp")
public class AdminFerieRttEmpController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absParPersDao = new AbsenceParPersonneDao();

		String selectedDate = req.getParameter("selectedDate");
		String selectedType = req.getParameter("selectedType");
		String selectedMotif = req.getParameter("selectedMotif");

		String erreurConnexion = null;
		boolean test = absParPersDao.validationDateFerié(selectedDate);

		if (absParPersDao.validationDateFerié(selectedDate)) {

			if (selectedType.equals("ferié")) {
				absParPersDao.addJourFerie(selectedDate, selectedMotif);

			} else {
				absParPersDao.addRttEmployeur(selectedDate, selectedMotif);

			}

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

			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jours-feries.jsp");
			dispatcher.forward(req, resp);
		} else {
			erreurConnexion = "erreur";
			req.setAttribute("erreur", erreurConnexion);

			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/ajout-feries.jsp");
			dispatcher.forward(req, resp);
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/ajout-feries.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		String idCongeString = req.getParameter("suppr");

		String[] elements = idCongeString.split("_");

		Integer idConge = Integer.parseInt(elements[0]);
		String dateConge = elements[1];

		absenceDao.SupprimerCongesAll(idConge, dateConge);

		resp.getWriter().append("<p>Le congé a bien été supprimé !</p>");

	}

}