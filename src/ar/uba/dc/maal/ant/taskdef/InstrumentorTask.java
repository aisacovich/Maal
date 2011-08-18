package ar.uba.dc.maal.ant.taskdef;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import ar.uba.dc.maal.trace.definition.TraceDefinition;

/**
 * Intrumenta código java con eventos a partir de un xml.
 * @author mcastellani
 * @version $Id: InstrumentorTask.java,v 1.1 2003/11/07 01:11:39 matias Exp $
 */
public class InstrumentorTask extends Task {
	protected File eventDefinitionFile = null;
	protected File destDir = null ;

	/**
	 * @see org.apache.tools.ant.Task#execute()
	 */
	public void execute() throws BuildException {
		TraceDefinition trace;
		try {
			trace = new TraceDefinition();
			trace.generateAspectJCode(
				destDir.getAbsolutePath(),
				new FileInputStream(getEventDefinitionFile()));
		} catch (IOException e) {
			throw new BuildException(e);
		}
	}
	
	/**
	 * @return Returns the eventDefinitionFile.
	 */
	public File getEventDefinitionFile() {
		return eventDefinitionFile;
	}

	/**
	 * @param eventDefinitionFile The eventDefinitionFile to set.
	 */
	public void setEventDefinitionFile(File eventDefinitionFile) {
		this.eventDefinitionFile = eventDefinitionFile;
	}

	/**
	 * @return Returns the destDir.
	 */
	public File getDestDir() {
		return destDir;
	}

	/**
	 * @param destDir The destDir to set.
	 */
	public void setDestDir(File destDir) {
		this.destDir = destDir;
	}

}
