package ar.uba.dc.maal.event.test;

/**
 * @author aisacovich
 * 
 * @version $Id: EqualsTester.java,v 1.1 2003/11/06 23:45:13 alejandro Exp $
 */
public class EqualsTester {

	/**
	 * Este test esta pensado para chequear (no demostrar) las caracteristicas básicas de la relacion
	 * equals para implementaciones distitnas del <code>"same"</code> (<code>==</code>) de <code>equals(Object)</code>.
	 * Para que tenga sentido este test, todos los parametros deberian ser instanicias distintos, es decir
	 * debeiran ser distintos para la relacion <code>==</code>.   
	 * Precondiciones (el "=" representa "equals"): 
	 * 		o1=o2=03
	 * 		o3!=04
	 */
	public static void testEqualsRestrictions(Object o1, Object o2, Object o3,Object o4) throws EqualsNotValidException {
		testReflexive(o1);
		testSymmetric(o1,o2,o3,o4);
		testTransitive(o1,o2,o3);
		testNullFalse(o1);
	}

	/**
	 * @param o1
	 */
	public static void testNullFalse(Object o1) throws EqualsNotValidException {
		try {
			if (o1.equals(null)) throw new EqualsNotValidException();
		} catch (Throwable e) {
			throw new EqualsNotValidException("equals(null) should be false");
		}
	}

	/**
	 * Precondiciones (el "=" representa "equals"): 
	 * 		o1=o2=03
	 * @param o1
	 * @param o2
	 * @param o3
	 */
	public static void testTransitive(Object o1, Object o2, Object o3) throws EqualsNotValidException {
		if (!(o1.equals(o2) && o2.equals(o3) && o3.equals(o1))) throw new EqualsNotValidException("Equals is not transitive");
	}

	/**
	 * Precondiciones (el "=" representa "equals"): 
	 * 		o1=o2=03
	 * 		o3!=04
	 * @param o1
	 * @param o2
	 * @param o3
	 * @param o4
	 */
	public static void testSymmetric(Object o1, Object o2, Object o3, Object o4) throws EqualsNotValidException {
		if (!(o1.equals(o2) && o2.equals(o1))) throw new EqualsNotValidException("Equals is not symmetric");
		if (!(!o3.equals(o4) && !o4.equals(o3))) throw new EqualsNotValidException("Equals is not symmetric");
	}

	public static void testReflexive(Object o1) throws EqualsNotValidException {
		if (!o1.equals(o1)) throw new EqualsNotValidException("Equals is not reflexive");
	}

}
