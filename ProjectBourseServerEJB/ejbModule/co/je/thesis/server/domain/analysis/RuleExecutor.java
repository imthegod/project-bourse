package co.je.thesis.server.domain.analysis;

import java.util.ArrayList;

import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.server.domain.dsl.commands.ConditionalOperatorCommand;
import co.je.thesis.server.domain.dsl.commands.ICommand;
import co.je.thesis.server.persistence.stocks.StockPersistence;

public class RuleExecutor {
	
	private ICommand commandBeforeCo;
	private ICommand conditionalOperatorCommand;
	private ICommand commandAfterCo;
	
	public RuleExecutor(ICommand commandBeforeCo, ICommand conditionalOperatorCommand,
			ICommand commandAfterCo) {
		
		this.commandBeforeCo = commandBeforeCo;
		this.conditionalOperatorCommand = conditionalOperatorCommand;
		this.commandAfterCo = commandAfterCo;
	}
	
	private boolean ruleIsTrue(double beforeCoAnswer, double afterCoAnswer) {
		
		boolean answer = false;
		
		String commandName = conditionalOperatorCommand.getCommandName();
		
		if (commandName.equalsIgnoreCase(ConditionalOperatorCommand.COMMAND_NAME)) {
			
			ConditionalOperatorCommand conditionalOpCommand = (ConditionalOperatorCommand) conditionalOperatorCommand;
			String conditionalOperator = conditionalOpCommand.getConditionalOperator();
			
			if (conditionalOperator.equals(ConditionalOperatorCommand.LESS)) {
				
				answer = (beforeCoAnswer < afterCoAnswer);
				
			} else if (conditionalOperator.equals(ConditionalOperatorCommand.LESS_EQUAL)) {
				
				answer = (beforeCoAnswer <= afterCoAnswer);
				
			} else if (conditionalOperator.equals(ConditionalOperatorCommand.GREATER)) {
				
				answer = (beforeCoAnswer > afterCoAnswer);
				
			} else if (conditionalOperator.equals(ConditionalOperatorCommand.GREATER_EQUAL)) {
				
				answer = (beforeCoAnswer >= afterCoAnswer);
				
			} else if (conditionalOperator.equals(ConditionalOperatorCommand.EQUAL_TO)) {
				
				answer = (beforeCoAnswer == afterCoAnswer);
				
			} else if (conditionalOperator.equals(ConditionalOperatorCommand.NOT_EQUAL)) {
				
				answer = (beforeCoAnswer != afterCoAnswer);
				
			} else {
				
				String exceptionMessage = "The conditional operator: " + conditionalOperator;
				throw new IllegalArgumentException(exceptionMessage);
			}
			
		} else {
			
			String exceptionMessage = "The conditional operator command object is not valid.";
			throw new IllegalArgumentException(exceptionMessage);
		}
		
		return answer;
	}
	
	public ArrayList<BaseStock> getRuleResults() {
		
		StockPersistence stockPersistence = new StockPersistence();
		
		ArrayList<BaseStock> baseStocks = stockPersistence.getBaseStocks();
		ArrayList<BaseStock> ruleAnswers = new ArrayList<BaseStock>();
		
		CommandExecutor commandExecutor = new CommandExecutor();
		
		double beforeCoAnswer = 0;
		double afterCoAnswer = 0;
		
		for (int i = 0; i < baseStocks.size(); i++) {
			
			BaseStock baseStock = baseStocks.get(i);
			String stockSymbol = baseStock.getSymbol();
			
			beforeCoAnswer = commandExecutor.getCommandAnswer(stockSymbol, commandBeforeCo);
			afterCoAnswer = commandExecutor.getCommandAnswer(stockSymbol, commandAfterCo);
			
			if (ruleIsTrue(beforeCoAnswer, afterCoAnswer)) {
				
				ruleAnswers.add(baseStock);
			}
		}
		
		return ruleAnswers;
	}
}