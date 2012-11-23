package co.je.thesis.server.persistence.rules;

import java.util.ArrayList;

import co.je.thesis.common.dbos.rules.RuleDBO;
import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.server.infrastructure.translators.dsl.RuleTranslator;
import co.je.thesis.server.persistence.DBManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class RulePersistence {
	
	public RulePersistence() {
	}
	
	public int getValidRulesVersion() {
		
		DBCollection validRulesVersionCollection = DBManager.getValidRulesVersionCollection();
		BasicDBObject basicDBO = (BasicDBObject) validRulesVersionCollection.findOne();
		int validRulesVerion = basicDBO.getInt("version");
		
		return validRulesVerion;
	}
	
	public ArrayList<ValidRule> getValidRules() {
		
		DBCollection validRulesCollection = DBManager.getValidRulesCollection();
		DBCursor cursor = validRulesCollection.find();
		
		ArrayList<ValidRule> validRules = new ArrayList<ValidRule>();
		
		RuleTranslator translator = new RuleTranslator();
		
		while (cursor.hasNext()) {

			BasicDBObject basicDBO = (BasicDBObject) cursor.next();
			RuleDBO ruleDBO = translator.toDBO(basicDBO);
			ValidRule validRule = translator.toDomainObject(ruleDBO);
			
			validRules.add(validRule);
		}
		
		return validRules;
	}
}