package fr.gda.model;

public class Employe extends Utilisateur {

	/**
	 * Constructor
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
	 */
	public Employe(int id, String nom, String prenom, String profil, String mail, String mdp, boolean isAdmin,
			int congeRestant, int rttRestant, int congePris, int rttPris, int idHierarchie) {
		super(id, nom, prenom, profil, mail, mdp, isAdmin, congeRestant, rttRestant, congePris, rttPris, idHierarchie);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gda.model.Utilisateur#isAuthorized(java.lang.String)
	 */
	@Override
	public boolean isAuthorized(String url) {
		if (this.getIsAdmin()) {
			return true;
		}
		return false;
	}

}
