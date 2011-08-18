package ar.uba.dc.maal.trace.definition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import ar.uba.dc.maal.event.definition.EventDefinition;
import ar.uba.dc.maal.event.definition.MessageDefinition;
import ar.uba.dc.maal.event.definition.MethodDefinition;
import ar.uba.dc.maal.event.definition.NodeRetreiverDefinition;
import ar.uba.dc.maal.event.definition.ReceiveEventDefinition;
import ar.uba.dc.maal.event.definition.SendEventDefinition;
import ar.uba.dc.maal.event.definition.codeGenerator.AspectJCodeGenerator;
import ar.uba.dc.maal.trace.log4j.TraceDefinitionLogInitializer;

/**
 * Conjunto de Definiciones de evento y mensajes para un Sistema en particular
 * 
 * @author mcastellani
 * @version $Id: TraceDefinition.java,v 1.9 2003/11/08 21:26:36 matias Exp $
 * 
 */
public class TraceDefinition {

	private Set messageDefinitions = new HashSet();
	private Set eventDefinitions = new HashSet();

	public TraceDefinition() throws IOException {
		TraceDefinitionLogInitializer.initialize();
	}

	public void addEventDefinition(EventDefinition messageEventDefinition) {
		getEventDefinitions().add(messageEventDefinition);
	}

	public void removeEventDefinition(EventDefinition messageEventDefinition) {
		getEventDefinitions().remove(messageEventDefinition);
	}

	public void addMessageDefinition(MessageDefinition messageEventDefinition) {
		getMessageDefinitions().add(messageEventDefinition);
	}

	public void removeMessageDefinition(MessageDefinition messageEventDefinition) {
		getMessageDefinitions().remove(messageEventDefinition);
	}

	/**
	 * @return
	 */
	public Set getEventDefinitions() {
		return eventDefinitions;
	}

	/**
	 * @return
	 */
	public Set getMessageDefinitions() {
		return messageDefinitions;
	}

	/**
	 * @param set
	 */
	public void setEventDefinitions(Set set) {
		eventDefinitions = set;
	}

	/**
	 * @param set
	 */
	public void setMessageDefinitions(Set set) {
		messageDefinitions = set;
	}

	private void generateMsgCode(AspectJCodeGenerator codeGenerator)
		throws IOException {
		Iterator iter = getMessageDefinitions().iterator();
		while (iter.hasNext()) {
			codeGenerator.generateCode((MessageDefinition) iter.next());
		}
	}

	private void generateEventCode(AspectJCodeGenerator codeGenerator)
		throws IOException {
		Iterator iter = getEventDefinitions().iterator();
		while (iter.hasNext()) {
			codeGenerator.generateCode((EventDefinition) iter.next());
		}
	}

	public void generateAspectJCode(String path, InputStream in)
		throws IOException {
		parse(in);
		AspectJCodeGenerator codeGenerator = new AspectJCodeGenerator(path);
		generateEventCode(codeGenerator);
		generateMsgCode(codeGenerator);
	}

	private void setMethorDefinition(Digester d, String pattern){
		//Crea las definiciones de los métodos de los eventos
		d.addObjectCreate( pattern + "/method", MethodDefinition.class.getName());
		d.addSetProperties(pattern + "/method");
		d.addSetNext(pattern + "/method", "setMethodDefinition", MethodDefinition.class.getName());
	}
	
	private void setNodeRetrieverDefinition(Digester d, String pattern){
		//Crea la definición de los owners de los eventos
		d.addObjectCreate(pattern + "/owner", NodeRetreiverDefinition.class.getName());
		d.addSetProperties(pattern + "/owner");
		d.addSetNext(pattern + "/owner", "setNodeRetrieverDefinition",	NodeRetreiverDefinition.class.getName());
	}
	
	private void parse(InputStream in) throws IOException {
		Digester d = new Digester();

		d.push(this);

		//Setea todas las propiedades del trace
		d.addSetProperties("trace");

		//Crea las definiciones de los eventos
		d.addObjectCreate("trace/event", EventDefinition.class.getName());
		d.addSetProperties("trace/event");
		d.addSetNext("trace/event", "addEventDefinition", EventDefinition.class.getName());

		//Crea las definiciones de los métodos de los eventos
		setMethorDefinition(d, "trace/event");

		//Crea la definición del owner del evento
		setNodeRetrieverDefinition(d, "trace/event");

		//********************************************************************************************

		//Crea las definiciones de los mensajes
		d.addObjectCreate("trace/message", MessageDefinition.class.getName());
		d.addSetProperties("trace/message");
		d.addSetNext("trace/message", "addMessageDefinition", MessageDefinition.class.getName());

		//Agrega la definición del sender
		d.addObjectCreate("trace/message/sender", SendEventDefinition.class.getName());
		d.addSetProperties("trace/message/sender");
		d.addSetNext("trace/message/sender", "setSendEventDefinition", SendEventDefinition.class.getName());

		//Crea las definiciones de los métodos de los eventos
		setMethorDefinition(d, "trace/message/sender");

		//Crea la definición del owner del evento de send
		setNodeRetrieverDefinition(d, "trace/message/sender");

		//Agrega el destination
		d.addObjectCreate( "trace/message/sender/destination", NodeRetreiverDefinition.class.getName());
		d.addSetProperties("trace/message/sender/destination");
		d.addSetNext("trace/message/sender/destination", "setDestinationRetreiverDefinition", NodeRetreiverDefinition.class.getName());
		
		//Agrega la definición del receiver
		d.addObjectCreate("trace/message/receiver",	ReceiveEventDefinition.class.getName());
		d.addSetProperties("trace/message/receiver");
		d.addSetNext( "trace/message/receiver", "setReceiveEventDefinition", ReceiveEventDefinition.class.getName());

		//Crea las definiciones de los métodos de los eventos
		setMethorDefinition(d, "trace/message/receiver");

		//Agrega el source
		d.addObjectCreate("trace/message/receiver/source", NodeRetreiverDefinition.class.getName());
		d.addSetProperties("trace/message/receiver/source");
		d.addSetNext("trace/message/receiver/source", "setSourceRetreiverDefinition", NodeRetreiverDefinition.class.getName());

		//Crea la definición del owner del evento de send
		setNodeRetrieverDefinition(d, "trace/message/receiver");

		try {
			d.parse(in);
		} catch (SAXException ex) {
			throw new RuntimeException("error parseando", ex);
		}
	}

	public static void main(String[] args) {
		try {
			TraceDefinition trace = new TraceDefinition();
			trace.generateAspectJCode(
				new File("C:/maal/").getAbsolutePath(),
				TraceDefinition.class.getResourceAsStream("test/trace.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
