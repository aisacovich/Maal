package ar.uba.dc.maal.trace.centralized;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import ar.uba.dc.maal.event.LocalEvent;
import ar.uba.dc.maal.trace.EventRaiser;
import ar.uba.dc.maal.trace.EventRaiserSystemTraceDecorator;
import ar.uba.dc.maal.trace.SystemTrace;

/**
 * @author aisacovich
 * 
 * @version $Id: DistributedEventsMerger.java,v 1.10 2003/11/06 23:39:46 alejandro Exp $
 */
public class DistributedEventsMerger {

	private Set nodeProcessors = new HashSet();
	private SystemTrace centralizedLog;
	
	/**
	 * 
	 */
	public DistributedEventsMerger(EventRaiser aRaiser) {
		this.setCentralizedLog(
			new EventRaiserSystemTraceDecorator(aRaiser,
				new CentralizedSystemTrace(new SimpleSystemTrace(),this)));
	}

	public void addEvent(final LocalEvent anEvent) {
		try {	
			this.getProcessorFor(anEvent).addEvent(anEvent);
		} catch (NullPointerException e) {
			NodeEventProcessor aNewProcessor = new NodeEventProcessor(this);
			aNewProcessor.setNode(anEvent.getOwner());
			nodeProcessors.add(aNewProcessor);
			aNewProcessor.addEvent(anEvent);
		}
	}

	private NodeEventProcessor getProcessorFor(final LocalEvent anEvent) {
		NodeEventProcessor processor =(NodeEventProcessor) CollectionUtils.find(getNodeProcessors(),new Predicate() {
			public boolean evaluate(Object nep) {
					NodeEventProcessor nodeEventProcessor = (NodeEventProcessor) nep;
					return nodeEventProcessor.getNode().equals(anEvent.getOwner());
			}
		});
		return processor;
	}

	/**
	 * @return
	 */
	protected Set getNodeProcessors() {
		return nodeProcessors;
	}

	/**
	 * @return
	 */
	public SystemTrace getCentralizedLog() {
		return centralizedLog;
	}

	/**
	 * @param trace
	 */
	private void setCentralizedLog(SystemTrace trace) {
		centralizedLog = trace;
	}

}