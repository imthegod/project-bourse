package co.je.thesis.src.firstStateLoader.HistoricalStockLoader.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Class that know how to retrieve historical stock data from internet
 * 
 * @author Julian Espinel
 */
public class HistoricalStockDataRetriever {

	/**
	 * Returns an ArrayList of String objects. Each String object contains eod (end
	 * of day) stock data. The first String object in the ArrayList contains the end
	 * of day stock data for today. The last String object in the ArrayList,
	 * contains the end of day stock data at the first day this stock got into the
	 * stock market.
	 * 
	 * @param stockSymbol The symbol of the stock which we want to get its
	 *        historical data
	 * @return An ArrayList of String objects. Each String object contains eod (end
	 *         of day) stock data from the first day this stock got into the stock
	 *         market, until today
	 */
	public ArrayList<String> getStockHistoricalData(String stockSymbol) {

		String stringUrl = "http://ichart.finance.yahoo.com/table.csv?s=" + stockSymbol + "&c=1962";

		ArrayList<String> stockData = new ArrayList<String>();

		try {

			URL url = new URL(stringUrl);
			URLConnection connection = url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			// This line contains the headers:
			// "Date, Open, High, Low, Close, Volume"
			br.readLine();

			String stockInfo = br.readLine();

			while (stockInfo != null && ! stockInfo.equals("")) {

				stockData.add(stockInfo);
				stockInfo = br.readLine();
			}

		} catch (MalformedURLException e) {

			System.out.println("MalformedURLException: " + e.getMessage());

		} catch (IOException e) {

			System.out.println("IOException: " + e.getMessage());
		}

		return stockData;
	}
}
