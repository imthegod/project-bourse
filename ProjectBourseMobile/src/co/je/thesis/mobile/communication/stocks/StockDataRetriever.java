package co.je.thesis.mobile.communication.stocks;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import co.je.thesis.mobile.communication.utils.CommunicationUtils;

/**
 * This class is responsible to know how to retrieve stock data from Internet. 
 * 
 * @author Julian Espinel
 */
public class StockDataRetriever extends AsyncTask<String, Void, Double> {

	/**
	 * Returns the URL where a specific stock's data could be located.
	 * 
	 * @param stockSymbol the symbol of the stock for which we want to retrieve the data.
	 * @return the URL where a specific stock's data could be located.
	 */
	private String getUrlForStock(String stockSymbol) {

		String url = "http://query.yahooapis.com/v1/public/yql?q=%20SELECT%20LastTradePriceOnly%20FROM%20yahoo.finance.quotes%20WHERE%20symbol%3D%22"
				+ stockSymbol
				+ "%22&format=json&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=";
		return url;
	}

	/**
	 * Returns a String object which contains the last trade price of a specific stock.
	 * 
	 * @param stockSymbol the symbol of the stock for which we want to retrieve its last trade price.
	 * @return a String object which contains the last trade price of a specific stock.
	 */
	private String getStockLastTradePriceJSONEntity(String stockSymbol) {

		String uriString = getUrlForStock(stockSymbol);

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(uriString);
		getRequest.addHeader("Content-Type", "application/json");

		String result = "";

		try {

			HttpResponse httpResponse = httpClient.execute(getRequest);
			HttpEntity httpResponseEntity = httpResponse.getEntity();

			//int statusCode = httpResponse.getStatusLine().getStatusCode();
			result = CommunicationUtils.getEntityAsString(httpResponseEntity);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Returns the last trade price of a given stock.
	 * 
	 * @param stockSymbol the symbol of the stock from which we want to know its last trade price.
	 * @return the last trade price of a given stock.
	 */
	private double getStockLastTradePrice(String stockSymbol) {

		String jsonEntity = getStockLastTradePriceJSONEntity(stockSymbol);

		double lastTradePrice = -1;

		try {

			JSONObject jsonObject = new JSONObject(jsonEntity);
			
			// Path to the last trade price: query.results.quote.LastTradePriceOnly
			JSONObject lastTradePriceJson = jsonObject.getJSONObject("query")
					.getJSONObject("results").getJSONObject("quote");
			lastTradePrice = lastTradePriceJson.getDouble("LastTradePriceOnly");

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return lastTradePrice;
	}

	/**
	 * Calls the getStockLastTradePrice method and returns the results of it.
	 * 
	 * @see http://developer.android.com/reference/android/os/AsyncTask.html#doInBackground(Params...)
	 */
	@Override
	protected Double doInBackground(String... params) {

		String stockSymbol = params[0];
		double lastTradePrice = getStockLastTradePrice(stockSymbol);

		return lastTradePrice;
	}
}