package fr.gda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.gda.connexion.ConnexionManager;
import fr.gda.exception.TechnicalException;
import fr.gda.model.Employe;
import fr.gda.model.Manager;

/**
 * Classe qui gère les action des utilisateurs
 * 
 * @author Cécile Peyras
 *
 */
public class UtilisateurDao {

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

}
