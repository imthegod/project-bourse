package co.je.thesis.test.firstStateLoader.BaseStockLoader.persistence;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.src.firstStateLoader.BaseStockLoader.persistence.BaseStockMapper;

public class TestBaseStockMapper {

	private BaseStockMapper baseStockMapper;

	@Before
	public void setUp() {

		baseStockMapper = new BaseStockMapper();
	}

	@After
	public void tearDown() {

		baseStockMapper = null;
	}

	@Test
	public void testCreate() {

		// Set up

		String symbol = "BAC";
		String name = "Bank of America Corp";
		BaseStock bankOfAmerica = new BaseStock(symbol, name);

		long numberOfDocuments = baseStockMapper.getNumberOfDocumentsInBaseStockCollection();
		assertEquals(numberOfDocuments, 0);

		// Exercise

		baseStockMapper.create(bankOfAmerica);

		// Verify

		numberOfDocuments = baseStockMapper.getNumberOfDocumentsInBaseStockCollection();
		assertEquals(numberOfDocuments, 1);

		BaseStock baseStock2 = baseStockMapper.read(bankOfAmerica);

		assertEquals(bankOfAmerica.compareTo(baseStock2), 0);

		// Tear down

		baseStockMapper.delete(bankOfAmerica);

		numberOfDocuments = baseStockMapper.getNumberOfDocumentsInBaseStockCollection();
		assertEquals(numberOfDocuments, 0);
	}

	@Test
	public void testRead() {

		// Set up

		String symbol = "MS";
		String name = "Morgan Stanley";
		BaseStock morganStanley = new BaseStock(symbol, name);

		long numberOfDocuments = baseStockMapper.getNumberOfDocumentsInBaseStockCollection();
		assertEquals(numberOfDocuments, 0);

		baseStockMapper.create(morganStanley);

		numberOfDocuments = baseStockMapper.getNumberOfDocumentsInBaseStockCollection();
		assertEquals(numberOfDocuments, 1);

		// Exercise

		BaseStock baseStock2 = baseStockMapper.read(morganStanley);

		// Verify

		assertEquals(morganStanley.compareTo(baseStock2), 0);

		// Tear down

		baseStockMapper.delete(morganStanley);

		numberOfDocuments = baseStockMapper.getNumberOfDocumentsInBaseStockCollection();
		assertEquals(numberOfDocuments, 0);
	}

	@Test
	public void testDelete() {

		// Set up

		String symbol = "AAPL";
		String name = "Apple Inc";
		BaseStock apple = new BaseStock(symbol, name);

		long numberOfDocuments = baseStockMapper.getNumberOfDocumentsInBaseStockCollection();
		assertEquals(numberOfDocuments, 0);

		baseStockMapper.create(apple);

		numberOfDocuments = baseStockMapper.getNumberOfDocumentsInBaseStockCollection();
		assertEquals(numberOfDocuments, 1);

		BaseStock baseStock2 = baseStockMapper.read(apple);
		assertEquals(apple.compareTo(baseStock2), 0);

		// Exercise

		baseStockMapper.delete(apple);
		
		// Verify
		
		numberOfDocuments = baseStockMapper.getNumberOfDocumentsInBaseStockCollection();
		assertEquals(numberOfDocuments, 0);
	}
}
