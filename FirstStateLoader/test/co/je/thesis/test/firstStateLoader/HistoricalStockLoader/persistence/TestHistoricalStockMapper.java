package co.je.thesis.test.firstStateLoader.HistoricalStockLoader.persistence;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import co.je.thesis.common.dtos.stocks.HistoricalStock;
import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.data.HistoricalStockDataParser;
import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.data.HistoricalStockDataRetriever;
import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.persistence.HistoricalStockMapper;

public class TestHistoricalStockMapper {

	public static final String SYMBOL = "MS";

	private HistoricalStockDataRetriever dataRetriever;
	private HistoricalStockDataParser dataParser;
	private HistoricalStockMapper historicalStockMapper;

	@Before
	public void setUp() {

		dataRetriever = new HistoricalStockDataRetriever();
		dataParser = new HistoricalStockDataParser();
		historicalStockMapper = new HistoricalStockMapper(SYMBOL);
	}

	@After
	public void tearDown() {

		historicalStockMapper = null;
		dataParser = null;
		dataRetriever = null;
	}

	@Test
	public void testCreateOne() {

		// Set up

		ArrayList<String> stockData = dataRetriever.getStockHistoricalData(SYMBOL);
		ArrayList<HistoricalStock> historicalData = dataParser.getHistoricalStocks(stockData);

		Random random = new Random();
		int numberOfDocuments = historicalData.size();

		int randomNumber = random.nextInt(numberOfDocuments);
		HistoricalStock randomStock = historicalData.get(randomNumber);

		long numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(0, numberOfDocumentsInCollection);

		// Exercise

		historicalStockMapper.create(randomStock);

		// Verify

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(1, numberOfDocumentsInCollection);

		HistoricalStock foundStock = historicalStockMapper.read(randomStock);

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(1, numberOfDocumentsInCollection);

		int compareToResult = randomStock.compareTo(foundStock);
		assertEquals(compareToResult, 0);

		// Tear down

		historicalStockMapper.delete(foundStock);

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(0, numberOfDocumentsInCollection);
	}

	@Test
	public void testCreateList() {

		// Set up

		ArrayList<String> stockData = dataRetriever.getStockHistoricalData(SYMBOL);
		ArrayList<HistoricalStock> historicalData = dataParser.getHistoricalStocks(stockData);

		Random random = new Random();
		int numberOfDocuments = historicalData.size();

		long numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(0, numberOfDocumentsInCollection);

		// Exercise

		historicalStockMapper.create(historicalData);

		// Verify

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(numberOfDocuments, numberOfDocumentsInCollection);

		int randomNumber = random.nextInt(numberOfDocuments);
		HistoricalStock randomStock = historicalData.get(randomNumber);

		HistoricalStock foundStock = historicalStockMapper.read(randomStock);

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(numberOfDocuments, numberOfDocumentsInCollection);

		int compareToResult = randomStock.compareTo(foundStock);
		assertEquals(compareToResult, 0);

		// Tear down

		historicalStockMapper.delete(foundStock);

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals((numberOfDocuments - 1), numberOfDocumentsInCollection);

		for (int i = 0; i < numberOfDocuments; i++) {

			HistoricalStock historicalStock = historicalData.get(i);
			historicalStockMapper.delete(historicalStock);
		}

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(0, numberOfDocumentsInCollection);
	}

	@Test
	public void testRead() {

		// Set up

		ArrayList<String> stockData = dataRetriever.getStockHistoricalData(SYMBOL);
		ArrayList<HistoricalStock> historicalData = dataParser.getHistoricalStocks(stockData);

		Random random = new Random();
		int numberOfDocuments = historicalData.size();

		int randomNumber = random.nextInt(numberOfDocuments);
		HistoricalStock randomStock = historicalData.get(randomNumber);

		long numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(0, numberOfDocumentsInCollection);

		historicalStockMapper.create(randomStock);

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(1, numberOfDocumentsInCollection);

		// Exercise

		HistoricalStock foundStock = historicalStockMapper.read(randomStock);

		// Verify

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(1, numberOfDocumentsInCollection);

		int compareToResult = randomStock.compareTo(foundStock);
		assertEquals(compareToResult, 0);

		// Tear down

		historicalStockMapper.delete(foundStock);

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(0, numberOfDocumentsInCollection);
	}

	@Test
	public void testDelete() {

		// Set up

		ArrayList<String> stockData = dataRetriever.getStockHistoricalData(SYMBOL);
		ArrayList<HistoricalStock> historicalData = dataParser.getHistoricalStocks(stockData);

		Random random = new Random();
		int numberOfDocuments = historicalData.size();

		int randomNumber = random.nextInt(numberOfDocuments);
		HistoricalStock randomStock = historicalData.get(randomNumber);

		long numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(0, numberOfDocumentsInCollection);

		historicalStockMapper.create(randomStock);

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(1, numberOfDocumentsInCollection);

		HistoricalStock foundStock = historicalStockMapper.read(randomStock);

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(1, numberOfDocumentsInCollection);

		int compareToResult = randomStock.compareTo(foundStock);
		assertEquals(compareToResult, 0);

		// Exercise

		historicalStockMapper.delete(foundStock);

		// Verify

		numberOfDocumentsInCollection = historicalStockMapper.getNumberOfDocumentsInTheCollection();
		assertEquals(0, numberOfDocumentsInCollection);
	}
}