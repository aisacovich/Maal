package ar.uba.dc.maal.trace.log4j.test;

import junit.framework.TestCase;
import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.net.test.MockNode;
import ar.uba.dc.maal.trace.log4j.Log4JEventRaiser;

/**
 * @author aisacovich
 * 
 * @version $Id: Log4JEventRaiserTest.java,v 1.3 2003/08/27 00:22:41 alejandro Exp $
 */
public class Log4JEventRaiserTest extends TestCase {

	/**
	 * Constructor for Log4JEventRaiserTest.
	 * @param aString
	 */
	public Log4JEventRaiserTest(String aString) {
		super(aString);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRaiseEvent() {
		Log4JEventRaiser raiser = new Log4JEventRaiser();
		raiser.raiseEvent(new LocalEvent(new MockNode("unNodo")));
	}

}
