package co.je.thesis.common.dtos.analysis;

import java.util.ArrayList;

import co.je.thesis.common.dtos.stocks.BaseStock;

/**
 * This class works as a structure to store a result generated after process an analysys request.
 * 
 * @author Julian Espinel
 */
public class AnalysisResultsStorageDTO {
	
	/**
	 * The user name of the investor who creates the analysis.
	 */
	private String ownerUserName;

	/**
	 * The universal unique identifier of the analysis request
	 */
	private String uuid;

	/**
	 * The stocks that fulfill all the rules described by the investor through the analysis request.
	 * The results generated after the analysis request has been processed.
	 */
	private ArrayList<BaseStock> resultStocks;

	/**
	 * Constructor with parameters.
	 * 
	 * @param ownerUserName the user name of the investor who creates the analysis.
	 * @param uuid the universal unique identifier of the analysis request
	 * @param resultStocks the results generated after the analysis request has been processed.
	 */
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