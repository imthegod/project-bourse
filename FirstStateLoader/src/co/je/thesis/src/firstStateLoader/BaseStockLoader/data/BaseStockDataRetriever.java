package co.je.thesis.src.firstStateLoader.BaseStockLoader.data;

import java.io.File;

/**
 * Class to load the files which contain stock information
 * 
 * @author Julian Espinel
 */
public class BaseStockDataRetriever {

	/**
	 * Default path where the files are located
	 */
	public static final String DEFAULT_DIRETORY = "./data/";

	/**
	 * Loads the Excel files and returns an array with those files.
	 * The Excel files contains the base stock information.
	 * 
	 * @return array of selected files
	 */
	public File[] loadStockBaseExcelFiles() {
		
		File file = new File(DEFAULT_DIRETORY);
		File[] files = file.listFiles();
		
		return files;
	}
}