package co.je.thesis.mobile.logic.stockController;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import co.je.thesis.mobile.communication.stocks.StockDataRetriever;
import co.je.thesis.mobile.logic.businessObjects.Stock;
import co.je.thesis.mobile.logic.portfolioManager.PortfolioManager;

/**
 * This class is responsible for checking that the stocks haven't exceeded its limits.
 * 
 * @author Julian Espinel
 */
public class LimitChecker {

	/**
	 * This attribute allow us to get all the investor's stocks.
	 */
	private PortfolioManager portfolioManager;

	/**
	 * LimitChecker constructor.
	 * 
	 * @param context the Android App context.
	 * @see http://developer.android.com/reference/android/content/Context.html
	 */
	public LimitChecker(Context context) {

		portfolioManager = new PortfolioManager(context);
	}

	/**
	 * Returns the current last trade price in the stock market for a given stock.
	 * 
	 * @param stockSymbol the symbol of the stock from which we want to know its last trade price.
	 * @return the current last trade price in the stock market for a given stock.
	 */
	private double getStockLastTradePrice(String stockSymbol) {

		double lastTradePice = -1;

		StockDataRetriever dataRetriever = new StockDataRetriever();
		try {

			String[] params = { stockSymbol };
			dataRetriever.execute(params);
			lastTradePice = dataRetriever.get();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (ExecutionException e) {

			e.printStackTrace();
		}

		return lastTradePice;
	}

	/**
	 * Determines if a given stock is out of its limits by comparing its defined stop loss and take 
	 * profit limits, against the current last trade price of the stock in the stock market.
	 * 
	 * @param stock the stock that we want to know if it is out of its limits or not.
	 * @return if a given stock is out of its limits, then returns true, else returns false.
	 */
	private boolean isStockIsOutOfLimits(Stock stock) {

		double lastTradePrice = getStockLastTradePrice(stock.getSymbol());

		boolean isOutOfLimits = false;
		
		if (lastTradePrice <= stock.getStopLoss1()) {

			isOutOfLimits = true;

		} else if (lastTradePrice <= stock.getStopLoss2()) {

			isOutOfLimits = true;

		} else if (lastTradePrice <= stock.getStopLoss3()) {

			isOutOfLimits = true;

		} else if (lastTradePrice >= stock.getTakeProfit1()) {

			isOutOfLimits = true;

		} else if (lastTradePrice >= stock.getTakeProfit2()) {

			isOutOfLimits = true;

		} else if (lastTradePrice >= stock.getTakeProfit3()) {
			
			isOutOfLimits = true;
		}

		return isOutOfLimits;
	}

	/**
	 * Determines if a specific stock of a given portfolio is out of its limits or not.
	 * 
	 * @param portfolioName the name of the portfolio which contains the stock.
	 * @param stockSymbol the symbol of the stock from which we want to know if is out of its limits
	 * 		  or not.
	 * @return if a specific stock of a given portfolio is out of its limits, then returns true,
	 * 		   else returns false.
	 */
	public boolean isStockOfPortfolioOutOfLimits(String portfolioName, String stockSymbol) {
		
		Stock stock = portfolioManager.getSingleStock(portfolioName, stockSymbol);
		boolean isOutOfLimits = isStockIsOutOfLimits(stock);

		return isOutOfLimits;
	}
}
