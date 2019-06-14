package fr.gda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.gda.model.AbsenceParPersonne;

/**
 * Classe qui gère les absences des personnes
 * 
 * @author Patrice Allardin
 *
 */
public class AbsenceParPersonneDao {

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
			statement = conn.prepareStatement("SELECT * FROM absence_personne WHERE statut = 'INITIALE'");

			curseur = statement.executeQuery();

			conn.commit();

			while (curseur.next()) {
				Integer id = curseur.getInt("id");
				Integer idUtil = curseur.getInt("id_util");
				Integer idAbsence = curseur.getInt("id_absence");
				Date dateDebut = curseur.getDate("date_debut");
				Date dateFin = curseur.getDate("date_fin");
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