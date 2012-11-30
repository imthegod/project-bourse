package co.je.thesis.mobile.logic.statisticCentre;

import co.je.thesis.mobile.logic.businessObjects.Stock;

public class StockStatistics {

	public static double getStockPercentageEarnings(Stock stock, double lastTradePrice) {

		double basePercentage = 100;
		double numerator = (lastTradePrice * basePercentage);
		double actualPercentage = numerator / stock.getBasePrice();
		double percentageEarnings = (actualPercentage - basePercentage);
		
		return percentageEarnings;
	}

	public static double getStockNetEarnings(Stock stock, double lastTradePrice) {

		int numberOfShares = stock.getNumberOfShares();

		double baseInvestedValue = (numberOfShares * stock.getBasePrice());
		double currentInvestedValue = (numberOfShares * lastTradePrice);

		double netEarnings = currentInvestedValue - baseInvestedValue;

		return netEarnings;
	}
}
