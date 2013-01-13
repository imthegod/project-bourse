package co.je.thesis.server.domain.analysis;

import java.util.ArrayList;

import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.server.persistence.analysis.AnalysisPersistence;

/**
 * This class encapsulates the logic necessary in order to support the functionalities related
 * with analisys requests.
 * 
 * @author Julian Espinel
 */
public class AnalysisCentre extends Thread {

	/**
	 * AnalysisCentre constructor.
	 */
	public AnalysisCentre() {
	}

	/**
	 * Returns the stocks the system gives as a result of the analysis execution.
	 * 
	 * @param analysisDTO the analysis to be executed.
	 * @return an ArrayList that contains the stocks the system gives as a result 
	 * 		   of the analysis execution.
	 */
	public ArrayList<BaseStock> getAnalysisResultStocks(AnalysisDTO analysisDTO) {

		AnalysisHandler analysisHandler = new AnalysisHandler();
		ArrayList<BaseStock> resultStocks = analysisHandler.executeAnalysis(analysisDTO);

		return resultStocks;
	}

	/**
	 * Returns the object that stores the analysis results.
	 * 
	 * @param ownerUserName the user name of the investor which created the analysis.
	 * @param uuid the UUID of the analysis we are looking for.
	 * @return the object that stores the analysis results.
	 */
	public AnalysisResultsStorageDTO getAnalysisResults(String ownerUserName, String uuid) {
		
		AnalysisPersistence analysisPersistence = new AnalysisPersistence();
		AnalysisResultsStorageDTO analysisResultsStorageDTO = analysisPersistence.getAnalysisResults(ownerUserName, uuid);
		analysisPersistence.removeAnalysisResult(ownerUserName, uuid);
		
		return analysisResultsStorageDTO;
	}
}