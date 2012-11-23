package co.je.thesis.setup.rules;

import java.io.File;
import java.util.ArrayList;

import co.je.thesis.common.constants.RulesCollectionNames;
import co.je.thesis.common.dbos.rules.RuleDBO;
import co.je.thesis.setup.rules.constants.ValidRulesSources;
import co.je.thesis.setup.rules.logic.ValidRulesLoader;
import co.je.thesis.setup.rules.logic.ValidRulesParser;
import co.je.thesis.setup.rules.persistence.ValidRulesPersistence;

public class ValidRulesMain {
	
	private ValidRulesLoader validRulesLoader;
	private ValidRulesParser validRulesParser;
	private ValidRulesPersistence validRulesPersistence;
	
	public ValidRulesMain() {
		
		validRulesLoader = new ValidRulesLoader();
		validRulesParser = new ValidRulesParser();
		validRulesPersistence = new ValidRulesPersistence();
	}
	
	public void updateValidRulesVersion() {
		
		validRulesPersistence.updateValidRulesVersion();
	}
	
	public void createValidRules() {
		
		String validRulesCollectionName = RulesCollectionNames.VALID_RULES_COLLECTION_NAME;
		validRulesPersistence.dropCollection(validRulesCollectionName);
		
		File excelFile = validRulesLoader.retrieveFile(ValidRulesSources.VALID_RULES_FILE_NAME);
		ArrayList<RuleDBO> validRulesDBO = validRulesParser.buildValidRulesDBOs(excelFile);
		validRulesPersistence.insertRulesIntoCollection(validRulesCollectionName, validRulesDBO);
	}
	
	public static void main(String[] args) {
		
		System.out.println("ValidRulesMain: begins");
		
		ValidRulesMain validRulesMain = new ValidRulesMain();
		validRulesMain.updateValidRulesVersion();
		validRulesMain.createValidRules();
		
		System.out.println("ValidRulesMain: ends");
	}
}