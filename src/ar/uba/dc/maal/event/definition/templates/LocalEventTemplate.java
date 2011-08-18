/*
 * Created on Oct 14, 2003
 *
 */
package ar.uba.dc.maal.event.definition.templates;

import java.io.IOException;
import java.net.URL;

import ar.uba.dc.maal.event.definition.MethodDefinition;

/**
 * 
 * @author mcastellani
 * @version $Id: LocalEventTemplate.java,v 1.2 2003/11/08 21:25:29 matias Exp $
 */
public class LocalEventTemplate extends Template {

	static private String MSG = "@MSG@";
	static private String METHOD = "@METHOD@";
	static private String OWNER = "@OWNER@";
	static private String PACKAGE = "@PACKAGE@";

	/**
	 * @param fileName
	 * @throws IOException
	 */
	public LocalEventTemplate(String fileName) throws IOException {
		super(fileName);
	}

	/**
	 * @param url
	 * @throws IOException
	 */
	public LocalEventTemplate(URL url) throws IOException {
		super(url);
	}

	public void setMsg(String msg){
		replaceAll(MSG, msg);
	}

	public void setMethodDefinition(MethodDefinition method){
		setDestPackage(method.getPackage());
		replaceAll(PACKAGE, method.getPackage());
		replaceAll(METHOD, method.getMethodPatter());
	}

	public void setOwner(String owner){
		replaceAll(OWNER, owner);
	}

}
