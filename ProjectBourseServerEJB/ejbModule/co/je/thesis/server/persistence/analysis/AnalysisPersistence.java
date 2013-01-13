package co.je.thesis.server.persistence.analysis;

import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.server.infrastructure.translators.analysis.AnalysisResultsStorageTranslator;
import co.je.thesis.server.persistence.DBManager;
import co.je.thesis.server.persistence.dbos.AnalysisResultsStorageDBO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

/**
 * This class is responsible for the persistence of the analisys requests.
 * 
 * @author Julian Espinel
 */
public class AnalysisPersistence {

	/**
	 * Saves the analysis results into the DB.
	 * 
	 * @param analysisResultsStorage the object that contains the analysis results.
	 */
	public void saveAnalysisResults(AnalysisResultsStorageDTO analysisResultsStorage) {

		AnalysisResultsStorageTranslator translator = new AnalysisResultsStorageTranslator();
		AnalysisResultsStorageDBO analysisResultsStorageDBO = translator.toDBO(analysisResultsStorage);
		DBCollection analysisResultsCollection = DBManager.getAnalysisResultsCollection();
		
		analysisResultsCollection.insert(analysisResultsStorageDBO);
	}

	/**
	 * Retrieves the results of a specific analysis. An analysis is identified by the user name
	 * of the investor that created it and the UUID of the analysis.
	 * 
	 * @param ownerUserName the user name of the investor who created the analysis.
	 * @param uuid the UUID of the analysis we are looking for.
	 * @return an AnalysisResultsStorageDTO object. This object contains the results of the
	 * 		   specified analysis.
	 */
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
	
	/**
	 * Remove the result of a specific analysis. An analysis is identified by the user name
	 * of the investor that created it and the UUID of the analysis.
	 * 
	 * @param ownerUserName the user name of the investor who created the analysis.
	 * @param uuid the UUID of the analysis for which we want to delete its results.
	 */
	public void removeAnalysisResult(String ownerUserName, String uuid) {
		
		AnalysisResultsStorageDBO queryDBO = AnalysisResultsStorageDBO.createQueryDBO(ownerUserName, uuid);
		DBCollection analysisResultsCollection = DBManager.getAnalysisResultsCollection();
		
		analysisResultsCollection.remove(queryDBO);
	}
}