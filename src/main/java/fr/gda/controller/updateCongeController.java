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

@WebServlet(urlPatterns = "/controller/updateConges/*")
public class updateCongeController extends HttpServlet {

	private static final Logger SERVICE_LOG = LoggerFactory.getLogger(AfficherCongeController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		String idCongeString = req.getParameter("update");
		Integer idConge = Integer.parseInt(idCongeString);

		String dateDebut = null;
		String dateFin = null;
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
					dateFin = liste.getDateFin().toString();
					typeConge = liste.getIdAbsence();
					if (typeConge == 1) {
						afficherConge = "RTT";
					} else if (typeConge == 2) {
						afficherConge = "congé payé";
					} else if (typeConge == 3) {
						afficherConge = "Congé sans solde";
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
			req.setAttribute("type", afficherConge);
			req.setAttribute("motif", motif);

			SERVICE_LOG.info("La récupération des informations pour la modification de congé s'est bien faite");

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
						List<AbsenceParPersonne> listeAbsenceAJour = absenceDao.afficherAbsencesPersonne(utilisateurId);

						req.setAttribute("afficherConge", listeAbsenceAJour);

						req.setAttribute("utilisateur", utilisateur);

						SERVICE_LOG.info("La modification de congé s'est bien faite");

						RequestDispatcher dispatcher = this.getServletContext()
								.getRequestDispatcher("/gestion-absences.jsp");
						dispatcher.forward(req, resp);

					} else {
						erreurConnexion = "erreur";
						req.setAttribute("erreur", erreurConnexion);

						SERVICE_LOG.error("La modification de congé n'a pas pu se faire");

						RequestDispatcher dispatcher = this.getServletContext()
								.getRequestDispatcher("/ajout-absences.jsp");
						dispatcher.forward(req, resp);
					}

				}
			}
		}
	}
}