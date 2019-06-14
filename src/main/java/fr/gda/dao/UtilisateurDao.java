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
		return false;
	}

}
