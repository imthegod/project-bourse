package co.je.thesis.mobile.communication.dsl;

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
 * This class handles the logic necessary for make a GET petition to the server in order to retrieve
 * the DSL version. This operation executes asynchronously.
 * 
 * @author Julian Espinel
 */
public class GetDSLVersionAsyncTask extends AsyncTask<String, Void, String> {
	
	/**
	 * Consumes the REST services exposed by the server, that allow us to retrieve 
	 * the current DSL version.
	 * 
	 * @param uri, the URI where the current DSL version is located.
	 * @return a String object which contains the current DSL version.
	 */
	private String getDSLVersion(String uri) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(uri);
		getRequest.addHeader("Content-Type", "application/json");

		String jsonDSLVersion = "";

		try {

			HttpResponse httpResponse = httpClient.execute(getRequest);
			HttpEntity httpResponseEntity = httpResponse.getEntity();
			jsonDSLVersion = CommunicationUtils.getEntityAsString(httpResponseEntity);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonDSLVersion;
	}

	/**
	 * Calls the getDSLVersion method and returns the results of it.
	 * 
	 * @see http://developer.android.com/reference/android/os/AsyncTask.html#doInBackground(Params...)
	 */
	@Override
	protected String doInBackground(String... params) {

		String uri = params[0];
		String jsonDSLVersion = getDSLVersion(uri);

		return jsonDSLVersion;
	}
}