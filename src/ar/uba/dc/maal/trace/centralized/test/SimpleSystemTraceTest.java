/***********************************************************************
 * Copyright (c) DataTransfer S.A., 2000-2002  Todos los derechos
 * reservados. Derechos no publicados reservados bajo las leyes de
 * derechos de copia de Argentina.
 * El software contenido en este medio es propiedad y parte de la
 * tecnologia confidencial de DataTransfer S.A. La posesion, uso,
 * duplicacion o diseminacion de este software o medio es autorizado
 * unicamente bajo licencia escrita de DataTransfer S.A.
 */
package ar.uba.dc.maal.trace.centralized.test;

import junit.framework.TestCase;
import ar.uba.dc.maal.event.MessageEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;
import ar.uba.dc.maal.net.Node;
import ar.uba.dc.maal.net.test.MockNode;
import ar.uba.dc.maal.trace.EventNotFoundException;
import ar.uba.dc.maal.trace.EventsNotMatchException;
import ar.uba.dc.maal.trace.SystemTrace;
import ar.uba.dc.maal.trace.centralized.SimpleSystemTrace;

/**
 * @author aisacovich
 * 
 * @version $Id: SimpleSystemTraceTest.java,v 1.1 2003/10/16 21:35:34 alejandro Exp $
 */
public class SimpleSystemTraceTest extends TestCase {

	/**
	 * Constructor for SimpleSystemTraceTest.
	 * @param arg0
	 */
	public SimpleSystemTraceTest(String arg0) {
		super(arg0);
	}
	
	public void testAddMessageOK() throws EventNotFoundException, EventsNotMatchException {
		SystemTrace st = new SimpleSystemTrace();
		Node n1 = new MockNode("nodo1");
		Node n2 = new MockNode("nodo2");
		SendEvent se = new SendEvent(n1,n2);
		ReceiveEvent re = new ReceiveEvent(n2,n1);
		st.addLocal(se);
		st.addLocal(re);
		st.addMessage(new MessageEvent(se,re));
	}

	public void testAddMessageOK2() throws EventNotFoundException, EventsNotMatchException {
		SystemTrace st = new SimpleSystemTrace();
		Node n1 = new MockNode("nodo1");
		Node n2 = new MockNode("nodo2");
		SendEvent se1 = new SendEvent(n1,n2);
		SendEvent se2 = new SendEvent(n1,n2);
		se2.setTimestamp(se1.getTimestamp());
		se2.setMessage(se1.getMessage());
		ReceiveEvent re1 = new ReceiveEvent(n2,n1);
		ReceiveEvent re2 = new ReceiveEvent(n2,n1);
		re2.setTimestamp(re1.getTimestamp());
		re2.setMessage(re1.getMessage());
		st.addLocal(se1);
		st.addLocal(re1);
		st.addMessage(new MessageEvent(se2,re2));
	}
	
	public void testNotSendNotFound() throws  EventsNotMatchException {
		SystemTrace st = new SimpleSystemTrace();
		Node n1 = new MockNode("nodo1");
		Node n2 = new MockNode("nodo2");
		SendEvent se1 = new SendEvent(n1,n2);
		SendEvent se2 = new SendEvent(n1,n2);
		se2.setTimestamp(se1.getTimestamp());
		ReceiveEvent re1 = new ReceiveEvent(n2,n1);
		st.addLocal(se1);
		st.addLocal(re1);
		try {
			st.addMessage(new MessageEvent(se2,re1));
		} catch (EventNotFoundException e) {
			return;
		}
		fail("Deberia lanzar EventNotFoundException");
	}	

	public void testNotReceiveNotFound() throws  EventsNotMatchException {
		SystemTrace st = new SimpleSystemTrace();
		Node n1 = new MockNode("nodo1");
		Node n2 = new MockNode("nodo2");
		Node n3 = new MockNode("nodo3");
		SendEvent se1 = new SendEvent(n1,n2);
		ReceiveEvent re1 = new ReceiveEvent(n2,n1);
		ReceiveEvent re2 = new ReceiveEvent (n2,n2);
		re2.setTimestamp(re1.getTimestamp());
		re2.setMessage(re1.getMessage());
		st.addLocal(se1);
		st.addLocal(re1);
		try {
			st.addMessage(new MessageEvent(se1,re2));
		} catch (EventNotFoundException e) {
			return;
		}
		fail("Deberia lanzar EventNotFoundException");
	}	

	public void testSendNotOfReceiveSource() throws EventNotFoundException {
		SystemTrace st = new SimpleSystemTrace();
		Node n1 = new MockNode("nodo1");
		Node n2 = new MockNode("nodo2");
		Node n3 = new MockNode("nodo3");
		SendEvent se1 = new SendEvent(n1,n2);
		ReceiveEvent re1 = new ReceiveEvent(n2,n3);
		st.addLocal(se1);
		st.addLocal(re1);
		try {
			st.addMessage(new MessageEvent(se1,re1));
		} catch (EventsNotMatchException e) {
			return;
		}
		fail("Deberia lanzar EventsNotMatchException");
	}	
	
	public void testSendAfterReceive() throws EventNotFoundException {
		SystemTrace st = new SimpleSystemTrace();
		Node n1 = new MockNode("nodo1");
		Node n2 = new MockNode("nodo2");
		SendEvent se1 = new SendEvent(n1,n2);
		ReceiveEvent re1 = new ReceiveEvent(n2,n1);
		st.addLocal(re1);
		st.addLocal(se1);
		try {
			st.addMessage(new MessageEvent(se1,re1));
		} catch (EventsNotMatchException e) {
			return;
		}
		fail("Deberia lanzar EventsNotMatchException");
	}
}
