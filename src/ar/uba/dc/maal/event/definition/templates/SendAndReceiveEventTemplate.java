/*
 * Created on Oct 14, 2003
 *
 */
package ar.uba.dc.maal.event.definition.templates;

import java.io.IOException;
import java.net.URL;

/**
 * 
 * @author mcastellani
 * @version $Id: SendAndReceiveEventTemplate.java,v 1.1 2003/10/14 21:48:00 matias Exp $
 */
public class SendAndReceiveEventTemplate extends LocalEventTemplate {

	static private String NODE = "@NODE@";

	/**
	 * @param fileName
	 * @throws IOException
	 */
	public SendAndReceiveEventTemplate(String fileName) throws IOException {
		super(fileName);
	}

	/**
	 * @param url
	 * @throws IOException
	 */
	public SendAndReceiveEventTemplate(URL url) throws IOException {
		super(url);
	}

	public void setNode(String node){
		replaceAll(NODE, node);
	}

}
