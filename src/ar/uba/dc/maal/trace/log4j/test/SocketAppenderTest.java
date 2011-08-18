package ar.uba.dc.maal.trace.log4j.test;

import junit.framework.TestCase;

import org.apache.log4j.net.SimpleSocketServer;

import ar.uba.dc.maal.event.Event;
import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.net.test.MockNode;
import ar.uba.dc.maal.trace.log4j.Log4jEvent;

/**
 * @author aisacovich
 * 
 * @version $Id: SocketAppenderTest.java,v 1.3 2003/08/27 00:22:41 alejandro Exp $
 */
public class SocketAppenderTest extends TestCase {

	/**
	 * Constructor for SocketAppenderTest.
	 * @param arg0
	 */
	public SocketAppenderTest(String arg0) {
		super(arg0);
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
	
	private boolean passFlag;
	public void testSocketAppender() throws InterruptedException {
		final String[] args = new String[] {"5555","C:\\eclipse\\workspace\\maal\\src\\ar\\uba\\dc\\maal\\trace\\log4j\\test\\Log4JSocketAppenderTest.xml"};
		SimpleSocketServer server = new SimpleSocketServer();
		Thread t = new Thread(new Runnable() {
			public void run() {
				SimpleSocketServer.main(args);
			}
		});
		t.start();
		Thread.sleep(3000);
//		URL url = SocketAppenderTest.class.getResource("log4j_client.xml");	
//		DOMConfigurator.configure(url);
//		org.apache.log4j.Logger log= org.apache.log4j.Logger.getLogger("");
		org.apache.log4j.Logger socketLogger = org.apache.log4j.Logger.getLogger("SocketAppenderLogger");
		passFlag=false;
		final LocalEvent event = new LocalEvent(new MockNode("Un Nodo"));
		TestAppender.addTestAppenderListener(new TestAppenderListener() {
			public void eventAppended(Event anEvent) {
				passFlag=event.equals((Event)anEvent);
			}
		});
		socketLogger.info(new Log4jEvent(event));
		Thread.sleep(1000);
		assertTrue(passFlag);
	}

}
