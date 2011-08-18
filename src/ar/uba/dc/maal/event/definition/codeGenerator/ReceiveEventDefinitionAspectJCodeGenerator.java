package ar.uba.dc.maal.event.definition.codeGenerator;

import java.io.IOException;

import org.apache.log4j.Logger;

import ar.uba.dc.maal.event.definition.ReceiveEventDefinition;
import ar.uba.dc.maal.event.definition.templates.SendAndReceiveEventTemplate;

/**
 * @author mcastellani
 * @version $Id: ReceiveEventDefinitionAspectJCodeGenerator.java,v 1.5 2003/11/08 21:25:03 matias Exp $
 * 
 */
public class ReceiveEventDefinitionAspectJCodeGenerator {

	private Logger log = Logger.getLogger(this.getClass());
	private String path;

	/**
	 * @param path String donde se va a generar el código. 
	 */
	public ReceiveEventDefinitionAspectJCodeGenerator(String path) throws IOException{
		setPath(path);
	}

	public void generateCode(ReceiveEventDefinition eventDef) throws IOException {
		SendAndReceiveEventTemplate codeTemplate = new SendAndReceiveEventTemplate("resources/ReceiveAspectEventDefinition.java.aspect");
		String className = eventDef.getMsg() + "ReceiveEventDefinition.java";
		log.info("Generating code for " + className);
		codeTemplate.setMethodDefinition(eventDef.getMethodDefinition());
		codeTemplate.setMsg(eventDef.getMsg().toString());
		codeTemplate.setNode(eventDef.getSourceRetreiverDefinition().getNodeRetreiverClassName());
		codeTemplate.setOwner(eventDef.getNodeRetrieverDefinition().getNodeRetreiverClassName());
		codeTemplate.writeCodeOn(getPath(), className);
	}

	private void setPath(String path) {
		this.path = path;
	}

	private String getPath() {
		return path + "/";
	}

}
