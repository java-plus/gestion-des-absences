package fr.gda.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gda.dao.AbsenceParPersonneDao;

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

		if (selectedType.equals("jour ferie")) {
			absParPersDao.addJourFerie(selectedDate, selectedMotif);

		} else {
			absParPersDao.addRttEmployeur(selectedDate, selectedMotif);

		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/ajout-feries.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		String idCongeString = req.getParameter("suppr");
		Integer idConge = Integer.parseInt(idCongeString);

		absenceDao.SupprimerConges(idConge);

		resp.getWriter().append("<p>Le congé a bien été supprimé !</p>");

	}

}
