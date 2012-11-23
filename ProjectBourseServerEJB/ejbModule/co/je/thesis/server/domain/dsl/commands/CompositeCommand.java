package co.je.thesis.server.domain.dsl.commands;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

public class CompositeCommand implements ICommand {

	public static final String COMMAND_NAME = "COMPOSITE_COMMAND";
	
	/**
	 * The String which represents the math operation to execute.
	 */
	private String mathOperation;

	/**
	 * The array which contains the children commands
	 */
	private ArrayList<ICommand> childrenCommands;
	
	public CompositeCommand() {
	}
	
	public CompositeCommand(String mathOperation) {

		this.mathOperation = mathOperation;
		this.childrenCommands = new ArrayList<ICommand>();
	}

	public String getMathOperation() {
		return mathOperation;
	}

	public void addChildCommand(ICommand childCommand) {

		if (childCommand.isBasicCommand()) {

			childrenCommands.add(childCommand);
		}
	}

	public ArrayList<ICommand> getChildrenCommands() {
		return childrenCommands;
	}

	@Override
	public boolean isBasicCommand() {
		return false;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}
	
	private static boolean elementValueIsValid(DSLElementDTO dslElementDTO) {

		boolean valueIsNotEmpty = !(dslElementDTO.getValue().isEmpty());
		boolean valueIsNotNull = (dslElementDTO.getValue() != null);
		boolean answer = valueIsNotEmpty && valueIsNotNull;

		return answer;
	}
	
	public static boolean dslElementsCorrespondToThisCommand(ArrayList<DSLElementDTO> elementsArray) {
		
		boolean elementsCorrespondToThisCommand = false;

		int i = 0;
		DSLElementDTO dslElementDTO = elementsArray.get(i);
		String category = dslElementDTO.getCategory();
		
		if (category.equalsIgnoreCase(DSLCategories.MATH_OPERATION) && elementValueIsValid(dslElementDTO)) {
			
			i++;
			dslElementDTO = elementsArray.get(i);
			category = dslElementDTO.getCategory();
			
			if (category.equalsIgnoreCase(DSLCategories.NUMBER) && elementValueIsValid(dslElementDTO)) {
				
				i++;
				dslElementDTO = elementsArray.get(i);
				category = dslElementDTO.getCategory();
				
			} if (category.equalsIgnoreCase(DSLCategories.UNIT) && elementValueIsValid(dslElementDTO)) {
				
				i++;
				dslElementDTO = elementsArray.get(i);
				category = dslElementDTO.getCategory();
				
				if (category.equalsIgnoreCase(DSLCategories.PROPERTY) && elementValueIsValid(dslElementDTO)) {
					
					i++;
					dslElementDTO = elementsArray.get(i);
					category = dslElementDTO.getCategory();
					
					if (category.equalsIgnoreCase(DSLCategories.TIME_FRAME) && elementValueIsValid(dslElementDTO)) {
						
						elementsCorrespondToThisCommand = true;
					}
					
				} else if (category.equalsIgnoreCase(DSLCategories.AVERAGE) && elementValueIsValid(dslElementDTO)) {
					
					i++;
					dslElementDTO = elementsArray.get(i);
					category = dslElementDTO.getCategory();
					
					if (category.equalsIgnoreCase(DSLCategories.PROPERTY) && elementValueIsValid(dslElementDTO)) {
						
						i++;
						dslElementDTO = elementsArray.get(i);
						category = dslElementDTO.getCategory();
						
						if (category.equalsIgnoreCase(DSLCategories.TIME_FRAME) && elementValueIsValid(dslElementDTO)) {
							
							i++;
							dslElementDTO = elementsArray.get(i);
							category = dslElementDTO.getCategory();
							
							if (category.equalsIgnoreCase(DSLCategories.TIME_FRAME) && elementValueIsValid(dslElementDTO)) {
								
								elementsCorrespondToThisCommand = true;
							}
						}
					}
				}
			}
		}
		
		return elementsCorrespondToThisCommand;
	}
	
	public static ICommand buildCommand(ArrayList<DSLElementDTO> elementsArray) {

		int i = 0;
		DSLElementDTO mainMathOperationElement = elementsArray.get(i);
		
		String mainMathOperation = mainMathOperationElement.getValue();
		CompositeCommand compositeCommand = new CompositeCommand(mainMathOperation);

		i++;
		DSLElementDTO numberElement = elementsArray.get(i);
		
		i++;
		DSLElementDTO unitElement = elementsArray.get(i);
		
		String numberString = numberElement.getValue();
		double doubleValue = Double.parseDouble(numberString);
		String unitValue = unitElement.getValue();
		NumberCommand numberCommand = new NumberCommand(doubleValue, unitValue);
		
		compositeCommand.addChildCommand(numberCommand);
		
		i++;
		DSLElementDTO uncertainElement = elementsArray.get(i);
		String uncertainElementCategory = uncertainElement.getCategory();
		
		if (uncertainElementCategory.equalsIgnoreCase(DSLCategories.PROPERTY)) {
			
			i++;
			DSLElementDTO timeFrameElement = elementsArray.get(i);
			
			String propertyName = uncertainElement.getValue();
			String timeFrame = timeFrameElement.getValue();
			
			PropertyCommand propertyCommand = new PropertyCommand(propertyName, timeFrame);
			compositeCommand.addChildCommand(propertyCommand);
			
		} else if (uncertainElementCategory.equalsIgnoreCase(DSLCategories.AVERAGE)) {
			
			i++;
			DSLElementDTO propertyElement = elementsArray.get(i);
			
			i++;
			DSLElementDTO timeFrame1Element = elementsArray.get(i);
			
			i++;
			DSLElementDTO timeFrame2Element = elementsArray.get(i);
			
			String mathOperation = uncertainElement.getValue();
			String property = propertyElement.getValue();
			String timeFrame1 = timeFrame1Element.getValue();
			String timeFrame2 = timeFrame2Element.getValue();
			
			AverageCommand averageCommand = new AverageCommand(mathOperation, property, timeFrame1, timeFrame2);
			compositeCommand.addChildCommand(averageCommand);
		}
		
		return compositeCommand;
	}
}