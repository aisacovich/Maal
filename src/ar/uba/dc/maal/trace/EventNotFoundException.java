package ar.uba.dc.maal.trace;

import ar.uba.dc.maal.event.Event;

/**
 * @author aisacovich
 * 
 * @version $Id: EventNotFoundException.java,v 1.2 2003/10/16 21:36:50 alejandro Exp $
 */
public class EventNotFoundException extends EventException {

	private Event event = null;
	
	/**
	 * 
	 */
	public EventNotFoundException() {
		super();
	}

	/**
	 * @param message
	 */
	public EventNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EventNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public EventNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param anEvent
	 */
	public EventNotFoundException(Event anEvent) {
		super();
		this.setEvent(anEvent);
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}

	/**
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {
		return super.toString() + ": [" + this.getEvent() + "]";
	}


}
