package ar.uba.dc.maal.event;

import java.io.Serializable;

import ar.uba.dc.maal.net.Node;
import ar.uba.dc.maal.net.NullNode;
import ar.uba.dc.maal.trace.centralized.EventLogger;

/**
 * Evento para sincronización de logs distribuidos. Significa que <code>owner</code> recivió un mensaje de
 * <code>source</code>. 
 * @author aisacovich
 * 
 * @version $Id: ReceiveEvent.java,v 1.8 2003/11/06 23:33:22 alejandro Exp $
 */
public class ReceiveEvent extends LocalEvent {
	private Node source= new NullNode();

	/**
	 * Constructor vacío para cumplir con bean.
	 *
	 */
	public ReceiveEvent() {
		super();
	}
	
	/**
	 * Constructor helper para facilitar
	 * @param anOwner el nodo donde se produjo el evento
	 */
	public ReceiveEvent(Node anOwner) {
		super(anOwner);
	}

	/**
	 * Constructor helper para facilitar
	 * @param anOwner el nodo donde se produjo el evento
	 * @param source el nodo de donde provino el mensaje
	 */
	public ReceiveEvent(Node anOwner, Node source) {
		super(anOwner);
		this.setSource(source);
	}

	/**
	 * Constructor helper para facilitar
	 * @param anOwner el nodo donde se produjo el evento
	 * @param destination el nodo que recivió el mensaje
	 * @param message la semantica del evento.
	 */
	public ReceiveEvent(Node anOwner,Node source, Serializable message) {
		super(anOwner,message);
		this.setSource(source);
	}
	
	/**
	 * @see ar.uba.dc.maal.event.LocalEvent#toString()
	 */
	public String toString() {
		return super.toString() + " Source:[" + getSource() + "]";
	}

	/**
	 * @return
	 */
	public Node getSource() {
		return source;
	}

	/**
	 * @param node
	 */
	public void setSource(Node node) {
		source = node;
	}
	
	/**
	 * @param sendEvent
	 * @return true si y solo si <code>sendEvent</code> es un evento de send que corresponde a este <code>ReceiveEvent</code> en cuanto a source y destination.
	 */
	public boolean matches(SendEvent sendEvent) {
		return sendEvent.matches(this);
	}

	/**
	 * @param receiveEvent
	 * @return true si y solo si <code>receiveEvent</code> es un evento equivalente a mi tanto en owner como en source.  
	 */
	public boolean matches(ReceiveEvent receiveEvent) {
		return this.getOwner().equals(receiveEvent.getOwner()) && this.getSource().equals(receiveEvent.getSource());
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ReceiveEvent) {
			return equalsReceiveEvent((ReceiveEvent) obj);
		}
		else {
			return false;
		}
	}

	/**
	 * @param receiveEvent que se quiere comprar con este objeto
	 * @return true si y solo si este objeto representa el mismo evento que receiveEvent  
	 */
	private boolean equalsReceiveEvent(ReceiveEvent receiveEvent) {
		return super.equals(receiveEvent) &&
				this.getSource().equals(receiveEvent.getSource());
	}

	/**
	 * @see ar.uba.dc.maal.event.LocalEvent#hashCode()
	 */
	public int hashCode() {
		return super.hashCode() + this.getSource().hashCode();
	}

	/**
	 * @see ar.uba.dc.maal.event.LocalEvent#logAt(ar.uba.dc.maal.trace.centralized.EventLogger)
	 */
	public void logAt(EventLogger logger) {
		logger.logReceiveEvent(this);
	}

}

