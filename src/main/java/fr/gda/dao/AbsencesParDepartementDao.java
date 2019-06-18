/**
 * 
 */
package fr.gda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.gda.connexion.ConnexionManager;
import fr.gda.exception.TechnicalException;
import fr.gda.model.AbsenceParPersonne;
import fr.gda.model.Utilisateur;

/**
 * 
 * Class qui récupère tous les congés (validés) pour chaque utilisateur.
 * ATTENTION : il n'existe pas pour le moment de notion de "departement".
 * 
 * @author Eloi
 *
 */
public class AbsencesParDepartementDao {

	public List<Utilisateur> AbsencesParDepartement() {

		List<Utilisateur> listeAbsenceParDepartement = new ArrayList<>();

		Connection conn = ConnexionManager.getInstance();
		PreparedStatement statement = null;
		ResultSet curseur = null;

		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement("SELECT AP.id, id_util, id_absence, date_debut, date_fin, statut, motif FROM absence_personne AP INNER JOIN utilisateur UT ON AP.id_util = UT.id WHERE (YEAR(date_debut) = ? OR YEAR(date_fin) = ?) AND (MONTH(date_debut) = ? OR MONTH(date_fin) = ?) AND id_departement = ?");
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
				Integer idAbsence = curseur.getInt("id_absence");
				LocalDate dateDebut = curseur.getDate("date_debut").toLocalDate();
				LocalDate dateFin = curseur.getDate("date_fin").toLocalDate();
				String statut = curseur.getString("statut");
				String motif = curseur.getString("motif");

				listeAbsencesDepartementMoisAnnee.add(new AbsenceParPersonne(id, idUtilisateur, idAbsence, dateDebut, dateFin, statut, motif));
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

		return listeAbsenceParDepartement;

	}

}
