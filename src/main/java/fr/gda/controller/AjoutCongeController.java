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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.dao.UtilisateurDao;
import fr.gda.model.AbsenceParPersonne;
import fr.gda.model.Utilisateur;

@WebServlet(urlPatterns = "/controller/ajoutConges/*")
public class AjoutCongeController extends HttpServlet {

	private static final Logger SERVICE_LOG = LoggerFactory.getLogger(AjoutCongeController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		HttpSession session = req.getSession(false);
		String ajoutConge = req.getParameter("ajout");

		if (ajoutConge.equals("add")) {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/ajout-absences.jsp");
			dispatcher.forward(req, resp);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		String ajoutConge = req.getParameter("ajout");
		if (ajoutConge.equals("ok")) {

			AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

			Object userId = session.getAttribute("utilisateurId");
			int utilisateurId = (int) userId;

			String typeAbsence = req.getParameter("type");

			String dateDebut = req.getParameter("dateDebut");
			String dateFin = req.getParameter("dateFin");
			String motif = req.getParameter("motif");

			String erreurConnexion = null;

			if (absenceDao.validationDateConge(utilisateurId, dateDebut, dateFin)) {

				absenceDao.addConge(utilisateurId, typeAbsence, dateDebut, dateFin, motif);

				UtilisateurDao utilisateurDao = new UtilisateurDao();

				Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);
				List<AbsenceParPersonne> listeAbsences = absenceDao.afficherAbsencesPersonne(utilisateurId);

				req.setAttribute("afficherConge", listeAbsences);

				req.setAttribute("utilisateur", utilisateur);

				SERVICE_LOG.info("un congé a été ajouté pour l'utilisateur : " + utilisateur + " du " + dateDebut
						+ " au " + dateFin);

				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/gestion-absences.jsp");
				dispatcher.forward(req, resp);

			} else {
				erreurConnexion = "erreur";
				req.setAttribute("erreur", erreurConnexion);

				SERVICE_LOG.error("Le congé n'a pas pu être ajouté");

				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/ajout-absences.jsp");
				dispatcher.forward(req, resp);
			}

		}
	}

}
