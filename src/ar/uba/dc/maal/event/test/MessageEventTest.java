package ar.uba.dc.maal.event.test;

import junit.framework.TestCase;
import ar.uba.dc.maal.event.MessageEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;
import ar.uba.dc.maal.net.Node;
import ar.uba.dc.maal.net.NullNode;

/**
 * @author aisacovich
 * 
 * @version $Id: MessageEventTest.java,v 1.1 2003/11/06 23:45:13 alejandro Exp $
 */
public class MessageEventTest extends TestCase {

	SendEvent s1 = new SendEvent();
	ReceiveEvent r1 = new ReceiveEvent();
	
	SendEvent s2 = new SendEvent();
	ReceiveEvent r2 = new ReceiveEvent();
	
	Node n1 = new NullNode();
	Node n2 = new NullNode();

	MessageEvent m1 = new MessageEvent(s1,r1);
	MessageEvent m2 = new MessageEvent(s2,r2);

	/**
	 * Constructor for MessageEventTest.
	 * @param arg0
	 */
	public MessageEventTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		s1.setMessage("Un mensaje send");
		s2.setMessage("Un mensaje send");
		s1.setOwner(n1);
		s2.setOwner(n1);
		s1.setDestination(n2);
		s2.setDestination(n2);
		s1.setTimestamp(s2.getTimestamp());
				
		r1.setMessage("Un mensaje receive");
		r2.setMessage("Un mensaje receive");
		r1.setOwner(n2);
		r2.setOwner(n2);
		r1.setSource(n1);
		r2.setSource(n1);
		r1.setTimestamp(r2.getTimestamp());
		
		m1.setMessage("Un mensaje de mensaje");
		m2.setMessage("Un mensaje de mensaje");
		super.setUp();
	}
	
	/*
	 * Test for boolean equals(Object)
	 */
	public void testEquals() throws InterruptedException {
		assertEquals(m1,m2);
	}

	public void testNotEquals() throws InterruptedException {
		r2.setMessage("Otro Mensaje");
		assertFalse(m1.equals(m2));
	}


	public void testValidEquals() throws EqualsNotValidException {
		MessageEvent m3 = new MessageEvent(s1,r2);
		m3.setMessage("Un mensaje de mensaje");
		MessageEvent m4 = new MessageEvent(new SendEvent(),new ReceiveEvent());
		EqualsTester.testEqualsRestrictions(m1,m2,m3,m4);
	}

	
}
