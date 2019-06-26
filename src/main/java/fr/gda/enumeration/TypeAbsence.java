package fr.gda.enumeration;

public enum TypeAbsence {

	RTT("RTT"), CONGE_PAYE("Congé payé"), CONGE_S_SOLDE("Congé sans solde"), MISSION("Mission"), RTT_EMPLOYEUR(
			"RTT employeur"), FERIE("Ferié");

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
