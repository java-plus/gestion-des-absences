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

@WebServlet(urlPatterns = "/controller/updateConges/*")
public class updateCongeController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		String idCongeString = req.getParameter("update");
		Integer idConge = Integer.parseInt(idCongeString);

		String dateDebut = null;
		String dateFin = null;
		String typeConge = null;
		String motif = null;

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();
		Object userId = session.getAttribute("utilisateurId");
		int utilisateurId = (int) userId;
		List<AbsenceParPersonne> listeAbsence = absenceDao.afficherAbsencesPersonne(utilisateurId);

		if (idConge != null) {

			for (AbsenceParPersonne liste : listeAbsence) {

				if (idConge.equals(liste.getId())) {

					dateDebut = liste.getDateDebut().toString();
					dateFin = liste.getDateFin().toString();
					typeConge = liste.getTypeAbsence();
					if (typeConge.equals("cp")) {
						typeConge = "2";
					} else if (typeConge.equals("rtt")) {
						typeConge = "1";
					} else if (typeConge.equals("css")) {
						typeConge = "3";
					}
					motif = liste.getMotif();
					if (motif == null) {
						motif = "/";
					}

				}
			}

			req.setAttribute("idConge", idConge);

			req.setAttribute("dateDebut", dateDebut);
			req.setAttribute("dateFin", dateFin);
			req.setAttribute("type", typeConge);
			req.setAttribute("motif", motif);

			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/update-absences.jsp?update=" + idConge);
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

		if (typeAbsence.equals("cp")) {
			typeAbsence = "2";
		} else if (typeAbsence.equals("rtt")) {
			typeAbsence = "1";
		} else if (typeAbsence.equals("css")) {
			typeAbsence = "3";
		}

		String dateDebut = req.getParameter("dateDebut");
		String dateFin = req.getParameter("dateFin");
		String motif = req.getParameter("motif");

		String erreurConnexion = null;

		if (idConge != null) {
			for (AbsenceParPersonne liste : listeAbsence) {
				if (idConge.equals(liste.getId())) {

					if (absenceDao.validationDateCongeUpdate(utilisateurId, idConge, dateDebut, dateFin)) {
						absenceDao.modifierConges(idConge, typeAbsence, dateDebut, dateFin, motif);
						UtilisateurDao utilisateurDao = new UtilisateurDao();

						Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);

						req.setAttribute("afficherConge", listeAbsence);

						req.setAttribute("utilisateur", utilisateur);

						RequestDispatcher dispatcher = this.getServletContext()
								.getRequestDispatcher("/gestion-absences.jsp");
						dispatcher.forward(req, resp);

					} else {
						erreurConnexion = "erreur";
						req.setAttribute("erreur", erreurConnexion);

						RequestDispatcher dispatcher = this.getServletContext()
								.getRequestDispatcher("/ajout-absences.jsp");
						dispatcher.forward(req, resp);
					}

				}
			}
		}
	}
}