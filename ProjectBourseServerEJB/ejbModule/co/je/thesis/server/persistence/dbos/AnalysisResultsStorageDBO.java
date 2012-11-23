package co.je.thesis.server.persistence.dbos;

import java.util.ArrayList;

import co.je.thesis.common.dbos.stocks.BaseStockDBO;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class AnalysisResultsStorageDBO extends BasicDBObject {

	public static final String OWNER_USER_NAME = "ownerUserName";
	public static final String UUID = "uuid";
	public static final String RESULT_STOCKS_DBO = "resultStocksDBO";

	public AnalysisResultsStorageDBO() {
	}
	
	public AnalysisResultsStorageDBO(String ownerUserName, String uuid,
			ArrayList<BaseStockDBO> resultStocksDBO) {
		
		put(OWNER_USER_NAME, ownerUserName);
		put(UUID, uuid);
		
		BasicDBList stocksDBO = new BasicDBList();
		stocksDBO.addAll(resultStocksDBO);
		put(RESULT_STOCKS_DBO, stocksDBO);
	}
	
	public String getOwnerUserName() {
		
		String ownerUserName = getString(OWNER_USER_NAME);
		return ownerUserName;
	}
	
	public String getUUID() {
		
		String uuid = getString(UUID);
		return uuid;
	}
	
	public BasicDBList getResultStocksDBO() {
		
		BasicDBList resultStocksDBO = (BasicDBList) this.get(RESULT_STOCKS_DBO);
		return resultStocksDBO;
	}
	
	public static AnalysisResultsStorageDBO createQueryDBO(String ownerUserName, String uuid) {
		
		AnalysisResultsStorageDBO analysisResultsStorageDBO = new AnalysisResultsStorageDBO();
		analysisResultsStorageDBO.put(OWNER_USER_NAME, ownerUserName);
		analysisResultsStorageDBO.put(UUID, uuid);
		
		return analysisResultsStorageDBO;
	}
}