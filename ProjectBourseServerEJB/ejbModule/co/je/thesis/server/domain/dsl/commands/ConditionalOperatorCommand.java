package co.je.thesis.server.domain.dsl.commands;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

/**
 * This class models the conditional operator command.
 * 
 * @author Julian Espinel
 */
public class ConditionalOperatorCommand extends BasicCommand {
	
	/**
	 * This constant stores the command name.
	 */
	public static final String COMMAND_NAME = "CONDITIONAL_OPERATOR_COMMAND";
	
	/*
	 * Conditional constants.
	 */
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

	/**
	 * ConditionalOperatorCommand constructor.
	 * 
	 * @param conditionalOperator the conditional operator of the command. Should match one
	 * 							  of the constants conditional defined in this class.
	 */
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
	
	/**
	 * Given an ArrayList of DSL elements, this method determines if all the elements of the 
	 * ArrayList correspond to this command and are in the correct order.
	 * 
	 * @param elementsArray the ArrayList that contains the DSL elements.
	 * @return if all the elements of the ArrayList correspond to this command and are in the 
	 * 		   correct order, then returns true, else returns false.
	 */
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