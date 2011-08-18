package ar.uba.dc.maal.event.definition;


/**
 * @author mcastellani
 * @version $Id: ReceiveEventDefinition.java,v 1.5 2003/07/24 00:44:30 matias Exp $
 * 
 */
public class ReceiveEventDefinition extends EventDefinition {

	private NodeRetreiverDefinition sourceRetreiverDefinition;

	public ReceiveEventDefinition(){
		this(null, null, null);
	}

	/**
	 * 
	 * @see EventDefinition
	 * 
	 * @param method
	 * @param sourceRetreiverDefinition
	 * 
	 */
	public ReceiveEventDefinition( String name, MethodDefinition method, NodeRetreiverDefinition ownerRetreiverDefinition) {
		super(name, method);
		setSourceRetreiverDefinition(ownerRetreiverDefinition);
	}

	/**
	 * @return
	 */
	public NodeRetreiverDefinition getSourceRetreiverDefinition() {
		return sourceRetreiverDefinition;
	}

	/**
	 * @param retreiver
	 */
	public void setSourceRetreiverDefinition(NodeRetreiverDefinition retreiver) {
		sourceRetreiverDefinition = retreiver;
	}

}
