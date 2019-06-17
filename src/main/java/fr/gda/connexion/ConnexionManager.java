package fr.gda.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fr.gda.exception.TechnicalException;

/**
 * Classe qui gère la connexion à la base de données
 * 
 * @author Cécile Peyras
 *
 */
public class ConnexionManager {
	/** bundle : ResourceBundle : fichier avec les identifiant de connexion */
	private static ResourceBundle bundle = ResourceBundle.getBundle("database");
	/** conn : Connection */
	private static Connection conn;

	static {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		} catch (SQLException e1) {
			throw new TechnicalException("La connexion ne s'est pas établi dans le main", e1);
		}
	}

	/**
	 * méthode qui permet d'établir la connexion avec la base de données.
	 * 
	 * @return
	 */
	public static Connection getInstance() {

		try {
			if (conn == null || conn.isClosed()) {

				String urlName = bundle.getString("database.url");
				String userName = bundle.getString("database.user");
				String password = bundle.getString("database.password");
				conn = DriverManager.getConnection(urlName, userName, password);

			}

		} catch (SQLException e) {

			throw new TechnicalException("La connexion n'a pas pu s'établir", e);

		}
		return conn;
	}
}
