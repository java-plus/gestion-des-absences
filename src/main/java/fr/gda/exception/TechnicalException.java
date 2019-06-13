package fr.gda.exception;

/**
 * Classe qui sert à gérer les exceptions
 * 
 * @author Cécile Peyras
 *
 */
public class TechnicalException extends RuntimeException {

	/**
	 * Constructeur
	 * 
	 * @param message
	 */
	public TechnicalException(String message) {
		super(message);

	}

	/**
	 * Constructeur
	 * 
	 * @param message
	 * @param cause
	 */
	public TechnicalException(String message, Throwable cause) {
		super(message, cause);

	}

}
