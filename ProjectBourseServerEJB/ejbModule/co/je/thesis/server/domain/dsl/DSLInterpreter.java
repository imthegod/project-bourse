package co.je.thesis.server.domain.dsl;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;
import co.je.thesis.server.domain.dsl.commands.AverageCommand;
import co.je.thesis.server.domain.dsl.commands.CompositeCommand;
import co.je.thesis.server.domain.dsl.commands.ConditionalOperatorCommand;
import co.je.thesis.server.domain.dsl.commands.ICommand;
import co.je.thesis.server.domain.dsl.commands.MathOpOver2PropertiesCommand;
import co.je.thesis.server.domain.dsl.commands.NumberCommand;
import co.je.thesis.server.domain.dsl.commands.PropertyCommand;
import co.je.thesis.server.domain.dsl.commands.SummationCommand;

public class DSLInterpreter {

	public DSLInterpreter() {
	}

	private ArrayList<DSLElementDTO> getDSLElementsBeforeConditionalOperator(RuleDTO ruleDTO) {

		ArrayList<DSLElementDTO> ruleElements = ruleDTO.getElements();
		ArrayList<DSLElementDTO> elementsBefore = new ArrayList<DSLElementDTO>();
		boolean foundConditionalOperator = false;

		for (int i = 0; i < ruleElements.size() && !foundConditionalOperator; i++) {

			DSLElementDTO dslElement = ruleElements.get(i);
			String category = dslElement.getCategory();

			if (!category.equalsIgnoreCase(DSLCategories.CONDITIONAL_OPERATOR)) {
				
				elementsBefore.add(dslElement);
				
			} else {
				
				foundConditionalOperator = true;
			}
		}

		return elementsBefore;
	}

	private ArrayList<DSLElementDTO> getDSLElementsAfterConditionalOperator(RuleDTO ruleDTO) {

		ArrayList<DSLElementDTO> ruleElements = ruleDTO.getElements();
		ArrayList<DSLElementDTO> elementsAfter = new ArrayList<DSLElementDTO>();
		boolean foundConditionalOperator = false;

		for (int i = 0; i < ruleElements.size(); i++) {

			DSLElementDTO dslElement = ruleElements.get(i);
			String category = dslElement.getCategory();

			if (category.equalsIgnoreCase(DSLCategories.CONDITIONAL_OPERATOR)) {
				
				foundConditionalOperator = true;
				
			} else if (foundConditionalOperator) {
				
				if (!category.equalsIgnoreCase(DSLCategories.CONDITIONAL_OPERATOR)) {
					
					elementsAfter.add(dslElement);
				}
			}
		}

		return elementsAfter;
	}

	private ICommand buildCommand(ArrayList<DSLElementDTO> dslElements) {

		ICommand command = null;
		
		if (NumberCommand.dslElementsCorrespondToThisCommand(dslElements)) {
			
			command = NumberCommand.buildCommand(dslElements);
			
		} else if (PropertyCommand.dslElementsCorrespondToThisCommand(dslElements)) {
			
			command = PropertyCommand.buildCommand(dslElements);
			
		} else if (AverageCommand.dslElementsCorrespondToThisCommand(dslElements)) {
			
			command = AverageCommand.buildCommand(dslElements);
			
		} else if (MathOpOver2PropertiesCommand.dslElementsCorrespondToThisCommand(dslElements)) {
			
			command = MathOpOver2PropertiesCommand.buildCommand(dslElements);
			
		} else if (SummationCommand.dslElementsCorrespondToThisCommand(dslElements)) {
			
			command = SummationCommand.buildCommand(dslElements);
			
		} else if (CompositeCommand.dslElementsCorrespondToThisCommand(dslElements)) {
			
			command = CompositeCommand.buildCommand(dslElements);
		}
		
		return command;
	}

	public ICommand getCommandBeforeConditionalOperator(RuleDTO ruleDTO) {

		ArrayList<DSLElementDTO> elementsBeforeCO = getDSLElementsBeforeConditionalOperator(ruleDTO);
		ICommand command = buildCommand(elementsBeforeCO);

		return command;
	}

	public ICommand getConditionalOperatorCommand(RuleDTO ruleDTO) {

		ArrayList<DSLElementDTO> ruleElements = ruleDTO.getElements();
		ICommand conditionalOperatorCommand = null;
		boolean foundConditionalOperator = false;

		for (int i = 0; i < ruleElements.size() && !foundConditionalOperator; i++) {

			DSLElementDTO dslElement = ruleElements.get(i);
			String category = dslElement.getCategory();

			if (category.equalsIgnoreCase(DSLCategories.CONDITIONAL_OPERATOR)) {

				String dslElementValue = dslElement.getValue();
				conditionalOperatorCommand = new ConditionalOperatorCommand(dslElementValue);
				foundConditionalOperator = true;
			}
		}

		return conditionalOperatorCommand;
	}

	public ICommand getCommandAfterConditionalOperator(RuleDTO ruleDTO) {

		ArrayList<DSLElementDTO> elementsAfterCO = getDSLElementsAfterConditionalOperator(ruleDTO);
		ICommand command = buildCommand(elementsAfterCO);

		return command;
	}
}