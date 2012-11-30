package co.je.thesis.server.persistence.analysis;

import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.server.infrastructure.translators.analysis.AnalysisResultsStorageTranslator;
import co.je.thesis.server.persistence.DBManager;
import co.je.thesis.server.persistence.dbos.AnalysisResultsStorageDBO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class AnalysisPersistence {

	public void saveAnalysisResults(AnalysisResultsStorageDTO analysisResultsStorage) {

		AnalysisResultsStorageTranslator translator = new AnalysisResultsStorageTranslator();
		AnalysisResultsStorageDBO analysisResultsStorageDBO = translator.toDBO(analysisResultsStorage);
		DBCollection analysisResultsCollection = DBManager.getAnalysisResultsCollection();
		
		analysisResultsCollection.insert(analysisResultsStorageDBO);
	}

	public AnalysisResultsStorageDTO getAnalysisResults(String ownerUserName, String uuid) {

		AnalysisResultsStorageDBO queryDBO = AnalysisResultsStorageDBO.createQueryDBO(ownerUserName, uuid);
		DBCollection analysisResultsCollection = DBManager.getAnalysisResultsCollection();

		BasicDBObject basicDBO = (BasicDBObject) analysisResultsCollection.findOne(queryDBO);
		
		if (basicDBO != null) {
			
			AnalysisResultsStorageTranslator translator = new AnalysisResultsStorageTranslator();
			AnalysisResultsStorageDBO analysisResultsStorageDBO = translator.toDBO(basicDBO);
			AnalysisResultsStorageDTO analysisResultsStorageDTO = translator.toDomainObject(analysisResultsStorageDBO);
			
			return analysisResultsStorageDTO;
			
		} else {
			
			String exceptionMessage = "It does not exist an analysis result with owner: " + ownerUserName + ", or uuid: " + uuid;
			throw new IllegalArgumentException(exceptionMessage);
		}
	}
	
	public void removeAnalysisResult(String ownerUserName, String uuid) {
		
		AnalysisResultsStorageDBO queryDBO = AnalysisResultsStorageDBO.createQueryDBO(ownerUserName, uuid);
		DBCollection analysisResultsCollection = DBManager.getAnalysisResultsCollection();
		
		analysisResultsCollection.remove(queryDBO);
	}
}
