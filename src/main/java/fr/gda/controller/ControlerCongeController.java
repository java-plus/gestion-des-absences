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
 * Classe de validation des congés
 * 
 * @author Patrice
 *
 */

@WebServlet(urlPatterns = "/controller/validerConges/*")
public class ControlerCongeController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		UtilisateurDao utilisateurDao = new UtilisateurDao();

		Object userId = session.getAttribute("utilisateurId");
		int utilisateurId = (int) userId;

		List<AbsenceParPersonne> listeAbsences = absenceDao.afficherAbsencesParManager(utilisateurId);

		Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);

		List<Utilisateur> groupeUitilisateurs = utilisateurDao.getUtilisateurs();

		req.setAttribute("afficherConge", listeAbsences);

		req.setAttribute("groupeUtilisateurs", groupeUitilisateurs);

		req.setAttribute("utilisateur", utilisateur);

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/validation-absences.jsp");
		dispatcher.forward(req, resp);
	}

}
