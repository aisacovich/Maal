package ar.uba.dc.maal.event;

/**
 * Representa un mensaje enviado entre dos nodos del sistema.
 * @author aisacovich
 * 
 * @version $Id: MessageEvent.java,v 1.2 2003/11/06 23:31:51 alejandro Exp $
 */
public class MessageEvent extends Event {
	private SendEvent aSendEvent;
	private ReceiveEvent aReceiveEvent;

	public MessageEvent(SendEvent aSendEvent, ReceiveEvent aReceiveEvent) {
		this.setReceiveEvent(aReceiveEvent);
		this.setSendEvent(aSendEvent);
		this.setMessage(new CompositeMessage(this.getSendEvent().getMessage(),this.getReceiveEvent().getMessage()));
	}

	protected void setReceiveEvent(ReceiveEvent aReceiveEvent) {
		this.aReceiveEvent = aReceiveEvent;
	}

	public ReceiveEvent getReceiveEvent() {
		return aReceiveEvent;
	}

	protected void setSendEvent(SendEvent aSendEvent) {
		this.aSendEvent = aSendEvent;
	}

	public SendEvent getSendEvent() {
		return aSendEvent;
	}

	/**
	 * @see ar.uba.dc.maal.event.Event#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof MessageEvent) {
			return equalsMessageEvent((MessageEvent) obj);
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param messageEvent que se quiere comparar con este objeto
	 * @return true si y solo si este objeto representa el mismo evento que messageEvent  
	 */
	private boolean equalsMessageEvent(MessageEvent messageEvent) {
		return super.equals(messageEvent) &&
				this.getReceiveEvent().equals(messageEvent.getReceiveEvent()) && this.getSendEvent().equals(messageEvent.getSendEvent());
	}
	
	/**
	 * @see ar.uba.dc.maal.event.Event#hashCode()
	 */
	public int hashCode() {
		return super.hashCode() + this.getReceiveEvent().hashCode() + this.getSendEvent().hashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + " SendEvent:[" + getSendEvent() + "] ReceiveEvent:[" +  getReceiveEvent() +"]\n";
	}

}
