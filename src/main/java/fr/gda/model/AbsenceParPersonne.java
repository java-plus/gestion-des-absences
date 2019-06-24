package fr.gda.model;

import java.time.LocalDate;

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
	private String typeAbsence;
	/** dateDebut : Date */
	private LocalDate dateDebut;
	/** dateFin : Date */
	private LocalDate dateFin;
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
	public AbsenceParPersonne(int id, int idUtil, String typeAbsence, LocalDate dateDebut, LocalDate dateFin,
			String statut, String motif) {
		super();
		this.id = id;
		this.idUtil = idUtil;
		switch (typeAbsence) {
		case "1":
			this.typeAbsence = "RTT";
			break;
		case "2":
			this.typeAbsence = "congé payé";
			break;
		case "3":
			this.typeAbsence = "congé sans solde";
			break;
		case "4":
			this.typeAbsence = "mission";
			break;
		case "5":
			this.typeAbsence = "RTT employeur";
			break;
		case "6":
			this.typeAbsence = "férié";
			break;
		}
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
	 * @return the typeAbsence
	 */
	public String getTypeAbsence() {
		return typeAbsence;
	}

	/**
	 * Setter
	 * 
	 * @param typeAbsence
	 *            the typeAbsence to set
	 */
	public void setTypeAbsence(String typeAbsence) {
		this.typeAbsence = typeAbsence;
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

	/** Méthode qui retourne le nombre de jours demandés */
	public Long getNombreJoursDemandesSansWE() {
		Long nbJours = 0l;
		Integer i = 0;
		do {
			if ((!this.getDateDebut().plusDays(i).getDayOfWeek().equals(this.getDateDebut().getDayOfWeek().SATURDAY))
					&& (!this.getDateDebut().plusDays(i).getDayOfWeek()
							.equals(this.getDateDebut().getDayOfWeek().SUNDAY))) {
				nbJours++;
			}
			i++;
		} while (!this.getDateDebut().plusDays(i).equals(this.getDateFin()));
		return ++nbJours;
	}

}
