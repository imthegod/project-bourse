package co.je.thesis.common.dtos.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;
import co.je.thesis.common.dtos.stocks.BaseStock;

public class AnalysisDTO implements Serializable {
	
	/**
	 * The user name of the person who creates the analysis.
	 */
	private String ownerUserName;
	
	/**
	 * A universal unique identifier. It is required in order to be able to identify this
	 * specific analysis
	 */
	private String uuid;
	
	private ArrayList<RuleDTO> rulesDTO;
	private ArrayList<DSLElementDTO> logicalOperatorsDTO;
	
	/**
	 * An ArrayList of BaseStock objects that represents the stocks which fulfill the rules.
	 */
	private ArrayList<BaseStock> resultStocks;
	
	/*
	 * Don't remove this, it is necessary for the rest services.
	 */
	public AnalysisDTO() {
		
		ownerUserName = "";
		uuid = getRandomUUID();
		rulesDTO = new ArrayList<RuleDTO>();
		logicalOperatorsDTO = new ArrayList<DSLElementDTO>();
		resultStocks = new ArrayList<BaseStock>();
	}
	
	public AnalysisDTO(String ownerUserName, ArrayList<RuleDTO> rulesDTO, ArrayList<DSLElementDTO> logicalOperatorsDTO) {

		this.ownerUserName = ownerUserName;
		uuid = getRandomUUID();
		this.rulesDTO = rulesDTO;
		this.logicalOperatorsDTO = logicalOperatorsDTO;
		resultStocks = new ArrayList<BaseStock>();
	}
	
	private String getRandomUUID() {
		
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();
		
		return uuidString;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}
	
	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public void addRuleDTO(RuleDTO ruleDTO) {
		
		rulesDTO.add(ruleDTO);
	}

	public ArrayList<RuleDTO> getRulesDTO() {
		return rulesDTO;
	}
	
	public void addLogicalOperator(DSLElementDTO dslElementDTO) {
		
		String category = dslElementDTO.getCategory();
		
		if (category.equalsIgnoreCase(DSLCategories.LOGICAL_OPERATOR)) {
			
			logicalOperatorsDTO.add(dslElementDTO);
		}
	}
	
	public ArrayList<DSLElementDTO> getLogicalOperatorsDTO() {
		return logicalOperatorsDTO;
	}

	public void addStockToResults(BaseStock stock) {
		
		resultStocks.add(stock);
	}
	
	public ArrayList<BaseStock> getResultStocks() {
		return resultStocks;
	}
}