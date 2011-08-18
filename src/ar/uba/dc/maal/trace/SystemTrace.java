package ar.uba.dc.maal.trace;

import java.util.List;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.event.MessageEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;

/**
 * Representa la traza global de un sistema (distribuido o no). Esta traza presenta un orden total de 
 * los eventos para cada nodo y un orden parcial para todo el sistema. Además, mantiene la causalidad
 * de los eventos entre dos nodos si hay registrado un mensaje entre ellos.
 * @author aisacovich
 * 
 * @version $Id: SystemTrace.java,v 1.2 2003/10/16 21:36:50 alejandro Exp $
 */
public interface SystemTrace {
	
	/**
	 * Agrega a la traza aLocalEvent 
	 * @param aLocalEvent
	 */
	public void addLocal(LocalEvent aLocalEvent);
	
	/**
	 * Agrega a la traza aMessageEvent. Los eventos Send y Receive deben estar registrados y se debe
	 * cumplir que getSendMatching(aMessageEvent.getReceiveEvent()).equals(aMessageEvent.getSendEvent)==true.
	 * @param aMessageEvent
	 */
	public void addMessage(MessageEvent aMessageEvent) throws EventNotFoundException,EventsNotMatchException;
	
	/**
	 * @param aReceiveEvent
	 * @return el primer SendEvent que machea con aReceiveEvent y no pertenece a ningun mensaje.
	 */
	public SendEvent getSendMatching(ReceiveEvent aReceiveEvent) throws EventNotFoundException;

	/**
	 * @param event1
	 * @param event2
	 * @return true si y solo si event1 esta en el trace y event2 sucedió después o todavia no sucedió.
	 */
	public boolean happenedBefore(LocalEvent event1,LocalEvent event2);
	
	public List toList(); 	
}
