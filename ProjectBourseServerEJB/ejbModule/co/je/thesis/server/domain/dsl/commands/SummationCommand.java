package co.je.thesis.server.domain.dsl.commands;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

/**
 * This class models the summation command.
 * 
 * @author Julian Espinel
 */
public class SummationCommand extends BasicCommand {

	/**
	 * This constant stores the command name.
	 */
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
	 * formatted like this: dd-MM-yyyy.
	 */
	private String timeFrame1;

	/**
	 * Time frame is a String object which contains one date. The date is
	 * formatted like this: dd-MM-yyyy.
	 */
	private String timeFrame2;

	public SummationCommand() {
	}

	/**
	 * SummationCommand constructor.
	 * 
	 * @param mainMathOperation the main/parent/most external math operation (summation).
	 * @param nestedMathOperation a math operation different tah summation.
	 * @param property1 a property of a stock. For example: close price, volume, etc.
	 * @param property2 a property of a stock. For example: close price, volume, etc.
	 * @param timeFrame1 a initial date formatted like this: dd-MM-yyyy.
	 * @param timeFrame2 a final date formatted like this: dd-MM-yyyy.
	 */
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

	/**
	 * Builds a SummationCommand with the DSL elements contained into the ArrayList.
	 * 
	 * @param elementsArray the ArrayList that contains the DSL elements.
	 * @return a SummationCommand with the DSL elements contained into the ArrayList.
	 */
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
