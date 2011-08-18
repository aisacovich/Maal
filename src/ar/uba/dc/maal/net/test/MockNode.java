package ar.uba.dc.maal.net.test;

import ar.uba.dc.maal.net.Node;

/**
 * Nodo mock para usar el <code>equals()</code>.
 * @author aisacovich
 * 
 * @version $Id: MockNode.java,v 1.3 2003/11/06 23:44:20 alejandro Exp $
 */
public class MockNode implements Node {
	String name;

	public MockNode(String name){
		 this.setName(name);
	}
	
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + " name: [" + this.getName() + "]";
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof MockNode) {
			return equalsMockNode((MockNode) obj);
		}
		else {
			return false;
		}
	}

	/**
	 * @param node
	 * @return
	 */
	private boolean equalsMockNode(MockNode node) {
		return this.getName().equals(node.getName());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.getName().hashCode();
	}

}
