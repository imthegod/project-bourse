package co.je.thesis.mobile.communication.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import co.je.thesis.mobile.communication.constants.CommunicationsConstants;

/**
 * Class that knows how to make a fast request to the system's server in order to test
 * if the server is alive.
 * 
 * @author Julian Espinel
 */
public class TestServerIsAliveAsyncTask extends AsyncTask<String, Void, Boolean> {
	
	/**
	 * Constant of the URL where the "test server availability" REST service is located.
	 */
	public static final String TEST_SERVER_IS_ALIVE_URL = "/test/status";
	
	/**
	 * Tests if the system's server is alive.
	 * 
	 * @return if the system's server is available then returns true, else returns false.
	 */
	private Boolean isServerAlive() {
		
		String url = CommunicationsConstants.BASE_URL + TEST_SERVER_IS_ALIVE_URL;
		
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		
		HttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpGet getRequest = new HttpGet(url);
		getRequest.addHeader("Content-Type", "application/json");

		boolean isAlive = false;

		try {
			
			HttpResponse httpResponse = httpClient.execute(getRequest);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			
			if (statusCode == 200) {
				
				isAlive = true;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return isAlive;
	}

	/**
	 * Calls the isServerAlive method and returns the results of it.
	 * 
	 * @see http://developer.android.com/reference/android/os/AsyncTask.html#doInBackground(Params...)
	 */
	@Override
	protected Boolean doInBackground(String... params) {

		boolean isAlive = isServerAlive();
		return isAlive;
	}
}