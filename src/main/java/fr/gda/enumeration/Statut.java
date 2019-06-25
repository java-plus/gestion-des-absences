package fr.gda.enumeration;

public enum Statut {

	INITIALE("INITIALE"), EN_ATTENTE_VALIDATION("EN_ATTENTE"), VALIDEE("VALIDEE"), REJETEE("REJETEE");

	/** type : String : type d'absence possible */
	private String Statut;

	/**
	 * Constructeur
	 * 
	 * @param statut
	 */
	private Statut(String statut) {
		Statut = statut;
	}

	/**
	 * Getter
	 * 
	 * @return the statut
	 */
	public String getStatut() {
		return Statut;
	}

	/**
	 * Setter
	 * 
	 * @param statut
	 *            the statut to set
	 */
	public void setStatut(String statut) {
		Statut = statut;
	}

}
