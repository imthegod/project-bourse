package co.je.thesis.setup.rules.logic;

import java.io.File;

/**
 * Class who knows how to load the files that contains the valid rules information.
 * 
 * @author Julian Espinel
 */
public class ValidRulesLoader {
	
	/**
	 * Default path where the files are located
	 */
	public static final String DEFAULT_DIRETORY = "./data/rules/";
	
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
