package ar.uba.dc.maal.event.definition.test;

import junit.framework.TestCase;
import ar.uba.dc.maal.event.definition.EventDefinition;
import ar.uba.dc.maal.event.definition.MethodDefinition;

/**
 * @author mcastellani
 * @version $Id: EventDefinitionTest.java,v 1.8 2003/08/09 21:02:04 matias Exp $
 * 
 */
public class EventDefinitionTest extends TestCase {

	/**
	 * 
	 */
	public EventDefinitionTest() {
		super();
	}

	/**
	 * @param arg0
	 */
	public EventDefinitionTest(String arg0) {
		super(arg0);
	}

	
	public void testGetterAndSetters(){
		MethodDefinition method = new MethodDefinition("pepe");
		String name = "hola";
		EventDefinition eventDefinition = new EventDefinition( name, method);
		assertEquals(method, eventDefinition.getMethodDefinition());
		assertEquals(name, eventDefinition.getMsg());
	}

}
