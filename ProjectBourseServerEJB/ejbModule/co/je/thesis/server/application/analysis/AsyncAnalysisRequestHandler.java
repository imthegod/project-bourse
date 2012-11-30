package co.je.thesis.server.application.analysis;

import java.util.ArrayList;

import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.server.domain.analysis.AnalysisCentre;
import co.je.thesis.server.persistence.analysis.AnalysisPersistence;

public class AsyncAnalysisRequestHandler implements Runnable {
	
	private AnalysisDTO analysisDTO;
	
//	private void printAnalysisDTO() {
//		
//		ArrayList<RuleDTO> rulesDTO = analysisDTO.getRulesDTO();
//		ArrayList<DSLElementDTO> logicalOperatorsDTO = analysisDTO.getLogicalOperatorsDTO();
//		
//		int numberOfRules = rulesDTO.size();
//		System.out.println("numberOfRules: " + numberOfRules);
//		
//		for (int i = 0; i < numberOfRules; i++) {
//			
//			System.out.println("Rule number: " + i);
//			
//			RuleDTO ruleDTO = rulesDTO.get(i);
//			ArrayList<DSLElementDTO> ruleElements = ruleDTO.getElements();
//			int numberOfRuleElements = ruleElements.size();
//			System.out.println("numberOfRuleElements: " + numberOfRuleElements);
//			
//			for (int j = 0; j < numberOfRuleElements; j++) {
//				
//				DSLElementDTO ruleElement = ruleElements.get(j);
//				System.out.println("j: " + j + ", cat: " + ruleElement.getCategory()
//						+ ", val: " + ruleElement.getValue());
//			}
//		}
//		
//		int numberOfLogicalOperators = logicalOperatorsDTO.size();
//		System.out.println("numberOfLogicalOperators: " + numberOfLogicalOperators);
//		
//		for (int i = 0; i < numberOfLogicalOperators; i++) {
//			
//			System.out.println("logical operator number: " + i);
//			
//			DSLElementDTO logicalOperator = logicalOperatorsDTO.get(i);
//			System.out.println("i: " + i + ", cat: " + logicalOperator.getCategory()
//					+ ", val: " + logicalOperator.getValue());
//		}
//	}
	
	public AsyncAnalysisRequestHandler(AnalysisDTO analysisDTO) {
		
		this.analysisDTO = analysisDTO;
	}
	
	private void executeAnalysisAndSaveResultsIntoDB() {
		
		System.out.println("Runnable: AsyncAnalysisRequestHandler: executeAnalysisAndSaveResultsIntoDB()");
		
		long initialTime = System.currentTimeMillis();
		
		AnalysisCentre analysisCentre = new AnalysisCentre();
		ArrayList<BaseStock> resultStocks = analysisCentre.getAnalysisResultStocks(analysisDTO);
		
		//System.out.println("AsyncAnalysisRequestHandler: resultStocks.size(): " + resultStocks.size());
		
		String ownerUserName = analysisDTO.getOwnerUserName();
		String uuid = analysisDTO.getUuid();
		
		AnalysisResultsStorageDTO analysisResultsStorage = new AnalysisResultsStorageDTO(ownerUserName, uuid, resultStocks);
		AnalysisPersistence analysisPersistence = new AnalysisPersistence();
		analysisPersistence.saveAnalysisResults(analysisResultsStorage);
		
		long finalTime = System.currentTimeMillis();
		System.out.println("TE: " + (finalTime - initialTime) + " RS: " + (resultStocks.size()));
	}
	
	@Override
	public void run() {
		
		executeAnalysisAndSaveResultsIntoDB();
	}
}