package ar.uba.dc.maal.event.definition;

/**
 * Define un evento que engloba dos eventos distintos, uno de send y otro de receive.
 * 
 * @author mcastellani
 * @version $Id: MessageDefinition.java,v 1.2 2003/07/23 23:38:45 matias Exp $
 * 
 */
public class MessageDefinition {

	private SendEventDefinition sendEventDefinition;
	private ReceiveEventDefinition receiveEventDefinition;

	public MessageDefinition(){
		this(null, null);
	}

	/**
	 * 
	 */
	public MessageDefinition(SendEventDefinition sendEventDefinition, ReceiveEventDefinition receiveEventDefinition) {
		setSendEventDefinition(sendEventDefinition);
		setReceiveEventDefinition(receiveEventDefinition);
	}

	/**
	 * @return
	 */
	public ReceiveEventDefinition getReceiveEventDefinition() {
		return receiveEventDefinition;
	}

	/**
	 * @return
	 */
	public SendEventDefinition getSendEventDefinition() {
		return sendEventDefinition;
	}

	/**
	 * @param definition
	 */
	public void setReceiveEventDefinition(ReceiveEventDefinition definition) {
		receiveEventDefinition = definition;
	}

	/**
	 * @param definition
	 */
	public void setSendEventDefinition(SendEventDefinition definition) {
		sendEventDefinition = definition;
	}

}
