package co.je.thesis.mobile.logic.stockController;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import co.je.thesis.mobile.R;
import co.je.thesis.mobile.logic.businessObjects.Portfolio;
import co.je.thesis.mobile.logic.businessObjects.Stock;
import co.je.thesis.mobile.logic.portfolioManager.PortfolioManager;
import co.je.thesis.mobile.presentation.portfolio.EditStocksOutOfLimitsActivity;

/**
 * This class is responsible of verify if any stock is out of its limits (stop loss and take profit),
 * and notify the user if so. 
 * 
 * @author Julian Espinel
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

	/**
	 * Default constructor.
	 */
	public AlarmBroadcastReceiver() {
	}

	/**
	 * Notifies the user about the out of limit stocks.
	 * 
	 * @param context the Android App context.
	 * @param outOfLimitsStocks an ArrayList that contains the stocks that have surpassed its
	 * 		  defined limits.
	 */
	private void notifyUser(Context context, ArrayList<Stock> outOfLimitsStocks) {

		int numberOfStocks = outOfLimitsStocks.size();

		Bundle bundle = new Bundle();

		String key = "stocks";
		bundle.putSerializable(key, outOfLimitsStocks);

		Intent editStocksOutOfLimitsActivityIntent = new Intent(context,
				EditStocksOutOfLimitsActivity.class);
		editStocksOutOfLimitsActivityIntent.putExtras(bundle);
		editStocksOutOfLimitsActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				editStocksOutOfLimitsActivityIntent, 0);

		// ---get the notification ID for the notification;
		// passed in by the MainActivity---
		int notifID = 1234567;

		NotificationManager notoficationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		CharSequence title = "Project Bourse";
		
		CharSequence message = "";
		
		if (numberOfStocks == 1) {
			
			message = "There is " + numberOfStocks + " stock out of limits";
			
		} else if (numberOfStocks > 1) {
			
			message = "There are " + numberOfStocks + " stocks out of limits";
		}
		
		Notification notification = new Notification(R.drawable.ic_launcher, title,
				System.currentTimeMillis());
		
		notification.setLatestEventInfo(context, title, message, pendingIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		notoficationManager.notify(notifID, notification);
	}

	/**
	 * Returns an ArrayList with the the stocks that have surpassed its limits.
	 * 
	 * @param context the Android App context.
	 * @return an ArrayList with the the stocks that have surpassed its limits.
	 */
	private ArrayList<Stock> getOutOfLimitsStocks(Context context) {

		PortfolioManager portfolioManager = new PortfolioManager(context);
		ArrayList<Portfolio> portfolios = portfolioManager.getAllPortfolios();

		LimitChecker limitChecker = new LimitChecker(context);

		ArrayList<Stock> stocksOutOfLimits = new ArrayList<Stock>();

		for (int i = 0; i < portfolios.size(); i++) {

			Portfolio portfolio = portfolios.get(i);
			ArrayList<Stock> portfolioStocks = portfolio.getStocks();

			for (int j = 0; j < portfolioStocks.size(); j++) {

				Stock stock = portfolioStocks.get(j);

				String portfolioName = portfolio.getName();
				String stockSymbol = stock.getSymbol();

				if (limitChecker.isStockOfPortfolioOutOfLimits(portfolioName, stockSymbol)) {

					stocksOutOfLimits.add(stock);
				}
			}
		}

		return stocksOutOfLimits;
	}

	/**
	 * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
	 * 
	 * @see http://developer.android.com/reference/android/content/BroadcastReceiver.html#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {

		// This method is called when the BroadcastReceiver is receiving
		// an Intent broadcast.

		System.out.println("AlarmBroadcastReceiver: onReceive()");

		ArrayList<Stock> outOfLimitsStocks = getOutOfLimitsStocks(context);

		if (outOfLimitsStocks.size() > 0) {
			
			System.out.println("AlarmBroadcastReceiver: onReceive(): notifyUser()");
			
			notifyUser(context, outOfLimitsStocks);
		}
	}
}