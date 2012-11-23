package co.je.thesis.setup.rules.persistence;

import java.net.UnknownHostException;

import co.je.thesis.common.constants.RulesCollectionNames;
import co.je.thesis.common.dbos.dsl.DSLElementDBO;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class DBManager {
	
public static final String DB_NAME = "project_bourse_db";
	
	private static Mongo mongoConnection;
	private static DB projectBourseDB;
	
	private DBManager() {
	}
	
	private static void initializeDBInstance() {
		
		if (projectBourseDB == null) {
			
			try {
				
				mongoConnection = new Mongo();
				projectBourseDB = mongoConnection.getDB(DB_NAME);
				
			} catch (UnknownHostException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public static DBCollection getValidRulesVersionCollection() {
		
		initializeDBInstance();
		String collectionName = RulesCollectionNames.VALID_RULES_VERSION_COLLECTION_NAME;
		DBCollection dslVersionCollection = projectBourseDB.getCollection(collectionName);
		dslVersionCollection.setObjectClass(BasicDBObject.class);
		
		return dslVersionCollection;
	}
	
	public static DBCollection getDBCollection(String collectionName) {
		
		initializeDBInstance();
		DBCollection anyCollection = projectBourseDB.getCollection(collectionName);
		anyCollection.setObjectClass(DSLElementDBO.class);
		
		return anyCollection;
	}
}