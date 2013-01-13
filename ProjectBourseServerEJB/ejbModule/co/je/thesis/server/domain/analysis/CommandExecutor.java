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
import co.je.thesis.server.persistence.analysis.CommandQueryExecutor;

/**
 * This class knows how to execute any system's command related with the execution of an
 * analysis request.
 * 
 * @author Julian Espinel
 */
public class CommandExecutor {

	/**
	 * Returns the answer of any system's command related with the execution of an
	 * analysis request.
	 * 
	 * @param stockSymbol the symbol of the stock for which we want to to evaluate the command.
	 * @param command the command to execute.
	 * @return the answer of the execution of the command, evaluated for the given stock.
	 */
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

	/**
	 * Executes a NumberCommand object.
	 * 
	 * @param command the NumberCommand object.
	 * @return the number contained by the NumberCommand object.
	 */
	private double executeNumberCommand(ICommand command) {

		NumberCommand numberCommand = (NumberCommand) command;
		double answer = numberCommand.getNumber();

		return answer;
	}

	/**
	 * Executes a PropertyCommand.
	 * 
	 * @param stockSymbol the symbol of the stock for which we want to to evaluate the command.
	 * @param command the PropertyCommand object.
	 * @return the answer of the execution of the command, evaluated for the given stock.
	 */
	private double executePropertyCommand(String stockSymbol, ICommand command) {

		double answer = -1;

		CommandQueryExecutor ruleQueryExecutor = new CommandQueryExecutor();
		PropertyCommand propertyCommand = (PropertyCommand) command;
		String property = propertyCommand.getPropertyName();
		String timeFrame = propertyCommand.getTimeFrame();

		answer = ruleQueryExecutor.getPropertyOnSpecificTimeFrame(stockSymbol, property, timeFrame);

		return answer;
	}

	/**
	 * Executes an AverageCommand.
	 * 
	 * @param stockSymbol the symbol of the stock for which we want to to evaluate the command.
	 * @param command the command to execute.
	 * @return the answer of the execution of the command, evaluated for the given stock.
	 */
	private double executeAverageCommand(String stockSymbol, ICommand command) {

		double answer = -1;

		CommandQueryExecutor ruleQueryExecutor = new CommandQueryExecutor();
		AverageCommand averageCommand = (AverageCommand) command;

		String property = averageCommand.getProperty();
		String timeFrame1 = averageCommand.getTimeFrame1();
		String timeFrame2 = averageCommand.getTimeFrame2();

		answer = ruleQueryExecutor.getAverage(stockSymbol, property, timeFrame1, timeFrame2);

		return answer;
	}

	/**
	 * Executes a MathOpOver2PropertiesCommand.
	 * 
	 * @param stockSymbol the symbol of the stock for which we want to to evaluate the command.
	 * @param command the command to execute.
	 * @return the answer of the execution of the command, evaluated for the given stock.
	 */
	private double executeMathOpOver2PropertiesCommand(String stockSymbol, ICommand command) {

		double answer = -1;

		CommandQueryExecutor ruleQueryExecutor = new CommandQueryExecutor();
		MathOpOver2PropertiesCommand mathOpOver2PropertiesCommand = (MathOpOver2PropertiesCommand) command;

		String mathOperation = mathOpOver2PropertiesCommand.getMathOperation();
		String propertyA = mathOpOver2PropertiesCommand.getFirstProperty();
		String propertyB = mathOpOver2PropertiesCommand.getSecondProperty();
		String timeFrame = mathOpOver2PropertiesCommand.getTimeFrame();

		answer = ruleQueryExecutor.getMathOpOver2Properties(stockSymbol, mathOperation, propertyA,
				propertyB, timeFrame);

		return answer;
	}

	/**
	 * Executes a SummationCommand.
	 * 
	 * @param stockSymbol the symbol of the stock for which we want to to evaluate the command.
	 * @param command the command to execute.
	 * @return the answer of the execution of the command, evaluated for the given stock.
	 */
	private double executeSummationCommand(String stockSymbol, ICommand command) {

		double answer = -1;

		CommandQueryExecutor ruleQueryExecutor = new CommandQueryExecutor();
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

	/**
	 * Executes a CompositeCommand.
	 * 
	 * @param stockSymbol the symbol of the stock for which we want to to evaluate the command.
	 * @param command the command to execute.
	 * @return the answer of the execution of the command, evaluated for the given stock.
	 */
	private double executeCompositeCommand(String stockSymbol, ICommand command) {

		double answer = -1;

		CommandQueryExecutor ruleQueryExecutor = new CommandQueryExecutor();
		CompositeCommand compositeCommand = (CompositeCommand) command;

		String mathOperation = compositeCommand.getMathOperation();
		ArrayList<ICommand> childrenCommands = compositeCommand.getChildrenCommands();

		answer = ruleQueryExecutor.getComposite(stockSymbol, mathOperation, childrenCommands);

		return answer;
	}
}