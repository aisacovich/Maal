package ar.uba.dc.maal.trace;

import ar.uba.dc.maal.event.Event;
import ar.uba.dc.maal.trace.log4j.Log4JEventRaiser;

/**
 * Informa que se produjeron eventos localmente. Tipicamente este es el objeto que se incorpora al sistema para
 * registrar los eventos que se utilizarán para la verificación. 
 * @author aisacovich
 * 
 * @version $Id: DefaultLocalEventRaiser.java,v 1.2 2003/07/26 20:07:48 alejandro Exp $
 */
public class DefaultLocalEventRaiser implements EventRaiser {

	private Log4JEventRaiser raiser=new Log4JEventRaiser(); 
	
	/**
	 * Informa que sucedió el evento anEvent
	 * @param anEvent
	 * @see ar.uba.dc.maal.trace.EventRaiser#raiseEvent(ar.uba.dc.maal.event.Event)
	 */
	public void raiseEvent(Event anEvent) {
		raiser.raiseEvent(anEvent);
	}

}
