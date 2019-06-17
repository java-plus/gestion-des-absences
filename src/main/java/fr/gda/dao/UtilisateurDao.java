package fr.gda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.gda.connexion.ConnexionManager;
import fr.gda.exception.TechnicalException;

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
	public boolean validerMdp(String email) {
		return false;

	}

	/**
	 * Récupération du nombre de jours restants de congés par type"
	 * 
	 * @ param idUtilisateur : Id de l'utilisateur typeConge : Type de congé
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

			conn.commit();

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
