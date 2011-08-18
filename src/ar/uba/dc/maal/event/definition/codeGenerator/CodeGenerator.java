package ar.uba.dc.maal.event.definition.codeGenerator;

import java.io.IOException;

import ar.uba.dc.maal.event.definition.EventDefinition;
import ar.uba.dc.maal.event.definition.MessageDefinition;

/**
 * Generador de código.
 * 
 * @author mcastellani
 * @version $Id: CodeGenerator.java,v 1.1 2003/07/26 21:17:52 matias Exp $
 * 
 */
public interface CodeGenerator {

	/**
	 * Genera el código de un <code>EventDefinition</code> 
	 * @param eventDef
	 */
	public void generateCode(EventDefinition eventDef) throws IOException;

	/**
	 * Genera el código de un <code>MessageDefinition</code> 
	 * @param eventDef
	 */
	public void generateCode(MessageDefinition eventDef) throws IOException;

}
