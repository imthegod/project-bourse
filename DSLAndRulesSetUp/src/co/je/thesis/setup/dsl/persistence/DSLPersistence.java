package co.je.thesis.setup.dsl.persistence;

import java.util.ArrayList;

import co.je.thesis.common.dbos.dsl.DSLElementDBO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class DSLPersistence {

	public DSLPersistence() {
	}

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

	public void updateDSLVersion() {

		int dslVersion = getDSLVersion();
		dslVersion++;

		DBCollection dslVersionCollection = DBManager.getDSLVersionCollection();
		BasicDBObject dbo = new BasicDBObject("version", dslVersion);

		// Eliminates the previous version and inserts the new one.
		dslVersionCollection.drop();
		dslVersionCollection.insert(dbo);
	}

	public void updateDSLVersion(int dslVersion) {

		DBCollection dslVersionCollection = DBManager.getDSLVersionCollection();
		BasicDBObject dbo = new BasicDBObject("version", dslVersion);

		// Eliminates the previous version and inserts the new one.
		dslVersionCollection.drop();
		dslVersionCollection.insert(dbo);
	}

	public void insertDSLElementsDBOIntoCollection(String collectionName,
			ArrayList<DSLElementDBO> dslElementsDBO) {

		DBCollection dslElementCollection = DBManager.getDBCollection(collectionName);

		for (int i = 0; i < dslElementsDBO.size(); i++) {

			DSLElementDBO subjectDBO = dslElementsDBO.get(i);
			dslElementCollection.insert(subjectDBO);
		}
	}

	public void dropCollection(String collectionName) {

		DBCollection dslElementCollection = DBManager.getDBCollection(collectionName);
		dslElementCollection.drop();
	}
}