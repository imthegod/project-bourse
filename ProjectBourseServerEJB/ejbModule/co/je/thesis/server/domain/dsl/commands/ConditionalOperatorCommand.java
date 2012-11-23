package co.je.thesis.server.domain.dsl.commands;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

public class ConditionalOperatorCommand extends BasicCommand {
	
	public static final String COMMAND_NAME = "CONDITIONAL_OPERATOR_COMMAND";
	
	public static final String LESS = "<";
	public static final String LESS_EQUAL = "<=";
	public static final String GREATER = ">";
	public static final String GREATER_EQUAL = ">=";
	public static final String EQUAL_TO = "=";
	public static final String NOT_EQUAL = "!=";
	
	/**
	 * The conditional operatin as a String object.
	 */
	private String conditionalOperator;
	
	public ConditionalOperatorCommand() {
	}

	public ConditionalOperatorCommand(String conditionalOperator) {
		this.conditionalOperator = conditionalOperator;
	}

	public String getConditionalOperator() {
		return conditionalOperator;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
	
	public static boolean dslElementsCorrespondToThisCommand(ArrayList<DSLElementDTO> elementsArray) {
		
		boolean elementsCorrespondToThisCommand = false;
		
		int i = 0;
		DSLElementDTO dslElementDTO = elementsArray.get(i);
		String category = dslElementDTO.getCategory();

		int numberOfFields = 1;

		if (elementsArray.size() == numberOfFields) {
			
			if (category.equalsIgnoreCase(DSLCategories.CONDITIONAL_OPERATOR)
					&& elementValueIsValid(dslElementDTO)) {
				
				elementsCorrespondToThisCommand = true;
			}
		}
		
		return elementsCorrespondToThisCommand;
	}
}
