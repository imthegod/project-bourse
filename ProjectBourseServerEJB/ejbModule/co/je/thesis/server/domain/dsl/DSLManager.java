package co.je.thesis.server.domain.dsl;

import java.util.ArrayList;

import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.dsl.DSLDataTransferObject;
import co.je.thesis.common.dtos.rules.RuleDTO;
import co.je.thesis.server.domain.dsl.commands.ICommand;
import co.je.thesis.server.persistence.dsl.DSLPersistence;
import co.je.thesis.server.persistence.rules.RulePersistence;

public class DSLManager {

	private DSLPersistence dslPersistence;
	private RulePersistence rulesPersistence;

	public DSLManager() {

		dslPersistence = new DSLPersistence();
		rulesPersistence = new RulePersistence();
	}

	// DSL

	public boolean isDSLUpToDate(int clientDSLVersion) {

		int dslVersion = dslPersistence.getDSLVersion();
		boolean answer = (dslVersion == clientDSLVersion);

		return answer;
	}

	public int getDSLVersion() {

		int dslVersion = dslPersistence.getDSLVersion();
		return dslVersion;
	}

	public DSLDataTransferObject getUpdatedDSL() {

		DSLDataTransferObject dsl = dslPersistence.getDSL();
		return dsl;
	}

	// Valid Rules (Grammar)

	public boolean areRulesUpToDate(int clientValidRulesVersion) {

		int validRulesVersion = rulesPersistence.getValidRulesVersion();
		boolean answer = (validRulesVersion == clientValidRulesVersion);

		return answer;
	}

	public int getValidRulesVersion() {

		int validRulesVersion = rulesPersistence.getValidRulesVersion();
		return validRulesVersion;
	}

	public ArrayList<ValidRule> getValidRules() {

		ArrayList<ValidRule> validRules = rulesPersistence.getValidRules();
		return validRules;
	}

	// Analysis services

	public void verifyAnalysis(AnalysisDTO analysisDTO) {

		ArrayList<ValidRule> validRules = getValidRules();
		DSLVerifier dslVerifier = new DSLVerifier(validRules);
		dslVerifier.verifyAnalysis(analysisDTO);
	}
	
	public ICommand getCommandBeforeConditionalOperator(RuleDTO ruleDTO) {
		
		DSLInterpreter dslInterpreter = new DSLInterpreter();
		ICommand commandBeforeCo = dslInterpreter.getCommandBeforeConditionalOperator(ruleDTO);
		
		return commandBeforeCo;
	}
	
	public ICommand getConditionalOperator(RuleDTO ruleDTO) {
		
		DSLInterpreter dslInterpreter = new DSLInterpreter();
		ICommand conditionalOperatorCommand = dslInterpreter.getConditionalOperatorCommand(ruleDTO);
		
		return conditionalOperatorCommand;
	}
	
	public ICommand getCommandAfterConditionalOperator(RuleDTO ruleDTO) {
		
		DSLInterpreter dslInterpreter = new DSLInterpreter();
		ICommand commandAfterCo = dslInterpreter.getCommandAfterConditionalOperator(ruleDTO);
		
		return commandAfterCo;
	}
}