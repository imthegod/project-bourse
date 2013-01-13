package co.je.thesis.setup.dsl.persistence;

import java.net.UnknownHostException;

import co.je.thesis.common.dbos.dsl.DSLElementDBO;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * Class that knows how to connect to the DB.
 * 
 * @author Julian Espinel
 */
public class DBManager {
	
	/**
	 * The DB name.
	 */
	public static final String DB_NAME = "project_bourse_db";
	
	/**
	 * Attribute that represents the DB connection.
	 */
	private static Mongo mongoConnection;
	
	/**
	 * Attribute that represents the system's DB instance.
	 */
	private static DB projectBourseDB;
	
	/**
	 * Constructor
	 */
	private DBManager() {
	}
	
	/**
	 * This method guarantees that the connection to the DB, and the attribute that
	 * represents the system's DB are both singleton.
	 */
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
	
	/**
	 * Returns the current DSL version collection.
	 * 
	 * @return current DSL version collection.
	 */
	public static DBCollection getDSLVersionCollection() {
		
		initializeDBInstance();
		String collectionName = "dsl_version";
		DBCollection dslVersionCollection = projectBourseDB.getCollection(collectionName);
		dslVersionCollection.setObjectClass(BasicDBObject.class);
		
		return dslVersionCollection;
	}
	
	/**
	 * Returns a collection given its name.
	 * 
	 * @param collectionName the name of the collection we want to return.
	 * @return the collection with the given name.
	 */
	public static DBCollection getDBCollection(String collectionName) {
		
		initializeDBInstance();
		DBCollection anyCollection = projectBourseDB.getCollection(collectionName);
		anyCollection.setObjectClass(DSLElementDBO.class);
		
		return anyCollection;
	}
}