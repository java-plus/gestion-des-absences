package fr.gda.controller;

import java.io.IOException;

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

	}
}
