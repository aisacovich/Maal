package ar.uba.dc.maal.event.definition.codeGenerator;

import java.io.IOException;

import org.apache.log4j.Logger;

import ar.uba.dc.maal.event.definition.SendEventDefinition;
import ar.uba.dc.maal.event.definition.templates.SendAndReceiveEventTemplate;

/**
 * @author mcastellani
 * @version $Id: SendEventDefinitionAspectJCodeGenerator.java,v 1.5 2003/11/08 21:25:03 matias Exp $
 * 
 */
public class SendEventDefinitionAspectJCodeGenerator {

	private Logger log = Logger.getLogger(this.getClass());
	private String path;


	/**
	 * @param path String que indica el directorio donde se va a generar el código 
	 */
	public SendEventDefinitionAspectJCodeGenerator(String path) throws IOException{
		setPath(path);
	}

	public void generateCode(SendEventDefinition eventDef) throws IOException {
		SendAndReceiveEventTemplate codeTemplate = new SendAndReceiveEventTemplate("resources/SendAspectEventDefinition.java.aspect");
		String className = eventDef.getMsg() + "SendEventDefinition.java";
		log.info("Generating code for " + className);
		codeTemplate.setMethodDefinition(eventDef.getMethodDefinition());
		codeTemplate.setMsg(eventDef.getMsg().toString());
		codeTemplate.setNode(eventDef.getDestinationRetreiverDefinition().getNodeRetreiverClassName());
		codeTemplate.setOwner(eventDef.getNodeRetrieverDefinition().getNodeRetreiverClassName());
		codeTemplate.writeCodeOn(getPath(), className);
	}

	private void setPath(String path) {
		this.path = path;
	}

	private String getPath() {
		return path + "\\";
	}
}
