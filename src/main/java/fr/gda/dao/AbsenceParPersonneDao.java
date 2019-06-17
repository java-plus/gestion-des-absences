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
				Integer idAbsence = curseur.getInt("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				;
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
				Integer idAbsence = curseur.getInt("id_absence");
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

}