package co.je.thesis.server.domain.dsl.commands;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

public class NumberCommand extends BasicCommand {

	public static final String COMMAND_NAME = "NUMBER_COMMAND";

	private double number;
	private String unit;

	public NumberCommand() {
	}

	public NumberCommand(double number, String unit) {
		
		// Corrects the number for percentage units
		if (unit.equalsIgnoreCase("%")) {

			this.number = (number / 100);
			
		} else {
			
			this.number = number;
		}
		
		this.unit = unit;
	}

	public double getNumber() {
		return number;
	}

	public String getUnit() {
		return unit;
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

		int numberOfFields = 2;

		if (elementsArray.size() == numberOfFields) {

			if (category.equalsIgnoreCase(DSLCategories.NUMBER)
					&& elementValueIsValid(dslElementDTO)) {

				i++;
				dslElementDTO = elementsArray.get(i);
				category = dslElementDTO.getCategory();

				if (category.equalsIgnoreCase(DSLCategories.UNIT)
						&& elementValueIsValid(dslElementDTO)) {

					elementsCorrespondToThisCommand = true;
				}
			}
		}

		return elementsCorrespondToThisCommand;
	}

	public static ICommand buildCommand(ArrayList<DSLElementDTO> elementsArray) {

		int i = 0;
		DSLElementDTO numberElement = elementsArray.get(i);

		i++;
		DSLElementDTO unitElement = elementsArray.get(i);

		String numberString = numberElement.getValue();
		double doubleValue = Double.parseDouble(numberString);
		String unitValue = unitElement.getValue();

		NumberCommand numberCommand = new NumberCommand(doubleValue, unitValue);

		return numberCommand;
	}
}