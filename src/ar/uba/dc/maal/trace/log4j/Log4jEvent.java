package ar.uba.dc.maal.trace.log4j;

import java.io.Serializable;

import ar.uba.dc.maal.event.Event;

/**
 * Encapsula el evento para ser tratado por Log4J.
 * @author aisacovich
 * 
 * @version $Id: Log4jEvent.java,v 1.1 2003/07/26 20:07:14 alejandro Exp $
 */
public class Log4jEvent implements Serializable{
	private Event event;
	
	public Log4jEvent(){
	}
	
	public Log4jEvent(Event anEvent) {
		this.setEvent(anEvent);
	}
	
	/**
	 * @return
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

}
