package co.je.thesis.server.domain.analysis;

import java.util.ArrayList;

import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.server.persistence.analysis.AnalysisPersistence;

public class AnalysisCentre extends Thread {

	public AnalysisCentre() {
	}

	public ArrayList<BaseStock> getAnalysisResultStocks(AnalysisDTO analysisDTO) {

		AnalysisHandler analysisHandler = new AnalysisHandler();
		ArrayList<BaseStock> resultStocks = analysisHandler.executeAnalysis(analysisDTO);

		return resultStocks;
	}

	public AnalysisResultsStorageDTO getAnalysisResults(String ownerUserName, String uuid) {
		
		AnalysisPersistence analysisPersistence = new AnalysisPersistence();
		AnalysisResultsStorageDTO analysisResultsStorageDTO = analysisPersistence.getAnalysisResults(ownerUserName, uuid);
		analysisPersistence.removeAnalysisResult(ownerUserName, uuid);
		
		return analysisResultsStorageDTO;
	}
}