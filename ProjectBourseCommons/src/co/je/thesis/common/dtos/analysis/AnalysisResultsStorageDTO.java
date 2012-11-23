package co.je.thesis.common.dtos.analysis;

import java.util.ArrayList;

import co.je.thesis.common.dtos.stocks.BaseStock;

public class AnalysisResultsStorageDTO {
	
	/**
	 * The user name of the person who creates the analysis.
	 */
	private String ownerUserName;

	/**
	 * The universal unique identifier of the analysis request
	 */
	private String uuid;

	private ArrayList<BaseStock> resultStocks;

	public AnalysisResultsStorageDTO(String ownerUserName, String uuid,
			ArrayList<BaseStock> resultStocks) {

		this.ownerUserName = ownerUserName;
		this.uuid = uuid;
		this.resultStocks = resultStocks;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	public String getUUID() {
		return uuid;
	}

	public ArrayList<BaseStock> getResultStocks() {
		return resultStocks;
	}
}