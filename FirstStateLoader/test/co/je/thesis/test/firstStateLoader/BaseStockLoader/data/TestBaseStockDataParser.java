package co.je.thesis.test.firstStateLoader.BaseStockLoader.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.src.firstStateLoader.BaseStockLoader.data.BaseStockDataParser;
import co.je.thesis.src.firstStateLoader.BaseStockLoader.data.BaseStockDataRetriever;

public class TestBaseStockDataParser {

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testGetStockNamesFromExcelFile() {
		
		// Set up
		
		BaseStockDataRetriever fileLoader = new BaseStockDataRetriever();
		File[] files = fileLoader.loadStockBaseExcelFiles();
		
		Random random = new Random();
		int randomNumber = random.nextInt(files.length);
		File randomFile = files[randomNumber];
		
		// Exercise
		
		BaseStockDataParser dataParser = new BaseStockDataParser();
		ArrayList<String> stockNames = dataParser.getStockNamesFromExcelFile(randomFile);
		
		// Verify
		
		assertTrue(stockNames != null);
		assertTrue(stockNames.size() > 0);
		
		// Tear down
		
		dataParser = null;
		fileLoader = null;
	}
	
	@Test
	public void testGetBaseStocks() {
		
		// Set up
		
		BaseStockDataRetriever fileLoader = new BaseStockDataRetriever();
		File[] files = fileLoader.loadStockBaseExcelFiles();
		
		Random random = new Random();
		int randomNumber = random.nextInt(files.length);
		File randomFile = files[randomNumber];
		
		BaseStockDataParser dataParser = new BaseStockDataParser();
		ArrayList<String> stockNames = dataParser.getStockNamesFromExcelFile(randomFile);
		
		// Exercise
		
		List<BaseStock> historicalStocks = dataParser.getBaseStocks(stockNames);
		
		// Verify
		
		assertTrue(historicalStocks != null);
		assertTrue(historicalStocks.size() > 0);
		assertEquals(historicalStocks.size(), stockNames.size());
		
		// Tear down
		
		dataParser = null;
		fileLoader = null;
	}
}
