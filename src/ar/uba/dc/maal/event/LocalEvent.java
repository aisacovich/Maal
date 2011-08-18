package ar.uba.dc.maal.event;

import java.io.Serializable;
import java.util.Date;

import ar.uba.dc.maal.net.Node;
import ar.uba.dc.maal.net.NullNode;
import ar.uba.dc.maal.trace.centralized.EventLogger;


/**
 * @author aisacovich
 * 
 * @version $Id: LocalEvent.java,v 1.7 2003/11/06 23:31:03 alejandro Exp $
 */
public class LocalEvent extends Event {
	private Node owner = new NullNode();
	private Date timestamp=new Date();
	
	public LocalEvent() {
	}
	
	public LocalEvent(Node anOwner) {
		this.setOwner(anOwner);
	}

	public LocalEvent(Node anOwner,Date timestamp) {
		this.setOwner(anOwner);
		this.setTimestamp(timestamp);
	}	

	/**
	 * @param anOwner
	 * @param message
	 */
	public LocalEvent(Node anOwner, Serializable message) {
		this(anOwner);
		this.setMessage(message);
	}
	
	public Node getOwner() {
		return owner;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + " Owner:[" + getOwner() + "]" + " Timestamp:[" + getTimestamp() + "]";
	}

	/**
	 * @param node
	 */
	public void setOwner(Node node) {
		owner = node;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof LocalEvent) {
			return equalsLocalEvent((LocalEvent) obj);
		}
		else {
			return false;
		}
	}

	/**
	 * @param localEvent que se quiere comprar con este objeto
	 * @return true si y solo si este objeto representa el mismo evento que localEvent  
	 */
	private boolean equalsLocalEvent(LocalEvent localEvent) {
		return super.equals(localEvent) && 
				this.getOwner().equals(localEvent.getOwner()) && 
				this.getTimestamp().equals(localEvent.getTimestamp());
	}

	/**
	 * @return
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param date
	 */
	public void setTimestamp(Date date) {
		timestamp = date;
	}

	/**
	 * @param logger
	 */
	public void logAt(EventLogger logger) {
		logger.logLocalEvent(this);
	}

	/**
	 * @see ar.uba.dc.maal.event.Event#hashCode()
	 */
	public int hashCode() {
		return super.hashCode() + this.getOwner().hashCode();
	}

}
