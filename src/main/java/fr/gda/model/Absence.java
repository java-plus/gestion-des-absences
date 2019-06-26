package fr.gda.model;

/**
 * Classe qui gère une absence
 * 
 * @author KHARBECHE Bilel
 *
 */
public class Absence {

	/** id : int */
	private int id;
	/** type : String */
	private String type;

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param type
	 */
	public Absence(int id, String type) {
		super();
		this.id = id;
		this.type = type;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
