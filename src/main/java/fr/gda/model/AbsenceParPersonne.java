package fr.gda.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import fr.gda.enumeration.TypeAbsence;

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
	public AbsenceParPersonne(int id, int idUtil, int idAbsence, LocalDate dateDebut, LocalDate dateFin, String statut,
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
	 * méthode qui convertit un id de congé en nom du congé
	 * 
	 * @param idConge
	 * @return
	 */
	public String typeConge(int idConge) {

		String typeConge = null;

		switch (idConge) {

		case 1:
			typeConge = TypeAbsence.RTT.getTypeAbsence();
			break;
		case 2:
			typeConge = TypeAbsence.CONGE_PAYE.getTypeAbsence();
			break;
		case 3:
			typeConge = TypeAbsence.CONGE_S_SOLDE.getTypeAbsence();
			break;
		case 4:
			typeConge = TypeAbsence.MISSION.getTypeAbsence();
			break;
		case 5:
			typeConge = TypeAbsence.RTT_EMPLOYEUR.getTypeAbsence();
			break;
		case 6:
			typeConge = TypeAbsence.FERIE.getTypeAbsence();
			break;

		}

		return typeConge;
	}

	public String afficherDate(LocalDate date) {

		String dateFr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		return dateFr;

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
		} while (!this.getDateDebut().plusDays(i++).isEqual(this.getDateFin()));
		return nbJours;
	}

}
