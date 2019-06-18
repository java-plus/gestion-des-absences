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
@WebServlet(urlPatterns = "/controller/jFerieRttEmp")
public class FerieRttEmpController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absParPersDao = new AbsenceParPersonneDao();

		String SelectedDate = req.getParameter("selectedDate");
		String SelectedType = req.getParameter("selectedType");
		String SelectedMotif = req.getParameter("selectedMotif");

		if (SelectedType.equals("jour ferie")) {
			absParPersDao.addJourFerie(SelectedDate, SelectedMotif);

		} else {
			absParPersDao.addRttEmployeur(SelectedDate, SelectedMotif);

		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		UtilisateurDao utilisateurDao = new UtilisateurDao();

		Object userId = session.getAttribute("utilisateurId");
		int utilisateurId = (int) userId;

		List<AbsenceParPersonne> listeAbsences = absenceDao.afficherAbsencesPersonne(utilisateurId);

		String typeConge = null;

		for (AbsenceParPersonne liste : listeAbsences) {
			typeConge = absenceDao.RecupererTypeConges(liste.getId());
		}

		Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);

		req.setAttribute("afficherConge", listeAbsences);
		req.setAttribute("afficherTypeConge", typeConge);
		req.setAttribute("utilisateur", utilisateur);

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jours-feries.jsp");
		dispatcher.forward(req, resp);

	}
}
