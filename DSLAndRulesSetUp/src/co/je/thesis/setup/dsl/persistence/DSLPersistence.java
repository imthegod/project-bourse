package co.je.thesis.setup.dsl.persistence;

import java.util.ArrayList;

import co.je.thesis.common.dbos.dsl.DSLElementDBO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

/**
 * Class that persists the information related with the DSL.
 * 
 * @author Julian Espinel
 */
public class DSLPersistence {

	/**
	 * Constructor
	 */
	public DSLPersistence() {
	}

	/**
	 * Returns the current DSL version.
	 * 
	 * @return the current DSL version.
	 */
	public int getDSLVersion() {

		DBCollection dslVersionCollection = DBManager.getDSLVersionCollection();
		BasicDBObject dbo = (BasicDBObject) dslVersionCollection.findOne();

		int dslVersion = 0;

		try {
			dslVersion = dbo.getInt("version");
		} catch (Exception e) {
			// returns 0 because it is the first DSL version.
		}

		return dslVersion;
	}

	/**
	 * Increments the DSL version by 1.
	 */
	public void updateDSLVersion() {

		int dslVersion = getDSLVersion();
		dslVersion++;

		DBCollection dslVersionCollection = DBManager.getDSLVersionCollection();
		BasicDBObject dbo = new BasicDBObject("version", dslVersion);

		// Eliminates the previous version and inserts the new one.
		dslVersionCollection.drop();
		dslVersionCollection.insert(dbo);
	}

	/**
	 * Updates the DSL version.
	 * 
	 * @param dslVersion the new version of the DSL.
	 */
	public void updateDSLVersion(int dslVersion) {

		DBCollection dslVersionCollection = DBManager.getDSLVersionCollection();
		BasicDBObject dbo = new BasicDBObject("version", dslVersion);

		// Eliminates the previous version and inserts the new one.
		dslVersionCollection.drop();
		dslVersionCollection.insert(dbo);
	}

	/**
	 * Insert DSL elements into a specific collection. 
	 * 
	 * @param collectionName the name of the collection in which the DSL elements will be inserted.
	 * @param dslElementsDBO the DSL elements to be inserted into the specific collection.
	 */
	public void insertDSLElementsDBOIntoCollection(String collectionName,
			ArrayList<DSLElementDBO> dslElementsDBO) {

		DBCollection dslElementCollection = DBManager.getDBCollection(collectionName);

		for (int i = 0; i < dslElementsDBO.size(); i++) {

			DSLElementDBO subjectDBO = dslElementsDBO.get(i);
			dslElementCollection.insert(subjectDBO);
		}
	}

	/**
	 * Deletes the collection with the given name.
	 * 
	 * @param collectionName the name of the collection to delete.
	 */
	public void dropCollection(String collectionName) {

		DBCollection dslElementCollection = DBManager.getDBCollection(collectionName);
		dslElementCollection.drop();
	}
}