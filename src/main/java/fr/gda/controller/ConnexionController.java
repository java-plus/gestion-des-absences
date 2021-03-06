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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	/** SERVICE_LOG : Logger */
	private static final Logger SERVICE_LOG = LoggerFactory.getLogger(ConnexionController.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// récupération des identifiants
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		password = DigestUtils.sha512Hex(password);

		SERVICE_LOG.info("Connexion avec email :  " + email);

		UtilisateurDao utilisateurDao = new UtilisateurDao();

		boolean connexion = utilisateurDao.validerMdp(email, password);

		String monProfil = null;

		String erreurConnexion = null;

		// test connexion
		if (connexion) {
			HttpSession session = req.getSession(true);

			// Traitement pour un employé
			if (utilisateurDao.validerProfil(email).equals("employé")) {
				Employe employe = utilisateurDao.getEmploye(email);
				monProfil = "employe";
				req.setAttribute("monProfil", monProfil);

				session.setAttribute("utilisateurId", employe.getId());
				session.setAttribute("prenom", employe.getPrenom());
				session.setAttribute("profil", employe.getProfil());
				session.setAttribute("isAdmin", employe.isAdmin());

				SERVICE_LOG.info("L'utilisateur est connecté en tant qu'employé");

				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(req, resp);

			} // Traitement pour un manager
			else if (utilisateurDao.validerProfil(email).equals("manager")) {
				Manager manager = utilisateurDao.getManager(email);
				monProfil = "manager";
				req.setAttribute("monProfil", monProfil);

				session.setAttribute("utilisateurId", manager.getId());
				session.setAttribute("prenom", manager.getPrenom());
				session.setAttribute("profil", manager.getProfil());
				session.setAttribute("isAdmin", manager.isAdmin());

				SERVICE_LOG.info("L'utilisateur est connecté en tant que manager");

				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(req, resp);

			}

		} // Traitement si la connexion ne peut pas s'établir
		else {
			erreurConnexion = "erreur";
			req.setAttribute("erreur", erreurConnexion);
			SERVICE_LOG.error("L'utilisateur n'a pas pu se connecter");
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(req, resp);

		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		session.invalidate();

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(req, resp);

	}
}
