package ar.uba.dc.maal.trace.centralized.test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import ar.uba.dc.maal.event.Event;
import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.event.MessageEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;
import ar.uba.dc.maal.net.Node;
import ar.uba.dc.maal.net.test.MockNode;
import ar.uba.dc.maal.trace.EventRaiser;
import ar.uba.dc.maal.trace.centralized.DistributedEventsMerger;

/**
 * @author aisacovich
 * 
 * @version $Id: DistributedEventsMergerTest.java,v 1.6 2003/11/06 23:45:14 alejandro Exp $
 */
public class DistributedEventsMergerTest extends TestCase {

	private static final int TEST_EVENTS_COUNT=1;
	/**
	 * Constructor for DistributedEventsMergerTest.
	 * @param arg0
	 */
	public DistributedEventsMergerTest(String arg0) {
		super(arg0);
	}

	/**
	 * Solo hay un nodo asi que solo loguea eventos locales secuencialmente.
	 */
	public void testSimpleOneNodeMerge() {
		DistributedEventsMerger merger = new DistributedEventsMerger(new EventRaiser() {
			public void raiseEvent(Event anEvent) {
				System.out.println("Evento a log central:+ [" + anEvent + "]");
			}
		});
		Node owner = new MockNode("unNodo");
		List events = new LinkedList(); 
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			events.add(new LocalEvent(owner));
		}
		Iterator i = events.iterator();
		while (i.hasNext()) {
			LocalEvent event = (LocalEvent) i.next();
			merger.addEvent(event);
		}
		assertEquals(events,merger.getCentralizedLog().toList());
	}

	/**
	 * Dos nodos, un mensaje entre ellos cuyos eventos de send y receive llegan en el orden en que
	 * sucedieron. Por ende, no hay blockeo de ningun tipo.
	 */
	public void testSimpleTwoNodeStraightMerge() {
		DistributedEventsMerger merger = new DistributedEventsMerger(new EventRaiser() {
			public void raiseEvent(Event anEvent) {
				System.out.println("Evento a log central:+ [" + anEvent + "]");
			}
		});
		Node owner1 = new MockNode("Nodo 1");
		Node owner2 = new MockNode("Nodo 2");
		List comingEvents = new LinkedList(); 
		List expectedEvents = new LinkedList();
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			LocalEvent l1 = new LocalEvent(owner1);
			LocalEvent l2 = new LocalEvent(owner2);
			comingEvents.add(l1);
			comingEvents.add(l2);
			expectedEvents.add(l1);
			expectedEvents.add(l2);
		}
		SendEvent se1 = new SendEvent(owner1,owner2);
//		se1.setMessage("Send message");
		ReceiveEvent re1 = new ReceiveEvent(owner2,owner1);
//		re1.setMessage("Receive message");
		comingEvents.add(se1);
		expectedEvents.add(se1);
		MessageEvent m = new MessageEvent(se1,re1);
		expectedEvents.add(m);
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			LocalEvent l1 = new LocalEvent(owner1);
			LocalEvent l2 = new LocalEvent(owner2);
			comingEvents.add(l1);
			comingEvents.add(l2);
			expectedEvents.add(l1);
			expectedEvents.add(l2);
		}
		comingEvents.add(re1);
		expectedEvents.add(re1);
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			LocalEvent l1 = new LocalEvent(owner1);
			LocalEvent l2 = new LocalEvent(owner2);
			comingEvents.add(l1);
			comingEvents.add(l2);
			expectedEvents.add(l1);
			expectedEvents.add(l2);
		}
		Iterator i = comingEvents.iterator();
		while (i.hasNext()) {
			LocalEvent event = (LocalEvent) i.next();
			merger.addEvent(event);
		}
		assertEquals(expectedEvents,merger.getCentralizedLog().toList());
	}

	/**
	 * Dos nodos, un mensaje entre ellos cuyos eventos de send y receive llegan en el orden INVERSO
	 * al en que sucedieron. Por ende, los eventos que suceden luego del receive son blockeados hasta
	 * que se recive el send correspondiente.
	 */
	public void testSimpleTwoNodeReceiveBlockingMerge() {
		DistributedEventsMerger merger = new DistributedEventsMerger(new EventRaiser() {
			public void raiseEvent(Event anEvent) {
				System.out.println("Evento a log central:+ [" + anEvent + "]");
			}
		});
		Node owner1 = new MockNode("Nodo 1");
		Node owner2 = new MockNode("Nodo 2");
		List comingEvents = new LinkedList(); 
		List expectedEvents = new LinkedList();
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			Event e1 = new LocalEvent(owner1);
			Event e2 = new LocalEvent(owner2);
			comingEvents.add(e1);
			comingEvents.add(e2);
			expectedEvents.add(e1);
			expectedEvents.add(e2);
		}
		SendEvent sendEvent= new SendEvent(owner1,owner2);
		ReceiveEvent receiveEvent= new ReceiveEvent(owner2,owner1);
		comingEvents.add(receiveEvent);
		List blockedEvents =new LinkedList();
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			Event e1 = new LocalEvent(owner1);
			Event e2 = new LocalEvent(owner2);
			comingEvents.add(e1);
			comingEvents.add(e2);
			expectedEvents.add(e1);
			blockedEvents.add(e2);
		}
		comingEvents.add(sendEvent);
		expectedEvents.add(sendEvent);
		MessageEvent m = new MessageEvent(sendEvent,receiveEvent);
		expectedEvents.add(m);
		expectedEvents.add(receiveEvent);
		expectedEvents.addAll(blockedEvents);
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			Event e1 = new LocalEvent(owner1);
			Event e2 = new LocalEvent(owner2);
			comingEvents.add(e1);
			comingEvents.add(e2);
			expectedEvents.add(e1);
			expectedEvents.add(e2);
		}
		Iterator i = comingEvents.iterator();
		while (i.hasNext()) {
			LocalEvent event = (LocalEvent) i.next();
			merger.addEvent(event);
		}
		assertEquals(expectedEvents,merger.getCentralizedLog().toList());
	}

	/**
	 * Dos nodos (A y B), un mensaje M1 de A a B cuyos eventos de send y receive llegan en el orden INVERSO
	 * al que sucedieron. Por ende, los eventos que suceden luego del receive son bloqueados hasta
	 * que se recibe el send correspondiente. Además, entre los eventos bloqueados se encuentra un 
	 * <code>SendEvent</code> de un mensaje M2 de respuesta de B a A.
	 * Al llegar el evento Send de M1 se desbloquea la cola de B y se procesan los eventos pendientes.
	 * Luego se envia el <code>ReceiveEvent</code> de M2.
	 * Este test no envia eventos nuevos mientras se estan procesando otros eventos, es decir, es single 
	 * threaded.
	 */
	public void testCallBackTwoNodeReceiveBlockingMerge() {
		DistributedEventsMerger merger = new DistributedEventsMerger(new EventRaiser() {
			public void raiseEvent(Event anEvent) {
				System.out.println("Evento a log central:+ [" + anEvent + "]");
			}
		});
		Node owner1 = new MockNode("Nodo A");
		Node owner2 = new MockNode("Nodo B");
		List comingEvents = new LinkedList(); 
		List expectedEvents = new LinkedList();
		
		// Envio eventos locales para llenar el log
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			Event e1 = new LocalEvent(owner1);
			Event e2 = new LocalEvent(owner2);
			comingEvents.add(e1);
			comingEvents.add(e2);
			expectedEvents.add(e1);
			expectedEvents.add(e2);
		}
		//Envio el receive, pero no el send
		SendEvent sendEvent= new SendEvent(owner1,owner2);
		ReceiveEvent receiveEvent= new ReceiveEvent(owner2,owner1);
		comingEvents.add(receiveEvent);
		
		//Envio mas eventos locales de A y de B, en el nodo B encola los eventos 
		List blockedEvents =new LinkedList();
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			Event e1 = new LocalEvent(owner1);
			Event e2 = new LocalEvent(owner2);
			comingEvents.add(e1);
			comingEvents.add(e2);
			expectedEvents.add(e1);
			blockedEvents.add(e2);
		}
		SendEvent sendCallBackEvent= new SendEvent(owner2,owner1);
		ReceiveEvent receiveCallBackEvent= new ReceiveEvent(owner1,owner2);
		
		//Envio el send del callback, que se encola.
		comingEvents.add(sendCallBackEvent);
		blockedEvents.add(sendCallBackEvent);
		MessageEvent m = new MessageEvent(sendCallBackEvent,receiveCallBackEvent);
		blockedEvents.add(m);
		
		//ACA NO PUEDO ENVIAR EL RECEIVE DEL CALLBACK PORQUE TODAVIA NO LLEGO EL SEND
		//DEL MENSAJE ORIGINAL. SE SUPONE QUE LOS MENSAJES DE UN MISMO NODO DEBEN LLEGAR
		//ORDENADOS.
		
		//Envio el send del primer mensaje, que destraba la cola encola de B.
		comingEvents.add(sendEvent);
		expectedEvents.add(sendEvent);
		expectedEvents.add(new MessageEvent(sendEvent,receiveEvent));
		
		//Computo el desbloqueo para el test
		expectedEvents.add(receiveEvent);
		expectedEvents.addAll(blockedEvents);
		
		//Envio el receive del callback
		comingEvents.add(receiveCallBackEvent);
		expectedEvents.add(receiveCallBackEvent);

		// Envio eventos locales para completar el log
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			Event e1 = new LocalEvent(owner1);
			Event e2 = new LocalEvent(owner2);
			comingEvents.add(e1);
			comingEvents.add(e2);
			expectedEvents.add(e1);
			expectedEvents.add(e2);
		}
		
		// Ejecuto el test
		Iterator i = comingEvents.iterator();
		while (i.hasNext()) {
			LocalEvent event = (LocalEvent) i.next();
			merger.addEvent(event);
		}
		assertEquals(expectedEvents,merger.getCentralizedLog().toList());
	}


	/**
	 * Dos nodos (A y B), un mensaje M1 de A a B cuyos eventos de send y receive llegan en el orden INVERSO
	 * al que sucedieron. Por ende, los eventos que suceden luego del receive son bloqueados hasta
	 * que se recibe el send correspondiente. Además, entre los eventos bloqueados se encuentra un 
	 * <code>ReceiveEvent</code> de otro mensaje (M2) A a B.
	 * Al llegar el evento Send de M1 se desbloquea la cola de B y se procesan los eventos pendientes.
	 * Luego se envia el <code>SendEvent</code> de M2 y se terminan de procesar los mensajes.
	 * Este test no envia eventos nuevos mientras se estan procesando otros eventos, es decir, es single 
	 * threaded.
	 */
	public void testTwoLevelNestedTwoNodeReceiveBlockingMerge() {
		DistributedEventsMerger merger = new DistributedEventsMerger(new EventRaiser() {
			public void raiseEvent(Event anEvent) {
				System.out.println("Evento a log central:+ [" + anEvent + "]");
			}
		});
		Node owner1 = new MockNode("Nodo A");
		Node owner2 = new MockNode("Nodo B");
		List comingEvents = new LinkedList(); 
		List expectedEvents = new LinkedList();
		
		// Envio eventos locales para llenar el log
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			Event e1 = new LocalEvent(owner1);
			Event e2 = new LocalEvent(owner2);
			comingEvents.add(e1);
			comingEvents.add(e2);
			expectedEvents.add(e1);
			expectedEvents.add(e2);
		}
		
		// Construyo los eventos de los dos mensajes M1 y M2
		SendEvent sendEventM1= new SendEvent(owner1,owner2,"SendEvent1");
		ReceiveEvent receiveEventM1= new ReceiveEvent(owner2,owner1,"ReceiveEvent1");
		SendEvent sendEventM2= new SendEvent(owner1,owner2,"SendEvent2");
		ReceiveEvent receiveEventM2= new ReceiveEvent(owner2,owner1,"ReceiveEvent2");

		//	Envio el receive de M1, pero no el send
		comingEvents.add(receiveEventM1);
		
		//Envio mas eventos locales de A y de B, en el nodo B encola los eventos 
		List blockedEventsM1 =new LinkedList();
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			Event e1 = new LocalEvent(owner1);
			Event e2 = new LocalEvent(owner2);
			comingEvents.add(e1);
			comingEvents.add(e2);
			expectedEvents.add(e1);
			blockedEventsM1.add(e2);
		}
		
		//	Envio el receive de M2, pero no el send
		comingEvents.add(receiveEventM2);
		
		//Envio mas eventos locales de A y de B, en el nodo B sigue encolando los eventos
		List blockedEventsM2 =new LinkedList(); 
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			Event e1 = new LocalEvent(owner1);
			Event e2 = new LocalEvent(owner2);
			comingEvents.add(e1);
			comingEvents.add(e2);
			expectedEvents.add(e1);
			blockedEventsM2.add(e2);
		}
	
		//Envio el send del primer mensaje, que destraba la cola encola de B.
		comingEvents.add(sendEventM1);
		expectedEvents.add(sendEventM1);
		expectedEvents.add(new MessageEvent(sendEventM1,receiveEventM1));

		//Computo el desbloqueo para el test
		expectedEvents.add(receiveEventM1);
		expectedEvents.addAll(blockedEventsM1);

		// Ejecuto el test hasta aca para chequear que no se hayan desbloqueado los eventos
		// que llegaron despues de receiveEventM2 
		Iterator iter = comingEvents.iterator();
		while (iter.hasNext()) {
			LocalEvent event = (LocalEvent) iter.next();
			merger.addEvent(event);
		}
		assertEquals(expectedEvents,merger.getCentralizedLog().toList());

		List moreComingEvents = new LinkedList(); 
		
		//Envio el send del segundo mensaje, que destraba la cola encola de B.
		moreComingEvents.add(sendEventM2);
		expectedEvents.add(sendEventM2);
		expectedEvents.add(new MessageEvent(sendEventM2,receiveEventM2));
		//Computo el desbloqueo para el test
		expectedEvents.add(receiveEventM2);
		expectedEvents.addAll(blockedEventsM2);

		// Envio eventos locales para completar el log
		for (int i = 0; i < TEST_EVENTS_COUNT; i++) {
			Event e1 = new LocalEvent(owner1);
			Event e2 = new LocalEvent(owner2);
			moreComingEvents.add(e1);
			moreComingEvents.add(e2);
			expectedEvents.add(e1);
			expectedEvents.add(e2);
		}
		
		// Ejecuto el resto del test
		Iterator i = moreComingEvents.iterator();
		while (i.hasNext()) {
			LocalEvent event = (LocalEvent) i.next();
			merger.addEvent(event);
		}
		assertEquals(expectedEvents,merger.getCentralizedLog().toList());
	}

}
