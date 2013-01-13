package co.je.thesis.server.persistence.dbos;

import java.util.ArrayList;

import co.je.thesis.common.dbos.stocks.BaseStockDBO;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

/**
 * This class models the object that handles the result of an analysis request.
 * This object can be stored into a DB.
 * 
 * @author Julian Espinel
 */
public class AnalysisResultsStorageDBO extends BasicDBObject {

	/**
	 * A the "ownerUserName" property of the object.
	 */
	public static final String OWNER_USER_NAME = "ownerUserName";
	
	/**
	 * The "uuid" property of the object.
	 */
	public static final String UUID = "uuid";
	
	/**
	 * the "resultStocksDBO" property of the object.
	 * This constant allows us to access a list that contains the result stocks.
	 */
	public static final String RESULT_STOCKS_DBO = "resultStocksDBO";

	public AnalysisResultsStorageDBO() {
	}
	
	/**
	 * AnalysisResultsStorageDBO constructor.
	 * 
	 * @param ownerUserName the user name of the investor who creates the analysis request.
	 * @param uuid the UUID of the analysis request.
	 * @param resultStocksDBO an ArrayList that contains the stocks that fulfills all the 
	 * 		  analysis request rules.
	 */
	public AnalysisResultsStorageDBO(String ownerUserName, String uuid,
			ArrayList<BaseStockDBO> resultStocksDBO) {
		
		put(OWNER_USER_NAME, ownerUserName);
		put(UUID, uuid);
		
		BasicDBList stocksDBO = new BasicDBList();
		stocksDBO.addAll(resultStocksDBO);
		put(RESULT_STOCKS_DBO, stocksDBO);
	}
	
	/**
	 * Returns the user name of the investor who creates the analysis request.
	 * 
	 * @return the user name of the investor who creates the analysis request.
	 */
	public String getOwnerUserName() {
		
		String ownerUserName = getString(OWNER_USER_NAME);
		return ownerUserName;
	}
	
	/**
	 * Returns the UUID of the analysis request.
	 * 
	 * @return the UUID of the analysis request.
	 */
	public String getUUID() {
		
		String uuid = getString(UUID);
		return uuid;
	}
	
	/**
	 * Returns an ArrayList that contains the stocks that fulfills all the 
	 * analysis request rules.
	 * 
	 * @return an ArrayList that contains the stocks that fulfills all the 
	 * 		   analysis request rules.
	 */
	public BasicDBList getResultStocksDBO() {
		
		BasicDBList resultStocksDBO = (BasicDBList) this.get(RESULT_STOCKS_DBO);
		return resultStocksDBO;
	}
	
	/**
	 * Returns the object that allow us to query and retrieve a the results of an 
	 * analysis request from the DB.
	 * 
	 * @param ownerUserName the user name of the investor who creates the analysis request.
	 * @param uuid the UUID of the analysis request.
	 * @return the object that allow us to query and retrieve a the results of an 
	 * 		   analysis request from the DB.
	 */
	public static AnalysisResultsStorageDBO createQueryDBO(String ownerUserName, String uuid) {
		
		AnalysisResultsStorageDBO analysisResultsStorageDBO = new AnalysisResultsStorageDBO();
		analysisResultsStorageDBO.put(OWNER_USER_NAME, ownerUserName);
		analysisResultsStorageDBO.put(UUID, uuid);
		
		return analysisResultsStorageDBO;
	}
}