package co.je.thesis.src.firstStateLoader.HistoricalStockLoader.concurrency;

import co.je.thesis.src.firstStateLoader.HistoricalStockLoader.HistoricalStockLoader;

/**
 * Class which know how to load the historical data of a given stock.
 * 
 * This Class implements the Runnable interface because it will be submited by a
 * ExecutorService.
 * 
 * See: http://docs.oracle.com/javase/tutorial/essential/concurrency/exinter.html
 * 
 * @author Julian Espinel
 */
public class HistoricalStockTask implements Runnable {

	private HistoricalStockLoader historicalStockLoader;

	public HistoricalStockTask(String stockSymbol) {

		historicalStockLoader = new HistoricalStockLoader(stockSymbol);
	}

	@Override
	public void run() {

		historicalStockLoader.loadHistoricalStock();
	}
}
