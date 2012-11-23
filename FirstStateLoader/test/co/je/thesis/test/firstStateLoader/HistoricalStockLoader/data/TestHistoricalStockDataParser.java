package co.je.thesis.test.firstStateLoader.HistoricalStockLoader.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import co.je.thesis.common.dtos.stocks.HistoricalStock;
import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.data.HistoricalStockDataParser;
import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.data.HistoricalStockDataRetriever;

public class TestHistoricalStockDataParser {

	@Test
	public void testGetDate() {

		String stringDate = "1984-09-20";
		Date date = HistoricalStockDataParser.getDate(stringDate);
		GregorianCalendar calendar = new GregorianCalendar();

		calendar.setTime(date);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);

		assertEquals(dayOfMonth, 20);
		// (9 -1) Because January = 0 and December = 11
		assertEquals(month, (9 - 1));
		assertEquals(year, 1984);
	}

	@Test
	public void testGetHistoricalStocks() {

		HistoricalStockDataRetriever dataRetriever = new HistoricalStockDataRetriever();

		String symbol = "MS";
		ArrayList<String> historicalStockInfo = dataRetriever.getStockHistoricalData(symbol);

		HistoricalStockDataParser dataParser = new HistoricalStockDataParser();
		ArrayList<HistoricalStock> historicalStocks = dataParser.getHistoricalStocks(historicalStockInfo);

		assertTrue(historicalStocks != null);
		assertTrue(historicalStocks.size() > 0);
		assertEquals(historicalStocks.size(), historicalStockInfo.size());
	}
}
