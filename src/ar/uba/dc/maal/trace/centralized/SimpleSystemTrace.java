package ar.uba.dc.maal.trace.centralized;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.event.MessageEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;
import ar.uba.dc.maal.trace.EventNotFoundException;
import ar.uba.dc.maal.trace.EventsNotMatchException;
import ar.uba.dc.maal.trace.SystemTrace;

/**
 * Implementacion simple de un SystemTrace con baja performance.
 * @author aisacovich
 * 
 * @version $Id: SimpleSystemTrace.java,v 1.2 2003/10/16 21:36:02 alejandro Exp $
 */
public class SimpleSystemTrace implements SystemTrace {

	private List events = new LinkedList();
	private Collection messages = new HashSet();
	
	/**
	 * 
	 */
	public SimpleSystemTrace() {
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#addLocal(ar.uba.dc.maal.event.LocalEvent)
	 */
	public void addLocal(LocalEvent aLocalEvent) {
		events.add(aLocalEvent);
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#addMessage(ar.uba.dc.maal.event.MessageEvent)
	 */
	public void addMessage(MessageEvent aMessageEvent) throws EventNotFoundException, EventsNotMatchException {
		if (!events.contains(aMessageEvent.getSendEvent())) {
			throw new EventNotFoundException(aMessageEvent.getSendEvent());
		}
		if (!events.contains(aMessageEvent.getReceiveEvent())) {
			throw new EventNotFoundException(aMessageEvent.getReceiveEvent());
		}
		try {
			if (!this.getSendMatching(aMessageEvent.getReceiveEvent()).equals(aMessageEvent.getSendEvent())) {
				throw new EventsNotMatchException(aMessageEvent.getSendEvent(),aMessageEvent.getReceiveEvent());
			}
		} catch (EventNotFoundException e) {
			throw (EventsNotMatchException)new EventsNotMatchException(aMessageEvent.getSendEvent(),aMessageEvent.getReceiveEvent()).initCause(e);			
		}
		messages.add(aMessageEvent);
	}

	/**
	 * Version MUY POCO performante de getSendMatching
	 * @see ar.uba.dc.maal.trace.SystemTrace#getSendMatching(ar.uba.dc.maal.event.ReceiveEvent)
	 */
	public SendEvent getSendMatching(ReceiveEvent aReceiveEvent) throws EventNotFoundException {
		for (Iterator iter = events.iterator(); iter.hasNext();) {
			Object element = (Object) iter.next();
			if (element instanceof SendEvent) {
				SendEvent sendEvent = (SendEvent) element;
				if (aReceiveEvent.matches(sendEvent) && !messagesContains(sendEvent) && happenedBefore(sendEvent,aReceiveEvent) ) {
					return sendEvent;
				}
			}
		}
		throw new EventNotFoundException("No matching send event for [" + aReceiveEvent + "]" );
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#happenedBefore(ar.uba.dc.maal.event.LocalEvent, ar.uba.dc.maal.event.LocalEvent)
	 */
	public boolean happenedBefore(LocalEvent event1,LocalEvent event2) {
		// si event2 todavia no paso pero event1 si, entonces event1 happenedBefore event2 es true.
		if (events.indexOf(event1) != -1 && events.indexOf(event2) == -1) return true;
		return events.indexOf(event1)<events.indexOf(event2);
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#toList()
	 */
	public List toList() {
		final List log = new LinkedList();
		CollectionUtils.forAllDo(events,new Closure() {
			public void execute(Object arg0) {
				try {
					log.add(arg0);
					log.add(SimpleSystemTrace.this.findMessageOf((SendEvent)arg0));
				} catch (ClassCastException e) {
					// si no era un SendEvent, no se hace nada.
				}
			}
		});
		return log;
	}

	/**
	 * @param sendEvent
	 * @return
	 */
	private boolean messagesContains(final SendEvent sendEvent) {
		Object f = findMessageOf(sendEvent);
		return !(f==null);
	}
	
	/**
	 * @param sendEvent
	 * @return el mensaje cuyo send es <code>sendEvent<code> o null si no lo encuentra.
	 */
	private Object findMessageOf(final SendEvent sendEvent) {
		Object f = CollectionUtils.find(messages,new Predicate() {
			public boolean evaluate(Object arg0) {
				MessageEvent message = (MessageEvent) arg0;				 
				return message.getSendEvent().equals(sendEvent);
			}
		});
		return f;
	}
	
}
