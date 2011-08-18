package ar.uba.dc.maal.event;

import java.io.Serializable;


/**
 * Representa un evento con algún interés. Tipicamente este evento es registrado para la verificación de un sistema.
 * @author aisacovich
 * 
 * @version $Id: Event.java,v 1.6 2003/11/06 23:29:56 alejandro Exp $
 */
public abstract class Event implements Serializable {
	private Serializable message=new NullMessage();
	
	/**
	 * @return el mensaje que representa la semántica de este evento.
	 */
	public Serializable getMessage() {
		return message;
	}

	/**
	 * El mensaje representa la semantica del evento. Como este evento normalmente se registra para futuro 
	 * analisis, esta semántica debe ser serializable.
	 * @param object
	 */
	public void setMessage(Serializable object) {
		message = object;
	}
	

	/**
	 * Dos eventos son el mismo si son de la misma clase y su mensaje es el mismo.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Event) {
			return equalsEvent((Event) obj);
		}
		else {
			return false;
		}
	}

	/**
	 * @param Event que se quiere comprar con este objeto
	 * @return true si y solo si este objeto representa el mismo evento que localEvent  
	 */
	private boolean equalsEvent(Event event) {
		return this.getMessage().equals(event.getMessage());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getMessage().hashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + " Message: [" + this.getMessage() + "]";
	}

}
