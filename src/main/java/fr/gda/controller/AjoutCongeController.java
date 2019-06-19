package fr.gda.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gda.dao.AbsenceParPersonneDao;

@WebServlet(urlPatterns = "/controller/updateConges/*")
public class AjoutCongeController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		String idCongeString = req.getParameter("update");
		Integer idConge = Integer.parseInt(idCongeString);

		absenceDao.modifierConges(idConge);

		resp.getWriter().append("<p>Le congé a bien été supprimé !</p>");
	}

}
