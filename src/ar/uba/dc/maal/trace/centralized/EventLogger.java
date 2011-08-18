package ar.uba.dc.maal.trace.centralized;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;

/**
 * @author aisacovich
 * 
 * @version $Id: EventLogger.java,v 1.1 2003/09/03 00:31:41 alejandro Exp $
 */
public interface EventLogger {
	public void logLocalEvent(LocalEvent localEvent);
	public void logSendEvent(SendEvent sendEvent);
	public void logReceiveEvent(ReceiveEvent receiveEvent);
}
