package ar.uba.dc.maal.trace;

import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;

/**
 * @author aisacovich
 * 
 * @version $Id: EventsNotMatchException.java,v 1.2 2003/10/16 21:36:50 alejandro Exp $
 */
public class EventsNotMatchException extends EventException {

	private SendEvent aSendEvent;
	private ReceiveEvent aReceiveEvent;

	/**
	 * 
	 */
	public EventsNotMatchException() {
		super();
	}

	/**
	 * @param message
	 */
	public EventsNotMatchException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EventsNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public EventsNotMatchException(Throwable cause) {
		super(cause);
	}

	public EventsNotMatchException(SendEvent aSendEvent, ReceiveEvent aReceiveEvent) {
		this.setReceiveEvent(aReceiveEvent);
		this.setSendEvent(aSendEvent);
	}
	
	/**
	 * @return
	 */
	public ReceiveEvent getReceiveEvent() {
		return aReceiveEvent;
	}

	/**
	 * @return
	 */
	public SendEvent getSendEvent() {
		return aSendEvent;
	}

	/**
	 * @param event
	 */
	public void setReceiveEvent(ReceiveEvent event) {
		aReceiveEvent = event;
	}

	/**
	 * @param event
	 */
	public void setSendEvent(SendEvent event) {
		aSendEvent = event;
	}
	
	/**
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {
		return super.toString() + ": (Send) [" + this.getSendEvent() + "], (Receive) [" + this.getReceiveEvent() + "]";
	}


}
