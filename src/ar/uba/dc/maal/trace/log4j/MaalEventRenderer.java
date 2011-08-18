package ar.uba.dc.maal.trace.log4j;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.or.ObjectRenderer;

/**
 * Renderer para serializar y deserailizar eventos. Se utiliza para el envio por red de objetos utilizando Log4J.
 * @author aisacovich
 * 
 * @version $Id: MaalEventRenderer.java,v 1.1 2003/07/26 20:07:14 alejandro Exp $
 */
public class MaalEventRenderer implements ObjectRenderer {

	/**
	 * @see org.apache.log4j.or.ObjectRenderer#doRender(java.lang.Object)
	 */
	public String doRender(Object o) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLEncoder enc = new XMLEncoder(baos);
		enc.writeObject(o);
		enc.close();
		try {
			baos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
			throw (IllegalStateException) new IllegalStateException("Algo esta definitivamente muy mal.").initCause(e1);
		}
		try {
			return baos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw (IllegalStateException) new IllegalStateException("The system must support UTF-8 encoding to run this program.").initCause(e);
		}
	}
	
	/**
	 * Recupera los objetos que fueron serializados con esta misma clase.
	 * @param La serializacion de un objeto
	 * @return El objeto deserializado
	 */
	public Object doUnrender(String s) {
		ByteArrayInputStream bais;
		try {
			bais = new ByteArrayInputStream(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw (IllegalStateException) new IllegalStateException("The system must support UTF-8 encoding to run this program.").initCause(e);
		}
		XMLDecoder dec= new XMLDecoder(bais);
		return dec.readObject();
	}

}
