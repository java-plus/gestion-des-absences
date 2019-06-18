package fr.gda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.gda.connexion.ConnexionManager;
import fr.gda.exception.TechnicalException;
import fr.gda.model.Employe;
import fr.gda.model.Manager;
import fr.gda.model.Utilisateur;

/**
 * Classe qui gère les action des utilisateurs
 * 
 * @author Cécile Peyras
 *
 */
public class UtilisateurDao {

	/** SERVICE_LOG : Logger */
	private static final Logger SERVICE_LOG = LoggerFactory.getLogger(UtilisateurDao.class);

	/**
	 * Classe qui valide le mot de passe de la connexion pour l'authentifier
	 * 
	 * @param email
	 *            : email saisi par l'utilisateur
	 * @return
	 */
	public boolean validerMdp(String email, String password) {
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM utilisateur WHERE mail = ?");
			statement.setString(1, email);
			curseur = statement.executeQuery();

			if (curseur.next()) {

				String mdp = curseur.getString("mdp");

				if (mdp.equals(password)) {
					return true;
				}

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
	 * méthode qui crée un employé
	 * 
	 * @param email
	 * @return
	 */
	public Employe getEmploye(String email) {
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM utilisateur WHERE mail = ?");
			statement.setString(1, email);
			curseur = statement.executeQuery();
			Employe employe = null;

			if (curseur.next()) {

				String profil = curseur.getString("profil");

				if (profil.equals("employé")) {

					int id = curseur.getInt("id");
					String nom = curseur.getString("nom");
					String prenom = curseur.getString("prenom");
					String mdp = curseur.getString("mdp");
					int isAdmin = curseur.getInt("is_admin");
					boolean isAdminBool;
					if (isAdmin == 0) {
						isAdminBool = false;
					} else {
						isAdminBool = true;
					}
					int congeRestant = curseur.getInt("conge_restant");
					int rttRestant = curseur.getInt("rtt_restant");
					int congePris = curseur.getInt("conge_pris");
					int rttPris = curseur.getInt("rtt_restant");
					int idHierarchie = curseur.getInt("id_hierarchie");

					employe = new Employe(id, nom, prenom, profil, email, mdp, isAdminBool, congeRestant, rttRestant,
							congePris, rttPris, idHierarchie);

				}

			}

			conn.commit();

			return employe;
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
	 * Récupération du nombre de jours restants de congés par type"
	 * 
	 * @param idUtilisateur
	 *            : Id de l'utilisateur
	 * @param typeConge
	 *            : Type de congé
	 * 
	 * @return Entier représentant le nombre de jours de congés d'un type
	 */
	public Integer recupererNombreJoursParTypeConge(Integer idUtilisateur, Integer typeConge) {

		Integer nombreJoursCongeRestant = 0;
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {

			conn.setAutoCommit(false);
			if (typeConge == 1) {
				statement = conn.prepareStatement("SELECT conge_restant FROM utilisateur Where id = ?");
			} else if (typeConge == 2) {
				statement = conn.prepareStatement("SELECT RTT_restant FROM utilisateur Where id = ?");
			}
			statement.setInt(1, idUtilisateur);
			curseur = statement.executeQuery();

			if (curseur.next()) {
				if (typeConge == 1) {
					nombreJoursCongeRestant = curseur.getInt("conge_restant");
				} else if (typeConge == 2) {
					nombreJoursCongeRestant = curseur.getInt("RTT_restant");
				}

			}
			conn.commit();

			return nombreJoursCongeRestant;

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
	 * Modification du nombre de jours d'une personne par type de congés
	 * 
	 * @param idUtilisateur
	 *            : Id de l'utilisateur
	 * @param typeAbsence
	 *            : Type de l'absence demandée
	 * @param nombreJours
	 *            : Nombre de jours à retirer
	 * 
	 */
	public void retirerJoursParTypeConge(Integer idUtilisateur, Integer typeAbsence, Long nombreJours) {

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;

		try {
			conn.setAutoCommit(false);
			if (typeAbsence == 1) {
				statement = conn.prepareStatement("UPDATE utilisateur SET conge_restant = ? WHERE id = ?");
			} else if (typeAbsence == 2) {
				statement = conn.prepareStatement("UPDATE utilisateur SET rtt_restant = ? WHERE id = ?");
			}

			statement.setLong(1, nombreJours);
			statement.setInt(2, idUtilisateur);

			if (statement.executeUpdate() == 0) {
				// Log de l'erreur
				SERVICE_LOG.error("impossible de mettre à jour la table");
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}
			throw new TechnicalException("La mise à jour s'est pas faite", e);
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
	 * méthode qui crée un manager
	 * 
	 * @param email
	 * @return
	 */

	public Manager getManager(String email) {
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM utilisateur WHERE mail = ?");
			statement.setString(1, email);
			curseur = statement.executeQuery();
			Manager manager = null;

			if (curseur.next()) {

				String profil = curseur.getString("profil");

				if (profil.equals("manager")) {
					int id = curseur.getInt("id");
					String nom = curseur.getString("nom");
					String prenom = curseur.getString("prenom");
					String mdp = curseur.getString("mdp");
					int isAdmin = curseur.getInt("is_admin");
					boolean isAdminBool;
					if (isAdmin == 0) {
						isAdminBool = false;
					} else {
						isAdminBool = true;
					}
					int congeRestant = curseur.getInt("conge_restant");
					int rttRestant = curseur.getInt("rtt_restant");
					int congePris = curseur.getInt("conge_pris");
					int rttPris = curseur.getInt("rtt_restant");
					int idHierarchie = curseur.getInt("id_hierarchie");

					manager = new Manager(id, nom, prenom, profil, email, mdp, isAdminBool, congeRestant, rttRestant,
							congePris, rttPris, idHierarchie);

				}

			}

			conn.commit();
			return manager;
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
	 * méthode qui crée un employé
	 * 
	 * @param email
	 * @return
	 */
	public List<Utilisateur> getUtilisateurs() {
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;
		List<Utilisateur> user = new ArrayList<>();

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM utilisateur");
			curseur = statement.executeQuery();
			Utilisateur utilisateur = null;

			while (curseur.next()) {

				int id = curseur.getInt("id");
				String nom = curseur.getString("nom");
				String prenom = curseur.getString("prenom");
				String profil = curseur.getString("profil");
				String email = curseur.getString("mail");
				String mdp = curseur.getString("mdp");
				int isAdmin = curseur.getInt("is_admin");
				boolean isAdminBool;
				if (isAdmin == 0) {
					isAdminBool = false;
				} else {
					isAdminBool = true;
				}
				int congeRestant = curseur.getInt("conge_restant");
				int rttRestant = curseur.getInt("rtt_restant");
				int congePris = curseur.getInt("conge_pris");
				int rttPris = curseur.getInt("rtt_restant");
				int idHierarchie = curseur.getInt("id_hierarchie");

				if (profil.equals("employé")) {
					utilisateur = new Employe(id, nom, prenom, profil, email, mdp, isAdminBool, congeRestant,
							rttRestant, congePris, rttPris, idHierarchie);

					user.add(utilisateur);
				} else {
					utilisateur = new Manager(id, nom, prenom, profil, email, mdp, isAdminBool, congeRestant,
							rttRestant, congePris, rttPris, idHierarchie);

					user.add(utilisateur);

				}
			}

			conn.commit();

			return user;
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
	 * méthode qui retourne si l'utilisateur est un employé ou un manager
	 * 
	 * @param email
	 * @return
	 */
	public String validerProfil(String email) {
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;
		String profil = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM utilisateur WHERE mail = ?");
			statement.setString(1, email);
			curseur = statement.executeQuery();

			if (curseur.next()) {

				profil = curseur.getString("profil");

			}

			conn.commit();
			return profil;
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
	 * méthode qui crée un utilisateur à partir de son id
	 * 
	 * @param email
	 * @return
	 */
	public Utilisateur getUtilisateur(int idUtilisateur) {
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM utilisateur WHERE id = ?");
			statement.setInt(1, idUtilisateur);
			curseur = statement.executeQuery();
			Utilisateur utilisateur = null;

			if (curseur.next()) {

				String profil = curseur.getString("profil");
				int id = curseur.getInt("id");
				String nom = curseur.getString("nom");
				String prenom = curseur.getString("prenom");
				String mdp = curseur.getString("mdp");
				String email = curseur.getString("mail");
				int isAdmin = curseur.getInt("is_admin");
				boolean isAdminBool;
				if (isAdmin == 0) {
					isAdminBool = false;
				} else {
					isAdminBool = true;
				}
				int congeRestant = curseur.getInt("conge_restant");
				int rttRestant = curseur.getInt("rtt_restant");
				int congePris = curseur.getInt("conge_pris");
				int rttPris = curseur.getInt("rtt_restant");
				int idHierarchie = curseur.getInt("id_hierarchie");

				if (profil.equals("employé")) {

					utilisateur = new Employe(id, nom, prenom, profil, email, mdp, isAdminBool, congeRestant,
							rttRestant, congePris, rttPris, idHierarchie);
				} else {
					utilisateur = new Manager(id, nom, prenom, profil, email, mdp, isAdminBool, congeRestant,
							rttRestant, congePris, rttPris, idHierarchie);
				}

			}

			conn.commit();

			return utilisateur;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new TechnicalException("Le rollback n'a pas fonctionné", e);
			}

			throw new TechnicalException("La mise à jour ne s'est pas fait", e);

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
