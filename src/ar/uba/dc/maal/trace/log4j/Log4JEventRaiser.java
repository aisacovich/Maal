package ar.uba.dc.maal.trace.log4j;

import ar.uba.dc.maal.event.Event;
import ar.uba.dc.maal.trace.EventRaiser;

/**
 * Implementación de <code>EventRaiser</code> que utiliza Log4J tanto para la registración como para la comunicación
 * de eventos entre los nodos y el central.
 * @author aisacovich
 * 
 * @version $Id: Log4JEventRaiser.java,v 1.2 2003/07/26 20:07:13 alejandro Exp $
 */
public class Log4JEventRaiser implements EventRaiser {

	private org.apache.log4j.Logger log= org.apache.log4j.Logger.getLogger(this.getClass());

	/**
	 * Constructor
	 */
	public Log4JEventRaiser() {
		LogInitializer.initializeLog4J();
	}

	/**
	 * @see ar.uba.dc.maal.trace.EventRaiser#raiseEvent(ar.uba.dc.maal.event.Event)
	 */
	public void raiseEvent(Event anEvent) {
		log.info(new Log4jEvent(anEvent));
	}

}
