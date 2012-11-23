package co.je.thesis.common.verifiers;

import java.util.ArrayList;

import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.common.dtos.rules.RuleDTO;

public class RuleVerifier {

	private ArrayList<ValidRule> validRules;

	public RuleVerifier(ArrayList<ValidRule> validRules) {
		this.validRules = validRules;
	}

	public ArrayList<ValidRule> getValidRules() {
		return validRules;
	}

	public void addValidRule(ValidRule validRule) {

		validRules.add(validRule);
	}

	/**
	 * Given a rule, verifies if it is a valid one.
	 * 
	 * @param ruleToVerify, the rule to verify to be valid.
	 * @return true if the parameter is a valid rule, false if it is not.
	 */
	public boolean isValidRule(RuleDTO ruleToVerify) {

		boolean isValid = false;

		for (int i = 0; i < validRules.size() && !isValid; i++) {

			ValidRule validRule = validRules.get(i);
			isValid = validRule.isValidRule(ruleToVerify);
		}

		return isValid;
	}

	private boolean isThisCategoryIntoTheArray(ArrayList<String> categories, String cateogry) {

		boolean foundRepeatedCategory = false;

		for (int i = 0; i < categories.size() && !foundRepeatedCategory; i++) {

			String elementCategory = categories.get(i);
			foundRepeatedCategory = elementCategory.equalsIgnoreCase(cateogry);
		}

		return foundRepeatedCategory;
	}

	/**
	 * Given a rule, it analyzes it and returns a list of the possible DSL
	 * elements categories to make this rule a valid one.
	 * 
	 * @param rule
	 *            , a rule that it is not finished.
	 * @return a list with the next possible categories values to complete the
	 *         rule, and make it a valid one.
	 */
	public ArrayList<String> getNextValidDSLElementsCategories(RuleDTO ruleDTO) {

		ArrayList<String> possibleCategories = new ArrayList<String>();
		
		for (int i = 0; i < validRules.size(); i++) {

			ValidRule validRule = validRules.get(i);
			String nextPossibleCategory = validRule.getNextPossibleElementCategory(ruleDTO);
			
			if (!nextPossibleCategory.equals("")) {
				
				if (!isThisCategoryIntoTheArray(possibleCategories, nextPossibleCategory)) {
					
					possibleCategories.add(nextPossibleCategory);
				}
			}
		}

		return possibleCategories;
	}
}