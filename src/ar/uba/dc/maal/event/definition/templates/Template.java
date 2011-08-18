/*
 * Created on Oct 14, 2003
 *
 */
package ar.uba.dc.maal.event.definition.templates;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;

import ar.uba.dc.maal.util.FileHelper;

/**
 * Se encarga de generar código a partir de templates.
 * @author mcastellani
 * @version $Id: Template.java,v 1.2 2003/11/08 21:25:29 matias Exp $
 */
public class Template {

	private String template;
	private String destPackage;

	/**
	 * @param fileName Nombre de un archivo relativo a la ubicación de la clase <code>Template</code>
	 */
	public Template(String fileName) throws IOException {
		this(Template.class.getResource(fileName));
	}

	/**
	 * @param url URL de donde obtener el template
	 */
	public Template(URL url) throws IOException {
		InputStream in = url.openStream();
		byte[] buf = new byte[in.available()];
		in.read(buf);
		setTemplate(new String(buf));
		
	}

	/**
	 * @see <code>writeCodeOn(Writer writer)</code>
	 */
	public void writeCodeOn(String path, String claseName) throws IOException {
		File file = FileHelper.createFile(path, claseName, getDestPackage());
		writeCodeOn( new FileWriter(file));

	}

	/**
	 * Escribe el template en un archivo, para que genere bien el código hay que hacer los reemplazos necesarios 
	 * antes de llamar a este método
	 * @param writer
	 * @throws IOException
	 */
	public void writeCodeOn(Writer writer) throws IOException {
		writer.write(getTemplate()); 
		writer.flush();
		writer.close();
	}

	public void replaceAll(String tag, String value){
		setTemplate(getTemplate().replaceAll(tag, value));
	}
	
	private void setTemplate(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}
	
	protected void setDestPackage(String destPackage) {
		this.destPackage = destPackage;
	}

	protected String getDestPackage() {
		return destPackage;
	}

	
}
