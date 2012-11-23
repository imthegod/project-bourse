package co.je.thesis.server.domain.dsl.commands;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

public class AverageCommand extends BasicCommand {

	public static final String COMMAND_NAME = "AVERAGE_COMMAND";

	/**
	 * The String which represents the math operation to execute.
	 */
	private String mathOperation;

	/**
	 * The property of a stock. For example: close price, volume, etc.
	 */
	private String property;

	/**
	 * Time frame is a String object which contains one date. The date is
	 * formatted like this: dd-mm-yyyy
	 */
	private String timeFrame1;

	/**
	 * Time frame is a String object which contains one date. The date is
	 * formatted like this: dd-mm-yyyy
	 */
	private String timeFrame2;

	public AverageCommand() {
	}

	public AverageCommand(String mathOperation, String property, String timeFrame1,
			String timeFrame2) {

		this.mathOperation = mathOperation;
		this.property = property;
		this.timeFrame1 = timeFrame1;
		this.timeFrame2 = timeFrame2;
	}

	public String getMathOperation() {
		return mathOperation;
	}

	public String getProperty() {
		return property;
	}

	public String getTimeFrame1() {
		return timeFrame1;
	}

	public String getTimeFrame2() {
		return timeFrame2;
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

		int numberOfFields = 4;

		if (elementsArray.size() == numberOfFields) {

			if (category.equalsIgnoreCase(DSLCategories.AVERAGE)
					&& elementValueIsValid(dslElementDTO)) {

				i++;
				dslElementDTO = elementsArray.get(i);
				category = dslElementDTO.getCategory();

				if (category.equalsIgnoreCase(DSLCategories.PROPERTY)
						&& elementValueIsValid(dslElementDTO)) {

					i++;
					dslElementDTO = elementsArray.get(i);
					category = dslElementDTO.getCategory();

					if (category.equalsIgnoreCase(DSLCategories.TIME_FRAME)
							&& elementValueIsValid(dslElementDTO)) {

						i++;
						dslElementDTO = elementsArray.get(i);
						category = dslElementDTO.getCategory();

						if (category.equalsIgnoreCase(DSLCategories.TIME_FRAME)
								&& elementValueIsValid(dslElementDTO)) {

							elementsCorrespondToThisCommand = true;
						}
					}
				}
			}

			// With subject first
		} else if (elementsArray.size() == (numberOfFields + 1)) {

			if (category.equalsIgnoreCase(DSLCategories.SUBJECT)
					&& elementValueIsValid(dslElementDTO)) {

				elementsArray.remove(i);
				elementsCorrespondToThisCommand = dslElementsCorrespondToThisCommand(elementsArray);
			}
		}

		return elementsCorrespondToThisCommand;
	}

	public static ICommand buildCommand(ArrayList<DSLElementDTO> elementsArray) {

		int numberOfFields = 4;

		int i = 0;

		// With subject first
		if (elementsArray.size() == (numberOfFields + 1)) {

			i = 1;
		}

		DSLElementDTO mathOperationElement = elementsArray.get(i);

		i++;
		DSLElementDTO propertyElement = elementsArray.get(i);

		i++;
		DSLElementDTO timeFrame1Element = elementsArray.get(i);

		i++;
		DSLElementDTO timeFrame2Element = elementsArray.get(i);

		String mathOperation = mathOperationElement.getValue();
		String property = propertyElement.getValue();
		String timeFrame1 = timeFrame1Element.getValue();
		String timeFrame2 = timeFrame2Element.getValue();
		AverageCommand averageCommand = new AverageCommand(mathOperation, property, timeFrame1,
				timeFrame2);

		return averageCommand;
	}
}