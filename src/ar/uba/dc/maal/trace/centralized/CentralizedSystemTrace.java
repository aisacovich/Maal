/***********************************************************************
 * Copyright (c) DataTransfer S.A., 2000-2002  Todos los derechos
 * reservados. Derechos no publicados reservados bajo las leyes de
 * derechos de copia de Argentina.
 * El software contenido en este medio es propiedad y parte de la
 * tecnologia confidencial de DataTransfer S.A. La posesion, uso,
 * duplicacion o diseminacion de este software o medio es autorizado
 * unicamente bajo licencia escrita de DataTransfer S.A.
 */
package ar.uba.dc.maal.trace.centralized;

import java.util.Iterator;
import java.util.List;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.event.MessageEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;
import ar.uba.dc.maal.trace.EventNotFoundException;
import ar.uba.dc.maal.trace.EventsNotMatchException;
import ar.uba.dc.maal.trace.SystemTrace;

/**
 * Este SystemTrace avisa a todos los NodeEventProcessors cuando llegó un evento de send
 * para que estos verifiquen si es el que estaban esperando y se destraben. 
 * @author aisacovich
 * 
 * @version $Id: CentralizedSystemTrace.java,v 1.1 2003/10/16 21:36:02 alejandro Exp $
 */
public class CentralizedSystemTrace implements SystemTrace {
	
	private DistributedEventsMerger merger = null;
	private SystemTrace centralizedLog = null;
	
	/**
	 * Construye un CentralizedSystemTrace asociado a un merger y a un centralizedLog. 
	 * @param centralizedLog
	 * @param merger
	 */
	public CentralizedSystemTrace(SystemTrace centralizedLog, DistributedEventsMerger merger) {
		this.setCentralizedLog(centralizedLog);
		this.merger = merger;
	}
	
	/**
	 * Loguea normalmente cualquier evento local. Si es un send, avisa a los procesadores que llegó
	 * un SendEvent para que revisen si tienen que destrabarse.
	 * @see ar.uba.dc.maal.trace.EventRaiser#raiseEvent(ar.uba.dc.maal.event.Event)
	 */
	public void addLocal(LocalEvent anEvent) {
		centralizedLog.addLocal(anEvent);
		if (anEvent instanceof SendEvent) {
			for (Iterator iter = merger.getNodeProcessors().iterator(); iter.hasNext();) {
				NodeEventProcessor nodeProcessor = (NodeEventProcessor) iter.next();
				nodeProcessor.sendEventReceived((SendEvent)anEvent);
			}
		}
	}
	
	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#addMessage(ar.uba.dc.maal.event.MessageEvent)
	 */
	public void addMessage(MessageEvent aMessageEvent) throws EventNotFoundException, EventsNotMatchException {
		this.getCentralizedLog().addMessage(aMessageEvent);
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#getSendMatching(ar.uba.dc.maal.event.ReceiveEvent)
	 */
	public SendEvent getSendMatching(ReceiveEvent aReceiveEvent) throws EventNotFoundException {
		return this.getCentralizedLog().getSendMatching(aReceiveEvent);
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#happenedBefore(ar.uba.dc.maal.event.LocalEvent, ar.uba.dc.maal.event.LocalEvent)
	 */
	public boolean happenedBefore(LocalEvent event1, LocalEvent event2) {
		return this.getCentralizedLog().happenedBefore(event1,event2);
	}

	/**
	 * @see ar.uba.dc.maal.trace.SystemTrace#toList()
	 */
	public List toList() {
		return this.getCentralizedLog().toList();
	}

	/**
	 * @return
	 */
	public SystemTrace getCentralizedLog() {
		return centralizedLog;
	}

	/**
	 * @param list
	 */
	public void setCentralizedLog(SystemTrace systemTrace) {
		centralizedLog = systemTrace;
	}

}
