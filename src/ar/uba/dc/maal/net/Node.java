package ar.uba.dc.maal.net;

import java.io.Serializable;


/**
 * Datos específicos del nodo que genera los eventos.
 * IMPORTANTE: Los algoritmos de centralizacion de mensajes utilizan java.lang.Object#equals(java.lang.Object) de Node 
 * intensamente para identificar los mensajes de un mismo nodo. Es muy apropiado sobreescribir este método para que dos
 * instancias distintas sean conceptualmente el mismo Nodo. En consecuencia, debe sobreescribirse tambien 
 * java.lang.Object#hashCode() 
 * 
 * @author mcastellani
 * @version $Id: Node.java,v 1.4 2003/11/06 23:36:19 alejandro Exp $
 * 
 */
public interface Node extends Serializable {

}
