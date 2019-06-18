package fr.gda.model;

import java.time.LocalDate;

/**
 * Classe de traitement de mail pour les managers
 * 
 * @author Patrice
 *
 */
public class TraitementMailManager {

	/** id de demande */
	Integer id;
	/** date de début */
	LocalDate dateDebut;
	/** date de fin */
	LocalDate dateFin;
	/** prenom de demandeur */
	String prenomDemandeur;
	/** nom de demandeur */
	String nomDemandeur;
	/** email de manager */
	String emailManager;
	/** type de congé */
	String typeConge;

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param prenomDemandeur
	 * @param nomDemandeur
	 * @param emailManager
	 * @param typeConge
	 */
	public TraitementMailManager(Integer id, LocalDate dateDebut, LocalDate dateFin, String prenomDemandeur,
			String nomDemandeur, String emailManager, String typeConge) {
		super();
		this.id = id;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.prenomDemandeur = prenomDemandeur;
		this.nomDemandeur = nomDemandeur;
		this.emailManager = emailManager;
		this.typeConge = typeConge;
	}

	/**
	 * Getter
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Setter
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Getter
	 * 
	 * @return the prenomDemandeur
	 */
	public String getPrenomDemandeur() {
		return prenomDemandeur;
	}

	/**
	 * Setter
	 * 
	 * @param prenomDemandeur
	 *            the prenomDemandeur to set
	 */
	public void setPrenomDemandeur(String prenomDemandeur) {
		this.prenomDemandeur = prenomDemandeur;
	}

	/**
	 * Getter
	 * 
	 * @return the nomDemandeur
	 */
	public String getNomDemandeur() {
		return nomDemandeur;
	}

	/**
	 * Setter
	 * 
	 * @param nomDemandeur
	 *            the nomDemandeur to set
	 */
	public void setNomDemandeur(String nomDemandeur) {
		this.nomDemandeur = nomDemandeur;
	}

	/**
	 * Getter
	 * 
	 * @return the emailManager
	 */
	public String getEmailManager() {
		return emailManager;
	}

	/**
	 * Setter
	 * 
	 * @param emailManager
	 *            the emailManager to set
	 */
	public void setEmailManager(String emailManager) {
		this.emailManager = emailManager;
	}

	/**
	 * Getter
	 * 
	 * @return the typeConge
	 */
	public String getTypeConge() {
		return typeConge;
	}

	/**
	 * Setter
	 * 
	 * @param typeConge
	 *            the typeConge to set
	 */
	public void setTypeConge(String typeConge) {
		this.typeConge = typeConge;
	}

	/**
	 * Getter
	 * 
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	/**
	 * Setter
	 * 
	 * @param dateDebut
	 *            the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Getter
	 * 
	 * @return the dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}

	/**
	 * Setter
	 * 
	 * @param dateFin
	 *            the dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

}
