package co.je.thesis.setup.rules.persistence;

import java.util.ArrayList;

import co.je.thesis.common.dbos.rules.RuleDBO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

/**
 * Class that knows how to persists the valid rules information into the DB.
 * 
 * @author Julian Espinel
 */
public class ValidRulesPersistence {
	
	/**
	 * Constructor
	 */
	public ValidRulesPersistence() {
	}

	/**
	 * Returns the current valid rules version.
	 * 
	 * @return current valid rules version.
	 */
	public int getValidRulesVersion() {

		DBCollection validRulesVersionCollection = DBManager.getValidRulesVersionCollection();
		BasicDBObject dbo = (BasicDBObject) validRulesVersionCollection.findOne();

		int validRulesVersion = 0;

		try {
			validRulesVersion = dbo.getInt("version");
		} catch (Exception e) {
			// returns 0 because it is the first ValidRules version.
		}

		return validRulesVersion;
	}

	/**
	 * Increments the valid rules version by one.
	 */
	public void updateValidRulesVersion() {

		int validRulesVersion = getValidRulesVersion();
		validRulesVersion++;

		DBCollection validRulesVersionCollection = DBManager.getValidRulesVersionCollection();
		BasicDBObject dbo = new BasicDBObject("version", validRulesVersion);

		// Eliminates the previous version and inserts the new one.
		validRulesVersionCollection.drop();
		validRulesVersionCollection.insert(dbo);
	}

	/**
	 * Updates the valid rules version with the given number.
	 * 
	 * @param validRulesVersion the new valid rules version.
	 */
	public void updateValidRulesVersion(int validRulesVersion) {

		DBCollection validRulesVersionCollection = DBManager.getValidRulesVersionCollection();
		BasicDBObject dbo = new BasicDBObject("version", validRulesVersion);

		// Eliminates the previous version and inserts the new one.
		validRulesVersionCollection.drop();
		validRulesVersionCollection.insert(dbo);
	}

	/**
	 * Inserts multiple rules into a specific collection.
	 * 
	 * @param collectionName the name of the collection in which we want to insert the rules.
	 * @param rulesDBO an ArrayList of rules. The rules we want to insert into the specific collection. 
	 */
	public void insertRulesIntoCollection(String collectionName,
			ArrayList<RuleDBO> rulesDBO) {

		DBCollection validRulesCollection = DBManager.getDBCollection(collectionName);

		for (int i = 0; i < rulesDBO.size(); i++) {

			RuleDBO ruleDBO = rulesDBO.get(i);
			validRulesCollection.insert(ruleDBO);
		}
	}

	/**
	 * Deletes the collection with the given name.
	 * 
	 * @param collectionName the name of the collection to delete.
	 */
	public void dropCollection(String collectionName) {

		DBCollection validRulesCollection = DBManager.getDBCollection(collectionName);
		validRulesCollection.drop();
	}
}
