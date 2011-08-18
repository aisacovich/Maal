package ar.uba.dc.maal.event;

import java.io.Serializable;

/**
 * @author aisacovich
 * 
 * @version $Id: CompositeMessage.java,v 1.1 2003/11/06 23:45:14 alejandro Exp $
 */
public class CompositeMessage implements Serializable {

	protected Serializable message1;
	protected Serializable message2;
	
	/**
	 * @param message1
	 * @param message2
	 */
	public CompositeMessage(Serializable message1, Serializable message2) {
		this.message1=message1;
		this.message2=message2;
	}

	/**
	 * @return
	 */
	public Serializable getMessage1() {
		return message1;
	}

	/**
	 * @return
	 */
	public Serializable getMessage2() {
		return message2;
	}

	/**
	 * @param serializable
	 */
	public void setMessage1(Serializable serializable) {
		message1 = serializable;
	}

	/**
	 * @param serializable
	 */
	public void setMessage2(Serializable serializable) {
		message2 = serializable;
	}

	/**
	 * @see ar.uba.dc.maal.event.Event#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof CompositeMessage) {
			return equalsCompositeMessage((CompositeMessage) obj);
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param messageEvent que se quiere comparar con este objeto
	 * @return true si y solo si este objeto representa el mismo evento que messageEvent  
	 */
	private boolean equalsCompositeMessage(CompositeMessage compositeMessage) {
		return this.getMessage1().equals(compositeMessage.getMessage1()) && this.getMessage2().equals(compositeMessage.getMessage2());
	}
	
	/**
	 * @see ar.uba.dc.maal.event.Event#hashCode()
	 */
	public int hashCode() {
		return super.hashCode() + this.getMessage1().hashCode() + this.getMessage2().hashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + " Message1:[" + getMessage1() + "] Message2:[" +  getMessage2() +"]\n";
	}

	
}
