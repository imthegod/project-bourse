package co.je.thesis.server.domain.dsl.commands;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

/**
 * This class models a PropertyCommand.
 * 
 * @author Julian Espinel
 */
public class PropertyCommand extends BasicCommand {

	/**
	 * This constant stores the command name.
	 */
	public static final String COMMAND_NAME = "PROPERTY_COMMAND";

	/**
	 * The property of a stock. For example: close price, volume, etc.
	 */
	private String propertyName;

	/**
	 * Time frame is a String object which contains one date. The date is
	 * formatted like this: dd-MM-yyyy.
	 */
	private String timeFrame;

	public PropertyCommand() {
	}
	
	/**
	 * PropertyCommand constructor.
	 * 
	 * @param propertyName a property of a stock. For example: close price, volume, etc.
	 * @param timeFrame a date formatted like this: dd-MM-yyyy.
	 */
	public PropertyCommand(String propertyName, String timeFrame) {

		this.propertyName = propertyName;
		this.timeFrame = timeFrame;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getTimeFrame() {
		return timeFrame;
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

		int numberOfFields = 2;

		if (elementsArray.size() == numberOfFields) {

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
	 * Builds a PropertyCommand with the DSL elements contained into the ArrayList.
	 * 
	 * @param elementsArray the ArrayList that contains the DSL elements.
	 * @return a PropertyCommand with the DSL elements contained into the ArrayList.
	 */
	public static ICommand buildCommand(ArrayList<DSLElementDTO> elementsArray) {

		int numberOfFields = 2;
		
		int i = 0;
			
		// With subject first
		if (elementsArray.size() == (numberOfFields + 1)) {
			
			i = 1;
		}
		
		DSLElementDTO propertyElement = elementsArray.get(i);

		i++;
		DSLElementDTO timeFrameElement = elementsArray.get(i);
		
		String propertyValue = propertyElement.getValue();
		String timeFrameValue = timeFrameElement.getValue();
		PropertyCommand propertyCommand = new PropertyCommand(propertyValue, timeFrameValue);

		return propertyCommand;
	}
}