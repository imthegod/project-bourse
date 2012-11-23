package co.je.thesis.server.domain.analysis;

import java.util.ArrayList;

import co.je.thesis.server.domain.dsl.commands.CompositeCommand;
import co.je.thesis.server.domain.dsl.commands.ConditionalOperatorCommand;
import co.je.thesis.server.domain.dsl.commands.ICommand;
import co.je.thesis.server.domain.dsl.commands.AverageCommand;
import co.je.thesis.server.domain.dsl.commands.MathOpOver2PropertiesCommand;
import co.je.thesis.server.domain.dsl.commands.NumberCommand;
import co.je.thesis.server.domain.dsl.commands.PropertyCommand;
import co.je.thesis.server.domain.dsl.commands.SummationCommand;
import co.je.thesis.server.persistence.rules.RuleQueryExecutor;

public class CommandExecutor {

	public double getCommandAnswer(String stockSymbol, ICommand command) {

		String commandName = command.getCommandName();
		double answer = -1;

		if (commandName.equals(NumberCommand.COMMAND_NAME)) {

			answer = executeNumberCommand(command);

		} else if (commandName.equals(PropertyCommand.COMMAND_NAME)) {

			answer = executePropertyCommand(stockSymbol, command);

		} else if (commandName.equals(AverageCommand.COMMAND_NAME)) {

			answer = executeAverageCommand(stockSymbol, command);

		} else if (commandName.equals(MathOpOver2PropertiesCommand.COMMAND_NAME)) {

			answer = executeMathOpOver2PropertiesCommand(stockSymbol, command);

		} else if (commandName.equals(SummationCommand.COMMAND_NAME)) {

			answer = executeSummationCommand(stockSymbol, command);

		} else if (commandName.equals(CompositeCommand.COMMAND_NAME)) {

			answer = executeCompositeCommand(stockSymbol, command);

		} else if (commandName.equals(ConditionalOperatorCommand.COMMAND_NAME)) {

			String exceptionMessage = "A conditional operator command shouldn't be here.";
			throw new IllegalArgumentException(exceptionMessage);
		}

		return answer;
	}

	private double executeNumberCommand(ICommand command) {

		NumberCommand numberCommand = (NumberCommand) command;
		double answer = numberCommand.getNumber();

		return answer;
	}

	private double executePropertyCommand(String stockSymbol, ICommand command) {

		double answer = -1;

		RuleQueryExecutor ruleQueryExecutor = new RuleQueryExecutor();
		PropertyCommand propertyCommand = (PropertyCommand) command;
		String property = propertyCommand.getPropertyName();
		String timeFrame = propertyCommand.getTimeFrame();

		answer = ruleQueryExecutor.getPropertyOnSpecificTimeFrame(stockSymbol, property, timeFrame);

		return answer;
	}

	private double executeAverageCommand(String stockSymbol, ICommand command) {

		double answer = -1;

		RuleQueryExecutor ruleQueryExecutor = new RuleQueryExecutor();
		AverageCommand averageCommand = (AverageCommand) command;

		String property = averageCommand.getProperty();
		String timeFrame1 = averageCommand.getTimeFrame1();
		String timeFrame2 = averageCommand.getTimeFrame2();

		answer = ruleQueryExecutor.getAverage(stockSymbol, property, timeFrame1, timeFrame2);

		return answer;
	}

	private double executeMathOpOver2PropertiesCommand(String stockSymbol, ICommand command) {

		double answer = -1;

		RuleQueryExecutor ruleQueryExecutor = new RuleQueryExecutor();
		MathOpOver2PropertiesCommand mathOpOver2PropertiesCommand = (MathOpOver2PropertiesCommand) command;

		String mathOperation = mathOpOver2PropertiesCommand.getMathOperation();
		String propertyA = mathOpOver2PropertiesCommand.getFirstProperty();
		String propertyB = mathOpOver2PropertiesCommand.getSecondProperty();
		String timeFrame = mathOpOver2PropertiesCommand.getTimeFrame();

		answer = ruleQueryExecutor.getMathOpOver2Properties(stockSymbol, mathOperation, propertyA,
				propertyB, timeFrame);

		return answer;
	}

	private double executeSummationCommand(String stockSymbol, ICommand command) {

		double answer = -1;

		RuleQueryExecutor ruleQueryExecutor = new RuleQueryExecutor();
		SummationCommand summationCommand = (SummationCommand) command;

		String nestedMathOperation = summationCommand.getNestedMathOperation();
		String property1 = summationCommand.getProperty1();
		String property2 = summationCommand.getProperty2();
		String timeFrame1 = summationCommand.getTimeFrame1();
		String timeFrame2 = summationCommand.getTimeFrame2();

		answer = ruleQueryExecutor.getSummation(stockSymbol, nestedMathOperation, property1,
				property2, timeFrame1, timeFrame2);

		return answer;
	}

	private double executeCompositeCommand(String stockSymbol, ICommand command) {

		double answer = -1;

		RuleQueryExecutor ruleQueryExecutor = new RuleQueryExecutor();
		CompositeCommand compositeCommand = (CompositeCommand) command;

		String mathOperation = compositeCommand.getMathOperation();
		ArrayList<ICommand> childrenCommands = compositeCommand.getChildrenCommands();

		answer = ruleQueryExecutor.getComposite(stockSymbol, mathOperation, childrenCommands);

		return answer;
	}
}