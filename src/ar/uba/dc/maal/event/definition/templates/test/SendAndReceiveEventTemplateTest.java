package ar.uba.dc.maal.event.definition.templates.test;

import java.io.IOException;

import ar.uba.dc.maal.event.definition.MethodDefinition;
import ar.uba.dc.maal.event.definition.templates.SendAndReceiveEventTemplate;
import junit.framework.TestCase;

/**
 * 
 * @author mcastellani
 * @version $Id: SendAndReceiveEventTemplateTest.java,v 1.2 2003/11/08 21:25:54 matias Exp $
 */
public class SendAndReceiveEventTemplateTest extends TestCase {

	/**
	 * Constructor for SendAndReceiveEventTemplateTest.
	 * @param arg0
	 */
	public SendAndReceiveEventTemplateTest(String arg0) {
		super(arg0);
	}

	/*
	 * Test for void writeCodeOn(Writer)
	 */
	public void testWriteCodeOnWriter() throws IOException{
		SendAndReceiveEventTemplate sret = new SendAndReceiveEventTemplate("test/templatetest");
		MethodDefinition methodDefinition = new MethodDefinition();
		methodDefinition.setMethodPatter("METHOD");
		sret.setMsg("MSG");
		sret.setMethodDefinition(methodDefinition);
		sret.setNode("NODE");
		sret.setOwner("OWNER");
		assertTrue(	"Message:MSG\r\nMethod:METHOD\r\nNode:NODE\r\nOwner:OWNER".equals(sret.getTemplate()) ||
					"Message:MSG\nMethod:METHOD\nNode:NODE\nOwner:OWNER".equals(sret.getTemplate()));
	}

}
