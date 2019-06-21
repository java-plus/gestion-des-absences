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
			statement = conn
					.prepareStatement("SELECT * FROM absence_personne WHERE statut = 'INITIALE' ORDER BY date_debut");

			curseur = statement.executeQuery();

			conn.commit();

			while (curseur.next()) {
				Integer id = curseur.getInt("id");
				Integer idUtil = curseur.getInt("id_util");
				String idAbsence = curseur.getString("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				listeDemandesEnStatutInitiale
						.add(new AbsenceParPersonne(id, idUtil, idAbsence, dateDebut, dateFin, statut, motif));
			}

			return listeDemandesEnStatutInitiale;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("La sélection s'est pas faite", e);
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
			statement = conn.prepareStatement(
					"select AP.id, AP.date_debut, AP.date_fin, UT.prenom, UT.nom, HU.mail, type_conge from absence_personne AP inner join utilisateur UT on UT.id = AP.id_util inner join absence A on A.id = AP.id_absence inner join utilisateur HU on HU.id = UT.id_hierarchie WHERE AP.id = ?");
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

				TraitementMailManager absencePourMail = new TraitementMailManager(apId, dateDebut, dateFin, utPrenom,
						utNom, huMail, typeConge);
				return absencePourMail;
			} else
				return null;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("La sélection s'est pas faite", e);
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
				SERVICE_LOG.error("impossible de mettre à jour la table");
			}

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("La modification ne s'est pas faite", e);
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

	public void addJourFerie(String date, String motif) {

		// List<AbsenceParPersonne> jourFerie = new ArrayList<>();

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		// ResultSet curseur = null;
		UtilisateurDao userDao = new UtilisateurDao();
		List<Utilisateur> users = (List<Utilisateur>) userDao.getUtilisateurs();

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(
					"INSERT INTO absence_personne (id_util, id_absence, date_debut, date_fin, statut, motif) VALUES (?, ?, ?, ?, ?, ?)");

			for (Utilisateur user : users) {
				statement.setInt(1, user.getId());
				statement.setInt(2, 6);
				statement.setString(3, date);
				statement.setString(4, date);
				statement.setString(5, "VALIDEE");
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

	public void addRttEmployeur(String date, String motif) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		// ResultSet curseur = null;
		UtilisateurDao userDao = new UtilisateurDao();
		List<Utilisateur> users = (List<Utilisateur>) userDao.getUtilisateurs();

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(
					"INSERT INTO absence_personne (id_util, id_absence, date_debut, date_fin, statut, motif) VALUES (?, ?, ?, ?, ?, ?)");

			for (Utilisateur user : users) {
				statement.setInt(1, user.getId());
				statement.setInt(2, 6);
				statement.setString(3, date);
				statement.setString(4, date);
				statement.setString(5, "INITIALE");
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
				String idAbsence = curseur.getString("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				listeAbsences
						.add(new AbsenceParPersonne(id, idUtilisateur, idAbsence, dateDebut, dateFin, statut, motif));
			}

			return listeAbsences;
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
	 * méthode qui retourne la liste des absences de toutes les personnes
	 * 
	 * @param mois
	 * @param annee
	 * @param departement
	 * @return
	 */
	public List<AbsenceParPersonne> afficherAbsencesParDepartementMoisAnnee(Integer mois, Integer annee,
			Integer departement) {

		List<AbsenceParPersonne> listeAbsencesDepartementMoisAnnee = new ArrayList<>();

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(
					"SELECT AP.id, id_util, id_absence, date_debut, date_fin, statut, motif FROM absence_personne AP INNER JOIN utilisateur UT ON AP.id_util = UT.id WHERE (YEAR(date_debut) = ? OR YEAR(date_fin) = ?) AND (MONTH(date_debut) = ? OR MONTH(date_fin) = ?) AND id_departement = ?");
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
				String idAbsence = curseur.getString("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				listeAbsencesDepartementMoisAnnee
						.add(new AbsenceParPersonne(id, idUtilisateur, idAbsence, dateDebut, dateFin, statut, motif));
			}

			return listeAbsencesDepartementMoisAnnee;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("La sélection ne s'est pas faite", e);
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

			}
			return typeConge;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("La mise à jour ne s'est pas faite", e);
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
	 * méthode supprime un congé grace à son id
	 * 
	 * @param idUtilisateur
	 * @return
	 */
	public void SupprimerConges(int idConge) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;
		String typeConge = null;

		try {
			conn.setAutoCommit(false);

			statement = conn.prepareStatement("DELETE FROM absence_personne WHERE id = ?");
			statement.setInt(1, idConge);

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

			statement = conn.prepareStatement(
					"UPDATE  absence_personne SET id_absence= ?, date_debut=?, date_fin= ?, motif=? WHERE id_absence= ? ");
			statement.setString(1, typeAbsence);
			statement.setString(2, dateDebut);
			statement.setString(3, dateFin);
			statement.setString(4, motif);
			statement.setInt(5, idConge);

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
	 * <<<<<<< HEAD Méthode qui ajoute un congé dans la base à un utilisateur
	 * identifié
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

		List<AbsenceParPersonne> liste = new ArrayList<>();

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(
					"INSERT INTO absence_personne (id_util, id_absence, date_debut, date_fin, statut, motif) VALUES (?, ?, ?, ?, ?, ?)");

			statement.setInt(1, idUser);
			statement.setInt(2, idUser);
			statement.setString(3, dateDebut);
			statement.setString(4, dateFin);
			statement.setString(5, "INITIALE");
			statement.setString(6, motif);

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
	 * méthode qui retourne la liste des absences pour un manager
	 * 
	 * @param idManager
	 * @return Liste d'absences par personne
	 */
	public List<AbsenceParPersonne> afficherAbsencesParManager(int idManager) {

		List<AbsenceParPersonne> listeAbsencesManager = new ArrayList<>();

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(
					"SELECT AP.* FROM absence_personne AP INNER JOIN utilisateur UT ON AP.id_util = UT.id WHERE id_hierarchie = ? AND statut = 'EN_ATTENTE_VALIDATION' ORDER BY date_debut");
			statement.setInt(1, idManager);

			curseur = statement.executeQuery();

			conn.commit();

			while (curseur.next()) {
				Integer id = curseur.getInt("id");
				Integer idUtil = curseur.getInt("id_util");
				String idAbsence = curseur.getString("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				listeAbsencesManager
						.add(new AbsenceParPersonne(id, idUtil, idAbsence, dateDebut, dateFin, statut, motif));
			}

			return listeAbsencesManager;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("La sélection ne s'est pas fait", e);
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
	public boolean validationDateConge(int idUser, String dateDebut, String dateFin) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;
		List<AbsenceParPersonne> liste = new ArrayList<>();

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(
					"SELECT date_debut, date_fin FROM absence_personne WHERE (? < date_fin) and (? >date_debut) AND id_util = ?;");
			statement.setString(1, dateDebut);
			statement.setString(2, dateFin);
			statement.setInt(3, idUser);

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

}
