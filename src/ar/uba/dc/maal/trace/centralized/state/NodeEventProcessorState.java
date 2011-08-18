package ar.uba.dc.maal.trace.centralized.state;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;
import ar.uba.dc.maal.trace.SystemTrace;
import ar.uba.dc.maal.trace.centralized.DistributedEventsMerger;
import ar.uba.dc.maal.trace.centralized.NodeEventProcessor;

/**
 * @author aisacovich
 * 
 * @version $Id: NodeEventProcessorState.java,v 1.5 2003/11/06 23:43:32 alejandro Exp $
 */
public abstract class NodeEventProcessorState {
	
	private DistributedEventsMerger merger=null;
	private NodeEventProcessor parent=null; 
	
	public NodeEventProcessorState(DistributedEventsMerger merger,NodeEventProcessor parent ){
		this.setMerger(merger);
		this.setParent(parent);
	}
	
	public abstract void addEvent(LocalEvent anEvent);
	public abstract void addEvent(SendEvent anEvent);
	public abstract void addEvent(ReceiveEvent anEvent);
	public abstract void sendEventReceived(SendEvent event);
	
	/**
	 * @return
	 */
	public DistributedEventsMerger getMerger() {
		return merger;
	}

	/**
	 * @param merger
	 */
	public void setMerger(DistributedEventsMerger merger) {
		this.merger = merger;
	}

	/**
	 * @return
	 */
	protected SystemTrace getCentralizedLog() {
		return this.getMerger().getCentralizedLog();
	}
	/**
	 * @return
	 */
	public NodeEventProcessor getParent() {
		return parent;
	}

	/**
	 * @param state
	 */
	public void setParent(NodeEventProcessor parent) {
		this.parent = parent;
	}

}
