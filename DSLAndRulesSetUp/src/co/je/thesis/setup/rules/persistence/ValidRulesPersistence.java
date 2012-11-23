package co.je.thesis.setup.rules.persistence;

import java.util.ArrayList;

import co.je.thesis.common.dbos.rules.RuleDBO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class ValidRulesPersistence {
	
	public ValidRulesPersistence() {
	}

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

	public void updateValidRulesVersion() {

		int validRulesVersion = getValidRulesVersion();
		validRulesVersion++;

		DBCollection validRulesVersionCollection = DBManager.getValidRulesVersionCollection();
		BasicDBObject dbo = new BasicDBObject("version", validRulesVersion);

		// Eliminates the previous version and inserts the new one.
		validRulesVersionCollection.drop();
		validRulesVersionCollection.insert(dbo);
	}

	public void updateValidRulesVersion(int validRulesVersion) {

		DBCollection validRulesVersionCollection = DBManager.getValidRulesVersionCollection();
		BasicDBObject dbo = new BasicDBObject("version", validRulesVersion);

		// Eliminates the previous version and inserts the new one.
		validRulesVersionCollection.drop();
		validRulesVersionCollection.insert(dbo);
	}

	public void insertRulesIntoCollection(String collectionName,
			ArrayList<RuleDBO> rulesDBO) {

		DBCollection validRulesCollection = DBManager.getDBCollection(collectionName);

		for (int i = 0; i < rulesDBO.size(); i++) {

			RuleDBO ruleDBO = rulesDBO.get(i);
			validRulesCollection.insert(ruleDBO);
		}
	}

	public void dropCollection(String collectionName) {

		DBCollection validRulesCollection = DBManager.getDBCollection(collectionName);
		validRulesCollection.drop();
	}
}
