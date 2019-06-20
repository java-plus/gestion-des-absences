package fr.gda.service;

import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import fr.gda.dao.AbsenceParPersonneDao;
import fr.gda.model.TraitementMailManager;

/**
 * Classe de gestion d'envoi par messagerie
 * 
 * @author Patrice
 *
 */

public class UtilMessagerie {

	/** Expéditeur */
	static final String FROM = "pat.allardin@gmail.com";
	static final String FROMNAME = "Gestion des absenses";

	/** Destinataire */
	// static String to = "pat.allardin@gmail.com";

	/** Nom d'utilisateur SMTP */
	static final String SMTP_USERNAME = "be4a2c514ea2aa8eb96ab8bb776ac024";

	/** Mot de passe SMTP */
	static final String SMTP_PASSWORD = "81713f2563d17269f5b399100ccf8047";

	/** Set de configuration */
	static final String CONFIGSET = "ConfigSet";

	/** Serveur SMTP */
	static final String HOST = "in-v3.mailjet.com";

	/** Port SMTP */
	static final int PORT = 587;
	// static final int PORT = 25;

	/** Sujet de mail */
	static final String SUBJECT = "Une demande de congé a été déposée";

	/** Méthode d'envoi de mail au manager */
	public static void EnvoyerMailManager(Integer demande, Integer demandeur, String typeAbsence,
			Long nombreJoursDemandes) throws Exception {
		// Récupération des infos de mail du manager, nom de demandeur, type
		// d'absence et nombre de jours de la demande, en fonction de la demande
		AbsenceParPersonneDao absenceParPersonneDao = new AbsenceParPersonneDao();
		TraitementMailManager traitementMailManager = absenceParPersonneDao.lireDemandesPourMailManager(demande);

		// Création des propriétés
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		// Création de session
		Session session = Session.getDefaultInstance(props);

		// Création du message
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(FROM, FROMNAME));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(traitementMailManager.getEmailManager()));
		msg.setSubject(SUBJECT);
		String body = String.join(System.getProperty("line.separator"), "<h1>Demande de congé</h1>",
				"<p>Une demande de congé a été réalisée par ");
		body += traitementMailManager.getPrenomDemandeur() + " " + traitementMailManager.getNomDemandeur() + ".<br>";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		body += "Cette demande est de type " + traitementMailManager.getTypeConge() + ", du "
				+ traitementMailManager.getDateDebut().format(formatter).toString() + " au "
				+ traitementMailManager.getDateFin().format(formatter).toString() + ".<br>";
		body += "Nombre de jours : " + nombreJoursDemandes + ".<br>";
		body += "<a href='http://localhost:8080/gda/login.jsp'>Gestion de congés</a>";

		msg.setContent(body, "text/html; charset=UTF-8");

		// Création d'entête de message
		msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

		// Création de la couche de transport
		Transport transport = session.getTransport();

		// Envoi de message
		try {
			System.out.println("Envoi en cours...");

			// Connexion au serveur
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

			// Envoi de l'email
			transport.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Email envoyé");
		} catch (Exception ex) {
			System.out.println("Email non envoyé");
			System.out.println("Message d'erreur : " + ex.getMessage());
		} finally {
			// Fermeture de connexion
			transport.close();
		}
	}
}