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

public class SecondScenarioExecutor {

	/**
	 * Returns a random between [initialNumber, lastNumber] both inclusive.
	 * 
	 * @param initialNumber
	 *            , initial number
	 * @param lastNumber
	 *            , last number
	 * 
	 * @return A random between [initialNumber, lastNumber] both inclusive.
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

	private String getRandomDate() {

		int newDay = getRandomNumberBetween(1, 30);
		int newMonth = getRandomNumberBetween(1, 12);
		int newYear = getRandomNumberBetween(1964, 2012);

		String randomDate = newDay + "-" + newMonth + "-" + newYear;

		return randomDate;
	}

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

	public SecondScenarioExecutor() {
	}

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
	
	public static void main(String[] args) {
		
		SecondScenarioExecutor secondScenarioExecutor = new SecondScenarioExecutor();
		
		int numberOfAnalysis = 52;
		secondScenarioExecutor.executeScenario(numberOfAnalysis);
	}
}