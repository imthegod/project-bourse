package co.je.server.services.test.load.analysis;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.je.server.services.test.load.analysis.concurrency.AnalysisTask;
import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;

/**
 * This class provides the methods required to execute the first test scenario.
 * 
 * Here is the description of the first test scenario provided by this class:
 * 
 * This scenario allows you to create analysis requests, composed by three basic rules. 
 * A basic rule is a rule that doesn't involve operations such as average or summation.
 * these three basic rules are created randomly.
 * 
 * Later you should define a the number of analysis request you want this class to create.
 * This class creates that number of analysis, and puts each analysis into one independent
 * thread from a thread pool. Finally sends all the threads to make the "create analysis
 * request" to the system's server.
 * 
 * @author Julian Espinel
 */
public class FirstScenarioExecutor {

	/**
	 * Returns a random number between [initialNumber, lastNumber] both inclusive.
	 * 
	 * @param initialNumber the initial number.
	 * @param lastNumber the last number.
	 * 
	 * @return A random number between [initialNumber, lastNumber] both inclusive.
	 */
	private int getRandomNumberBetween(int initialNumber, int lastNumber) {

		int diference = (lastNumber - initialNumber);

		int answer = lastNumber;

		if (diference != 0) {

			Random random = new Random();
			int limit = (lastNumber - initialNumber) + 1;

			// random number between [0, limit)
			int randomNumber = random.nextInt(limit);
			answer = randomNumber + initialNumber;
		}

		return answer;
	}

	/**
	 * Returns a random date before a given date. The returned and the given date, both
	 * must follow this format: dd-MM-yyyy.
	 * 
	 * @param actualDate the given date. This date acts as the superior limit.
	 * @return a random date before the given date, returned in the following format: dd-MM-yyyy.
	 */
	private String getRandomDateBefore(String actualDate) {

		String[] splitArray = actualDate.split("-");
		int day = Integer.parseInt(splitArray[0]);
		int month = Integer.parseInt(splitArray[1]);
		int year = Integer.parseInt(splitArray[2]);

		int newDay = getRandomNumberBetween(1, day);
		int newMonth = getRandomNumberBetween(1, month);
		int newYear = getRandomNumberBetween(1964, year);

		String randomDate = newDay + "-" + newMonth + "-" + newYear;

		return randomDate;
	}

	/**
	 * Returns a random date in the following format: dd-MM-yyyy.
	 * 
	 * @return a random date in the following format: dd-MM-yyyy.
	 */
	private String getRandomDate() {

		int newDay = getRandomNumberBetween(1, 30);
		int newMonth = getRandomNumberBetween(1, 12);
		int newYear = getRandomNumberBetween(1964, 2012);

		String randomDate = newDay + "-" + newMonth + "-" + newYear;

		return randomDate;
	}

	/**
	 * Returns a random date after a given date. The returned and the given date, both
	 * must follow this format: dd-MM-yyyy.
	 * 
	 * @param actualDate the given date. This date acts as the inferior limit.
	 * @return a random date after the given date, returned in the following format: dd-MM-yyyy.
	 */
	private String getRandomDateAfter(String actualDate) {

		String[] splitArray = actualDate.split("-");
		int day = Integer.parseInt(splitArray[0]);
		int month = Integer.parseInt(splitArray[1]);
		int year = Integer.parseInt(splitArray[2]);

		int newDay = getRandomNumberBetween(day, 30);
		int newMonth = getRandomNumberBetween(month, 12);
		int newYear = getRandomNumberBetween(year, 2012);

		String randomDate = newDay + "-" + newMonth + "-" + newYear;

		return randomDate;
	}

	/**
	 * Returns a basic rule with random dates. A basic rule is a rule that doesn't involve
	 * operations such as average or summation.
	 * 
	 * @return a basic rule with random dates.
	 */
	private RuleDTO getBasicRuleWithRandomDates() {

		ArrayList<DSLElementDTO> ruleElements = new ArrayList<DSLElementDTO>();

		String firstDate = getRandomDate();
		String secondDate = getRandomDate();

		ruleElements.add(new DSLElementDTO(DSLCategories.SUBJECT, "acciones"));
		ruleElements.add(new DSLElementDTO(DSLCategories.PROPERTY, "close"));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, firstDate));

		ruleElements.add(new DSLElementDTO(DSLCategories.CONDITIONAL_OPERATOR, "<="));

		ruleElements.add(new DSLElementDTO(DSLCategories.MATH_OPERATION, "multiplicacion"));
		ruleElements.add(new DSLElementDTO(DSLCategories.NUMBER, "93"));
		ruleElements.add(new DSLElementDTO(DSLCategories.UNIT, "%"));
		ruleElements.add(new DSLElementDTO(DSLCategories.PROPERTY, "close"));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, secondDate));

		RuleDTO ruleDTO = new RuleDTO(ruleElements);

		return ruleDTO;
	}
	
	/**
	 * FirstScenarioExecutor constructor.
	 */
	public FirstScenarioExecutor() {
	}
	
	/**
	 * Returns an analysis request with three random basic rules.
	 * 
	 * @return an analysis request with three random basic rules.
	 */
	private AnalysisDTO getRandomAnalysisDTOForFirstScenario() {
		
		RuleDTO firstRule = getBasicRuleWithRandomDates();
		RuleDTO secondRule = getBasicRuleWithRandomDates();
		RuleDTO thirdRule = getBasicRuleWithRandomDates();
		
		ArrayList<RuleDTO> rulesDTO = new ArrayList<RuleDTO>();
		rulesDTO.add(firstRule);
		rulesDTO.add(secondRule);
		rulesDTO.add(thirdRule);
		
		ArrayList<DSLElementDTO> logicalOperatorsDTO = new ArrayList<DSLElementDTO>();
		DSLElementDTO logicalOperator = new DSLElementDTO(DSLCategories.LOGICAL_OPERATOR, "AND");
		
		logicalOperatorsDTO.add(logicalOperator);
		logicalOperatorsDTO.add(logicalOperator);
		
		String ownerUserName = "owner";
		
		AnalysisDTO analysisDTO = new AnalysisDTO(ownerUserName, rulesDTO, logicalOperatorsDTO);
		
		return analysisDTO;
	}
	
	/**
	 * Executes the first scenario.
	 * 
	 * This method creates a fixed thread pool of 30 threads. Then creates the given
	 * number of analysis request. Each created analysis is created using the 
	 * getRandomAnalysisDTOForFirstScenario method.
	 * 
	 * The method creates one AnalysisTask object per analysis request. Finally sends all
	 * the analysis request to the system's server concurrently.
	 * 
	 * @param numberOfAnalysis the number of analysis request we want to create.
	 */
	public void executeScenario(int numberOfAnalysis) {
		
		int numberOfThreads = 30;
		ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);
		
		for (int i = 0; i < numberOfAnalysis; i++) {
			
			AnalysisDTO analysisDTO = getRandomAnalysisDTOForFirstScenario();
			AnalysisTask analysisTask = new AnalysisTask(analysisDTO);
			threadPool.submit(analysisTask);
			
			System.out.println("First Scenario, thread: " + (i + 1));
		}
		
		threadPool.shutdown();
	}
	
	/**
	 * Creates a FirstScenarioExecutor object, defines the number of the analysis we want
	 * to create for the test, and then executes the scenario by calling the
	 * executeScenario method.
	 * 
	 * @param args none.
	 */
	public static void main(String[] args) {
		
		FirstScenarioExecutor firstScenarioExecutor = new FirstScenarioExecutor();
		
		int numberOfAnalysis = 1;
		firstScenarioExecutor.executeScenario(numberOfAnalysis);
	}
}