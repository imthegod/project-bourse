package co.je.thesis.server.persistence.rules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import co.je.thesis.common.dbos.stocks.HistoricalStockDBO;
import co.je.thesis.server.domain.dsl.commands.AverageCommand;
import co.je.thesis.server.domain.dsl.commands.ICommand;
import co.je.thesis.server.domain.dsl.commands.NumberCommand;
import co.je.thesis.server.domain.dsl.commands.PropertyCommand;
import co.je.thesis.server.infrastructure.translators.stocks.HistoricalStockTranslator;
import co.je.thesis.server.persistence.DBManager;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class RuleQueryExecutor {

	public RuleQueryExecutor() {
	}

	private DBCollection getStockCollection(String stockSymbol) {

		DBCollection stockCollection = DBManager.getStockCollection(stockSymbol);
		return stockCollection;
	}

	private double getProperty(HistoricalStockDBO historicalStockDBO, String property) {

		double answer = -1;

		if (property.equalsIgnoreCase(HistoricalStockDBO.OPEN)) {

			answer = historicalStockDBO.getOpen();

		} else if (property.equalsIgnoreCase(HistoricalStockDBO.HIGH)) {

			answer = historicalStockDBO.getHigh();

		} else if (property.equalsIgnoreCase(HistoricalStockDBO.LOW)) {

			answer = historicalStockDBO.getLow();

		} else if (property.equalsIgnoreCase(HistoricalStockDBO.CLOSE)) {

			answer = historicalStockDBO.getClose();

		} else if (property.equalsIgnoreCase(HistoricalStockDBO.VOLUME)) {

			answer = historicalStockDBO.getVolume();
		}

		return answer;
	}

	private Date parseDate(String stringDate) {

		try {

			String pattern = "dd-MM-yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = sdf.parse(stringDate);
			return date;

		} catch (ParseException e) {

			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public double getPropertyOnSpecificTimeFrame(String stockSymbol, String property,
			String timeFrame) {

		double answer = -1;

		DBCollection stockCollection = getStockCollection(stockSymbol);
		Date date = parseDate(timeFrame);

		HistoricalStockDBO queryDBO = HistoricalStockDBO.createQueryDBO(date);
		BasicDBObject basicDBO = (BasicDBObject) stockCollection.findOne(queryDBO);

		if (basicDBO != null) {

			HistoricalStockTranslator translator = new HistoricalStockTranslator();
			HistoricalStockDBO historicalStockDBO = translator.translateToDBO(basicDBO);
			answer = getProperty(historicalStockDBO, property);

		} else {

			// Does not found a document with the given date
		}

		return answer;
	}

	public double getAverage(String stockSymbol, String property, String timeFrameA,
			String timeFrameB) {

		// Property average over dateA and dateB.

		double answer = -1;

		DBCollection stockCollection = getStockCollection(stockSymbol);

		double accumulator = 0;

		Date dateA = parseDate(timeFrameA);
		Date dateB = parseDate(timeFrameB);

		DBObject dateQuery = BasicDBObjectBuilder.start("$gte", dateA).add("$lte", dateB).get();
		HistoricalStockDBO queryDBO = new HistoricalStockDBO();
		queryDBO.put(HistoricalStockDBO.DATE, dateQuery);

		// Don't limit the cursor to a specific number of results because
		// the cursor makes lazy
		// fetching from the DB. See http://api.mongodb.org/java/current/
		// and find the DBCursor
		// class documentation.
		DBCursor cursor = stockCollection.find(queryDBO);
		HistoricalStockTranslator translator = new HistoricalStockTranslator();

		while (cursor.hasNext()) {

			BasicDBObject basicDBO = (BasicDBObject) cursor.next();
			HistoricalStockDBO historicalStockDBO = translator.translateToDBO(basicDBO);
			accumulator += getProperty(historicalStockDBO, property);
		}

		int numberOfResults = cursor.count();
		answer = accumulator / numberOfResults;

		return answer;
	}

	private double executeMathOp(String mathOperation, double propertyOne, double propertyTwo) {

		double answer = -1;

		if (mathOperation.equalsIgnoreCase("suma")) {

			answer = propertyOne + propertyTwo;

		} else if (mathOperation.equalsIgnoreCase("resta")) {

			answer = propertyOne - propertyTwo;

		} else if (mathOperation.equalsIgnoreCase("multiplicacion")) {

			answer = propertyOne * propertyTwo;

		} else {

			String exceptionMenssage = "The math operation: " + mathOperation
					+ " is not a valid one for this command.";
			throw new IllegalArgumentException(exceptionMenssage);
		}

		return answer;
	}

	public double getMathOpOver2Properties(String stockSymbol, String mathOperation,
			String propertyA, String propertyB, String timeFrame) {

		// just dateA (one date).
		// Math operation over 2 properties at the same date.
		// Often diference (close price - open price)

		double answer = -1;

		DBCollection stockCollection = getStockCollection(stockSymbol);

		Date date = parseDate(timeFrame);
		HistoricalStockDBO queryDBO = HistoricalStockDBO.createQueryDBO(date);
		BasicDBObject basicDBO = (BasicDBObject) stockCollection.findOne(queryDBO);

		if (basicDBO != null) {

			HistoricalStockTranslator translator = new HistoricalStockTranslator();
			HistoricalStockDBO historicalStockDBO = translator.translateToDBO(basicDBO);
			double propertyOne = getProperty(historicalStockDBO, propertyA);
			double propertyTwo = getProperty(historicalStockDBO, propertyB);

			answer = executeMathOp(mathOperation, propertyOne, propertyTwo);

		} else {

			// Does not found a document with the given date
		}

		return answer;
	}

	public double getSummation(String stockSymbol, String nestedMathOperation, String property1, String property2,
			String timeFrame1, String timeFrame2) {

		double answer = -1;

		DBCollection stockCollection = getStockCollection(stockSymbol);

		double accumulator = 0;

		Date dateA = parseDate(timeFrame1);
		Date dateB = parseDate(timeFrame2);

		DBObject dateQuery = BasicDBObjectBuilder.start("$gte", dateA).add("$lte", dateB).get();
		HistoricalStockDBO queryDBO = new HistoricalStockDBO();
		queryDBO.put(HistoricalStockDBO.DATE, dateQuery);

		// Don't limit the cursor to a specific number of results because
		// the cursor makes lazy
		// fetching from the DB. See http://api.mongodb.org/java/current/
		// and find the DBCursor
		// class documentation.
		DBCursor cursor = stockCollection.find(queryDBO);
		HistoricalStockTranslator translator = new HistoricalStockTranslator();

		while (cursor.hasNext()) {

			BasicDBObject basicDBO = (BasicDBObject) cursor.next();
			HistoricalStockDBO historicalStockDBO = translator
					.translateToDBO(basicDBO);

			double propertyAValue = getProperty(historicalStockDBO, property1);
			double propertyBValue = getProperty(historicalStockDBO, property2);

			accumulator += executeMathOp(nestedMathOperation, propertyAValue,
					propertyBValue);
		}
		
		answer = accumulator;

		return answer;
	}

	public double getComposite(String stockSymbol, String mathOperation,
			ArrayList<ICommand> childrenCommands) {

		int numberOfChildren = childrenCommands.size();

		double answer = -1;

		if (numberOfChildren > 0) {

			ICommand firstChild = childrenCommands.get(0);
			String commandName = firstChild.getCommandName();

			if (commandName.equalsIgnoreCase(NumberCommand.COMMAND_NAME)) {

				// (a, )

				ICommand secondChild = childrenCommands.get(1);
				commandName = secondChild.getCommandName();

				if (commandName.equalsIgnoreCase(PropertyCommand.COMMAND_NAME)) {

					// (a, b)

					NumberCommand numberCommand = (NumberCommand) firstChild;
					PropertyCommand propertyCommand = (PropertyCommand) secondChild;

					double number = numberCommand.getNumber();

					String property = propertyCommand.getPropertyName();
					String timeFrame = propertyCommand.getTimeFrame();
					double propertyCommandAnswer = getPropertyOnSpecificTimeFrame(stockSymbol,
							property, timeFrame);

					answer = executeMathOp(mathOperation, number, propertyCommandAnswer);

				} else if (commandName.equalsIgnoreCase(AverageCommand.COMMAND_NAME)) {

					// (a, c)

					NumberCommand numberCommand = (NumberCommand) firstChild;
					AverageCommand mathOpOver1PropertyCommand = (AverageCommand) secondChild;

					double number = numberCommand.getNumber();

					String property = mathOpOver1PropertyCommand.getProperty();
					String timeFrameA = mathOpOver1PropertyCommand.getTimeFrame1();
					String timeFrameB = mathOpOver1PropertyCommand.getTimeFrame2();
					double mathOpOver1PropertyAnswer = getAverage(stockSymbol, property,
							timeFrameA, timeFrameB);

					answer = executeMathOp(mathOperation, number, mathOpOver1PropertyAnswer);
				}

			}

		} else {

			String exceptionMessage = "The number of children commands must be > 0.";
			throw new IllegalArgumentException(exceptionMessage);
		}

		return answer;
	}
}