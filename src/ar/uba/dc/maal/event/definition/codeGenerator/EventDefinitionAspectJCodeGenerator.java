package ar.uba.dc.maal.event.definition.codeGenerator;

import java.io.IOException;

import org.apache.log4j.Logger;

import ar.uba.dc.maal.event.definition.EventDefinition;
import ar.uba.dc.maal.event.definition.templates.LocalEventTemplate;

/**
 * Se encarga de generar el código AspectJ que generar los eventos.
 * 
 * @author mcastellani
 * @version $Id: EventDefinitionAspectJCodeGenerator.java,v 1.8 2003/11/08 21:25:04 matias Exp $
 * 
 */
public class EventDefinitionAspectJCodeGenerator {

	private Logger log = Logger.getLogger(this.getClass());
	private String path;

	public EventDefinitionAspectJCodeGenerator(String path) throws IOException{
		setPath(path);
	}


	public void generateCode(EventDefinition eventDefinition) throws IOException{
		LocalEventTemplate codeTemplate = new LocalEventTemplate("resources/AspectEventDefinition.java.aspect");
		String claseName = eventDefinition.getMsg() + "EventDefinition.java";
		log.info("Generating code for " +  claseName);
		codeTemplate.setMsg(eventDefinition.getMsg().toString());
		codeTemplate.setMethodDefinition(eventDefinition.getMethodDefinition());
		codeTemplate.setOwner(eventDefinition.getNodeRetrieverDefinition().getNodeRetreiverClassName());
		codeTemplate.writeCodeOn(getPath(), claseName);
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

}
