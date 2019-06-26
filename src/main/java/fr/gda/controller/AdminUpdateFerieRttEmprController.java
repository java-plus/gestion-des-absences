package fr.gda.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

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

/**
 * gère la modification de jour ferié
 * 
 * @author KHARBECHE Bilel
 *
 */
@WebServlet(urlPatterns = "/controller/updateFerie/*")
public class AdminUpdateFerieRttEmprController extends HttpServlet {

	/** SERVICE_LOG : Logger */
	private static final Logger SERVICE_LOG = LoggerFactory.getLogger(AfficherCongeController.class);

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

			SERVICE_LOG.info("Le jour ferié/Rtt employeur a été mis à jour");

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

		LocalDate jour = LocalDate.parse(dateDebut);
		String jourSemaine = jour.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE);

		String erreurJour = null;
		String erreurConnexion = null;

		if (jourSemaine.equals("dimanche") || jourSemaine.equals("samedi")) {

			erreurJour = "erreurJ";
			req.setAttribute("erreurJ", erreurJour);

			SERVICE_LOG.error("Le jour ferié/Rtt employeur n'a été mis à jour car c'est un samedi ou dimanche");

			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/ajout-feries.jsp");
			dispatcher.forward(req, resp);

		} else {

			if (idConge != null) {

				for (AbsenceParPersonne liste : listeAbsence) {
					String ancienneDate = liste.getDateDebut().toString();
					if (idConge.equals(liste.getId())) {

						if (absenceDao.validationDateFerieUpdate(dateDebut)) {
							absenceDao.modifierFeries(idConge, typeAbsence, dateDebut, ancienneDate, motif);
							UtilisateurDao utilisateurDao = new UtilisateurDao();

							Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);
							List<AbsenceParPersonne> listeAbsenceAJour = absenceDao
									.afficherAbsencesPersonne(utilisateurId);

							req.setAttribute("afficherConge", listeAbsenceAJour);

							req.setAttribute("utilisateur", utilisateur);
							SERVICE_LOG.info("Le jour ferié/Rtt employeur a été mis à jour");

							RequestDispatcher dispatcher = this.getServletContext()
									.getRequestDispatcher("/jours-feries.jsp");
							dispatcher.forward(req, resp);

						} else {
							erreurConnexion = "erreur";
							req.setAttribute("erreur", erreurConnexion);

							SERVICE_LOG.error("Le jour ferié/Rtt employeur n'a été mis à jour il chevauche un autre");

							RequestDispatcher dispatcher = this.getServletContext()
									.getRequestDispatcher("/ajout-feries.jsp");
							dispatcher.forward(req, resp);
						}

					}
				}
			}
		}
	}

}
