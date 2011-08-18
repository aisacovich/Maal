package ar.uba.dc.maal.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.log4j.Logger;


/**
 * @author mcastellani
 * @version $Id: FileHelper.java,v 1.1 2003/11/08 21:22:40 matias Exp $
 */
public class FileHelper {

	private static Logger log = Logger.getLogger(FileHelper.class.getClass());

	/**
	 * Crea un archivo con el nombre pasado como parámetro.
	 * 
	 * @param fileName nombre del archivo a crear.
	 * @return
	 * @throws IOException
	 */
	static public File createFile(String path, String fileName, String aPackage) throws IOException{
		String realPath = buildRealPath(path, aPackage);
		mkDirs(realPath);
		File file = new File(realPath + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * Crea los directorios y arroja una IOException si no puede.
	 * @param path
	 * @throws IOException
	 */
	static public void mkDirs(String path) throws IOException {
		File dirs = new File(path);
		if (!dirs.exists()){
			if (!dirs.mkdirs()) {
				throw new IOException("No se pudo generar el directorio " + path);
			}
		}
	}

	/**
	 * Contruye el path completo a partir de un dest dir y un paquete
	 * @param path
	 * @param aPackage
	 * @return
	 */
	static public String buildRealPath(String path, String aPackage){
		String suffix = aPackage.replaceAll("\\.", "/");
		return path + "/" + suffix + "/"; 
	}
	
	static public void deleteFile(File file) {
		if (file.isDirectory()){
			deleteDir(file);
		}else{
			log.info("Deleting file " + file.getAbsolutePath());
			log.info("The files was " + (file.delete() ? "" : "not ") + "deleted");
		}
	}

	private static void deleteDir(File dir){
		if (dir.listFiles() != null){
			CollectionUtils
				.forAllDo(
					IteratorUtils.toList(
						IteratorUtils.arrayIterator(dir.listFiles())),
					new Closure() { /**
						 * @see org.apache.commons.collections.Closure#execute(java.lang.Object)
						 */
				public void execute(Object arg0) {
					File f = (File) arg0;
					deleteFile(f);
				}
			});
		}
		log.info("Deleting dir " + dir.getAbsolutePath());
		log.info("The dir was " + (dir.delete() ? "" : "not ") + "deleted");
	}
}
