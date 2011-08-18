package ar.uba.dc.maal.event.definition;

/**
 * Los métodos están definidos por el patrón que lo representa
 * Ejemplo:
 * <code>
 * 		"java.util.String.toString()"
 * </code>
 * 
 * @author mcastellani
 * @version $Id: MethodDefinition.java,v 1.3 2003/11/08 21:23:28 matias Exp $
 * 
 */
public class MethodDefinition {

	public final static String DEFAULT_PACKAGE = "ar.uba.dc.maal.event.aspect";
	
	private String thePackage = DEFAULT_PACKAGE;
	private String methodPatter;
	
	public MethodDefinition(){
	}

	/**
	 * @param methodPatter Patrón que representa el método
	 */
	public MethodDefinition(String methodPatter){
		setMethodPatter(methodPatter);
	}

	/**
	 * @return
	 */
	public String getMethodPatter() {
		return methodPatter;
	}

	/**
	 * @param string
	 */
	public void setMethodPatter(String string) {
		methodPatter = string;
	}

	/**
	 * @return Returns the thePackage.
	 */
	public String getPackage() {
		return thePackage;
	}

	/**
	 * @param thePackage The thePackage to set.
	 */
	public void setPackage(String aPackage) {
		this.thePackage = aPackage;
	}

}
