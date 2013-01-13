package co.je.thesis.server.domain.dsl;

import java.util.ArrayList;

import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.verifiers.AnalysisVerifier;

/**
 * This class knows how to verify DSL related aspects.
 * 
 * @author Julian Espinel
 */
public class DSLVerifier {
	
	/**
	 * This attribute knows how to verify the structure and rules of an analysis request.
	 */
	private AnalysisVerifier analysisVerifier;
	
	public DSLVerifier(ArrayList<ValidRule> validRules) {
		
		analysisVerifier = new AnalysisVerifier(validRules);
	}
	
	/**
	 * Verifies that the analysisDTO parameter is a valid analysis.
	 * This means that it has a valid structure, and all its rules
	 * are also valid.
	 * 
	 * @param analysisDTO, the analysis object which will be verified.
	 */
	public void verifyAnalysis(AnalysisDTO analysisDTO) {
		
		analysisVerifier.verifyAnalysisIsValid(analysisDTO);
	}
}
