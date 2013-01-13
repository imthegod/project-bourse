package co.je.thesis.server.domain.analysis;

import java.util.ArrayList;

import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.server.domain.dsl.commands.ConditionalOperatorCommand;
import co.je.thesis.server.domain.dsl.commands.ICommand;
import co.je.thesis.server.persistence.stocks.StockPersistence;

/**
 * This class knows how to execute any rule that belongs to an analysis request.
 * 
 * @author Julian Espinel
 */
public class RuleExecutor {
	
	/**
	 * The command that is composed by the elements that are before the conditional 
	 * operator of the rule.
	 */
	private ICommand commandBeforeCo;
	
	/**
	 * The command that contains the conditional operator of the rule.
	 */
	private ICommand conditionalOperatorCommand;
	
	/**
	 * The command that is composed by the elements that are after the conditional 
	 * operator of the rule.
	 */
	private ICommand commandAfterCo;
	
	/**
	 * RuleExecutor constructor.
	 * 
	 * @param commandBeforeCo the command that is composed by the elements that are before 
	 * 						  the conditional operator of the rule.
	 * @param conditionalOperatorCommand the command that contains the conditional operator 
	 * 									 of the rule.
	 * @param commandAfterCo the command that is composed by the elements that are after 
	 * 						 the conditional operator of the rule.
	 */
	public RuleExecutor(ICommand commandBeforeCo, ICommand conditionalOperatorCommand,
			ICommand commandAfterCo) {
		
		this.commandBeforeCo = commandBeforeCo;
		this.conditionalOperatorCommand = conditionalOperatorCommand;
		this.commandAfterCo = commandAfterCo;
	}
	
	/**
	 * Evaluates the answers of the commandBeforeCo and commandAfterCo  according to the conditional
	 * operator of the rule. return (commandBeforeCo Co commandAfterCo).
	 * 
	 * @param beforeCoAnswer the answer of the commandBeforeCo
	 * @param afterCoAnswer the answer of the commandAfterCo
	 * @return if (commandBeforeCo Co commandAfterCo) then returns true, else returns false.
	 */
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
	
	/**
	 * Returns an ArrayList with the stocks that fulfill the rule.
	 * 
	 * @return an ArrayList with the stocks that fulfill the rule.
	 */
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