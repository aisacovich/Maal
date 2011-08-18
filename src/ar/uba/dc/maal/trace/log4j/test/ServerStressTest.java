package ar.uba.dc.maal.trace.log4j.test;

import java.net.URL;

import org.apache.log4j.xml.DOMConfigurator;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.net.test.MockNode;
import ar.uba.dc.maal.trace.log4j.Log4jEvent;

/**
 * @author aisacovich
 * 
 * @version $Id: ServerStressTest.java,v 1.2 2003/08/27 00:22:41 alejandro Exp $
 */
public class ServerStressTest {

	public static void main(String[] args) {
		Thread[] threads = new Thread[50]; 
		URL url = SocketAppenderTest.class.getResource("Log4JSocketAppenderTest.xml");	
		DOMConfigurator.configure(url);
		
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread("Test thread nro: " + (i+1)) {
				public void run() {
					for (int j = 0; j < 200; j++) {
						LocalEvent event = new LocalEvent(new MockNode("Un Nodo"));
						event.setMessage("*EVENTO* - Nro: ["+ j +"] " + this );
						org.apache.log4j.Logger socketLogger = org.apache.log4j.Logger.getLogger("SocketAppenderLogger");
						socketLogger.info(new Log4jEvent(event));
					}
				}
			};
		}
		for (int i = 0; i < threads.length; i++) {
			Thread thread = threads[i];
			thread.start();
		}

	}
}
