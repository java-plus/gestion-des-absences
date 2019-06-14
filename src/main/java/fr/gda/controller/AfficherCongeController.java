package fr.gda.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.model.AbsenceParPersonne;

@WebServlet(urlPatterns = "/connexion/afficherConges*")
public class AfficherCongeController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		Object userId = req.getAttribute("utilisateurId");
		int utilisateurId = (int) userId;

		List<AbsenceParPersonne> listeAbsences = absenceDao.afficherAbsencesPersonne(utilisateurId);

		req.setAttribute("afficherConge", listeAbsences);

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/visualisationabsence.jsp");
		dispatcher.forward(req, resp);
	}

}
