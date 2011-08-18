package ar.uba.dc.maal.event.definition;

import java.io.Serializable;


/**
 * Definición de un evento.
 * Consta de las siguientes partes:
 * 
 * Method Definition:	Método que indica produce el evento.
 * 
 * Mensaje que identifica el evento.
 * 
 * Definition del nodo que va a originar el evento.
 * 
 * @author mcastellani
 * @version $Id: EventDefinition.java,v 1.9 2003/08/09 21:00:29 matias Exp $
 * 
 */
public class EventDefinition {

	private MethodDefinition methodDefinition;
	private NodeRetreiverDefinition nodeRetrieverDefinition;
	private Serializable msg;
	
	public EventDefinition(){
	}
	
	public EventDefinition(Serializable msg, MethodDefinition method){
		setMethodDefinition(method);
		setMsg(msg);
	}


	/**
	 * Retorna la definición del método
	 * 
	 * @return MethodDefinition
	 */
	public MethodDefinition getMethodDefinition() {
		return methodDefinition;
	}

	/**
	 * @param definition
	 */
	public void setMethodDefinition(MethodDefinition definition) {
		methodDefinition = definition;
	}


	/**
	 * @return
	 */
	public Serializable getMsg() {
		return msg;
	}

	/**
	 * @param string
	 */
	public void setMsg(Serializable msg) {
		this.msg = msg;
	}

	/**
	 * @return
	 */
	public NodeRetreiverDefinition getNodeRetrieverDefinition() {
		return nodeRetrieverDefinition;
	}

	/**
	 * @param definition
	 */
	public void setNodeRetrieverDefinition(NodeRetreiverDefinition definition) {
		nodeRetrieverDefinition = definition;
	}

}
