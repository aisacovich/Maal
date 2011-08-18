package ar.uba.dc.maal.trace.log4j.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import ar.uba.dc.maal.event.Event;
import ar.uba.dc.maal.trace.log4j.Log4jEvent;
import ar.uba.dc.maal.trace.log4j.MaalEventRenderer;

/**
 * @author aisacovich
 * 
 * @version $Id: TestAppender.java,v 1.1 2003/07/26 20:07:14 alejandro Exp $
 */
public class TestAppender extends AppenderSkeleton {
	
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
	
	static private Set listeners = new HashSet();
	
	/**
	 * 
	 */
	public TestAppender() {
		super();
	}

	/**
	 * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
	 */
	private int count = 0;
	protected void append(LoggingEvent aLoggingEvent) {
		//System.out.println("" + this + " append: " + aLoggingEvent);
		//System.out.println("Message:" + aLoggingEvent.getMessage());
		Log4jEvent ev =(Log4jEvent) new MaalEventRenderer().doUnrender(aLoggingEvent.getRenderedMessage());
		count++;
		System.out.println("Log4JEvent:" + ev.getEvent().getMessage() + "nro:" + count);
		log.debug(ev.getEvent().getMessage().toString() + "nro:" + count);
		this.fireEventAppended(ev.getEvent());
	}


	/**
	 * @see org.apache.log4j.Appender#close()
	 */
	public void close() {
		System.out.println("" + this + " close");

	}

	/**
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	public boolean requiresLayout() {
		return false;
	}

	/**
	 * Agrega un timerListener que va a eschcuar el ring.
	 * @param aListener 
	 */
	static public void addTestAppenderListener(TestAppenderListener aListener) {
		listeners.add(aListener);
	}

	/**
	 * Elimina <tt>aListener</tt> de los timerListeners del timer.
	 * @param aListener
	 */
	static public void removeTestAppenderListener(TestAppenderListener aListener) {
		listeners.remove(aListener);
	} 
	
	public void fireEventAppended(Event anEvent) {
		Iterator iter = listeners.iterator();
		while (iter.hasNext()) {
			TestAppenderListener listener = (TestAppenderListener) iter.next();
				listener.eventAppended(anEvent);
		}
	}
}
