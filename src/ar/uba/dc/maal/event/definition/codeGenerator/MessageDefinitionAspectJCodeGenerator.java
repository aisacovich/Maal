package ar.uba.dc.maal.event.definition.codeGenerator;

import java.io.IOException;

import ar.uba.dc.maal.event.definition.MessageDefinition;

/**
 * Genera aspectos para los mensajes de alto nivel
 * 
 * @author mcastellani
 * @version $Id: MessageDefinitionAspectJCodeGenerator.java,v 1.2 2003/08/06 00:32:04 matias Exp $
 * 
 */
public class MessageDefinitionAspectJCodeGenerator {

	private String path;
	private ReceiveEventDefinitionAspectJCodeGenerator receiveCodeGenerator;
	private SendEventDefinitionAspectJCodeGenerator sendCodeGenerator;

	public MessageDefinitionAspectJCodeGenerator(String path) throws IOException{
		setPath(path);
		setReceiveCodeGenerator(new ReceiveEventDefinitionAspectJCodeGenerator(getPath()));
		setSendCodeGenerator(new SendEventDefinitionAspectJCodeGenerator(getPath()));
	}
	
	public void generateCode(MessageDefinition msgDefinition) throws IOException{
		getReceiveCodeGenerator().generateCode(msgDefinition.getReceiveEventDefinition());
		getSendCodeGenerator().generateCode(msgDefinition.getSendEventDefinition());
	}

	/**
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param string
	 */
	public void setPath(String string) {
		path = string;
	}

	private void setReceiveCodeGenerator(ReceiveEventDefinitionAspectJCodeGenerator receiveCodeGenerator) {
		this.receiveCodeGenerator = receiveCodeGenerator;
	}

	private ReceiveEventDefinitionAspectJCodeGenerator getReceiveCodeGenerator() {
		return receiveCodeGenerator;
	}

	private void setSendCodeGenerator(SendEventDefinitionAspectJCodeGenerator sendCodeGenerator) {
		this.sendCodeGenerator = sendCodeGenerator;
	}

	private SendEventDefinitionAspectJCodeGenerator getSendCodeGenerator() {
		return sendCodeGenerator;
	}

}
