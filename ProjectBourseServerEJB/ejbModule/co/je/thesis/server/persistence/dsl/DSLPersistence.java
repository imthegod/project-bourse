package co.je.thesis.server.persistence.dsl;

import java.util.ArrayList;
import java.util.List;

import co.je.thesis.common.constants.DSLCollectionNames;
import co.je.thesis.common.dbos.dsl.DSLElementDBO;
import co.je.thesis.common.dtos.dsl.DSLDataTransferObject;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.server.infrastructure.translators.dsl.DSLElementTranslator;
import co.je.thesis.server.persistence.DBManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * This class is responsible for handling the DSL data persistence.
 * 
 * @author Julian Espinel
 */
public class DSLPersistence {
	
	/**
	 * DSLPersistence constructor.
	 */
	public DSLPersistence() {
	}

	/**
     * Returns the whole DSL on  its current (and last) version.
     * 
     * @return the whole DSL on  its current (and last) version.
     */
	public DSLDataTransferObject getDSL() {
		
		int version = getDSLVersion();
		ArrayList<DSLElementDTO> subjects = getDSLSubjects();
		ArrayList<DSLElementDTO> properties = getDSLProperties();
		ArrayList<DSLElementDTO> mathOperations = getDSLMathOperations();
		ArrayList<DSLElementDTO> specificMathOperations = getDSLSpecificMathOperations();
		ArrayList<DSLElementDTO> conditionalOperators = getDSLConditionalOperators();
		ArrayList<DSLElementDTO> logicalOperators = getDSLLogicalOperators();
		ArrayList<DSLElementDTO> units = getDSLUnits();
		
		DSLDataTransferObject dsl = new DSLDataTransferObject(version, subjects, properties, mathOperations, specificMathOperations, conditionalOperators, logicalOperators, units);
		
		return dsl;
	}

	/**
	 * Gets a DBCursor object and creates an ArrayList object with the information contained
	 * by the cursor.
	 * 
	 * @param cursor the DBCursor object we want to convert into an ArrayList.
	 * @return an ArrayList object with the information contained
	 * 		   by the cursor.
	 */
	private ArrayList<DSLElementDTO> convertToArrayOfDSLElements(DBCursor cursor) {

		ArrayList<DSLElementDTO> dslElements = new ArrayList<DSLElementDTO>();
		
		DSLElementTranslator translator = new DSLElementTranslator();
		List<DBObject> dbos = cursor.toArray();
		
		for (int i = 0; i < dbos.size(); i++) {
			
			BasicDBObject basicDBO = (BasicDBObject) dbos.get(i);
			DSLElementDBO dslElementDBO = translator.toDBO(basicDBO);
			DSLElementDTO dslElement = translator.toDTO(dslElementDBO);
			dslElements.add(dslElement);
		}
		
		return dslElements;
	}
	
	/**
     * Returns the current (and last) version number of the system's DSL.
     * 
     * @return the current (and last) version number of the system's DSL.
     */
	public int getDSLVersion() {
		
		DBCollection dslVersionCollection = DBManager.getDSLVersionCollection();
		BasicDBObject dbo = (BasicDBObject) dslVersionCollection.findOne();
		int dslVersion = dbo.getInt("version");
		
		return dslVersion;
	}
	
	/**
	 * Returns an ArrayList which contains the DSL elements that belong to the category "subject".
	 * 
	 * @return an ArrayList which contains the DSL elements that belong to the category "subject".
	 */
	public ArrayList<DSLElementDTO> getDSLSubjects() {

		String collectionName = DSLCollectionNames.SUBJECTS_COLLECTION_NAME;
		DBCollection dslSubjectsCollection = DBManager.getDSLCollection(collectionName);
		DBCursor cursor = dslSubjectsCollection.find();
		ArrayList<DSLElementDTO> dslProperties = convertToArrayOfDSLElements(cursor);
		
		return dslProperties;
	}

	/**
	 * Returns an ArrayList which contains the DSL elements that belong to the category "property".
	 * 
	 * @return an ArrayList which contains the DSL elements that belong to the category "property".
	 */
	public ArrayList<DSLElementDTO> getDSLProperties() {

		String collectionName = DSLCollectionNames.PROPERTIES_COLLECTION_NAME;
		DBCollection dslPropertiesCollection = DBManager.getDSLCollection(collectionName);
		DBCursor cursor = dslPropertiesCollection.find();
		ArrayList<DSLElementDTO> dslProperties = convertToArrayOfDSLElements(cursor);
		
		return dslProperties;
	}
	
	/**
	 * Returns an ArrayList which contains the DSL elements that belong to the category "math_operation".
	 * 
	 * @return an ArrayList which contains the DSL elements that belong to the category "math_operation".
	 */
	public ArrayList<DSLElementDTO> getDSLMathOperations() {

		String collectionName = DSLCollectionNames.MATH_OPERATIONS_COLLECTION_NAME;
		DBCollection dslMathOperationsCollection = DBManager.getDSLCollection(collectionName);
		DBCursor cursor = dslMathOperationsCollection.find();
		ArrayList<DSLElementDTO> dslProperties = convertToArrayOfDSLElements(cursor);
		
		return dslProperties;
	}
	
	/**
	 * Returns an ArrayList which contains the DSL elements that belong to the category 
	 * "specific_math_operation".
	 * 
	 * @return an ArrayList which contains the DSL elements that belong to the category 
	 * 		   "specific_math_operation".
	 */
	public ArrayList<DSLElementDTO> getDSLSpecificMathOperations() {

		String collectionName = DSLCollectionNames.SPECIFIC_MATH_OPERATIONS_COLLECTION_NAME;
		DBCollection dslMathOperationsCollection = DBManager.getDSLCollection(collectionName);
		DBCursor cursor = dslMathOperationsCollection.find();
		ArrayList<DSLElementDTO> dslProperties = convertToArrayOfDSLElements(cursor);
		
		return dslProperties;
	}
	
	/**
	 * Returns an ArrayList which contains the DSL elements that belong to the category 
	 * "conditional_operator".
	 * 
	 * @return an ArrayList which contains the DSL elements that belong to the category 
	 * 		   "conditional_operator".
	 */
	public ArrayList<DSLElementDTO> getDSLConditionalOperators() {

		String collectionName = DSLCollectionNames.CONDITIONAL_OPERATORS_COLLECTION_NAME;
		DBCollection dslConditionalOperatorsCollection = DBManager.getDSLCollection(collectionName);
		DBCursor cursor = dslConditionalOperatorsCollection.find();
		ArrayList<DSLElementDTO> dslProperties = convertToArrayOfDSLElements(cursor);
		
		return dslProperties;
	}
	
	/**
	 * Returns an ArrayList which contains the DSL elements that belong to the category 
	 * "logical_operator".
	 * 
	 * @return an ArrayList which contains the DSL elements that belong to the category 
	 * 		   "logical_operator".
	 */
	public ArrayList<DSLElementDTO> getDSLLogicalOperators() {

		String collectionName = DSLCollectionNames.LOGICAL_OPERATORS_COLLECTION_NAME;
		DBCollection dslLogicalOperatorsCollection = DBManager.getDSLCollection(collectionName);
		DBCursor cursor = dslLogicalOperatorsCollection.find();
		ArrayList<DSLElementDTO> dslProperties = convertToArrayOfDSLElements(cursor);
		
		return dslProperties;
	}
	
	/**
	 * Returns an ArrayList which contains the DSL elements that belong to the category "unit".
	 * 
	 * @return an ArrayList which contains the DSL elements that belong to the category "unit".
	 */
	public ArrayList<DSLElementDTO> getDSLUnits() {

		String collectionName = DSLCollectionNames.UNITS_COLLECTION_NAME;
		DBCollection dslUnitsCollection = DBManager.getDSLCollection(collectionName);
		DBCursor cursor = dslUnitsCollection.find();
		ArrayList<DSLElementDTO> dslProperties = convertToArrayOfDSLElements(cursor);
		
		return dslProperties;
	}
}