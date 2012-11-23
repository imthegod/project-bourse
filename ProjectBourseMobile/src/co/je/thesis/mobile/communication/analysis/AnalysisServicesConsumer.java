package co.je.thesis.mobile.communication.analysis;

import java.util.concurrent.ExecutionException;

import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.mobile.communication.constants.CommunicationsConstants;

import com.google.gson.Gson;

public class AnalysisServicesConsumer {

	public static final String ANALYSIS_SERVICES_URL = "/analysis";
	
	public AnalysisResultsStorageDTO getAnalysisResults(String ownerUserName, String uuid) {

		String url = CommunicationsConstants.BASE_URL + ANALYSIS_SERVICES_URL;

		String[] params = { url, ownerUserName, uuid };

		GetAnalysisResultsAsyncTask getAnalysisResultsAsyncTask = new GetAnalysisResultsAsyncTask();
		getAnalysisResultsAsyncTask.execute(params);

		String jsonAnalysisResultsStorage = "";
		AnalysisResultsStorageDTO analysisResultsStorageDTO = null;

		try {
			jsonAnalysisResultsStorage = getAnalysisResultsAsyncTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if (!jsonAnalysisResultsStorage.equalsIgnoreCase("")) {
			
			Gson gson = new Gson();
			analysisResultsStorageDTO = gson.fromJson(jsonAnalysisResultsStorage,
					AnalysisResultsStorageDTO.class);
		}

		return analysisResultsStorageDTO;
	}

	public void sendAnalysisRequest(AnalysisDTO analysisDTO) {

		String url = CommunicationsConstants.BASE_URL + ANALYSIS_SERVICES_URL;

		Gson gson = new Gson();
		String jsonAnalysisDTO = gson.toJson(analysisDTO);

		String[] params = { url, jsonAnalysisDTO };
		PostAnalysisRequestAsyncTask postAnalysisRequestAsyncTask = new PostAnalysisRequestAsyncTask();
		postAnalysisRequestAsyncTask.execute(params);
	}
}