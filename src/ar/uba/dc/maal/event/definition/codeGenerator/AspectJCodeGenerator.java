package ar.uba.dc.maal.event.definition.codeGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.log4j.Logger;

import ar.uba.dc.maal.event.definition.EventDefinition;
import ar.uba.dc.maal.event.definition.MessageDefinition;
import ar.uba.dc.maal.event.definition.MethodDefinition;
import ar.uba.dc.maal.event.definition.templates.Template;
import ar.uba.dc.maal.util.FileHelper;

/**
 * Clase que se encarga de generar el código AspectJ de los eventos y mensajes de alto nivel
 * 
 * @author mcastellani
 * @version $Id: AspectJCodeGenerator.java,v 1.5 2003/11/08 21:24:01 matias Exp $
 * 
 */
public class AspectJCodeGenerator implements CodeGenerator {

	private Logger log = Logger.getLogger(this.getClass());
	private String path;
	private EventDefinitionAspectJCodeGenerator eventCodeGenerator;
	private MessageDefinitionAspectJCodeGenerator msgCodeGenerator;

	public AspectJCodeGenerator() throws IOException{
		this("C:/maal");
	}

	public AspectJCodeGenerator(String path) throws IOException {
		setPath(path);
		setEventCodeGenerator(new EventDefinitionAspectJCodeGenerator(getPath()));
		setMsgCodeGenerator(new MessageDefinitionAspectJCodeGenerator(getPath()));
		deleteOldFiles();
		generateAbstractCode();
	}

	private void deleteOldFiles(){
		File path = new File(getPath());
		if (path.listFiles() != null){
			CollectionUtils
				.forAllDo(
					IteratorUtils.toList(
						IteratorUtils.arrayIterator(path.listFiles())),
					new Closure() { /**
						 * @see org.apache.commons.collections.Closure#execute(java.lang.Object)
						 */
				public void execute(Object arg0) {
					FileHelper.deleteFile((File) arg0);
				}
			});
		}
	}



	private void generateAbstractCode() throws IOException  {
		log.info("Generating abstract code ...");
		copyAbstractEventCode();
		copyAbstractSendEventCode();
		copyAbstractReceiveEventCode();
		
	}

	private void copyAbstractEventCode()  throws IOException {
		//AbstractAspectEventDefinition
		writeFileWith( 	Template.class.getResourceAsStream("resources/AbstractAspectEventDefinition.java.aspect"), 
						getPath(),
						"/AbstractAspectEventDefinition.java");
	}

	private void copyAbstractSendEventCode() throws IOException { 
		//AbstractSendAspectEventDefinition
		writeFileWith( 	Template.class.getResourceAsStream("resources/AbstractSendAspectEventDefinition.java.aspect"), 
						getPath(),
						"/AbstractSendAspectEventDefinition.java");
	}

	private void copyAbstractReceiveEventCode() throws IOException {
		//AbstractReceiveAspectEventDefinition
		writeFileWith( 	Template.class.getResourceAsStream("resources/AbstractReceiveAspectEventDefinition.java.aspect"), 
						getPath(),
						"/AbstractReceiveAspectEventDefinition.java");
	}

	/**
	 * Escribe en un archivo el contenido del inputStream
	 * @param is source
	 * @param fileName destination
	 * @throws IOException
	 */
	public void writeFileWith(InputStream is, String path, String fileName)throws IOException {
		File file = FileHelper.createFile(path, fileName, MethodDefinition.DEFAULT_PACKAGE);
		OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file));
		os.write("package " + MethodDefinition.DEFAULT_PACKAGE + ";\n");
		do {
			int b = is.read();
			// Si llego al final deja de copiar.
			if (b == -1) {
				break;
			}
			os.write(b);
		} while (true);
		os.close();
		is.close();
	}



	/**
	 * @see ar.uba.dc.maal.event.definition.codeGenerator.CodeGenerator#generateCode(ar.uba.dc.maal.event.definition.EventDefinition)
	 */
	public void generateCode(EventDefinition eventDef) throws IOException{
		getEventCodeGenerator().generateCode(eventDef);
	}

	/**
	 * @see ar.uba.dc.maal.event.definition.codeGenerator.CodeGenerator#generateCode(ar.uba.dc.maal.event.definition.MessageDefinition)
	 */
	public void generateCode(MessageDefinition eventDef) throws IOException{
		getMsgCodeGenerator().generateCode(eventDef);
	}

	/**
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param string
	 */
	public void setPath(String string) {
		path = string;
	}

	private void setEventCodeGenerator(EventDefinitionAspectJCodeGenerator eventCodeGenerator) {
		this.eventCodeGenerator = eventCodeGenerator;
	}

	private EventDefinitionAspectJCodeGenerator getEventCodeGenerator() {
		return eventCodeGenerator;
	}

	private void setMsgCodeGenerator(MessageDefinitionAspectJCodeGenerator msgCodeGenerator) {
		this.msgCodeGenerator = msgCodeGenerator;
	}

	private MessageDefinitionAspectJCodeGenerator getMsgCodeGenerator() {
		return msgCodeGenerator;
	}

}
