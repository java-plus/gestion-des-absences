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

@WebServlet(urlPatterns = "/controller/updateFerie/*")
public class AdminUpdateFerieRttEmprController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		String idCongeString = req.getParameter("update");
		Integer idConge = Integer.parseInt(idCongeString);

		String dateDebut = null;
		int typeConge = 0;
		String motif = null;
		String afficherConge = null;

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();
		Object userId = session.getAttribute("utilisateurId");
		int utilisateurId = (int) userId;
		List<AbsenceParPersonne> listeAbsence = absenceDao.afficherAbsencesPersonne(utilisateurId);

		if (idConge != null) {

			for (AbsenceParPersonne liste : listeAbsence) {

				if (idConge.equals(liste.getId())) {

					dateDebut = liste.getDateDebut().toString();
					typeConge = liste.getIdAbsence();
					if (typeConge == 6) {
						afficherConge = "Ferié";
					} else {
						afficherConge = "RTT employeur";
					}

					motif = liste.getMotif();
					if (motif == null) {
						motif = "/";
					}

				}
			}

			req.setAttribute("idConge", idConge);
			req.setAttribute("dateDebut", dateDebut);
			req.setAttribute("type", afficherConge);
			req.setAttribute("motif", motif);

			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/update-jours-feries.jsp?update=" + idConge);
			dispatcher.forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		String idCongeString = req.getParameter("update");
		Integer idConge = Integer.parseInt(idCongeString);

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		Object userId = session.getAttribute("utilisateurId");
		int utilisateurId = (int) userId;
		List<AbsenceParPersonne> listeAbsence = absenceDao.afficherAbsencesPersonne(utilisateurId);

		String typeAbsence = req.getParameter("type");
		String dateDebut = req.getParameter("dateDebut");
		String motif = req.getParameter("motif");

		String erreurConnexion = null;

		if (idConge != null) {

			for (AbsenceParPersonne liste : listeAbsence) {
				String ancienneDate = liste.getDateDebut().toString();
				if (idConge.equals(liste.getId())) {

					if (absenceDao.validationDateFerié(dateDebut)) {
						absenceDao.modifierFeries(idConge, typeAbsence, dateDebut, ancienneDate, motif);
						UtilisateurDao utilisateurDao = new UtilisateurDao();

						Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);
						List<AbsenceParPersonne> listeAbsenceAJour = absenceDao.afficherAbsencesPersonne(utilisateurId);

						req.setAttribute("afficherConge", listeAbsenceAJour);

						req.setAttribute("utilisateur", utilisateur);

						RequestDispatcher dispatcher = this.getServletContext()
								.getRequestDispatcher("/jours-feries.jsp");
						dispatcher.forward(req, resp);

					} else {
						erreurConnexion = "erreur";
						req.setAttribute("erreur", erreurConnexion);

						RequestDispatcher dispatcher = this.getServletContext()
								.getRequestDispatcher("/ajout-feries.jsp");
						dispatcher.forward(req, resp);
					}

				}
			}
		}
	}

}