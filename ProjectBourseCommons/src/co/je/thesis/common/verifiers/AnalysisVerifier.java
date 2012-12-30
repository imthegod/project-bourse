package co.je.thesis.common.verifiers;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;

/**
 * This class knows how to verify if an analysis is valid or not. 
 * 
 * @author Julian Espinel
 */
public class AnalysisVerifier {

	/**
	 * Attribute that knows how to verify the rules that compound the analysis.
	 */
	private RuleVerifier ruleVerifier;
	
	/**
	 * Constructor with parameters. 
	 * 
	 * @param validRules the system's valid rules.
	 */
	public AnalysisVerifier(ArrayList<ValidRule> validRules) {
		
		this.ruleVerifier = new RuleVerifier(validRules);
	}
	
	/**
	 * Verifies if the analysis logical operators are rulesDTO.size() - 1.
	 * 
	 * @param analysisDTO, the analysis object which will be verified.
	 */
	private void verifyAnalysisStructure(AnalysisDTO analysisDTO) {

		ArrayList<RuleDTO> rulesDTO = analysisDTO.getRulesDTO();
		ArrayList<DSLElementDTO> logicalOperatorsDTO = analysisDTO.getLogicalOperatorsDTO();

		int numberOfRules = rulesDTO.size();
		int numberOfLogicalOperators = logicalOperatorsDTO.size();

		if (numberOfLogicalOperators != (numberOfRules - 1)) {
			
			String exceptionMessage = "The analysis object structure is not valid.";
			throw new IllegalStateException(exceptionMessage);
		}
	}

	/**
	 * Verifies if all the objects within the logical operators ArrayList belong
	 * to the logical operator category.
	 * 
	 * @param analysisDTO, the analysis object which will be verified.
	 */
	private void verifyLogicalOperatorsObjectsAreValid(AnalysisDTO analysisDTO) {

		boolean isLogicalOperator = true;
		ArrayList<DSLElementDTO> logicalOperatorsDTO = analysisDTO.getLogicalOperatorsDTO();

		for (int i = 0; i < logicalOperatorsDTO.size() && isLogicalOperator; i++) {

			DSLElementDTO logicalOperatorDTO = logicalOperatorsDTO.get(i);
			String category = logicalOperatorDTO.getCategory();
			isLogicalOperator = category.equalsIgnoreCase(DSLCategories.LOGICAL_OPERATOR);
		}

		if (!isLogicalOperator) {
			
			String exceptionMessage = "At least one object into the logicalOperators array is not a logical operator.";
			throw new IllegalStateException(exceptionMessage);
		}
	}
	
	/**
	 * Verifies that all the rules of the analysis are valid.
	 * 
	 * @param analysisDTO, the analysis object which will be verified.
	 */
	private void verifyAnalysisRulesAreValid(AnalysisDTO analysisDTO) {
		
		boolean isValidRule = true;
		ArrayList<RuleDTO> rulesDTO = analysisDTO.getRulesDTO();
		
		for (int i = 0; i < rulesDTO.size() && isValidRule; i++) {
			
			RuleDTO ruleDTO = rulesDTO.get(i);
			isValidRule = ruleVerifier.isValidRule(ruleDTO);
		}
		
		if (!isValidRule) {
			
			String exceptionMessage = "At least one rule of the analysis is not a valid one.";
			throw new IllegalStateException(exceptionMessage);
		}
	}

	/**
	 * Verifies that the analysisDTO parameter is a valid analysis.
	 * This means that it has a valid structure, and all its rules
	 * are also valid.
	 * 
	 * @param analysisDTO, the analysis object which will be verified.
	 */
	public void verifyAnalysisIsValid(AnalysisDTO analysisDTO) {

		verifyAnalysisStructure(analysisDTO);
		verifyLogicalOperatorsObjectsAreValid(analysisDTO);
		verifyAnalysisRulesAreValid(analysisDTO);
	}
}