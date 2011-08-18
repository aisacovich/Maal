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

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.event.MessageEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;
import ar.uba.dc.maal.trace.EventException;
import ar.uba.dc.maal.trace.EventNotFoundException;
import ar.uba.dc.maal.trace.centralized.DistributedEventsMerger;
import ar.uba.dc.maal.trace.centralized.NodeEventProcessor;


public class EmptyState extends NodeEventProcessorState {
	
	/**
	 * @param raiser
	 */
	public EmptyState(DistributedEventsMerger merger,NodeEventProcessor parent) {
		super(merger,parent);
	}

	/**
	 * Como este nodo no tiene nada pendiente solo hay que registralo en el central. 
	 * @see ar.uba.dc.maal.trace.centralized.NodeEventProcessorState#addEvent(ar.uba.dc.maal.event.LocalEvent)
	 */
	public void addEvent(LocalEvent anEvent) { 
		this.getCentralizedLog().addLocal(anEvent);	
	}

	/**
	 * Como este nodo no tiene nada pendiente puedo registralo en el central directamente.
	 * Como es un send, puede que haya un nodo que tenga pendiente el receive esperando por este send
	 * entonces tengo que avisarle al resto de los nodos que llegó este send. Esto lo hace el eventRaiser.
	 * @see ar.uba.dc.maal.trace.centralized.NodeEventProcessorState#addEvent(ar.uba.dc.maal.event.SendEvent)
	 */
	public void addEvent(SendEvent anEvent) {
		this.getCentralizedLog().addLocal(anEvent);
	}

	/**
	 * Si hay un send logueado que corresponde a este receive, no hay problema y se loguea.
	 * Si no lo hay, pasa al estado <code>NotEmptyState</code> con <code>anEvent</code> como blocking event.
	 * @see ar.uba.dc.maal.trace.centralized.state.NodeEventProcessorState#addEvent(ar.uba.dc.maal.event.ReceiveEvent)
	 */
	public void addEvent(final ReceiveEvent aReceiveEvent) {
		SendEvent theSend;
		try {
			theSend = this.getCentralizedLog().getSendMatching(aReceiveEvent);
		} catch (EventNotFoundException e) {
			this.getParent().setState(new NotEmptyState(this.getMerger(),this.getParent(),aReceiveEvent));
			return;
		}
		this.addMessage(aReceiveEvent, theSend);
	}

	private void addMessage(final ReceiveEvent aReceiveEvent, SendEvent theSend) {
		this.getCentralizedLog().addLocal(aReceiveEvent);
		MessageEvent theMessage=new  MessageEvent(theSend,aReceiveEvent);
		try {
			this.getCentralizedLog().addMessage(theMessage);
		} catch (EventException e) {
			throw (IllegalStateException) new IllegalStateException("Las condiciones fueron chequeadas.").initCause(e);
		}
	}

	/**
	 * No hago nada porque no tengo nada pendiente.
	 * @see ar.uba.dc.maal.trace.centralized.state.NodeEventProcessorState#sendEventReceived(ar.uba.dc.maal.event.SendEvent)
	 */
	public void sendEventReceived(SendEvent event) {
	}

}