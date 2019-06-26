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

@WebServlet(urlPatterns = "/controller/validerDemande/*")
public class ValiderDemandeController extends HttpServlet {

	/** SERVICE_LOG : Logger */
	private static final Logger SERVICE_LOG = LoggerFactory.getLogger(ValiderDemandeController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		AbsenceParPersonneDao absenceDao = new AbsenceParPersonneDao();

		UtilisateurDao utilisateurDao = new UtilisateurDao();

		Integer idDemande = Integer.parseInt(req.getParameter("demande"));

		absenceDao.modifierStatut(idDemande, "VALIDEE");

		Object userId = session.getAttribute("utilisateurId");
		int utilisateurId = (int) userId;

		List<AbsenceParPersonne> listeAbsences = absenceDao.afficherAbsencesParManagerTrie(utilisateurId,
				"DateDebutAsc");

		Utilisateur utilisateur = utilisateurDao.getUtilisateur(utilisateurId);

		List<Utilisateur> groupeUitilisateurs = utilisateurDao.getUtilisateurs();

		req.setAttribute("afficherConge", listeAbsences);

		req.setAttribute("groupeUtilisateurs", groupeUitilisateurs);

		req.setAttribute("utilisateur", utilisateur);

		SERVICE_LOG.info("Le congé a été validé");

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/validation-absences.jsp");
		dispatcher.forward(req, resp);
	}

}
