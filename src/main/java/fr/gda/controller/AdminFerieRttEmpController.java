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
 * Gère l'ajout et la suppression de jour ferié ou RTT employeur ainsi que
 * l'affichage de ces derniers
 * 
 * @author KHARBECHE Bilel
 *
 */
@WebServlet(urlPatterns = "/controller/adminJFerieRttEmp")
public class AdminFerieRttEmpController extends HttpServlet {

	/** SERVICE_LOG : Logger */
	private static final Logger SERVICE_LOG = LoggerFactory.getLogger(AdminFerieRttEmpController.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absParPersDao = new AbsenceParPersonneDao();

		String selectedDate = req.getParameter("selectedDate");
		String selectedType = req.getParameter("selectedType");
		String selectedMotif = req.getParameter("selectedMotif");

		LocalDate jour = LocalDate.parse(selectedDate);
		String jourSemaine = jour.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE);

		String erreurJour = null;
		String erreurConnexion = null;

		if (jourSemaine.equals("dimanche") || jourSemaine.equals("samedi")) {

			erreurJour = "erreurJ";
			req.setAttribute("erreurJ", erreurJour);

			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/ajout-feries.jsp");
			dispatcher.forward(req, resp);

		} else {

			if (absParPersDao.validationDateFerie(selectedDate)) {

				if (selectedType.equals("6")) {
					absParPersDao.addJourFerie(selectedDate, selectedMotif);

				} else {
					if (jourSemaine.equals("dimanche") || jourSemaine.equals("samedi")) {
					} else {
						absParPersDao.addRttEmployeur(selectedDate, selectedMotif);
					}

				}

				UtilisateurDao utilisateurDao = new UtilisateurDao();

				Object userId = session.getAttribute("utilisateurId");
				int utilisateurId = (int) userId;

				List<AbsenceParPersonne> listeAbsences = absParPersDao.afficherAbsencesPersonne(utilisateurId);

				String typeConge = null;

				for (AbsenceParPersonne liste : listeAbsences) {

					int absId = liste.getIdAbsence();

					if (absId == 5 || absId == 6) {
						typeConge = absParPersDao.RecupererTypeConges(liste.getId());
					}
				}

				Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);

				req.setAttribute("afficherConge", listeAbsences);
				req.setAttribute("afficherTypeConge", typeConge);
				req.setAttribute("utilisateur", utilisateur);
				SERVICE_LOG.info("Le jour férié/RTT employeur a été ajouté");

				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jours-feries.jsp");
				dispatcher.forward(req, resp);
			} else {
				erreurConnexion = "erreur";
				req.setAttribute("erreur", erreurConnexion);

				SERVICE_LOG.error("Le jour férié/RTT employer n'a pas pu être ajouter car il chevauche un autre");

				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/ajout-feries.jsp");
				dispatcher.forward(req, resp);
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/ajout-feries.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		String idCongeString = req.getParameter("suppr");

		String[] elements = idCongeString.split("_");

		Integer idConge = Integer.parseInt(elements[0]);
		String dateConge = elements[1];

		absenceDao.SupprimerCongesAll(idConge, dateConge);

		SERVICE_LOG.info("Le jour férié/RTT employeur a été supprimé");

		resp.getWriter().append("<p>Le congé a bien été supprimé !</p>");

	}

}