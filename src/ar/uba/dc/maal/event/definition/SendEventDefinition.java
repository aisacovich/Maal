package ar.uba.dc.maal.event.definition;


/**
 * Define el evento de envio de un mensaje.
 * A diferencia de un evento simple, este tiene que proveer a quien va a enviar el mensaje..
 * 
 * @author mcastellani
 * @version $Id: SendEventDefinition.java,v 1.6 2003/07/24 00:44:30 matias Exp $
 * 
 */
public class SendEventDefinition extends EventDefinition {

	private NodeRetreiverDefinition destinationRetreiverDefinition;

	public SendEventDefinition(){
		this(null, null, null);
	}

	/**
	 * 
	 * @see EventDefinition
	 * 
	 * @param method
	 * @param destinationRetreiverDefinition
	 * 
	 */
	public SendEventDefinition( String name, MethodDefinition method, NodeRetreiverDefinition destinationRetreiver) {
		super(name, method);
		setDestinationRetreiverDefinition(destinationRetreiver);
	}

	/**
	 * @return
	 */
	public NodeRetreiverDefinition getDestinationRetreiverDefinition() {
		return destinationRetreiverDefinition;
	}

	/**
	 * @param provider
	 */
	public void setDestinationRetreiverDefinition(NodeRetreiverDefinition provider) {
		destinationRetreiverDefinition = provider;
	}

}
