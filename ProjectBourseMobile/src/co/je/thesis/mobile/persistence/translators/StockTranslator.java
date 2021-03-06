package co.je.thesis.mobile.persistence.translators;

import co.je.thesis.mobile.logic.businessObjects.Stock;
import co.je.thesis.mobile.persistence.dbo.StockDBO;

/**
 * This class knows how to translate objects of type Stock, to StockDBO objects 
 * and vice versa.
 * 
 * @author Julian Espinel
 */
public class StockTranslator {

	/**
	 * Translates a Stock object into a StockDBO object.
	 * 
	 * @param stock the Stock object we want to translate.
	 * @return a StockDBO object with the information stored by the Stock object.
	 */
	public static StockDBO toDBO(Stock stock) {
		
		String symbol = stock.getSymbol();
		String name = stock.getName();
		int numberOfShares = stock.getNumberOfShares();
		
		String portfolioName = stock.getPortfolioName();
		String correctedPortfolioNameForDB = PortfolioTranslator.getCorrectedNameForDb(portfolioName);
		
		double basePrice = stock.getBasePrice();
		
		double stopLoss1 = stock.getStopLoss1();
		double stopLoss2 = stock.getStopLoss2();
		double stopLoss3 = stock.getStopLoss3();
		
		double takeProfit1 = stock.getTakeProfit1();
		double takeProfit2 = stock.getTakeProfit2();
		double takeProfit3 = stock.getTakeProfit3();
		
		StockDBO stockDBO = new StockDBO(symbol, name, numberOfShares, correctedPortfolioNameForDB,
				basePrice, stopLoss1, stopLoss2, stopLoss3, takeProfit1, takeProfit2,
				takeProfit3);
		
		return stockDBO;
	}
	
	/**
	 * Translates a StockDBO object into a Stock object.
	 * 
	 * @param stockDBO the StockDBO object we want to translate.
	 * @return a Stock object with the information stored by the StockDBO object.
	 */
	public static Stock toBusinessObjetc(StockDBO stockDBO) {
		
		String symbol = stockDBO.getSymbol();
		String name = stockDBO.getName();
		int numberOfShares = stockDBO.getNumberOfShares();
		
		String correctedPortfolioName = stockDBO.getPortfolioName();
		String portfolioName = PortfolioTranslator.getOriginalPortfolioName(correctedPortfolioName);
		
		double basePrice = stockDBO.getBasePrice();
		
		double stopLoss1 = stockDBO.getStopLoss1();
		double stopLoss2 = stockDBO.getStopLoss2();
		double stopLoss3 = stockDBO.getStopLoss3();
		
		double takeProfit1 = stockDBO.getTakeProfit1();
		double takeProfit2 = stockDBO.getTakeProfit2();
		double takeProfit3 = stockDBO.getTakeProfit3();
		
		Stock stock = new Stock(symbol, name, numberOfShares, portfolioName, basePrice,
				stopLoss1, stopLoss2, stopLoss3, takeProfit1, takeProfit2, takeProfit3);
		
		return stock;
	}
}
