package co.je.thesis.src.firstStateLoader.HistoricalStockLoader;

import java.util.ArrayList;

import co.je.thesis.common.dtos.stocks.HistoricalStock;
import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.data.HistoricalStockDataParser;
import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.data.HistoricalStockDataRetriever;
import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.persistence.HistoricalStockMapper;

/**
 * Class which knows how to load the historical stock data with which the system
 * will work.
 * 
 * @author Julian Espinel
 */
public class HistoricalStockLoader {

	/**
	 * The symbol of the stock for which this HistoricalStockLoader will work.
	 */
	private String stockSymbol;

	public HistoricalStockLoader(String stockSymbol) {

		this.stockSymbol = stockSymbol;
	}

	/**
	 * Returns an ArrayList of String objects. Each String object into the ArrayList
	 * contains historical data of the stock for which this HistoricalStockLoader is
	 * working for.
	 * 
	 * @return Returns an ArrayList of String objects. Each String object into the
	 *         ArrayList contains historical data of the stock of this
	 *         HistoricalStockLoader.
	 */
	private ArrayList<String> getStockHistoricalData() {

		HistoricalStockDataRetriever dataRetriever = new HistoricalStockDataRetriever();
		ArrayList<String> stockHistoricalData = dataRetriever.getStockHistoricalData(stockSymbol);

		return stockHistoricalData;
	}

	/**
	 * Returns an ArrayList of HistoricalStock objects for the stock of this
	 * HistoricalStockLoader.
	 * 
	 * @param stockHistoricalData ArrayList which contains String objects. Each
	 *        String object into the ArrayList contains historical data of the stock
	 *        for which this HistoricalStockLoader is working for.
	 * @return An ArrayList of HistoricalStock objects for the stock of this
	 *         HistoricalStockLoader.
	 */
	private ArrayList<HistoricalStock> getHistoricalStocks(ArrayList<String> stockHistoricalData) {

		HistoricalStockDataParser dataParser = new HistoricalStockDataParser();
		ArrayList<HistoricalStock> historicalStocks = dataParser.getHistoricalStocks(stockHistoricalData);

		return historicalStocks;
	}

	/**
	 * Saves multiple HistoricalStock objects into the database. Each Stock has one
	 * Collection into the database. Thus, all the HistoricalStock objects of a
	 * specific stock will be saved to the correspondent stock collection.
	 * 
	 * For example, All the HistoricalStock objects of Apple, will be stored into a
	 * collection named AAPL (The Apple stock symbol).
	 * 
	 * @param historicalStocks An ArrayList which contains all the HistoricalStock
	 *        objects that conform the full historical information of a specific
	 *        stock.
	 */
	private void saveStockHistoryInDB(ArrayList<HistoricalStock> historicalStocks) {

		HistoricalStockMapper mapper = new HistoricalStockMapper(stockSymbol);
		mapper.create(historicalStocks);
	}

	/**
	 * This method can be considered as the main function of the
	 * HistoricalStockLoader module.
	 * 
	 * What this method does is described in the following steps:
	 * 
	 * 1. Retrieves the stock historical data from Internet.
	 * 
	 * 2. Parses the information retrieved on step one, an returns an ArrayList of
	 * HistoricalStock objects.
	 * 
	 * 3. Saves the whole historical information of the stock into the database.
	 */
	public void loadHistoricalStock() {

		ArrayList<String> historicalStockData = getStockHistoricalData();
		ArrayList<HistoricalStock> historicalStocks = getHistoricalStocks(historicalStockData);
		saveStockHistoryInDB(historicalStocks);
	}
}
