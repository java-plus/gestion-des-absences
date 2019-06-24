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
import fr.gda.model.AbsenceParPersonne;

@WebServlet(urlPatterns = "/controller/updateConges/*")
public class updateCongeController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		String idCongeString = req.getParameter("update");
		Integer idConge = Integer.parseInt(idCongeString);

		String dateDebut = null;
		String dateFin = null;
		int typeConge = 0;
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
					typeConge = liste.getIdAbsence();
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

		if (idConge != null) {
			for (AbsenceParPersonne liste : listeAbsence) {
				if (idConge.equals(liste.getId())) {
					absenceDao.modifierConges(idConge, typeAbsence, dateDebut, dateFin, motif);
				}

			}

			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/gestion-absences.jsp");
			dispatcher.forward(req, resp);
		}
	}

}
