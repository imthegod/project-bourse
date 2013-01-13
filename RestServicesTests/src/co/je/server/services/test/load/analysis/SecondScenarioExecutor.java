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
 * This class provides the methods required to execute the second test scenario.
 * 
 * Here is the description of the second test scenario provided by this class:
 * 
 * This scenario allows you to create analysis requests, composed by three rules:  
 * a basic rule, a rule that involves average and a rule that involves summation.
 * these three rules are created randomly.
 * 
 * Later you should define a the number of analysis request you want this class to create.
 * This class creates that number of analysis, and puts each analysis into one independent
 * thread from a thread pool. Finally sends all the threads to make the "create analysis
 * request" to the system's server.
 * 
 * @author Julian Espinel
 */
public class SecondScenarioExecutor {

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
	 * operations such as average, summation or composite rules.
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
	 * Returns a rule that involves the average operation. Average is an operation that
	 * requires an interval of time. The dates that specify that time interval are generated
	 * randomly, but assuring that initialDate < finalDate.
	 * 
	 * @return Returns a rule that involves the average operation.
	 */
	private RuleDTO getRuleWithAverageAndRandomDates() {

		ArrayList<DSLElementDTO> ruleElements = new ArrayList<DSLElementDTO>();

		String firstDate = getRandomDate();

		String baseDate = "30-12-1990";
		String secondDate = getRandomDateBefore(baseDate);
		String thirdDate = getRandomDateAfter(baseDate);

		ruleElements.add(new DSLElementDTO(DSLCategories.SUBJECT, "acciones"));
		ruleElements.add(new DSLElementDTO(DSLCategories.PROPERTY, "close"));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, firstDate));

		ruleElements.add(new DSLElementDTO(DSLCategories.CONDITIONAL_OPERATOR, ">="));

		ruleElements.add(new DSLElementDTO(DSLCategories.MATH_OPERATION, "multiplicacion"));
		ruleElements.add(new DSLElementDTO(DSLCategories.NUMBER, "97"));
		ruleElements.add(new DSLElementDTO(DSLCategories.UNIT, "%"));
		ruleElements.add(new DSLElementDTO(DSLCategories.AVERAGE, "average"));
		ruleElements.add(new DSLElementDTO(DSLCategories.PROPERTY, "close"));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, secondDate));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, thirdDate));

		RuleDTO ruleDTO = new RuleDTO(ruleElements);

		return ruleDTO;
	}

	/**
	 * Returns a rule that involves the summation operation. Summation is an operation that
	 * requires an interval of time. The dates that specify that time interval are generated
	 * randomly, but assuring that initialDate < finalDate.
	 * 
	 * @return Returns a rule that involves the summation operation.
	 */
	private RuleDTO getRuleWithSummationAndRandomDates() {

		ArrayList<DSLElementDTO> ruleElements = new ArrayList<DSLElementDTO>();

		String baseDate = "30-12-1990";
		String firstDate = getRandomDateBefore(baseDate);
		String secondDate = getRandomDateAfter(baseDate);

		ruleElements.add(new DSLElementDTO(DSLCategories.SUBJECT, "acciones"));
		ruleElements.add(new DSLElementDTO(DSLCategories.SUMMATION, "summation"));
		ruleElements.add(new DSLElementDTO(DSLCategories.MATH_OPERATION, "resta"));
		ruleElements.add(new DSLElementDTO(DSLCategories.PROPERTY, "close"));
		ruleElements.add(new DSLElementDTO(DSLCategories.PROPERTY, "open"));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, firstDate));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, secondDate));

		ruleElements.add(new DSLElementDTO(DSLCategories.CONDITIONAL_OPERATOR, ">="));

		ruleElements.add(new DSLElementDTO(DSLCategories.NUMBER, "300"));
		ruleElements.add(new DSLElementDTO(DSLCategories.UNIT, "%"));

		RuleDTO ruleDTO = new RuleDTO(ruleElements);

		return ruleDTO;
	}

	/**
	 * SecondScenarioExecutor constructor.
	 */
	public SecondScenarioExecutor() {
	}

	/**
	 * Returns an analysis request that is composed by three rules: a basic rule, a rule that 
	 * involves average and a rule that involves summation. these rules are generated using the
	 * following methods respectively: getBasicRuleWithRandomDates, 
	 * getRuleWithAverageAndRandomDates and getRuleWithSummationAndRandomDates.
	 * 
	 * @return an analysis request that is composed by three rules: a basic rule, a rule that 
	 * 		   involves average and a rule that involves summation.
	 */
	private AnalysisDTO getRandomAnalysisDTOForSecondScenario() {

		RuleDTO firstRule = getBasicRuleWithRandomDates();
		RuleDTO secondRule = getRuleWithAverageAndRandomDates();
		RuleDTO thirdRule = getRuleWithSummationAndRandomDates();

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
	 * Executes the second scenario.
	 * 
	 * This method creates a fixed thread pool of 30 threads. Then creates the given
	 * number of analysis request. Each created analysis is created using the 
	 * getRandomAnalysisDTOForSecondScenario method.
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

			AnalysisDTO analysisDTO = getRandomAnalysisDTOForSecondScenario();
			AnalysisTask analysisTask = new AnalysisTask(analysisDTO);
			threadPool.submit(analysisTask);

			System.out.println("Second Scenario, thread: " + (i + 1));
		}

		threadPool.shutdown();
	}
	
	/**
	 * Creates a SecondScenarioExecutor object, defines the number of the analysis we want
	 * to create for the test, and then executes the scenario by calling the
	 * executeScenario method.
	 * 
	 * @param args none.
	 */
	public static void main(String[] args) {
		
		SecondScenarioExecutor secondScenarioExecutor = new SecondScenarioExecutor();
		
		int numberOfAnalysis = 52;
		secondScenarioExecutor.executeScenario(numberOfAnalysis);
	}
}