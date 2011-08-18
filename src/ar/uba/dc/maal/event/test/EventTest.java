package ar.uba.dc.maal.event.test;

import ar.uba.dc.maal.event.Event;
import junit.framework.TestCase;

/**
 * @author aisacovich
 * 
 * @version $Id: EventTest.java,v 1.1 2003/11/06 23:45:13 alejandro Exp $
 */
public class EventTest extends TestCase {

	class MockEvent extends Event {};
	
	/**
	 * Constructor for EventTest.
	 * @param arg0
	 */
	public EventTest(String arg0) {
		super(arg0);
	}

	/*
	 * Test for boolean equals(Object)
	 */
	public void testEquals() {
		Event ev1 = new MockEvent();
		Event ev2 = new MockEvent();
		ev1.setMessage("Un Mensaje");
		ev2.setMessage("Un Mensaje");
		assertEquals(ev1,ev2);
	}

	/*
	 * Test for boolean equals(Object)
	 */
	public void testDefault() {
		Event ev1 = new MockEvent();
		Event ev2 = new MockEvent();
		assertFalse(ev1.equals(ev2));
	}

	public void testNotEquals() {
		Event ev1 = new MockEvent();
		Event ev2 = new MockEvent();
		ev1.setMessage("Un Mensaje");
		ev2.setMessage("Otro Mensaje");
		assertFalse(ev1.equals(ev2));
	}
	
	public void testValidEquals() throws EqualsNotValidException {
		Event o1 = new MockEvent();
		Event o2 = new MockEvent();
		Event o3 = new MockEvent();
		Event o4 = new MockEvent();
		o1.setMessage("Un Mensaje");
		o2.setMessage("Un Mensaje");
		o3.setMessage("Un Mensaje");
		o4.setMessage("Otro Mensaje");
		EqualsTester.testEqualsRestrictions(o1,o2,o3,o4);
	}

}
