package co.je.thesis.server.domain.dsl.commands;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

public class MathOpOver2PropertiesCommand extends BasicCommand {

	public static final String COMMAND_NAME = "MATH_OVER_TWO_PROPERTIES_COMMAND";

	/**
	 * The String which represents the math operation to execute.
	 */
	private String mathOperation;

	/**
	 * The property of a stock. For example: close price, volume, etc.
	 */
	private String firstProperty;

	/**
	 * The property of a stock. For example: close price, volume, etc.
	 */
	private String secondProperty;

	/**
	 * Time frame is a String object which contains one date. The date is
	 * formatted like this: dd-mm-yyyy
	 */
	private String timeFrame;

	public MathOpOver2PropertiesCommand() {
	}

	public MathOpOver2PropertiesCommand(String mathOperation, String firstProperty,
			String secondProperty, String timeFrame) {

		this.mathOperation = mathOperation;
		this.firstProperty = firstProperty;
		this.secondProperty = secondProperty;
		this.timeFrame = timeFrame;
	}

	public String getMathOperation() {
		return mathOperation;
	}

	public String getFirstProperty() {
		return firstProperty;
	}

	public String getSecondProperty() {
		return secondProperty;
	}

	public String getTimeFrame() {
		return timeFrame;
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

			if (category.equalsIgnoreCase(DSLCategories.MATH_OPERATION)
					&& elementValueIsValid(dslElementDTO)) {

				i++;
				dslElementDTO = elementsArray.get(i);
				category = dslElementDTO.getCategory();

				if (category.equalsIgnoreCase(DSLCategories.PROPERTY)
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
		DSLElementDTO property1Element = elementsArray.get(i);

		i++;
		DSLElementDTO property2Element = elementsArray.get(i);

		i++;
		DSLElementDTO timeFrameElement = elementsArray.get(i);

		String mathOperation = mathOperationElement.getValue();
		String property1 = property1Element.getValue();
		String property2 = property2Element.getValue();
		String timeFrame = timeFrameElement.getValue();
		MathOpOver2PropertiesCommand mathOpOver2PropertiesCommand = new MathOpOver2PropertiesCommand(
				mathOperation, property1, property2, timeFrame);

		return mathOpOver2PropertiesCommand;
	}
}