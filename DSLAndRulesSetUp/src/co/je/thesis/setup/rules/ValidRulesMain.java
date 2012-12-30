package co.je.thesis.setup.rules;

import java.io.File;
import java.util.ArrayList;

import co.je.thesis.common.constants.RulesCollectionNames;
import co.je.thesis.common.dbos.rules.RuleDBO;
import co.je.thesis.setup.rules.constants.ValidRulesSources;
import co.je.thesis.setup.rules.logic.ValidRulesLoader;
import co.je.thesis.setup.rules.logic.ValidRulesParser;
import co.je.thesis.setup.rules.persistence.ValidRulesPersistence;

/**
 * This class executes all the logic needed to load the valid rules into the system.
 * 
 * @author Julian Espinel
 */
public class ValidRulesMain {
	
	/**
	 * Attribute that knows how to load the files which contains the valid rules information.
	 */
	private ValidRulesLoader validRulesLoader;
	
	/**
	 * Attribute that knows how to parse the information stored in files, to actual valid rules elements.
	 */
	private ValidRulesParser validRulesParser;
	
	/**
	 * Attribute that knows how to persist the valid rules data into the system's DB.
	 */
	private ValidRulesPersistence validRulesPersistence;
	
	public ValidRulesMain() {
		
		validRulesLoader = new ValidRulesLoader();
		validRulesParser = new ValidRulesParser();
		validRulesPersistence = new ValidRulesPersistence();
	}
	
	/**
	 * Updates the valid rules version.
	 */
	public void updateValidRulesVersion() {
		
		validRulesPersistence.updateValidRulesVersion();
	}
	
	/**
	 * Create all the valid rules into the system DB.
	 */
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