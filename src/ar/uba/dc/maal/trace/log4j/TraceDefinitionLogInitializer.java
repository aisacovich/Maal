package ar.uba.dc.maal.trace.log4j;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author mcastellani
 * @version $Id: TraceDefinitionLogInitializer.java,v 1.1 2003/08/26 23:44:54 matias Exp $
 * 
 */
public class TraceDefinitionLogInitializer {
	public static void initialize(){
		DOMConfigurator.configure(TraceDefinitionLogInitializer.class.getResource("log4j.xml"));
	}
}
