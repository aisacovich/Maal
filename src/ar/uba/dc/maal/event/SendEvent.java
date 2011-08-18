package ar.uba.dc.maal.event;

import java.io.Serializable;

import ar.uba.dc.maal.net.Node;
import ar.uba.dc.maal.net.NullNode;
import ar.uba.dc.maal.trace.centralized.EventLogger;


/**
 * Evento para sincronización de logs distribuidos. Significa que <code>owner</code> envió un mensaje a
 * <code>destination</code>. 
 * @author aisacovich
 * 
 * @version $Id: SendEvent.java,v 1.8 2003/11/06 23:34:42 alejandro Exp $
 */
public class SendEvent extends LocalEvent {
	private Node destination= new NullNode();
	
	/**
	 * Constructor vacío para cumplir con bean.
	 *
	 */
	public SendEvent() {
		super();
	}
	
	/**
	 * Constructor helper para facilitar
	 * @param anOwner el nodo donde se produjo el evento
	 */
	public SendEvent(Node anOwner) {
		super(anOwner);
	}

	/**
	 * Constructor helper para facilitar
	 * @param anOwner el nodo donde se produjo el evento
	 * @param destination el nodo que recivió el mensaje
	 */
	public SendEvent(Node anOwner,Node destination) {
		super(anOwner);
		this.setDestination(destination);
	}

	/**
	 * Constructor helper para facilitar
	 * @param anOwner el nodo donde se produjo el evento
	 * @param destination el nodo que recivió el mensaje
	 * @param message la semantica del evento.
	 */
	public SendEvent(Node anOwner,Node destination, Serializable message) {
		super(anOwner,message);
		this.setDestination(destination);
	}

	/**
	 * @see ar.uba.dc.maal.event.LocalEvent#toString()
	 */
	public String toString() {
		return super.toString() + " Destination:[" + getDestination() + "]";
	}

	/**
	 * @return the destination
	 */
	public Node getDestination() {
		return destination;
	}

	/**
	 * @param node
	 */
	public void setDestination(Node node) {
		destination = node;
	}
	
	/**
	 * @param receiveEvent
	 * @return true si y solo si <code>receiveEvent</code> es un evento de receive que corresponde a este <code>sendEvent</code>
	 */
	public boolean matches(ReceiveEvent receiveEvent) {
		return this.getDestination().equals(receiveEvent.getOwner()) && this.getOwner().equals(receiveEvent.getSource());
	}
	
	
	/**
	 * @param sendEvent
	 * @return true si y solo si <code>sendEvent</code> es un evento equivalente a mi tanto en owner como en destination.  
	 */
	public boolean matches(SendEvent sendEvent) {
		return this.getOwner().equals(sendEvent.getOwner()) && this.getDestination().equals(sendEvent.getDestination());
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof SendEvent) {
			return equalsSendEvent((SendEvent) obj);
		}
		else {
			return false;
		}
	}

	/**
	 * @param sendEvent que se quiere comparar con este objeto
	 * @return true si y solo si este objeto representa el mismo evento que sendEvent  
	 */
	private boolean equalsSendEvent(SendEvent sendEvent) {
		return super.equals(sendEvent) &&
				this.getDestination().equals(sendEvent.getDestination());
	}

	/**
	 * @see ar.uba.dc.maal.event.LocalEvent#hashCode()
	 */
	public int hashCode() {
		return super.hashCode() + this.getDestination().hashCode();
	}

	/**
	 * @see ar.uba.dc.maal.event.LocalEvent#logAt(ar.uba.dc.maal.trace.centralized.EventLogger)
	 */
	public void logAt(EventLogger logger) {
		logger.logSendEvent(this);
	}

}
