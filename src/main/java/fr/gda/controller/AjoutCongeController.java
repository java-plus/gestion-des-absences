package fr.gda.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gda.dao.AbsenceParPersonneDao;

@WebServlet(urlPatterns = "/controller/updateConges/*")
public class AjoutCongeController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		String ajoutConge = req.getParameter("ajout");
		if (ajoutConge.equals("add")) {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/ajout-absence.jsp");
			dispatcher.forward(req, resp);
		}

		String idCongeString = req.getParameter("update");
		Integer idConge = Integer.parseInt(idCongeString);

		absenceDao.modifierConges(idConge);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}
