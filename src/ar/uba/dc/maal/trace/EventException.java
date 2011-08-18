package ar.uba.dc.maal.trace;

/**
 * Excepcion generica por problemas en los eventos.
 * @author aisacovich
 * 
 * @version $Id: EventException.java,v 1.1 2003/10/14 21:57:32 alejandro Exp $
 */
public class EventException extends Exception {

	/**
	 * 
	 */
	public EventException() {
		super();
	}

	/**
	 * @param message
	 */
	public EventException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EventException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public EventException(Throwable cause) {
		super(cause);
	}

}
