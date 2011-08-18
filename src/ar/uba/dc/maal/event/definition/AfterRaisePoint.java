package ar.uba.dc.maal.event.definition;

/**
 * Define el momento de ocurrencia after
 * @see ar.uba.dc.event.definition.RaisePoint
 * 
 * @author mcastellani
 * @version $Id: AfterRaisePoint.java,v 1.2 2003/07/22 22:34:56 matias Exp $
 */
public class AfterRaisePoint implements RaisePoint {


	/**
	 * @see ar.uba.dc.event.definition.RaisePoint#getRaisePoint()
	 */
	public String getRaisePoint() {
		return "after";
	}

}
