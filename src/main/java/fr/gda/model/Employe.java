package fr.gda.model;

public class Employe extends Utilisateur {

	public Employe() {
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean isAuthorized(String url) {
		if (getIsAdmin() == true) {
			return true;
		}
		return false;
	}
}
