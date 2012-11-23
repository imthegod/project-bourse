package co.je.thesis.server.domain.dsl;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.server.domain.dsl.commands.CompositeCommand;
import co.je.thesis.server.domain.dsl.commands.ICommand;
import co.je.thesis.server.domain.dsl.commands.AverageCommand;
import co.je.thesis.server.domain.dsl.commands.MathOpOver2PropertiesCommand;
import co.je.thesis.server.domain.dsl.commands.NumberCommand;
import co.je.thesis.server.domain.dsl.commands.PropertyCommand;
import co.je.thesis.server.domain.dsl.commands.SummationCommand;

public class CommandBuilder {

	public static final String CATEGORY_EXCEPTION_MESSAGE = "At least one argument corresponds to a category different than the expected one";

	public ICommand createNumberCommand(DSLElementDTO number, DSLElementDTO unit) {

		boolean isNumber = number.getCategory().equalsIgnoreCase(DSLCategories.NUMBER);
		boolean isUnit = unit.getCategory().equalsIgnoreCase(DSLCategories.UNIT);

		if (isNumber && isUnit) {

			double doubleNumber = Double.parseDouble(number.getValue());
			String unitString = unit.getValue();
			NumberCommand numberCommand = new NumberCommand(doubleNumber, unitString);

			return numberCommand;

		} else {

			throw new IllegalArgumentException(CATEGORY_EXCEPTION_MESSAGE);
		}
	}

	public ICommand createPropertyCommand(DSLElementDTO property, DSLElementDTO timeFrame) {

		boolean isProperty = property.getCategory().equalsIgnoreCase(DSLCategories.PROPERTY);
		boolean isTimeFrame = timeFrame.getCategory().equalsIgnoreCase(DSLCategories.TIME_FRAME);

		if (isProperty && isTimeFrame) {

			String propertyName = property.getValue();
			String timeFrameString = timeFrame.getValue();
			PropertyCommand propertyCommand = new PropertyCommand(propertyName, timeFrameString);

			return propertyCommand;

		} else {

			throw new IllegalArgumentException(CATEGORY_EXCEPTION_MESSAGE);
		}
	}

	public ICommand createAvergeCommand(DSLElementDTO mathOperation, DSLElementDTO property,
			DSLElementDTO timeFrame1, DSLElementDTO timeFrame2) {

		boolean isAverage = mathOperation.getCategory().equalsIgnoreCase(DSLCategories.AVERAGE);
		boolean isProperty = property.getCategory().equalsIgnoreCase(DSLCategories.PROPERTY);
		boolean isTimeFrame1 = timeFrame1.getCategory().equalsIgnoreCase(DSLCategories.TIME_FRAME);
		boolean isTimeFrame2 = timeFrame2.getCategory().equalsIgnoreCase(DSLCategories.TIME_FRAME);

		if (isAverage && isProperty && isTimeFrame1 && isTimeFrame2) {

			String mathOperationString = mathOperation.getValue();
			String propertyString = property.getValue();
			String timeFrameString1 = timeFrame1.getValue();
			String timeFrameString2 = timeFrame2.getValue();

			AverageCommand command = new AverageCommand(mathOperationString, propertyString,
					timeFrameString1, timeFrameString2);

			return command;

		} else {

			throw new IllegalArgumentException(CATEGORY_EXCEPTION_MESSAGE);
		}
	}

	public ICommand createSummationCommand(DSLElementDTO mainMathOperation,
			DSLElementDTO nestedMathOperation, DSLElementDTO property1, DSLElementDTO property2,
			DSLElementDTO timeFrame1, DSLElementDTO timeFrame2) {

		boolean isSummation = mainMathOperation.getCategory().equalsIgnoreCase(
				DSLCategories.SUMMATION);
		boolean isNestedMathOperation = nestedMathOperation.getCategory().equalsIgnoreCase(
				DSLCategories.MATH_OPERATION);
		boolean isProperty1 = property1.getCategory().equalsIgnoreCase(DSLCategories.PROPERTY);
		boolean isProperty2 = property2.getCategory().equalsIgnoreCase(DSLCategories.PROPERTY);
		boolean isTimeFrame1 = timeFrame1.getCategory().equalsIgnoreCase(DSLCategories.TIME_FRAME);
		boolean isTimeFrame2 = timeFrame2.getCategory().equalsIgnoreCase(DSLCategories.TIME_FRAME);

		if (isSummation && isNestedMathOperation && isProperty1 && isProperty2 && isTimeFrame1
				&& isTimeFrame2) {

			String mainMathOperationString = mainMathOperation.getValue();
			String nestedMathOperationString = nestedMathOperation.getValue();
			String propertyString1 = property1.getValue();
			String propertyString2 = property2.getValue();
			String timeFrameString1 = timeFrame1.getValue();
			String timeFrameString2 = timeFrame2.getValue();

			SummationCommand command = new SummationCommand(mainMathOperationString, nestedMathOperationString,
					propertyString1, propertyString2, timeFrameString1, timeFrameString2);

			return command;

		} else {

			throw new IllegalArgumentException(CATEGORY_EXCEPTION_MESSAGE);
		}
	}

	public ICommand createMathOpOver2PropertiesCommand(DSLElementDTO mathOperation,
			DSLElementDTO property1, DSLElementDTO property2, DSLElementDTO timeFrame) {

		boolean isMathOperation = mathOperation.getCategory().equalsIgnoreCase(
				DSLCategories.MATH_OPERATION);
		boolean isProperty1 = property1.getCategory().equalsIgnoreCase(DSLCategories.PROPERTY);
		boolean isProperty2 = property2.getCategory().equalsIgnoreCase(DSLCategories.PROPERTY);
		boolean isTimeFrame = timeFrame.getCategory().equalsIgnoreCase(DSLCategories.TIME_FRAME);

		if (isMathOperation && isProperty1 && isProperty2 && isTimeFrame) {

			String mathOperationString = mathOperation.getValue();
			String property1String = property1.getValue();
			String property2String = property2.getValue();
			String timeFrameString = timeFrame.getValue();
			MathOpOver2PropertiesCommand command = new MathOpOver2PropertiesCommand(
					mathOperationString, property1String, property2String, timeFrameString);

			return command;

		} else {

			throw new IllegalArgumentException(CATEGORY_EXCEPTION_MESSAGE);
		}
	}

	public ICommand createCompositeCommand(DSLElementDTO mathOperation,
			ArrayList<ICommand> childrenCommands) {

		boolean isMathOperation = mathOperation.getCategory().equalsIgnoreCase(
				DSLCategories.MATH_OPERATION);

		if (isMathOperation) {

			String mathOperationString = mathOperation.getValue();
			CompositeCommand compositeCommand = new CompositeCommand(mathOperationString);

			// Adds the basic commands to the composite command
			for (int i = 0; i < childrenCommands.size(); i++) {

				ICommand childCommand = childrenCommands.get(i);
				compositeCommand.addChildCommand(childCommand);
			}

			return compositeCommand;

		} else {

			throw new IllegalArgumentException(CATEGORY_EXCEPTION_MESSAGE);
		}
	}
}