package co.je.thesis.mobile.communication.analysis;

import java.util.concurrent.ExecutionException;

import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.mobile.communication.constants.CommunicationsConstants;

import com.google.gson.Gson;

/**
 * This class contains the logic necessary to consume the analysis REST services, 
 * exposed by the server.
 * 
 * @author Julian Espinel
 */
public class AnalysisServicesConsumer {

	/**
	 * Path that points to the analysis services.
	 */
	public static final String ANALYSIS_SERVICES_URL = "/analysis";
	
	/**
	 * Retrieves the results of a specific analysis from the system's server.
	 * 
	 * @param ownerUserName the user name of the investor that created the analysis request.
	 * @param uuid the id of the analysis from which we want to retrieve the results.
	 * @return the results of the specified analysis.
	 */
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

	/**
	 * Sends an analysis request created by the investor to the system's server.
	 * 
	 * @param analysisDTO a data transfer objects which represents the analysis request we want to send.
	 */
	public void sendAnalysisRequest(AnalysisDTO analysisDTO) {

		String url = CommunicationsConstants.BASE_URL + ANALYSIS_SERVICES_URL;

		Gson gson = new Gson();
		String jsonAnalysisDTO = gson.toJson(analysisDTO);

		String[] params = { url, jsonAnalysisDTO };
		PostAnalysisRequestAsyncTask postAnalysisRequestAsyncTask = new PostAnalysisRequestAsyncTask();
		postAnalysisRequestAsyncTask.execute(params);
	}
}