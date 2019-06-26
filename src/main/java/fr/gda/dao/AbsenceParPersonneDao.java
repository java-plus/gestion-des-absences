package fr.gda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.gda.connexion.ConnexionManager;
import fr.gda.enumeration.Statut;
import fr.gda.exception.TechnicalException;
import fr.gda.model.AbsenceParPersonne;
import fr.gda.model.TraitementMailManager;
import fr.gda.model.Utilisateur;

/**
 * Classe qui gère les absences des personnes
 * 
 * @author Patrice Allardin
 *
 */
public class AbsenceParPersonneDao {

	/** SERVICE_LOG : Logger */
	private static final Logger SERVICE_LOG = LoggerFactory.getLogger(AbsenceParPersonneDao.class);

	/**
	 * Récupération des demandes en statut "INITIALE"
	 * 
	 * 
	 * @return Liste des demandes d'absence au statut "INITIALE"
	 */
	public List<AbsenceParPersonne> lireDemandesEnStatutInitiale() {

		List<AbsenceParPersonne> listeDemandesEnStatutInitiale = new ArrayList<>();
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM absence_personne WHERE statut = 'INITIALE' ORDER BY date_debut");

			curseur = statement.executeQuery();

			conn.commit();

			while (curseur.next()) {
				Integer id = curseur.getInt("id");
				Integer idUtil = curseur.getInt("id_util");
				Integer idAbsence = curseur.getInt("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				listeDemandesEnStatutInitiale.add(new AbsenceParPersonne(id, idUtil, idAbsence, dateDebut, dateFin, statut, motif));
				SERVICE_LOG.info("Une absence en statut initale a été ajoutée : " + id + " " + idUtil + " " + idAbsence + " " + dateDebut + " " + dateFin + " " + statut + " " + motif);
			}

			return listeDemandesEnStatutInitiale;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La sélection s'est pas faite", e);
			throw new TechnicalException("La sélection s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * Récupération des infos pour composition de mail au manager
	 * 
	 * @param idAbsence
	 * 
	 */
	public TraitementMailManager lireDemandesPourMailManager(Integer idAbsence) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("select AP.id, AP.date_debut, AP.date_fin, UT.prenom, UT.nom, HU.mail, type_conge from absence_personne AP inner join utilisateur UT on UT.id = AP.id_util inner join absence A on A.id = AP.id_absence inner join utilisateur HU on HU.id = UT.id_hierarchie WHERE AP.id = ?");
			statement.setInt(1, idAbsence);
			curseur = statement.executeQuery();

			conn.commit();

			if (curseur.next()) {
				Integer apId = curseur.getInt("AP.id");
				LocalDate dateDebut = curseur.getDate("AP.date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("AP.date_fin").toLocalDate();
				String utPrenom = curseur.getString("UT.prenom");
				String utNom = curseur.getString("UT.nom");
				String huMail = curseur.getString("HU.mail");
				String typeConge = curseur.getString("type_conge");

				TraitementMailManager absencePourMail = new TraitementMailManager(apId, dateDebut, dateFin, utPrenom, utNom, huMail, typeConge);
				SERVICE_LOG.info("Récupération des infos pour mail au manager réussie.");

				return absencePourMail;
			} else {
				SERVICE_LOG.error("Aucune info récupérée pour le mail au manager");
				return null;
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La sélection s'est pas faite", e);
			throw new TechnicalException("La sélection s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * Modification de statut
	 * 
	 * @param Id
	 *            de demande de congé
	 * @param Statut
	 * 
	 * 
	 *
	 */
	public void modifierStatut(Integer idDemandeConge, String statut) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("UPDATE absence_personne SET statut = ? WHERE id = ?");
			statement.setString(1, statut);
			statement.setInt(2, idDemandeConge);

			if (statement.executeUpdate() == 0) {
				// Log de l'erreur
				SERVICE_LOG.error("impossible d'effecturer le changement de statut pour le congé : " + idDemandeConge);
			}
			SERVICE_LOG.info("Le statut a été mis à jour pour le congé " + idDemandeConge);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La modification ne s'est pas faite", e);
			throw new TechnicalException("La modification ne s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * Ajout de jour ferié
	 * 
	 * @param date
	 * @param motif
	 */
	public void addJourFerie(String date, String motif) {

		// List<AbsenceParPersonne> jourFerie = new ArrayList<>();

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		// ResultSet curseur = null;
		UtilisateurDao userDao = new UtilisateurDao();
		List<Utilisateur> users = (List<Utilisateur>) userDao.getUtilisateurs();

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("INSERT INTO absence_personne (id_util, id_absence, date_debut, date_fin, statut, motif) VALUES (?, ?, ?, ?, ?, ?)");

			for (Utilisateur user : users) {
				statement.setInt(1, user.getId());
				statement.setInt(2, 6);
				statement.setString(3, date);
				statement.setString(4, date);
				statement.setString(5, Statut.VALIDEE.toString());
				statement.setString(6, motif);

				statement.executeUpdate();
			}

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("L'ajout ne s'est pas fait", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {

				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}
	}

	/**
	 * Ajout de RTT employeur
	 * 
	 * @param date
	 * @param motif
	 */
	public void addRttEmployeur(String date, String motif) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		// ResultSet curseur = null;
		UtilisateurDao userDao = new UtilisateurDao();
		List<Utilisateur> users = (List<Utilisateur>) userDao.getUtilisateurs();

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("INSERT INTO absence_personne (id_util, id_absence, date_debut, date_fin, statut, motif) VALUES (?, ?, ?, ?, ?, ?)");

			for (Utilisateur user : users) {
				statement.setInt(1, user.getId());
				statement.setInt(2, 5);
				statement.setString(3, date);
				statement.setString(4, date);
				statement.setString(5, Statut.INITIALE.toString());
				statement.setString(6, motif);

				statement.executeUpdate();
			}

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("L'ajout ne s'est pas fait", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {

				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}
	}

	/**
	 * méthode qui retourne la liste des absences pour une personne donnée
	 * 
	 * @param idUtilisateur
	 * @return
	 */
	public List<AbsenceParPersonne> afficherAbsencesPersonne(int idUtilisateur) {

		List<AbsenceParPersonne> listeAbsences = new ArrayList<>();

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM absence_personne WHERE id_util = ? ORDER BY date_debut");
			statement.setInt(1, idUtilisateur);

			curseur = statement.executeQuery();

			conn.commit();

			while (curseur.next()) {
				Integer id = curseur.getInt("id");
				Integer idAbsence = curseur.getInt("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				listeAbsences.add(new AbsenceParPersonne(id, idUtilisateur, idAbsence, dateDebut, dateFin, statut, motif));

			}

			SERVICE_LOG.info("La récupération de la liste des absences s'est bien faite");
			return listeAbsences;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La récupération des données ne s'est pas faite", e);
			throw new TechnicalException("La récupération des données ne s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * méthode qui retourne la liste des absences pour une personne donnée,
	 * triée
	 * 
	 * @param idUtilisateur
	 * @param ordreTri
	 * @return
	 */
	public List<AbsenceParPersonne> afficherAbsencesPersonneTrie(int idUtilisateur, String ordreTri) {

		List<AbsenceParPersonne> listeAbsences = new ArrayList<>();

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			if (ordreTri.equals("DateDebutAsc")) {
				statement = conn.prepareStatement("SELECT * FROM absence_personne WHERE id_util = ? ORDER BY date_debut ASC");
			} else if (ordreTri.equals("DateDebutDesc")) {
				statement = conn.prepareStatement("SELECT * FROM absence_personne WHERE id_util = ? ORDER BY date_debut DESC");
			} else if (ordreTri.equals("DateFinAsc")) {
				statement = conn.prepareStatement("SELECT * FROM absence_personne WHERE id_util = ? ORDER BY date_fin ASC");
			} else if (ordreTri.equals("DateFinDesc")) {
				statement = conn.prepareStatement("SELECT * FROM absence_personne WHERE id_util = ? ORDER BY date_fin DESC");
			} else if (ordreTri.equals("StatutAsc")) {
				statement = conn.prepareStatement("SELECT * FROM absence_personne WHERE id_util = ? ORDER BY statut ASC");
			} else if (ordreTri.equals("StatutDesc")) {
				statement = conn.prepareStatement("SELECT * FROM absence_personne WHERE id_util = ? ORDER BY statut DESC");
			}

			statement.setInt(1, idUtilisateur);

			curseur = statement.executeQuery();

			conn.commit();

			while (curseur.next()) {
				Integer id = curseur.getInt("id");
				Integer idAbsence = curseur.getInt("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				listeAbsences.add(new AbsenceParPersonne(id, idUtilisateur, idAbsence, dateDebut, dateFin, statut, motif));
			}

			return listeAbsences;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("La récupération des données ne s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {

				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * méthode qui retourne une absence donnée
	 * 
	 * @param idDemande
	 * @return
	 */
	public AbsenceParPersonne afficherAbsenceParId(int idDemande) {

		AbsenceParPersonne absence = null;

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM absence_personne WHERE id = ?");
			statement.setInt(1, idDemande);

			curseur = statement.executeQuery();

			conn.commit();

			if (curseur.next()) {
				Integer id = curseur.getInt("id");
				Integer idUtilisateur = curseur.getInt("id_util");
				Integer idAbsence = curseur.getInt("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				absence = new AbsenceParPersonne(id, idUtilisateur, idAbsence, dateDebut, dateFin, statut, motif);

				SERVICE_LOG.info("La liste des absence de l'ulisateur est bien crée");
			}

			return absence;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La sélection ne s'est pas faite", e);
			throw new TechnicalException("La sélection ne s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * méthode qui retourne la liste des absences de toutes les personnes
	 * 
	 * @param mois
	 * @param annee
	 * @param departement
	 * @return
	 */
	public List<AbsenceParPersonne> afficherAbsencesParDepartementMoisAnnee(Integer mois, Integer annee, Integer departement) {

		List<AbsenceParPersonne> listeAbsencesDepartementMoisAnnee = new ArrayList<>();

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT AP.id, id_util, id_absence, date_debut, date_fin, statut, motif FROM absence_personne AP INNER JOIN utilisateur UT ON AP.id_util = UT.id WHERE (YEAR(date_debut) = ? OR YEAR(date_fin) = ?) AND (MONTH(date_debut) = ? OR MONTH(date_fin) = ?) AND id_departement = ?");
			statement.setInt(1, annee);
			statement.setInt(2, annee);
			statement.setInt(3, mois);
			statement.setInt(4, mois);
			statement.setInt(5, departement);

			curseur = statement.executeQuery();

			conn.commit();

			while (curseur.next()) {
				Integer id = curseur.getInt("AP.id");
				Integer idUtilisateur = curseur.getInt("id_util");
				Integer idAbsence = curseur.getInt("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				listeAbsencesDepartementMoisAnnee.add(new AbsenceParPersonne(id, idUtilisateur, idAbsence, dateDebut, dateFin, statut, motif));

				SERVICE_LOG.info("La liste des absences par département et année s'est bien faite");
			}

			return listeAbsencesDepartementMoisAnnee;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La sélection ne s'est pas faite", e);
			throw new TechnicalException("La sélection ne s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * méthode qui récupère le type de congés en String
	 * 
	 * @param idUtilisateur
	 * @return
	 */
	public String RecupererTypeConges(int idConge) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;
		String typeConge = null;

		try {
			conn.setAutoCommit(false);

			statement = conn.prepareStatement("SELECT * FROM absence WHERE id = ?");
			statement.setInt(1, idConge);

			curseur = statement.executeQuery();

			conn.commit();

			if (curseur.next()) {
				typeConge = curseur.getString("type_conge");
				SERVICE_LOG.info("Le type de congé a bien été récupéré");

			}
			return typeConge;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La récupération de donnée ne s'est pas faite", e);
			throw new TechnicalException("La récupération de donnée ne s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * méthode supprime un congé grace à son id
	 * 
	 * @param idUtilisateur
	 * @return
	 */
	public void SupprimerConges(int idConge) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;

		try {
			conn.setAutoCommit(false);

			statement = conn.prepareStatement("DELETE FROM absence_personne WHERE id = ?");
			statement.setInt(1, idConge);

			statement.executeUpdate();

			SERVICE_LOG.info("Le congé a bien été supprimé, id du congé : " + idConge);

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La suppression ne s'est pas faite", e);
			throw new TechnicalException("La suppression ne s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * méthode supprime un congé grace à son id
	 * 
	 * @param idUtilisateur
	 * @return
	 */
	public void SupprimerCongesAll(int idConge, String date) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;
		String typeConge = null;

		try {
			conn.setAutoCommit(false);

			statement = conn.prepareStatement("DELETE FROM absence_personne WHERE id_absence = ? and date_debut = ?");
			statement.setInt(1, idConge);
			statement.setString(2, date);

			statement.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("L'ajout ne s'est pas fait", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {

				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * méthode qui modifie un congé grace à son id
	 * 
	 * @param idUtilisateur
	 * @return
	 */
	public void modifierConges(Integer idConge, String typeAbsence, String dateDebut, String dateFin, String motif) {
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;

		try {
			conn.setAutoCommit(false);

			statement = conn.prepareStatement("UPDATE  absence_personne SET id_absence= ?, date_debut=?, date_fin= ?, motif=?, statut= ? WHERE id= ? ");
			statement.setString(1, typeAbsence);
			statement.setString(2, dateDebut);
			statement.setString(3, dateFin);
			statement.setString(4, motif);
			statement.setString(5, "INITIALE");
			statement.setInt(6, idConge);

			statement.executeUpdate();

			SERVICE_LOG.info("la modification du congé s'est bien faite, id du congé : " + idConge);
			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La modification ne s'est pas faite", e);
			throw new TechnicalException("La modification ne s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * Méthode qui ajoute un congé dans la base à un utilisateur identifié
	 * 
	 * @param idUser
	 * @param idAbsence
	 * @param dateDebut
	 * @param dateFin
	 * @param motif
	 */
	public void addConge(int idUser, String idAbsence, String dateDebut, String dateFin, String motif) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("INSERT INTO absence_personne (id_util, id_absence, date_debut, date_fin, statut, motif) VALUES (?, ?, ?, ?, ?, ?)");

			statement.setInt(1, idUser);
			statement.setString(2, idAbsence);
			statement.setString(3, dateDebut);
			statement.setString(4, dateFin);
			statement.setString(5, "INITIALE");
			statement.setString(6, motif);

			statement.executeUpdate();

			SERVICE_LOG.info("L'ajout du congé s'est bien fait, utilisateur : " + idUser + ", date de début : " + dateDebut + ", date de fin : " + dateFin);

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("L'ajout ne s'est pas fait", e);
			throw new TechnicalException("L'ajout ne s'est pas fait", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}
	}

	/**
	 * méthode qui retourne la liste des absences pour un manager, triée
	 * 
	 * @param idManager
	 * @param ordreTri
	 * @return Liste d'absences par personne
	 */
	public List<AbsenceParPersonne> afficherAbsencesParManagerTrie(int idManager, String ordreTri) {

		List<AbsenceParPersonne> listeAbsencesManager = new ArrayList<>();

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			if (ordreTri.equals("DateDebutAsc")) {
				statement = conn.prepareStatement("SELECT AP.* FROM absence_personne AP INNER JOIN utilisateur UT ON AP.id_util = UT.id WHERE id_hierarchie = ? AND statut = 'EN_ATTENTE_VALIDATION' ORDER BY date_debut ASC");
			} else if (ordreTri.equals("DateDebutDesc")) {
				statement = conn.prepareStatement("SELECT AP.* FROM absence_personne AP INNER JOIN utilisateur UT ON AP.id_util = UT.id WHERE id_hierarchie = ? AND statut = 'EN_ATTENTE_VALIDATION' ORDER BY date_debut DESC");
			} else if (ordreTri.equals("DateFinAsc")) {
				statement = conn.prepareStatement("SELECT AP.* FROM absence_personne AP INNER JOIN utilisateur UT ON AP.id_util = UT.id WHERE id_hierarchie = ? AND statut = 'EN_ATTENTE_VALIDATION' ORDER BY date_fin ASC");
			} else if (ordreTri.equals("DateFinDesc")) {
				statement = conn.prepareStatement("SELECT AP.* FROM absence_personne AP INNER JOIN utilisateur UT ON AP.id_util = UT.id WHERE id_hierarchie = ? AND statut = 'EN_ATTENTE_VALIDATION' ORDER BY date_fin DESC");
			} else if (ordreTri.equals("NomAsc")) {
				statement = conn.prepareStatement("SELECT AP.* FROM absence_personne AP INNER JOIN utilisateur UT ON AP.id_util = UT.id WHERE id_hierarchie = ? AND statut = 'EN_ATTENTE_VALIDATION' ORDER BY UT.prenom ASC, UT.nom ASC");
			} else if (ordreTri.equals("NomDesc")) {
				statement = conn.prepareStatement("SELECT AP.* FROM absence_personne AP INNER JOIN utilisateur UT ON AP.id_util = UT.id WHERE id_hierarchie = ? AND statut = 'EN_ATTENTE_VALIDATION' ORDER BY UT.prenom DESC, UT.nom DESC");
			}

			statement.setInt(1, idManager);

			curseur = statement.executeQuery();

			conn.commit();

			while (curseur.next()) {
				Integer id = curseur.getInt("id");
				Integer idUtil = curseur.getInt("id_util");
				Integer idAbsence = curseur.getInt("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				listeAbsencesManager.add(new AbsenceParPersonne(id, idUtil, idAbsence, dateDebut, dateFin, statut, motif));
				SERVICE_LOG.info("La liste des absence par manager a bien été faite");
			}

			return listeAbsencesManager;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.info("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.info("La sélection ne s'est pas fait", e);
			throw new TechnicalException("La sélection ne s'est pas fait", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.info("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * Méthode qui valide que les dates de congés ne se chevauchent pas pour un
	 * utilisateur donné
	 * 
	 * @param idUser
	 * @param idAbsence
	 * @param dateDebut
	 * @param dateFin
	 * @param motif
	 * @return
	 */
	public boolean validationDateConge(int idUser, String dateDebut, String dateFin) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT date_debut, date_fin FROM absence_personne WHERE (? <= date_fin) and (? >=date_debut) AND id_util = ? and statut != ? and id_absence != 5 AND id_absence !=6;");
			statement.setString(1, dateDebut);
			statement.setString(2, dateFin);
			statement.setInt(3, idUser);
			statement.setString(4, Statut.REJETEE.getStatut());

			curseur = statement.executeQuery();

			if (curseur.next()) {
				SERVICE_LOG.info("les congés se chevauchent");
				return false;
			}
			conn.commit();
			SERVICE_LOG.info("les congés ne se chevauchent pas");
			return true;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La vérification ne s'est pas faite", e);
			throw new TechnicalException("La vérification ne s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}
	}

	/**
	 * méthode qui modifie un jour ferié ou rtt employeur grâce à la date
	 * 
	 * @param idConge
	 * @param typeAbsence
	 * @param dateDebut
	 * @param ancienneDate
	 * @param motif
	 */
	public void modifierFeries(Integer idConge, String typeAbsence, String dateDebut, String ancienneDate, String motif) {
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;

		try {
			conn.setAutoCommit(false);

			statement = conn.prepareStatement("UPDATE  absence_personne SET id_absence= ?, date_debut=?, date_fin= ?, motif=?, statut= ? WHERE date_debut= ? ");
			statement.setString(1, typeAbsence);
			statement.setString(2, dateDebut);
			statement.setString(3, dateDebut);
			statement.setString(4, motif);
			statement.setString(5, "INITIALE");
			statement.setString(6, ancienneDate);

			statement.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("L'ajout ne s'est pas fait", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {

				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}

	}

	/**
	 * Méthode qui valide que les dates de jours feriés et rtt employeurs ne se
	 * chevauchent pas
	 * 
	 * @param dateDebut
	 * @return
	 */
	public boolean validationDateFerie(String dateDebut) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;
		List<AbsenceParPersonne> liste = new ArrayList<>();

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT date_debut FROM absence_personne WHERE date_debut = ? AND (id_absence = 5 OR id_absence = 6);");
			statement.setString(1, dateDebut);

			curseur = statement.executeQuery();

			if (!curseur.next()) {

				return true;
			}
			conn.commit();
			return false;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("L'ajout ne s'est pas fait", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {

				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}
	}

	/**
	 * Méthode qui valide que les dates de jours feriés et rtt employeurs ne se
	 * chevauchent pas
	 * 
	 * @param dateDebut
	 * @return
	 */
	public boolean validationDateFerieUpdate(String dateDebut) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;
		List<AbsenceParPersonne> liste = new ArrayList<>();

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT date_debut FROM absence_personne WHERE date_debut < ? AND (id_absence = 5 OR id_absence = 6);");
			statement.setString(1, dateDebut);

			curseur = statement.executeQuery();

			if (!curseur.next()) {

				return true;
			}
			conn.commit();
			return false;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("L'ajout ne s'est pas fait", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {

				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}
	}

	/**
	 * Méthode qui valide que les dates de congés ne se chevauchent pas pour un
	 * utilisateur donné
	 * 
	 * @param idUser
	 * @param idAbsence
	 * @param dateDebut
	 * @param dateFin
	 * @param motif
	 * @return
	 */
	public boolean validationDateCongeUpdate(int idUser, Integer idConge, String dateDebut, String dateFin) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT date_debut, date_fin FROM absence_personne WHERE (? <= date_fin) and (? >= date_debut) AND id_util = ? and id !=? and statut != ? and id_absence != 5 AND id_absence !=6;");

			statement.setString(1, dateDebut);
			statement.setString(2, dateFin);
			statement.setInt(3, idUser);
			statement.setInt(4, idConge);
			statement.setString(5, Statut.REJETEE.getStatut());

			curseur = statement.executeQuery();

			if (curseur.next()) {
				SERVICE_LOG.info("Les congés se chevauchent");
				return false;
			}
			conn.commit();
			SERVICE_LOG.info("Les congés ne se chevauchent pas");
			return true;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				SERVICE_LOG.error("Le rollback n'a pas fonctionné", e);
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			SERVICE_LOG.error("La vérification ne s'est pas faite", e);
			throw new TechnicalException("La vérification ne s'est pas faite", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				SERVICE_LOG.error("La fermeture ne s'est pas faite", e);
				throw new TechnicalException("La fermeture ne s'est pas faite", e);
			}
		}
	}

}