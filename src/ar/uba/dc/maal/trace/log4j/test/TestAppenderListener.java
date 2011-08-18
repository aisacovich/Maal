package ar.uba.dc.maal.trace.log4j.test;

import ar.uba.dc.maal.event.Event;

/**
 * @author aisacovich
 * 
 * @version $Id: TestAppenderListener.java,v 1.1 2003/07/26 20:07:14 alejandro Exp $
 */
public interface TestAppenderListener {

	/**
	 * @param anEvent
	 */
	void eventAppended(Event anEvent);

}
