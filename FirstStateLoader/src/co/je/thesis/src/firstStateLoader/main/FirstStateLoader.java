package co.je.thesis.src.firstStateLoader.main;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.src.firstStateLoader.BaseStockLoader.BaseStockLoader;
import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.concurrency.HistoricalStockTask;

/**
 * Class which know how to load the system first state.
 * 
 * @author Julian Espinel
 */
public class FirstStateLoader {
	
	/**
	 * Loads the base stocks and stores them into the database. 
	 */
	private void loadBaseStocks() {
		
		BaseStockLoader baseStockLoader = new BaseStockLoader();
		baseStockLoader.loadBaseStocks();
	}
	
	/**
	 * Load the historical records for all the base stocks, and saves all the
	 * historical records for each stock into the database.
	 */
	private void loadHistoricalStocks() {
		
		BaseStockLoader baseStockLoader = new BaseStockLoader();
		List<BaseStock> baseStocks = baseStockLoader.getBaseStocksFromDB();
		
		// Saves the stock history
		
		int numberOfStocks = baseStocks.size();
		String message = "Number of stocks: " + numberOfStocks;
		System.out.println(message);
		
		int numberOfThreads = 100;
		ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);
		
		for (int i = 0; i < numberOfStocks; i++) {
			
			BaseStock baseStock = baseStocks.get(i);
			String stockSymbol = baseStock.getSymbol();
			HistoricalStockTask task = new HistoricalStockTask(stockSymbol);
			
			threadPool.submit(task);
		}
		
		threadPool.shutdown();
	}
	
	/**
	 * Loads the system's first state
	 */
	public void loadFirstState() {
		
		loadBaseStocks();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		loadHistoricalStocks();
	}
	
	
	/**
	 * First state loader main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		FirstStateLoader firstStateLoader = new FirstStateLoader();
		firstStateLoader.loadFirstState();
	}
}