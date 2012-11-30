package co.je.thesis.mobile.logic.stockController;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import co.je.thesis.mobile.communication.stocks.StockDataRetriever;
import co.je.thesis.mobile.logic.businessObjects.Stock;
import co.je.thesis.mobile.logic.portfolioManager.PortfolioManager;

public class LimitChecker {

	private PortfolioManager portfolioManager;

	public LimitChecker(Context context) {

		portfolioManager = new PortfolioManager(context);
	}

	private double getStockLastTradePrice(String stockSymbol) {

		double lastTradePice = -1;

		StockDataRetriever dataRetriever = new StockDataRetriever();
		try {

			String[] params = { stockSymbol };
			dataRetriever.execute(params);
			lastTradePice = dataRetriever.get();
			
			System.out.println("LimitChecker: lastTradePrice: " + stockSymbol + ", " + lastTradePice);

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (ExecutionException e) {

			e.printStackTrace();
		}

		return lastTradePice;
	}

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

	public boolean isStockOfPortfolioOutOfLimits(String portfolioName, String stockSymbol) {
		
		Stock stock = portfolioManager.getSingleStock(portfolioName, stockSymbol);
		boolean isOutOfLimits = isStockIsOutOfLimits(stock);

		return isOutOfLimits;
	}
}
