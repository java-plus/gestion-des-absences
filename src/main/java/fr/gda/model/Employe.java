package fr.gda.model;

/**
 * Classe qui gère un employe
 * 
 * @author Cécile Peyras
 *
 */
public class Employe extends Utilisateur {

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param profil
	 * @param mail
	 * @param mdp
	 * @param isAdmin
	 * @param congeRestant
	 * @param rttRestant
	 * @param congePris
	 * @param rttPris
	 * @param idHierarchie
	 * @param idDepartement
	 */
	public Employe(int id, String nom, String prenom, String profil, String mail, String mdp, boolean isAdmin,
			int congeRestant, int rttRestant, int congePris, int rttPris, int idHierarchie, int idDepartement) {
		super(id, nom, prenom, profil, mail, mdp, isAdmin, congeRestant, rttRestant, congePris, rttPris, idHierarchie,
				idDepartement);

	}

}
