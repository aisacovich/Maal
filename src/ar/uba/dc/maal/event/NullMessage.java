package ar.uba.dc.maal.event;

import java.io.Serializable;

/**
 * Todos los mensajes nulos son distintos entre si.
 * @author aisacovich
 * 
 * @version $Id: NullMessage.java,v 1.2 2003/11/06 23:56:54 alejandro Exp $
 */
public class NullMessage implements Serializable {
	private static final String MESSAGE = "Mensaje nulo"; 
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + " " + MESSAGE;
	}
	
}
