package co.je.thesis.setup.dsl;

import java.io.File;
import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCollectionNames;
import co.je.thesis.common.dbos.dsl.DSLElementDBO;
import co.je.thesis.setup.dsl.constants.DSLSources;
import co.je.thesis.setup.dsl.logic.DSLElementsLoader;
import co.je.thesis.setup.dsl.logic.DSLElementsParser;
import co.je.thesis.setup.dsl.persistence.DSLPersistence;

/**
 * This class executes all the logic needed to load the DSL into the system.
 * 
 * @author Julian Espinel
 */
public class DSLMain {
	
	/**
	 * Attribute that knows how to load the files which contains the DSL information.
	 */
	private DSLElementsLoader dslElementsLoader;
	
	/**
	 * Attribute that knows how to parse the information stored in files, to actual DSL elements.
	 */
	private DSLElementsParser dslElementsParser;
	
	/**
	 * Attribute that knows how to persist the DSL data into the system's DB.
	 */
	private DSLPersistence dslPersistence;
	
	public DSLMain() {
		
		dslElementsLoader = new DSLElementsLoader();
		dslElementsParser = new DSLElementsParser();
		dslPersistence = new DSLPersistence();
	}
	
	/**
	 * Updates the DSL version.
	 */
	public void updateDSLVersion() {
		
		dslPersistence.updateDSLVersion();
	}
	
	/**
	 * Inserts the DSL elements of contained in a file to a DB collection.
	 * 
	 * @param dslFileName the name of the file which contains the DSL elements to insert.
	 * @param dslCollectionName the name of the collection in which the DSL elements will be inserted.
	 */
	public void createDSLElements(String dslFileName, String dslCollectionName) {
		
		dslPersistence.dropCollection(dslCollectionName);
		
		File excelFile = dslElementsLoader.retrieveFile(dslFileName);
		ArrayList<DSLElementDBO> dslElementsDBO = dslElementsParser.buildDSLElements(excelFile);
		dslPersistence.insertDSLElementsDBOIntoCollection(dslCollectionName, dslElementsDBO);
	}
	
	public static void main(String[] args) {
		
		System.out.println("DSLMain: begins");
		
		DSLMain dslMain = new DSLMain();
		
		dslMain.updateDSLVersion();
		dslMain.createDSLElements(DSLSources.SUBJECTS_FILE_NAME, DSLCollectionNames.SUBJECTS_COLLECTION_NAME);
		dslMain.createDSLElements(DSLSources.PROPERTIES_FILE_NAME, DSLCollectionNames.PROPERTIES_COLLECTION_NAME);
		dslMain.createDSLElements(DSLSources.MATH_OPERATIONS_FILE_NAME, DSLCollectionNames.MATH_OPERATIONS_COLLECTION_NAME);
		dslMain.createDSLElements(DSLSources.SPECIFIC_MATH_OPERATIONS_FILE_NAME, DSLCollectionNames.SPECIFIC_MATH_OPERATIONS_COLLECTION_NAME);
		dslMain.createDSLElements(DSLSources.CONDITIONAL_OPERATORS_FILE_NAME, DSLCollectionNames.CONDITIONAL_OPERATORS_COLLECTION_NAME);
		dslMain.createDSLElements(DSLSources.LOGICAL_OPERATORS_FILE_NAME, DSLCollectionNames.LOGICAL_OPERATORS_COLLECTION_NAME);
		//main.createDSLElements(Constants.TIME_FRAMES_FILE_NAME, DSLCollectionNames.TIME_FRAMES_COLLECTION_NAME);
		dslMain.createDSLElements(DSLSources.UNITS_FILE_NAME, DSLCollectionNames.UNITS_COLLECTION_NAME);
		
		System.out.println("DSLMain: ends");
	}
}
