package ar.uba.dc.maal.trace;

import ar.uba.dc.maal.event.Event;

/**
 * Informa que se produjeron eventos localmente. Tipicamente este es el objeto que se incorpora al sistema para
 * registrar los eventos que se utilizar�n para la verificaci�n. 
 * 
 * @author aisacovich
 * 
 * @version $Id: EventRaiser.java,v 1.2 2003/07/26 20:07:48 alejandro Exp $
 */
public interface EventRaiser {

	/**
	 * Informa que sucedi� el evento anEvent
	 * @param anEvent
	 */
	public void raiseEvent(Event anEvent);
}
