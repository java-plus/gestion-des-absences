package fr.gda.model;

/**
 * Classe qui gère un utilisateur
 * 
 * @author KHARBECHE Bilel
 *
 */
public abstract class Utilisateur {

	/** id : int */
	private int id;
	/** nom : String */
	private String nom;
	/** prenom : String */
	private String prenom;
	/** profil : String */
	private String profil;
	/** mail : String */
	private String mail;
	/** mdp : String */
	private String mdp;
	/** isAdmin : Boolean */
	private boolean isAdmin;
	/** congeRestant : int */
	private int congeRestant;
	/** rttRestant : int */
	private int rttRestant;
	/** congePris : int */
	private int congePris;
	/** rttPris : int */
	private int rttPris;
	/** idHierarchie : int */
	private int idHierarchie;
	/** idDepartement : int */
	private int idDepartement;

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
	public Utilisateur(int id, String nom, String prenom, String profil, String mail, String mdp, boolean isAdmin,
			int congeRestant, int rttRestant, int congePris, int rttPris, int idHierarchie, int idDepartement) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.profil = profil;
		this.mail = mail;
		this.mdp = mdp;
		this.isAdmin = isAdmin;
		this.congeRestant = congeRestant;
		this.rttRestant = rttRestant;
		this.congePris = congePris;
		this.rttPris = rttPris;
		this.idHierarchie = idHierarchie;
		this.idDepartement = idDepartement;
	}

	/**
	 * méthode qui vérifie si un utilisateur est aussi admin pour acceder à
	 * l'URL
	 * 
	 * @param url
	 * @return
	 */
	public boolean isAuthorized(String url) {
		if (this.isAdmin()) {
			return true;
		}
		return false;
	}

	/**
	 * Getter
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter
	 * 
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter
	 * 
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter
	 * 
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Setter
	 * 
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Getter
	 * 
	 * @return the profil
	 */
	public String getProfil() {
		return profil;
	}

	/**
	 * Setter
	 * 
	 * @param profil
	 *            the profil to set
	 */
	public void setProfil(String profil) {
		this.profil = profil;
	}

	/**
	 * Getter
	 * 
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Setter
	 * 
	 * @param mail
	 *            the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Getter
	 * 
	 * @return the mdp
	 */
	public String getMdp() {
		return mdp;
	}

	/**
	 * Setter
	 * 
	 * @param mdp
	 *            the mdp to set
	 */
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	/**
	 * Getter
	 * 
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * Setter
	 * 
	 * @param isAdmin
	 *            the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * Getter
	 * 
	 * @return the congeRestant
	 */
	public int getCongeRestant() {
		return congeRestant;
	}

	/**
	 * Setter
	 * 
	 * @param congeRestant
	 *            the congeRestant to set
	 */
	public void setCongeRestant(int congeRestant) {
		this.congeRestant = congeRestant;
	}

	/**
	 * Getter
	 * 
	 * @return the rttRestant
	 */
	public int getRttRestant() {
		return rttRestant;
	}

	/**
	 * Setter
	 * 
	 * @param rttRestant
	 *            the rttRestant to set
	 */
	public void setRttRestant(int rttRestant) {
		this.rttRestant = rttRestant;
	}

	/**
	 * Getter
	 * 
	 * @return the congePris
	 */
	public int getCongePris() {
		return congePris;
	}

	/**
	 * Setter
	 * 
	 * @param congePris
	 *            the congePris to set
	 */
	public void setCongePris(int congePris) {
		this.congePris = congePris;
	}

	/**
	 * Getter
	 * 
	 * @return the rttPris
	 */
	public int getRttPris() {
		return rttPris;
	}

	/**
	 * Setter
	 * 
	 * @param rttPris
	 *            the rttPris to set
	 */
	public void setRttPris(int rttPris) {
		this.rttPris = rttPris;
	}

	/**
	 * Getter
	 * 
	 * @return the idHierarchie
	 */
	public int getIdHierarchie() {
		return idHierarchie;
	}

	/**
	 * Setter
	 * 
	 * @param idHierarchie
	 *            the idHierarchie to set
	 */
	public void setIdHierarchie(int idHierarchie) {
		this.idHierarchie = idHierarchie;
	}

	/**
	 * Getter
	 * 
	 * @return the idDepartement
	 */
	public int getIdDepartement() {
		return idDepartement;
	}

	/**
	 * Setter
	 * 
	 * @param idDepartement
	 *            the idDepartement to set
	 */
	public void setIdDepartement(int idDepartement) {
		this.idDepartement = idDepartement;
	}

}