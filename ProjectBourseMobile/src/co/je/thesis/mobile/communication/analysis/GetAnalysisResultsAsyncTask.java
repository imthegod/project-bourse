package co.je.thesis.mobile.communication.analysis;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.net.Uri;
import android.os.AsyncTask;
import co.je.thesis.mobile.communication.utils.CommunicationUtils;

/**
 * This class handles the logic necessary for make a GET request to the server in order to retrieve
 * the results of a specific analysis request. This operation executes asynchronously.
 * 
 * @author Julian Espinel
 */
public class GetAnalysisResultsAsyncTask extends AsyncTask<String, Void, String> {
	
	/**
	 * Consumes the REST services exposed by the server, that allow us to retrieve the results of a
	 * specific analysis request.
	 * 
	 * @param url the URL where the "retrieve the result of a specific analysis" service is located.
	 * @param ownerUserName the user name of the investor that created the analysis request.
	 * @param uuid the id of the analysis from which we want to retrieve the results.
	 * @return A string which contains the JSON representation of the expected result. The expected 
	 * 		   result is an object of the class AnalysisResultsStorageDTO.
	 */
	private String getAnalysisResults(String url, String ownerUserName, String uuid) {
		
		String userNameParam = "username";
		String uuidParam = "uuid";
		
		Uri uri = new Uri.Builder()
						// Must use encoded path, otherwise it will encode the path with utf-8
						.encodedPath(url) 
						.appendQueryParameter(userNameParam, ownerUserName)
						.appendQueryParameter(uuidParam, uuid)
						.build();
		
		String uriString = uri.toString();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(uriString);
		getRequest.addHeader("Content-Type", "application/json");

		String result = "";

		try {
			
			HttpResponse httpResponse = httpClient.execute(getRequest);
			HttpEntity httpResponseEntity = httpResponse.getEntity();
			
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			System.out.println("GetAnalysisResultsAsyncTask: getAnalysisResults(): statusCode: " + statusCode);
			
			result = CommunicationUtils.getEntityAsString(httpResponseEntity);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Calls the getAnalysisResults method and returns the results of it.
	 * 
	 * @see http://developer.android.com/reference/android/os/AsyncTask.html#doInBackground(Params...)
	 */
	@Override
	protected String doInBackground(String... params) {

		String uri = params[0];
		String ownerUserName = params[1];
		String uuid = params[2];

		String result = getAnalysisResults(uri, ownerUserName, uuid);

		return result;
	}
}