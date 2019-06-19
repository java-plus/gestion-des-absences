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

@WebServlet(urlPatterns = "/controller/afficherConges/*")
public class AfficherCongeController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		UtilisateurDao utilisateurDao = new UtilisateurDao();

		Object userId = session.getAttribute("utilisateurId");
		int utilisateurId = (int) userId;

		List<AbsenceParPersonne> listeAbsences = absenceDao.afficherAbsencesPersonne(utilisateurId);

		String typeConge = null;

		Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);

		req.setAttribute("afficherConge", listeAbsences);
		req.setAttribute("afficherTypeConge", typeConge);
		req.setAttribute("utilisateur", utilisateur);

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/gestion-absences.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		String idCongeString = req.getParameter("suppr");
		Integer idConge = Integer.parseInt(idCongeString);

		absenceDao.SupprimerConges(idConge);

		resp.getWriter().append(" ok ok ok");

	}

}
