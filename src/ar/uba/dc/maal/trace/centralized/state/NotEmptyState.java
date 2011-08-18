/***********************************************************************
 * Copyright (c) DataTransfer S.A., 2000-2002  Todos los derechos
 * reservados. Derechos no publicados reservados bajo las leyes de
 * derechos de copia de Argentina.
 * El software contenido en este medio es propiedad y parte de la
 * tecnologia confidencial de DataTransfer S.A. La posesion, uso,
 * duplicacion o diseminacion de este software o medio es autorizado
 * unicamente bajo licencia escrita de DataTransfer S.A.
 */
package ar.uba.dc.maal.trace.centralized.state;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;
import ar.uba.dc.maal.trace.EventRaiser;
import ar.uba.dc.maal.trace.centralized.DistributedEventsMerger;
import ar.uba.dc.maal.trace.centralized.NodeEventProcessor;


/**
 * No estoy empty porque estoy esperando un evento de send.
 * El primer elemento de la cola que tengo es el receive cuyo send todavía no reciví. 
 * @author aisacovich
 * 
 * @version $Id: NotEmptyState.java,v 1.4 2003/09/03 00:31:41 alejandro Exp $
 */
class NotEmptyState extends NodeEventProcessorState {

	private List eventsQueue=new LinkedList();
	private EventRaiser raiser=null;
	private ReceiveEvent blockingEvent =null; 
	
	/**
	 * @param merger
	 * @param parent
	 */
	public NotEmptyState(DistributedEventsMerger merger, NodeEventProcessor parent,ReceiveEvent blockingEvent) {
		super(merger, parent);
		this.setBlockingEvent(blockingEvent);
		this.addEvent(this.getBlockingEvent());
	}

	public void addEvent(LocalEvent anEvent) {
		getEventsQueue().add(anEvent);
	}

	public void addEvent(SendEvent anEvent) {
		getEventsQueue().add(anEvent);
	}

	public void addEvent(ReceiveEvent anEvent) {
		getEventsQueue().add(anEvent);
	}
	/**
	 * @return
	 */
	public EventRaiser getRaiser() {
		return raiser;
	}

	/**
	 * @param raiser
	 */
	public void setRaiser(EventRaiser raiser) {
		this.raiser = raiser;
	}

	/**
	 * Primero cambio de estado a empty. 
	 * No estoy empty porque estoy esperando un evento de send.
	 * Si este send corresponde con el receive que tengo pendiente, me destrabo y envio los eventos que tengo. 
	 * @see ar.uba.dc.maal.trace.centralized.state.NodeEventProcessorState#sendEventReceived(ar.uba.dc.maal.event.SendEvent)
	 */
	public void sendEventReceived(SendEvent sendEvent) {
		if (sendEvent.matches(getBlockingEvent())) {
			setStateToEmpty();
			redispatchQueuedEvents();
		}
	}

	private void redispatchQueuedEvents() {
		CollectionUtils.forAllDo(this.getEventsQueue(),new Closure() {
			public void execute(Object anEvent) {
				getMerger().addEvent((LocalEvent) anEvent);
			}
		});
	}

	private void setStateToEmpty() {
		this.getParent().setState(new EmptyState(this.getMerger(),this.getParent()));
	}

	/**
	 * @return
	 */
	public List getEventsQueue() {
		return eventsQueue;
	}

	/**
	 * @param stack
	 */
	public void setEventsQueue(List queue) {
		eventsQueue = queue;
	}

	private void setBlockingEvent(ReceiveEvent blockingEvent) {
		this.blockingEvent = blockingEvent;
	}

	private ReceiveEvent getBlockingEvent() {
		return blockingEvent;
	}

}