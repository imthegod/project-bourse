package co.je.thesis.server.application.analysis;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.server.domain.analysis.AnalysisCentre;

/**
 * Session Bean implementation class AnalysisRequestHandler
 */
@Stateless
@LocalBean
public class AnalysisRequestHandler {
	
	/**
	 * Default constructor.
	 */
	public AnalysisRequestHandler() {
	}
	
	public AnalysisResultsStorageDTO getAnalysisResults(String ownerUserName, String uuid) {
		
		System.out.println("EJB: AnalysisRequestHandler: getAnalysisResults()");
		
		AnalysisCentre analysisCentre = new AnalysisCentre();
		AnalysisResultsStorageDTO analysisResultsStorageDTO = analysisCentre.getAnalysisResults(ownerUserName, uuid);
		
		return analysisResultsStorageDTO;
	}
}