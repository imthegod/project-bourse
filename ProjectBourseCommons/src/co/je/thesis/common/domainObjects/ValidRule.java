package co.je.thesis.common.domainObjects;

import java.util.ArrayList;

import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;

/**
 * Class that models a valid rule object.
 * 
 * @author Julian Espinel
 */
public class ValidRule {
	
	/**
	 * The DSL elements that compound the valid rule.
	 */
	private ArrayList<DSLElementDTO> elements;

	/**
	 * Constructor with parameters.
	 * 
	 * @param elements the DSL elements that compound the valid rule.
	 */
	public ValidRule(ArrayList<DSLElementDTO> elements) {
		this.elements = elements;
	}

	/**
	 * Returns the DSL elements that compound the valid rule.
	 * 
	 * @return the DSL elements that compound the valid rule.
	 */
	public ArrayList<DSLElementDTO> getElements() {
		return elements;
	}

	/**
	 * Adds a DSL element to the valid rule.
	 * 
	 * @param dslElement the DSL element to be added.
	 */
	public void addElement(DSLElementDTO dslElement) {

		elements.add(dslElement);
	}

	/**
	 * Verifies if a given rule is a valid rule.
	 * 
	 * @param ruleToVerify any rule of type RuleDTO (Data transfer object).
	 * @return True if the DSL elements of the ruleToVerify are the same and in the same order of the 
	 * 		   DSL elements of the valid rule. 
	 */
	public boolean isValidRule(RuleDTO ruleToVerify) {
		
		boolean answer = false;
		boolean isSameNumberOfElements = (elements.size() == (ruleToVerify.getElements().size()));
		
		if (isSameNumberOfElements) {
			
			boolean belongToTheSameCategory = true;
			ArrayList<DSLElementDTO> paramElements = ruleToVerify.getElements();
			for (int i = 0; i < elements.size() && belongToTheSameCategory; i++) {
				
				DSLElementDTO dslElement = elements.get(i);
				DSLElementDTO paramElement = paramElements.get(i);
				
				belongToTheSameCategory = dslElement.belongToTheSameCategory(paramElement);
			}
			
			if (belongToTheSameCategory) {
				
				answer = true;
			}
		}
		
		return answer;
	}
	
	/**
	 * Given a rule that it is not completed, this method compares the elements
	 * of this object and the elements of the rule that enter as a parameter. If
	 * the elements are the same until the end of the elements of the parameter
	 * rule, then returns the elements of this object that are not into the
	 * parameter elements.
	 * 
	 * @param rule, a rule that it is not completed.
	 * @return return the elements that are into this object, but not into the
	 *         parameter's elements.
	 */
	public String getNextPossibleElementCategory(RuleDTO ruleDTO) {
		

		String nextPossibleCategory = "";
		ArrayList<DSLElementDTO> ruleDTOElements = ruleDTO.getElements();
		
		if (elements.size() > ruleDTOElements.size()) {
			
			boolean belongToTheSameCategory = true;
			
			for (int i = 0; i < ruleDTOElements.size() && belongToTheSameCategory; i++) {
				
				DSLElementDTO dslElement = elements.get(i);
				DSLElementDTO paramElement = ruleDTOElements.get(i);
				
				belongToTheSameCategory = dslElement.belongToTheSameCategory(paramElement);
			}
			
			if (belongToTheSameCategory) {
				
				int nextElementIndex = ruleDTOElements.size();
				
				// Verifies that nextElementIndex < elements.size()
				if (nextElementIndex < elements.size()) {
					
					DSLElementDTO dslElementDTO = elements.get(nextElementIndex);
					nextPossibleCategory = dslElementDTO.getCategory();
				}
			}
		}
		
		return nextPossibleCategory;
	}
}