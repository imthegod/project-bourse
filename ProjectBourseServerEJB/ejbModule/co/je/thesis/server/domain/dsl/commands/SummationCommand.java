package co.je.thesis.server.domain.dsl.commands;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

public class SummationCommand extends BasicCommand {

	public static final String COMMAND_NAME = "SUMMATION_COMMAND";

	/**
	 * The String which represents the math operation to execute.
	 */
	private String mainMathOperation;

	/**
	 * The String which represents the math operation to execute.
	 */
	private String nestedMathOperation;

	/**
	 * The property of a stock. For example: close price, volume, etc.
	 */
	private String property1;

	/**
	 * The property of a stock. For example: close price, volume, etc.
	 */
	private String property2;

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

	public SummationCommand() {
	}

	public SummationCommand(String mainMathOperation, String nestedMathOperation, String property1,
			String property2, String timeFrame1, String timeFrame2) {

		this.mainMathOperation = mainMathOperation;
		this.nestedMathOperation = nestedMathOperation;
		this.property1 = property1;
		this.property2 = property2;
		this.timeFrame1 = timeFrame1;
		this.timeFrame2 = timeFrame2;
	}

	public String getMainMathOperation() {
		return mainMathOperation;
	}

	public String getNestedMathOperation() {
		return nestedMathOperation;
	}

	public String getProperty1() {
		return property1;
	}

	public String getProperty2() {
		return property2;
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

		int numberOfFields = 6;

		if (elementsArray.size() == numberOfFields) {

			if (category.equalsIgnoreCase(DSLCategories.SUMMATION)
					&& elementValueIsValid(dslElementDTO)) {

				i++;
				dslElementDTO = elementsArray.get(i);
				category = dslElementDTO.getCategory();

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

		int numberOfFields = 6;
		
		int i = 0;
			
		// With subject first
		if (elementsArray.size() == (numberOfFields + 1)) {
			
			i = 1;
		}
		
		DSLElementDTO summationElement = elementsArray.get(i);

		i++;
		DSLElementDTO nestedMathOperationElement = elementsArray.get(i);

		i++;
		DSLElementDTO property1Element = elementsArray.get(i);

		i++;
		DSLElementDTO property2Element = elementsArray.get(i);

		i++;
		DSLElementDTO timeFrame1Element = elementsArray.get(i);

		i++;
		DSLElementDTO timeFrame2Element = elementsArray.get(i);

		String summation = summationElement.getValue();
		String nestedMathOperation = nestedMathOperationElement.getValue();
		String property1 = property1Element.getValue();
		String property2 = property2Element.getValue();
		String timeFrame1 = timeFrame1Element.getValue();
		String timeFrame2 = timeFrame2Element.getValue();
		SummationCommand summationCommand = new SummationCommand(summation, nestedMathOperation,
				property1, property2, timeFrame1, timeFrame2);

		return summationCommand;
	}
}
