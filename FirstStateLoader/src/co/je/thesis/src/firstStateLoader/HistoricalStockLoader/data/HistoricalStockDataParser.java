package co.je.thesis.src.firstStateLoader.HistoricalStockLoader.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import co.je.thesis.common.dtos.stocks.HistoricalStock;

/**
 * Class to parse historical stock data
 * 
 * @author Julian Espinel
 */
public class HistoricalStockDataParser {

	/**
	 * Constant to parse the String which contains historical stock information
	 */
	public static final String SEPARATOR = ",";

	/**
	 * Returns a Date given a String object which represents a date
	 * 
	 * @param stringDate String object which represents a date. This stringDate
	 *        comes in the following format: 2012-06-17 (year-month-day).
	 * @return A Date object with the date represented by the String parameter
	 */
	public static Date getDate(String stringDate) {

		String dateSpliter = "-";
		String dateArray[] = stringDate.split(dateSpliter);

		int year = Integer.parseInt(dateArray[0]);
		int month = Integer.parseInt(dateArray[1]);
		month = month - 1; // Because January = 0 and December = 11
		int day = Integer.parseInt(dateArray[2]);

		GregorianCalendar calendar = new GregorianCalendar(year, month, day);
		Date date = calendar.getTime();

		return date;
	}

	/**
	 * Given a String which contains all the information needed to build a
	 * HistoricalStock object, parses that String, creates and returns and object of
	 * type HistoricalStock
	 * 
	 * @param stockInfo A String which contains all the information needed to build
	 *        a HistoricalStock object
	 * @return Object of type HistoricalStock with the information contained by the
	 *         parameter
	 */
	private HistoricalStock buildHistoricalStockObject(String stockInfo) {

		String array[] = stockInfo.split(SEPARATOR);

		Date date = getDate(array[0]);

		double open = Double.parseDouble(array[1]);
		double high = Double.parseDouble(array[2]);
		double low = Double.parseDouble(array[3]);
		double close = Double.parseDouble(array[4]);
		double volume = Double.parseDouble(array[5]);

		HistoricalStock historicalStock = new HistoricalStock(date, open, high, low, close, volume);

		return historicalStock;
	}

	/**
	 * Given an arraylist of Strings, this method parses those strings and returns
	 * and arraylist of HistoricalStock objects.
	 * 
	 * @param stockHistoricalData ArrayList of string objects. Each String object in
	 *        this ArrayList contains stock historical information.
	 * @return Arraylist of HistoricalStock objects.
	 */
	public ArrayList<HistoricalStock> getHistoricalStocks(ArrayList<String> stockHistoricalData) {

		ArrayList<HistoricalStock> historicalStocks = new ArrayList<HistoricalStock>();

		for (int i = 0; i < stockHistoricalData.size(); i++) {

			String stockInfo = stockHistoricalData.get(i);
			HistoricalStock historicalStock = buildHistoricalStockObject(stockInfo);

			historicalStocks.add(historicalStock);
		}

		return historicalStocks;
	}
}