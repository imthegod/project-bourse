package co.je.thesis.src.firstStateLoader.common.translators;

import java.util.Date;

import co.je.thesis.common.dbos.stocks.HistoricalStockDBO;
import co.je.thesis.common.dtos.stocks.HistoricalStock;

/**
 * Class to know how to translate from HistoricalStock to HistoricalStockDBO
 * objects and vice versa.
 * 
 * @author Julian Espinel
 */
public class HistoricalStockTranslator {
	
	/**
	 * Translates a HistoricalStockDBO object, to a HistoricalStock object
	 * 
	 * @param historicalStockDBO The database object representation of a HistoricalStock object
	 * @return A HistoricalStock object with the information contained by the parameter object
	 */
	public HistoricalStock translateToDomainObject(HistoricalStockDBO historicalStockDBO) {
		
		Date date = historicalStockDBO.getDate();
		double open = historicalStockDBO.getOpen();
		double high = historicalStockDBO.getHigh();
		double low = historicalStockDBO.getLow();
		double close = historicalStockDBO.getClose();
		double volume = historicalStockDBO.getVolume();
		
		HistoricalStock historicalStock = new HistoricalStock(date, open, high, low, close, volume);
		
		return historicalStock;
	}
	
	/**
	 * Translates a HistoricalStock object, to a HistoricalStockDBO object
	 * 
	 * @param historicalStock The HistoricalStock object we want to translate into a
	 * 		  HistoricalStockDBO object
	 * @return A HistoricalStockDBO object with the information contained by the HistoricalStock
	 * 		   object
	 */
	public HistoricalStockDBO translateToDBO(HistoricalStock historicalStock) {
		
		Date date = historicalStock.getDate();
		double open = historicalStock.getOpen();
		double high = historicalStock.getHigh();
		double low = historicalStock.getLow();
		double close = historicalStock.getClose();
		double volume = historicalStock.getVolume();
		
		HistoricalStockDBO historicalStockDBO = new HistoricalStockDBO(date, open, 
			high, low, close, volume);
		
		return historicalStockDBO;
	}
}