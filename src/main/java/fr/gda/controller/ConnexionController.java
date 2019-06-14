package fr.gda.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import fr.gda.dao.UtilisateurDao;
import fr.gda.model.Employe;
import fr.gda.model.Manager;

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
		password = DigestUtils.sha512Hex(password);

		UtilisateurDao utilisateurDao = new UtilisateurDao();

		boolean connexion = utilisateurDao.validerMdp(email, password);

		if (connexion) {
			HttpSession session = req.getSession(true);

			if (utilisateurDao.validerProfil(email).equals("employé")) {
				Employe employe = utilisateurDao.getEmploye(email);
				if (employe.getIsAdmin()) {
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/");
					dispatcher.forward(req, resp);
					session.setAttribute("utilisateurId", employe.getId());
					session.setAttribute("prenom", employe.getPrenom());
					session.setAttribute("profil", employe.getProfil());
					session.setAttribute("isAdmin", employe.getIsAdmin());
				} else {
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/");
					dispatcher.forward(req, resp);
					session.setAttribute("utilisateurId", employe.getId());
					session.setAttribute("prenom", employe.getPrenom());
					session.setAttribute("profil", employe.getProfil());
					session.setAttribute("isAdmin", employe.getIsAdmin());
				}
			} else if (utilisateurDao.validerProfil(email).equals("manager")) {
				Manager manager = utilisateurDao.getManager(email);
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/");
				dispatcher.forward(req, resp);
				session.setAttribute("utilisateurId", manager.getId());
				session.setAttribute("prenom", manager.getPrenom());
				session.setAttribute("profil", manager.getProfil());
				session.setAttribute("isAdmin", manager.getIsAdmin());
			}

		} else {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/");
			dispatcher.forward(req, resp);

		}
	}

}
