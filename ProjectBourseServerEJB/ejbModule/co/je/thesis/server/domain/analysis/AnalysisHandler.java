package co.je.thesis.server.domain.analysis;

import java.util.ArrayList;

import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.server.domain.dsl.DSLManager;
import co.je.thesis.server.domain.dsl.commands.ICommand;

/**
 * This class is responsible for receive and process an analysis request.
 * 
 * @author Julian Espinel
 */
public class AnalysisHandler {

	/**
	 * AnalysisHandler constructor.
	 */
	public AnalysisHandler() {
	}

	/**
	 * Returns the number of occurrences of a specific BaseStock object within an ArrayList. 
	 * 
	 * @param baseStocks the ArrayList of BaseStock objects.
	 * @param element the specific BaseStock object we want to find.
	 * @return the number of occurrences of a specific BaseStock object within an ArrayList.
	 */
	private int getOccurrencesOfAnElemtIntoTheArray(ArrayList<BaseStock> baseStocks, BaseStock element) {
		
		int elemntOccurrences = 0;

		for (int i = 0; i < baseStocks.size(); i++) {

			BaseStock baseStock = baseStocks.get(i);
			int compareToAnswer = baseStock.compareTo(element);

			if (compareToAnswer == 0) {

				elemntOccurrences++;
			}
		}

		return elemntOccurrences;
	}

	/**
	 * Returns an ArrayList with the stocks that fulfill all the rules of the analysis.
	 * 
	 * @param accumulatedResults the results of all the rules of the analysis.
	 * @param numberOfRules the number of rules that compound the analysis.
	 * @return an ArrayList with the stocks that fulfill all the rules of the analysis.
	 */
	private ArrayList<BaseStock> getIntersectionArray(ArrayList<BaseStock> accumulatedResults, int numberOfRules) {

		ArrayList<BaseStock> intersectionArray = new ArrayList<BaseStock>();

		for (int i = 0; i < accumulatedResults.size(); i++) {

			BaseStock baseStock = accumulatedResults.get(i);
			int elemntOccurrences = getOccurrencesOfAnElemtIntoTheArray(accumulatedResults, baseStock);
			
			// If the element is replicated X times into the array. 
			// And considering X as the number of rules in the analysis,
			// it means the element is a result for every rule. (intersection).
			if (elemntOccurrences == numberOfRules) {

				intersectionArray.add(baseStock);
				
				// Removes the occurrences of the same object
				for (int j = 0; j < numberOfRules; j++) {
					
					accumulatedResults.remove(baseStock);
				}
			}
		}

		return intersectionArray;
	}

	/**
	 * Executes the analysis request and returns an ArrayList with the stocks that fulfill
	 * all rules of the analysis.
	 * 
	 * @param analysisDTO the analysis we want to be executed.
	 * @return an ArrayList with the stocks that fulfill all rules of the analysis.
	 */
	public ArrayList<BaseStock> executeAnalysis(AnalysisDTO analysisDTO) {

		DSLManager dslManager = new DSLManager();
		dslManager.verifyAnalysis(analysisDTO);

		// Accumulates the results of every rule of the analysis
		ArrayList<BaseStock> accumulatedResults = new ArrayList<BaseStock>();

		ArrayList<RuleDTO> analysisRules = analysisDTO.getRulesDTO();
		int numberOfRules = analysisRules.size();

		for (int i = 0; i < numberOfRules; i++) {

			RuleDTO ruleDTO = analysisRules.get(i);
			ICommand commandBeforeCo = dslManager.getCommandBeforeConditionalOperator(ruleDTO);
			ICommand conditionalOperatorCommand = dslManager.getConditionalOperator(ruleDTO);
			ICommand commandAfterCo = dslManager.getCommandAfterConditionalOperator(ruleDTO);

			RuleExecutor ruleExecutor = new RuleExecutor(commandBeforeCo, conditionalOperatorCommand, commandAfterCo);
			ArrayList<BaseStock> parcialResultStocks = ruleExecutor.getRuleResults();
			accumulatedResults.addAll(parcialResultStocks);
		}
		
		ArrayList<BaseStock> intersectionArray = accumulatedResults;
		
		if (numberOfRules > 1) {
			
			intersectionArray = getIntersectionArray(accumulatedResults, numberOfRules);
		}

		return intersectionArray;
	}
}