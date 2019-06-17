package fr.gda.model;

import java.util.Date;

/**
 * Classe qui gère une Absence par Personne
 * 
 * @author KHARBECHE Bilel
 *
 */
public class AbsenceParPersonne {

	/** id : int */
	private int id;
	/** idUtil : int */
	private int idUtil;
	/** idAbsence : int */
	private int idAbsence;
	/** dateDebut : Date */
	private Date dateDebut;
	/** dateFin : Date */
	private Date dateFin;
	/** statut : String */
	private String statut;
	/** motif : String */
	private String motif;

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param idUtil
	 * @param idAbsence
	 * @param dateDebut
	 * @param dateFin
	 * @param statut
	 * @param motif
	 */
	public AbsenceParPersonne(int id, int idUtil, int idAbsence, Date dateDebut, Date dateFin, String statut,
			String motif) {
		super();
		this.id = id;
		this.idUtil = idUtil;
		this.idAbsence = idAbsence;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.statut = statut;
		this.motif = motif;
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
	 * @return the idUtil
	 */
	public int getIdUtil() {
		return idUtil;
	}

	/**
	 * Setter
	 * 
	 * @param idUtil
	 *            the idUtil to set
	 */
	public void setIdUtil(int idUtil) {
		this.idUtil = idUtil;
	}

	/**
	 * Getter
	 * 
	 * @return the idAbsence
	 */
	public int getIdAbsence() {
		return idAbsence;
	}

	/**
	 * Setter
	 * 
	 * @param idAbsence
	 *            the idAbsence to set
	 */
	public void setIdAbsence(int idAbsence) {
		this.idAbsence = idAbsence;
	}

	/**
	 * Getter
	 * 
	 * @return the dateDebut
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * Setter
	 * 
	 * @param dateDebut
	 *            the dateDebut to set
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Getter
	 * 
	 * @return the dateFin
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * Setter
	 * 
	 * @param dateFin
	 *            the dateFin to set
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * Getter
	 * 
	 * @return the statut
	 */
	public String getStatut() {
		return statut;
	}

	/**
	 * Setter
	 * 
	 * @param statut
	 *            the statut to set
	 */
	public void setStatut(String statut) {
		this.statut = statut;
	}

	/**
	 * Getter
	 * 
	 * @return the motif
	 */
	public String getMotif() {
		return motif;
	}

	/**
	 * Setter
	 * 
	 * @param motif
	 *            the motif to set
	 */
	public void setMotif(String motif) {
		this.motif = motif;
	}
}
