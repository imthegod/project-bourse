package co.je.thesis.setup.dsl.logic;

import java.io.File;

public class DSLElementsLoader {
	
	/**
	 * Default path where the files are located
	 */
	public static final String DEFAULT_DIRETORY = "./data/dsl/";
	
	/**
	 * Retrieves a file with a given name.
	 * 
	 * @param fileName, the name of the file we want to retrieve.
	 * @return The file of name fileName.
	 */
	public File retrieveFile(String fileName) {
		
		String filePath = DEFAULT_DIRETORY + fileName;
		File file = new File(filePath);
		
		return file;
	}
}
