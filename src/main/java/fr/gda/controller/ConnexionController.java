package fr.gda.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gda.dao.UtilisateurDao;

/**
 * Servlet qui sert à valider la connexion au site
 * 
 * @author Cécile Peyras
 *
 */
@WebServlet(urlPatterns = "/connexion/*")
public class ConnexionController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String email = req.getParameter("email");
		String password = req.getParameter("password");

		UtilisateurDao utilisateurDao = new UtilisateurDao();

		boolean connexion = utilisateurDao.validerMdp(email, password);

		if (connexion) {

			if (utilisateurDao.validerProfil(email).equals("employé")) {
				if (utilisateurDao.validerAdmin(email)) {
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("menu_admin");
					dispatcher.forward(req, resp);
				}
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("menu_employe");
				dispatcher.forward(req, resp);
			} else if (utilisateurDao.validerProfil(email).equals("manager")) {
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("menu_manager");
				dispatcher.forward(req, resp);
			}

		}
	}

}
