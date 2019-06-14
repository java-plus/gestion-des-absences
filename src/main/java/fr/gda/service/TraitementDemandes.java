package fr.gda.service;

import java.util.List;

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.model.AbsenceParPersonne;

/**
 * Classe de traiement des demandes (dit traitement de nuit)
 * 
 * @author Patrice
 *
 */
public class TraitementDemandes {

	public TraitementDemandes() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		AbsenceParPersonneDao absenceParPersonneDao = new AbsenceParPersonneDao();
		List<AbsenceParPersonne> absenceAuStatutInitiale = absenceParPersonneDao.lireDemandesEnStatutInitiale();

		for (AbsenceParPersonne abs : absenceAuStatutInitiale)
			System.out.println(abs.getStatut());

	}

}
