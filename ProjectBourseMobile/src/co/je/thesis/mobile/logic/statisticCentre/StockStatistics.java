package co.je.thesis.mobile.logic.statisticCentre;

import co.je.thesis.mobile.logic.businessObjects.Stock;

/**
 * This class is responsible of generate the stocks statistics.
 * 
 * @author Julian Espinel
 */
public class StockStatistics {

	/**
	 * Returns the percentage earnings of a given stock, comparing its base price against the
	 * current last trade price.
	 * 
	 * @param stock the stock we want to get statistics for.
	 * @param lastTradePrice the last trade price for the given stock in the stock market.
	 * @return the percentage earnings of a given stock, comparing its base price against the
	 * 		   current last trade price.
	 */
	public static double getStockPercentageEarnings(Stock stock, double lastTradePrice) {

		double basePercentage = 100;
		double numerator = (lastTradePrice * basePercentage);
		double actualPercentage = numerator / stock.getBasePrice();
		double percentageEarnings = (actualPercentage - basePercentage);
		
		return percentageEarnings;
	}

	/**
	 * Returns the net earnings in US dollars for the given stock. The result is calculated
	 * using the stock base price and the current last trade price.
	 * 
	 * @param stock the stock we want to get statistics for.
	 * @param lastTradePrice the last trade price for the given stock in the stock market.
	 * @return the net earnings in US dollars for the given stock. The result is calculated
	 * 		   using the stock base price and the current last trade price.
	 */
	public static double getStockNetEarnings(Stock stock, double lastTradePrice) {

		int numberOfShares = stock.getNumberOfShares();

		double baseInvestedValue = (numberOfShares * stock.getBasePrice());
		double currentInvestedValue = (numberOfShares * lastTradePrice);

		double netEarnings = currentInvestedValue - baseInvestedValue;

		return netEarnings;
	}
}
