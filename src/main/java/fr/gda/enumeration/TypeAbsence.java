package fr.gda.enumeration;

public enum TypeAbsence {

	RTT("RTT"), CONGE_PAYE("congé payé"), CONGE_S_SOLDE("congé sans solde"), MISSION("mission"), RTT_EMPLOYEUR(
			"RTT employeur"), FERIE("ferié");

	/** type : String : type d'absence possible */
	private String typeAbsence;

	/**
	 * Constructeur
	 * 
	 * @param typeAbsence
	 */
	private TypeAbsence(String typeAbsence) {
		this.typeAbsence = typeAbsence;
	}

	/**
	 * Getter
	 * 
	 * @return the typeAbsence
	 */
	public String getTypeAbsence() {
		return typeAbsence;
	}

}
