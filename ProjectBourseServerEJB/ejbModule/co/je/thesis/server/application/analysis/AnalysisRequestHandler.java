package co.je.thesis.server.application.analysis;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.server.domain.analysis.AnalysisCentre;

/**
 * This class represents an EJB that knows how to retrieve and pass the results of a given
 * analysis request to a client.
 * 
 * @author Julian Espinel
 */
@Stateless
@LocalBean
public class AnalysisRequestHandler {
	
	/**
	 * Default constructor.
	 */
	public AnalysisRequestHandler() {
	}
	
	/**
	 * Returns the results of a specific analysis request. The analysis request is identified
	 * by its UUID and the username of the investor who created that analysis request.
	 * 
	 * @param ownerUserName the username of the investor who created the analysis.
	 * @param uuid the UUID of the analysis we are looking for.
	 * @return the results of a specific analysis request.
	 */
	public AnalysisResultsStorageDTO getAnalysisResults(String ownerUserName, String uuid) {
		
		System.out.println("EJB: AnalysisRequestHandler: getAnalysisResults()");
		
		AnalysisCentre analysisCentre = new AnalysisCentre();
		AnalysisResultsStorageDTO analysisResultsStorageDTO = analysisCentre.getAnalysisResults(ownerUserName, uuid);
		
		return analysisResultsStorageDTO;
	}
}