/***********************************************************************
 * Copyright (c) DataTransfer S.A., 2000-2002  Todos los derechos
 * reservados. Derechos no publicados reservados bajo las leyes de
 * derechos de copia de Argentina.
 * El software contenido en este medio es propiedad y parte de la
 * tecnologia confidencial de DataTransfer S.A. La posesion, uso,
 * duplicacion o diseminacion de este software o medio es autorizado
 * unicamente bajo licencia escrita de DataTransfer S.A.
 */
package ar.uba.dc.maal.trace.log4j.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.net.test.MockNode;

import junit.framework.TestCase;

/**
 * @author aisacovich
 * 
 * @version $Id: SerializeDeserializeTest.java,v 1.2 2003/08/27 00:22:41 alejandro Exp $
 */
public class SerializeDeserializeTest extends TestCase {

	/**
	 * Constructor for SerializeDeserializeTest.
	 * @param arg0
	 */
	public SerializeDeserializeTest(String arg0) {
		super(arg0);
	}
	
	public void testSerializeDeserialize() throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(byteos);
		oos.writeObject(new LocalEvent(new MockNode("un Nodo")));
		oos.flush();
		ByteArrayInputStream byteis = new ByteArrayInputStream(byteos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(byteis);
		LocalEvent ev = (LocalEvent) ois.readObject();
		System.out.println(ev);
	}

}
