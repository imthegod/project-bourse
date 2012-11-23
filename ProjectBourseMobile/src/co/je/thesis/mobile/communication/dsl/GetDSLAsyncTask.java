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

public class GetDSLAsyncTask extends AsyncTask<String, Void, String> {
	
	private String getDSLDataTransferObject(String url) {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);
		getRequest.addHeader("Content-Type", "application/json");
		
		String jsonDSL = "";
		
		try {
			
			HttpResponse httpResponse = httpClient.execute(getRequest);
			HttpEntity httpResponseEntity = httpResponse.getEntity();
			jsonDSL = CommunicationUtils.getEntityAsString(httpResponseEntity);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonDSL;
	}

	@Override
	protected String doInBackground(String... params) {
		
		String uri = params[0];
		String jsonDSL = getDSLDataTransferObject(uri);
		
		return jsonDSL;
	}
}