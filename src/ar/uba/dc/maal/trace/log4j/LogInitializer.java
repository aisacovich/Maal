package ar.uba.dc.maal.trace.log4j;

import java.net.URL;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * Inicializa Log4J para ser utilizado como medio de transporte y registracion de eventos.
 * Una vez que este objeto inicializo la Log4J ya no vuelve a hacerlo aunque se lo vuelva a llamar.  
 * @author aisacovich
 * 
 * @version $Id: LogInitializer.java,v 1.2 2003/07/26 20:07:14 alejandro Exp $
 */
public class LogInitializer {
	
	static private boolean alreadyInitialized = false;

	/**
	 * Inicializa Log4J haya o no sido inicialidado
	 */
	static public void reInitializeLog4J() {
		setAlreadyInitialized(false);
		initializeLog4J();
	} 
	
	/**
	 * Inicializa Log4J si no estaba inicializado.
	 */
	static public void initializeLog4J() {
		if (!isAlreadyInitialized()) {
			URL url = LogInitializer.class.getResource("log4j_config.xml");	
			DOMConfigurator.configure(url);
			setAlreadyInitialized(true);
		}
	}
	
	/**
	 * @return si ya fue inicialido log4J
	 */
	public static boolean isAlreadyInitialized() {
		return alreadyInitialized;
	}

	/**
	 * @param b indica si fue o no inicialido log4j
	 */
	private static void setAlreadyInitialized(boolean b) {
		alreadyInitialized = b;
	}

}
