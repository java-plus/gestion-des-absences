package fr.gda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;

import fr.gda.connexion.ConnexionManager;
import fr.gda.exception.TechnicalException;

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
				DigestUtils.sha512(mdp);

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
	 * méthode qui retourne si l'utilisateur est un admin
	 * 
	 * @param email
	 * @return
	 */
	public boolean validerAdmin(String email) {
		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;
		int admin;
		boolean adminBoolean = false;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM utilisateur WHERE mail = ?");
			statement.setString(1, email);
			curseur = statement.executeQuery();

			if (curseur.next()) {

				admin = curseur.getInt("is_admin");
				if (admin == 0) {
					return adminBoolean = false;
				} else {
					return adminBoolean = true;
				}

			}

			conn.commit();
			return adminBoolean;
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
