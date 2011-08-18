package ar.uba.dc.maal.trace.centralized;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.event.ReceiveEvent;
import ar.uba.dc.maal.event.SendEvent;
import ar.uba.dc.maal.net.Node;
import ar.uba.dc.maal.trace.centralized.state.EmptyState;
import ar.uba.dc.maal.trace.centralized.state.NodeEventProcessorState;

/**
 * @author aisacovich
 * 
 * @version $Id: NodeEventProcessor.java,v 1.4 2003/09/03 00:31:41 alejandro Exp $
 */
public class NodeEventProcessor implements EventLogger{
	
	private NodeEventProcessorState state;
	private DistributedEventsMerger merger;
	private Node node;
	
	NodeEventProcessor(DistributedEventsMerger merger) {
		this.setMerger(merger);
		this.setState(new EmptyState(this.getMerger(),this));
	}
	
	public void addEvent(LocalEvent anEvent) {
		anEvent.logAt(this);
	}

	public void logLocalEvent(LocalEvent anEvent) {
		state.addEvent(anEvent);
	}

	public void logSendEvent(SendEvent anEvent) {
		state.addEvent(anEvent);
	}

	public void logReceiveEvent(ReceiveEvent anEvent) {
		state.addEvent(anEvent);
	}
	/**
	 * @return
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param node
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * @param event
	 */
	public void sendEventReceived(SendEvent event) {
		state.sendEventReceived(event);
	}

	/**
	 * @return
	 */
	private DistributedEventsMerger getMerger() {
		return merger;
	}

	/**
	 * @param merger
	 */
	private void setMerger(DistributedEventsMerger merger) {
		this.merger = merger;
	}

	/**
	 * @return
	 */
	public NodeEventProcessorState getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(NodeEventProcessorState state) {
		this.state = state;
	}

}	
