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

import fr.gda.dao.UtilisateurDao;
import fr.gda.model.Departement;
import fr.gda.service.FormatageListAffichageVues;
import fr.gda.service.RecupererNbreJoursDuMois;

@WebServlet(urlPatterns = "/controller/afficherPlanningAbs")
public class AfficherPlanningAbsController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		UtilisateurDao utilisateurDao = new UtilisateurDao();

		// recuperation de tous les departements pour les afficher dans le filtre
		List<Departement> listeDepartements = utilisateurDao.getTousLesDepartements();
		req.setAttribute("listeDepartements", listeDepartements);

		// récuperation des valeurs indiquées par le filtre
		Integer numeroMois = 8;
		Integer annee = 2019;
		Integer idDepartement = 1;

		//
		HttpSession session = req.getSession(false);

		int utilisateurId = (int) session.getAttribute("utilisateurId");

		// récupération de tous les jours du mois avec les indications pour chaque jours des congés
		FormatageListAffichageVues formatageListVue = new FormatageListAffichageVues();
		String[] listJours = formatageListVue.construireArrayJoursMois(numeroMois, annee, utilisateurId);
		req.setAttribute("listJours", listJours);

		// // Récupération du 1er jourdu mois
		RecupererNbreJoursDuMois joursMois = new RecupererNbreJoursDuMois();
		String premierJour = joursMois.getPremierJour(annee, numeroMois);
		// req.setAttribute("premierJour", premierJour);

		int decalage;

		switch (premierJour) {

		case "lundi":
			decalage = 0;
			break;

		case "mardi":
			decalage = 1;
			break;

		case "mercredi":
			decalage = 2;
			break;

		case "jeudi":
			decalage = 3;
			break;

		case "vendredi":
			decalage = 4;
			break;

		case "samedi":
			decalage = 5;
			break;

		case "dimanche":
			decalage = 6;
			break;

		default:
			decalage = 0;
			break;
		}

		req.setAttribute("decalage", decalage);

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/planning-abs.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
