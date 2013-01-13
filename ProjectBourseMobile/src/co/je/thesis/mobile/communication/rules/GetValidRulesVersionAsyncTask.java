package co.je.thesis.mobile.communication.rules;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import co.je.thesis.mobile.communication.utils.CommunicationUtils;

/**
 * This class handles the logic necessary for make a GET request to the server in order to retrieve
 * the valid rules version. This operation executes asynchronously.
 * 
 * @author Julian Espinel
 */
public class GetValidRulesVersionAsyncTask extends AsyncTask<String, Void, String> {
	
	/**
	 * Consumes the REST services exposed by the server, that allow us to retrieve the valid rules 
	 * version.
	 * 
	 * @param uri, the URI to access the valid rules version.
	 * @return a String object which contains the current valid rules version.
	 */
	private String getValidRulesVersion(String uri) {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(uri);
		getRequest.addHeader("Content-Type", "application/json");
		
		String jsonValidRulesVersion = "";
		
		try {
			
			HttpResponse httpResponse = httpClient.execute(getRequest);
			HttpEntity httpResponseEntity = httpResponse.getEntity();
			jsonValidRulesVersion = CommunicationUtils.getEntityAsString(httpResponseEntity);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonValidRulesVersion;
	}

	/**
	 * Calls the getValidRulesVersion method and returns the results of it.
	 * 
	 * @see http://developer.android.com/reference/android/os/AsyncTask.html#doInBackground(Params...)
	 */
	@Override
	protected String doInBackground(String... params) {
		
		String uri = params[0];
		String jsonValidRulesVersion = getValidRulesVersion(uri);
		
		return jsonValidRulesVersion;
	}
}