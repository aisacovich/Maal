package ar.uba.dc.maal.event.test;

import java.util.Date;

import junit.framework.TestCase;
import ar.uba.dc.maal.event.Event;
import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.net.Node;
import ar.uba.dc.maal.net.NullNode;

/**
 * @author aisacovich
 * 
 * @version $Id: LocalEventTest.java,v 1.1 2003/11/06 23:45:13 alejandro Exp $
 */
public class LocalEventTest extends TestCase {

	LocalEvent ev1 = new LocalEvent();
	LocalEvent ev2 = new LocalEvent();
	Node n = new NullNode();
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		ev1.setMessage("Un Mensaje");
		ev2.setMessage("Un Mensaje");
		ev1.setOwner(n);
		ev2.setOwner(n);
		ev1.setTimestamp(ev2.getTimestamp());
	}

	/**
	 * Constructor for LocalEventTest.
	 * @param arg0
	 */
	public LocalEventTest(String arg0) {
		super(arg0);
	}

	/*
	 * Test for boolean equals(Object)
	 */
	public void testEquals() throws InterruptedException {
		assertEquals(ev1,ev2);
	}

	public void testNotEqualsMessage() throws InterruptedException {
		ev2.setMessage("Otro Mensaje");
		assertFalse(ev1.equals(ev2));
	}

	public void testNotEqualsOwner() throws InterruptedException {
		Node n2 = new NullNode();
		ev2.setOwner(n2);
		assertFalse(ev1.equals(ev2));
	}

	public void testNotEqualsTimestamp() throws InterruptedException {
		ev1.setTimestamp(new Date(ev2.getTimestamp().getTime() + 1));
		assertFalse(ev1.equals(ev2));
	}

	public void testDefault() {
		Event ev1 = new LocalEvent();
		Event ev2 = new LocalEvent();
		assertFalse(ev1.equals(ev2));
	}

	public void testValidEquals() throws EqualsNotValidException {
		LocalEvent ev3 = new LocalEvent();
		LocalEvent ev4 = new LocalEvent();
		ev3.setMessage("Un Mensaje");
		ev3.setOwner(n);
		ev3.setTimestamp(ev2.getTimestamp());
		EqualsTester.testEqualsRestrictions(ev1,ev2,ev3,ev4);
	}

}
