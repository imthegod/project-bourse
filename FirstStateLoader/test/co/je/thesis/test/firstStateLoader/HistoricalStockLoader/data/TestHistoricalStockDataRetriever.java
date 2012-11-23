package co.je.thesis.test.firstStateLoader.HistoricalStockLoader.data;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.data.HistoricalStockDataRetriever;

public class TestHistoricalStockDataRetriever {
	
	@Test
	public void testGetStockData() {
		
		HistoricalStockDataRetriever dataRetriever = new HistoricalStockDataRetriever();
		
		String symbol = "AAPL";
		ArrayList<String> stockData = dataRetriever.getStockHistoricalData(symbol);
		
		assertTrue(stockData != null);
		assertTrue(stockData.size() > 0);
	}
}
