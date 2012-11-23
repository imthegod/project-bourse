package co.je.thesis.mobile.logic.stockController;

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

public class DataRetriever extends AsyncTask<String, Void, Double> {

	private String getUriForStock(String stockSymbol) {

		String uri = "http://query.yahooapis.com/v1/public/yql?q=%20SELECT%20LastTradePriceOnly%20FROM%20yahoo.finance.quotes%20WHERE%20symbol%3D%22"
				+ stockSymbol
				+ "%22&format=json&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=";
		return uri;
	}

	private String getStockLastTradePriceJSONEntity(String stockSymbol) {

		String uriString = getUriForStock(stockSymbol);

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(uriString);
		getRequest.addHeader("Content-Type", "application/json");

		String result = "";

		try {

			HttpResponse httpResponse = httpClient.execute(getRequest);
			HttpEntity httpResponseEntity = httpResponse.getEntity();

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			result = CommunicationUtils.getEntityAsString(httpResponseEntity);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

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

	@Override
	protected Double doInBackground(String... params) {

		String stockSymbol = params[0];
		double lastTradePrice = getStockLastTradePrice(stockSymbol);

		return lastTradePrice;
	}
}
