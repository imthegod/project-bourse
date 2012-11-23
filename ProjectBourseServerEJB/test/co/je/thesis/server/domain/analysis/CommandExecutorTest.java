package co.je.thesis.server.domain.analysis;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;
import co.je.thesis.server.domain.dsl.commands.CompositeCommand;
import co.je.thesis.server.domain.dsl.commands.ICommand;
import co.je.thesis.server.domain.dsl.commands.AverageCommand;
import co.je.thesis.server.domain.dsl.commands.MathOpOver2PropertiesCommand;
import co.je.thesis.server.domain.dsl.commands.NumberCommand;
import co.je.thesis.server.domain.dsl.commands.PropertyCommand;
import co.je.thesis.server.domain.dsl.commands.SummationCommand;

import com.google.gson.Gson;

public class CommandExecutorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private void testGetPropertyCommand() {

		String stockSymbol = "Z";

		String propertyName = "close";
		String timeFrame = "07-11-2012";
		ICommand propertyCommand = new PropertyCommand(propertyName, timeFrame);

		CommandExecutor commandExecutor = new CommandExecutor();
		double answer = commandExecutor.getCommandAnswer(stockSymbol, propertyCommand);
		double expectedAnswer = 27.28;

		double delta = 0.01;
		assertEquals(answer, expectedAnswer, delta);
	}

	private void testGetAverageCommand() {

		String stockSymbol = "AAPL";

		String mathOperation = "average";
		String propertyName = "close";
		String timeFrame1 = "10-10-2012";
		String timeFrame2 = "07-11-2012";

		ICommand mathOpOver1PropertyCommand = new AverageCommand(mathOperation, propertyName,
				timeFrame1, timeFrame2);

		CommandExecutor commandExecutor = new CommandExecutor();
		double answer = commandExecutor.getCommandAnswer(stockSymbol, mathOpOver1PropertyCommand);
		double expectedAnswer = 612.75;

		double delta = 0.01;
		assertEquals(answer, expectedAnswer, delta);
	}

	private void testGetMathOpOver2PropertiesCommand() {

		String stockSymbol = "AAPL";

		String mathOperation = "resta";
		String propertyA = "close";
		String propertyB = "open";
		String timeFrame = "07-11-2012";

		ICommand mathOpOver2Properties = new MathOpOver2PropertiesCommand(mathOperation, propertyA,
				propertyB, timeFrame);

		CommandExecutor commandExecutor = new CommandExecutor();
		double answer = commandExecutor.getCommandAnswer(stockSymbol, mathOpOver2Properties);
		double expectedAnswer = -15.84;

		double delta = 0.01;
		assertEquals(answer, expectedAnswer, delta);
	}

	private void testGetComposite1() {

		// Mo + a + b

		String stockSymbol = "AAPL";

		double number = 0.3;
		String unit = "";
		ICommand numberCommand = new NumberCommand(number, unit);

		String propertyName = "close";
		String timeFrame = "07-11-2012";
		ICommand propertyCommand = new PropertyCommand(propertyName, timeFrame);

		String mathOperation = "multiplicacion";
		CompositeCommand compositeCommand = new CompositeCommand(mathOperation);
		compositeCommand.addChildCommand(numberCommand);
		compositeCommand.addChildCommand(propertyCommand);

		CommandExecutor commandExecutor = new CommandExecutor();
		double answer = commandExecutor.getCommandAnswer(stockSymbol, compositeCommand);
		double expectedAnswer = 167.4;

		double delta = 0.01;
		assertEquals(answer, expectedAnswer, delta);

	}

	private void testGetComposite2() {

		// Mo + a + c

		String stockSymbol = "AAPL";

		double number = 0.3;
		String unit = "";
		ICommand numberCommand = new NumberCommand(number, unit);

		String nestedMathOperation = "average";
		String nestedPropertyName = "close";
		String nestedTimeFrame1 = "10-10-2012";
		String nestedTimeFrame2 = "07-11-2012";

		ICommand mathOpOver1PropertyCommand = new AverageCommand(nestedMathOperation,
				nestedPropertyName, nestedTimeFrame1, nestedTimeFrame2);

		String mathOperation = "multiplicacion";

		CompositeCommand compositeCommand = new CompositeCommand(mathOperation);
		compositeCommand.addChildCommand(numberCommand);
		compositeCommand.addChildCommand(mathOpOver1PropertyCommand);

		CommandExecutor commandExecutor = new CommandExecutor();
		double answer = commandExecutor.getCommandAnswer(stockSymbol, compositeCommand);
		double expectedAnswer = 183.825;

		double delta = 0.01;
		assertEquals(answer, expectedAnswer, delta);
	}

	private void testGetSummation() {

		// summation

		String stockSymbol = "AAPL";

		String mathOperation = "summation";
		String nestedMathOperation = "resta";
		String propertyA = "close";
		String propertyB = "open";
		String timeFrame1 = "10-10-2012";
		String timeFrame2 = "07-11-2012";

		SummationCommand summationCommand = new SummationCommand(mathOperation,
				nestedMathOperation, propertyA, propertyB, timeFrame1, timeFrame2);

		CommandExecutor commandExecutor = new CommandExecutor();
		double answer = commandExecutor.getCommandAnswer(stockSymbol, summationCommand);
		double expectedAnswer = -91.65;

		double delta = 0.01;
		assertEquals(answer, expectedAnswer, delta);
	}

	private void printJsonAnalysisDTO() {

		ArrayList<RuleDTO> rulesDTO = new ArrayList<RuleDTO>();

		ArrayList<DSLElementDTO> ruleElements = new ArrayList<DSLElementDTO>();
		ruleElements.add(new DSLElementDTO(DSLCategories.SUBJECT, "acciones"));
		ruleElements.add(new DSLElementDTO(DSLCategories.PROPERTY, "close"));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, "07-11-2012"));

		ruleElements.add(new DSLElementDTO(DSLCategories.CONDITIONAL_OPERATOR, "<"));

		ruleElements.add(new DSLElementDTO(DSLCategories.MATH_OPERATION, "multiplicacion"));
		ruleElements.add(new DSLElementDTO(DSLCategories.NUMBER, "0.3"));
		ruleElements.add(new DSLElementDTO(DSLCategories.UNIT, "%"));
		ruleElements.add(new DSLElementDTO(DSLCategories.AVERAGE, "average"));
		ruleElements.add(new DSLElementDTO(DSLCategories.PROPERTY, "close"));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, "10-10-2012#07-11-2012"));

		RuleDTO ruleDTO = new RuleDTO(ruleElements);
		rulesDTO.add(ruleDTO);

		ArrayList<DSLElementDTO> logicalOperatorsDTO = new ArrayList<DSLElementDTO>();

		AnalysisDTO analysisDTO = new AnalysisDTO("owner", rulesDTO, logicalOperatorsDTO);

		Gson gson = new Gson();
		String jsonAnalysisDTO = gson.toJson(analysisDTO);
		System.out.println(jsonAnalysisDTO);
	}

	@Test
	public void testGetCommandAnswer() {

		testGetPropertyCommand();
		testGetAverageCommand();
		testGetMathOpOver2PropertiesCommand();

		testGetComposite1();
		testGetComposite2();
		testGetSummation();

		printJsonAnalysisDTO();
	}
}
