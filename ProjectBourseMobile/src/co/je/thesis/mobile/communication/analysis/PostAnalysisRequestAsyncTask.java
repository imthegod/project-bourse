package co.je.thesis.mobile.communication.analysis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

/**
 * This class knows how to send a new analysis request to the system's server.
 * This operation is performed asynchronously.
 * 
 * @author Julian Espinel
 */
public class PostAnalysisRequestAsyncTask extends AsyncTask<String, Void, String> {

	/**
	 * Consumes the REST service exposed by the server, that allow us to send a new analysis request.
	 * 
	 * @param url the URL where the "send a new analysis request" service is located.
	 * @param jsonAnalysisDTO the JSON representation of the analysis request we want to send.
	 */
	private void postAnalysisRequest(String url, String jsonAnalysisDTO) {
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "application/json");
			
			HttpEntity entity = new StringEntity(jsonAnalysisDTO);
			httpPost.setEntity(entity);
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			
			System.out.println("PostAnalysisRequestAsyncTask: postAnalysisRequest(): statusCode: " + statusCode);
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see http://developer.android.com/reference/android/os/AsyncTask.html#doInBackground(Params...)
	 */
	@Override
	protected String doInBackground(String... params) {
		
		String url = params[0];
		String jsonAnalysisDTO = params[1];
		
		postAnalysisRequest(url, jsonAnalysisDTO);
		
		return "";
	}
}