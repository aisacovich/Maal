package ar.uba.dc.maal.trace;

import java.util.List;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.event.MessageEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;

/**
 * Decora un SystemTrace para que, cuando haya un nuevo evento en este, se levante 
 * un evento en un EventRaiser
 * @author aisacovich
 * 
 * @version $Id: EventRaiserSystemTraceDecorator.java,v 1.1 2003/11/06 23:41:59 alejandro Exp $
 */
public class EventRaiserSystemTraceDecorator implements SystemTrace {

	private EventRaiser raiser;
	private SystemTrace decoratee;

	/**
	 * 
	 */
	public EventRaiserSystemTraceDecorator(EventRaiser aRaiser, SystemTrace decoratee ) {
		this.setDecoratee(decoratee);
		this.setRaiser(aRaiser);
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#addLocal(ar.uba.dc.maal.event.LocalEvent)
	 */
	public void addLocal(LocalEvent aLocalEvent) {
		this.getDecoratee().addLocal(aLocalEvent);
		this.getRaiser().raiseEvent(aLocalEvent);
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#addMessage(ar.uba.dc.maal.event.MessageEvent)
	 */
	public void addMessage(MessageEvent aMessageEvent) throws EventNotFoundException, EventsNotMatchException {
		this.getDecoratee().addMessage(aMessageEvent);
		this.getRaiser().raiseEvent(aMessageEvent);
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#getSendMatching(ar.uba.dc.maal.event.ReceiveEvent)
	 */
	public SendEvent getSendMatching(ReceiveEvent aReceiveEvent) throws EventNotFoundException {
		return this.getDecoratee().getSendMatching(aReceiveEvent);
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#happenedBefore(ar.uba.dc.maal.event.LocalEvent, ar.uba.dc.maal.event.LocalEvent)
	 */
	public boolean happenedBefore(LocalEvent event1, LocalEvent event2) {
		return this.getDecoratee().happenedBefore(event1, event2);
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#toList()
	 */
	public List toList() {
		return getDecoratee().toList();
	}

	/**
	 * @return
	 */
	public SystemTrace getDecoratee() {
		return decoratee;
	}

	/**
	 * @return
	 */
	public EventRaiser getRaiser() {
		return raiser;
	}

	/**
	 * @param trace
	 */
	public void setDecoratee(SystemTrace trace) {
		decoratee = trace;
	}

	/**
	 * @param raiser
	 */
	public void setRaiser(EventRaiser raiser) {
		this.raiser = raiser;
	}

}
