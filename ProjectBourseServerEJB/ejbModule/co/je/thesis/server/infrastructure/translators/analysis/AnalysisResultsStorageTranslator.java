package co.je.thesis.server.infrastructure.translators.analysis;

import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import co.je.thesis.common.dbos.stocks.BaseStockDBO;
import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.server.infrastructure.translators.stocks.BaseStockTranslator;
import co.je.thesis.server.persistence.dbos.AnalysisResultsStorageDBO;

public class AnalysisResultsStorageTranslator {

	public AnalysisResultsStorageDTO toDomainObject(AnalysisResultsStorageDBO analysisResultsStorageDBO) {

		String ownerUserName = analysisResultsStorageDBO.getOwnerUserName();
		String uuid = analysisResultsStorageDBO.getUUID();

		BasicDBList resultStocksDBO = analysisResultsStorageDBO.getResultStocksDBO();
		ArrayList<BaseStock> resultStocks = new ArrayList<BaseStock>();

		BaseStockTranslator translator = new BaseStockTranslator();

		for (int i = 0; i < resultStocksDBO.size(); i++) {

			BasicDBObject basicBaseStock = (BasicDBObject) resultStocksDBO.get(i);
			BaseStockDBO baseStockDBO = translator.translateToDBO(basicBaseStock);
			BaseStock baseStock = translator.translateToDomainObject(baseStockDBO);

			resultStocks.add(baseStock);
		}

		AnalysisResultsStorageDTO analysisResultsStorageDTO = new AnalysisResultsStorageDTO(ownerUserName, uuid, resultStocks);

		return analysisResultsStorageDTO;
	}

	public AnalysisResultsStorageDBO toDBO(AnalysisResultsStorageDTO analysisResultsStorageDTO) {

		String ownerUserName = analysisResultsStorageDTO.getOwnerUserName();
		String uuid = analysisResultsStorageDTO.getUUID();

		ArrayList<BaseStock> resultStocks = analysisResultsStorageDTO.getResultStocks();
		ArrayList<BaseStockDBO> resultStocksDBO = new ArrayList<BaseStockDBO>();

		BaseStockTranslator translator = new BaseStockTranslator();

		for (int i = 0; i < resultStocks.size(); i++) {

			BaseStock baseStock = resultStocks.get(i);
			BaseStockDBO baseStockDBO = translator.translateToDBO(baseStock);

			resultStocksDBO.add(baseStockDBO);
		}

		AnalysisResultsStorageDBO analysisResultsStorageDBO = new AnalysisResultsStorageDBO(ownerUserName, uuid, resultStocksDBO);

		return analysisResultsStorageDBO;
	}
	
	public AnalysisResultsStorageDBO toDBO(BasicDBObject basicDBO) {
		
		String ownerUserName = basicDBO.getString(AnalysisResultsStorageDBO.OWNER_USER_NAME);
		String uuid = basicDBO.getString(AnalysisResultsStorageDBO.UUID);
		
		BasicDBList basicStocksDBO = (BasicDBList) basicDBO.get(AnalysisResultsStorageDBO.RESULT_STOCKS_DBO);
		ArrayList<BaseStockDBO> resultStocksDBO = new ArrayList<BaseStockDBO>();
		
		BaseStockTranslator translator = new BaseStockTranslator();
		
		for (int i = 0; i < basicStocksDBO.size(); i++) {
			
			BasicDBObject basicBaseStockDBO = (BasicDBObject) basicStocksDBO.get(i);
			BaseStockDBO baseStockDBO = translator.translateToDBO(basicBaseStockDBO);
			
			resultStocksDBO.add(baseStockDBO);
		}
		
		AnalysisResultsStorageDBO analysisResultsStorageDBO = new AnalysisResultsStorageDBO(ownerUserName, uuid, resultStocksDBO);

		return analysisResultsStorageDBO;
	}
}
