package co.je.thesis.server.application.analysis;

import java.util.ArrayList;

import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.server.domain.analysis.AnalysisCentre;
import co.je.thesis.server.persistence.analysis.AnalysisPersistence;

/**
 * This class knows how to receive an analysis request and execute it asynchronously.
 * 
 * @author Julian Espinel
 */
public class AsyncAnalysisRequestHandler implements Runnable {
	
	/**
	 * This attribute stored the analysis we need to execute.
	 */
	private AnalysisDTO analysisDTO;
	
	/**
	 * AsyncAnalysisRequestHandler constructor.
	 * 
	 * @param analysisDTO the analysis we need to execute.
	 */
	public AsyncAnalysisRequestHandler(AnalysisDTO analysisDTO) {
		
		this.analysisDTO = analysisDTO;
	}
	
	/**
	 * Execute the analysis request and save the results into the DB.
	 */
	private void executeAnalysisAndSaveResultsIntoDB() {
		
		System.out.println("Runnable: AsyncAnalysisRequestHandler: executeAnalysisAndSaveResultsIntoDB()");
		
		long initialTime = System.currentTimeMillis();
		
		AnalysisCentre analysisCentre = new AnalysisCentre();
		ArrayList<BaseStock> resultStocks = analysisCentre.getAnalysisResultStocks(analysisDTO);
		
		String ownerUserName = analysisDTO.getOwnerUserName();
		String uuid = analysisDTO.getUuid();
		
		AnalysisResultsStorageDTO analysisResultsStorage = new AnalysisResultsStorageDTO(ownerUserName, uuid, resultStocks);
		AnalysisPersistence analysisPersistence = new AnalysisPersistence();
		analysisPersistence.saveAnalysisResults(analysisResultsStorage);
		
		long finalTime = System.currentTimeMillis();
		System.out.println("TE: " + (finalTime - initialTime) + " RS: " + (resultStocks.size()));
	}
	
	/**
	 * Calls the executeAnalysisAndSaveResultsIntoDB method.
	 */
	@Override
	public void run() {
		
		executeAnalysisAndSaveResultsIntoDB();
	}
}