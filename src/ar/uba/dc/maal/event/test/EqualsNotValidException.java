package ar.uba.dc.maal.event.test;

/**
 * @author aisacovich
 * 
 * @version $Id: EqualsNotValidException.java,v 1.1 2003/11/06 23:45:14 alejandro Exp $
 */
public class EqualsNotValidException extends Exception {

	/**
	 * 
	 */
	public EqualsNotValidException() {
		super();
	}

	/**
	 * @param message
	 */
	public EqualsNotValidException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EqualsNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public EqualsNotValidException(Throwable cause) {
		super(cause);
	}

}
