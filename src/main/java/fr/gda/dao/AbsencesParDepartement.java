/**
 * 
 */
package fr.gda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.gda.connexion.ConnexionManager;
import fr.gda.exception.TechnicalException;
import fr.gda.model.AbsenceParPersonne;
import fr.gda.model.Utilisateur;

/**
 * 
 * Class qui récupère tous les congés (validés) pour chaque utilisateur
 * 
 * @author Eloi
 *
 */
public class AbsencesParDepartement {

	public List<Utilisateur> AbsencesParDepartement() {
		return null;
		// TODO Auto-generated constructor stub

	}

	public List<AbsenceParPersonne> afficherAbsencesPersonne(int idUtilisateur) {

		List<AbsenceParPersonne> listeAbsences = new ArrayList<>();

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT * FROM absence_personne ORDER BY date_debut");
			statement.setInt(1, idUtilisateur);

			curseur = statement.executeQuery();

			conn.commit();

			while (curseur.next()) {
				Integer id = curseur.getInt("id");
				Integer idAbsence = curseur.getInt("id_absence");
				Date dateDebut = curseur.getDate("date_debut");
				Date dateFin = curseur.getDate("date_fin");
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

}
