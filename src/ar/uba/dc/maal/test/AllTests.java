package ar.uba.dc.maal.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import ar.uba.dc.maal.event.definition.templates.test.SendAndReceiveEventTemplateTest;
import ar.uba.dc.maal.event.definition.test.EventDefinitionTest;
import ar.uba.dc.maal.event.test.EventTest;
import ar.uba.dc.maal.event.test.LocalEventTest;
import ar.uba.dc.maal.event.test.MessageEventTest;
import ar.uba.dc.maal.trace.centralized.test.DistributedEventsMergerTest;
import ar.uba.dc.maal.trace.centralized.test.SimpleSystemTraceTest;
import ar.uba.dc.maal.trace.log4j.test.Log4JEventRaiserTest;
import ar.uba.dc.maal.trace.log4j.test.SerializeDeserializeTest;

/**
 * @author aisacovich
 * 
 * @version $Id: AllTests.java,v 1.1 2003/11/06 23:45:14 alejandro Exp $
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for ar.uba.dc.maal.event.test");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(EventTest.class));
		suite.addTest(new TestSuite(LocalEventTest.class));
		suite.addTest(new TestSuite(MessageEventTest.class));
		suite.addTest(new TestSuite(DistributedEventsMergerTest.class));
		suite.addTest(new TestSuite(EventDefinitionTest.class));
		suite.addTest(new TestSuite(Log4JEventRaiserTest.class));
		suite.addTest(new TestSuite(SendAndReceiveEventTemplateTest.class));
		suite.addTest(new TestSuite(SerializeDeserializeTest.class));
		suite.addTest(new TestSuite(SimpleSystemTraceTest.class));
//		suite.addTest(new TestSuite(SocketAppenderTest.class));
		//$JUnit-END$
		return suite;
	}
}
