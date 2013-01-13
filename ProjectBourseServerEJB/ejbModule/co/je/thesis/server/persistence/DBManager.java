package co.je.thesis.server.persistence;

import java.net.UnknownHostException;

import co.je.thesis.common.constants.DSLCollectionNames;
import co.je.thesis.common.constants.RulesCollectionNames;
import co.je.thesis.common.dbos.dsl.DSLElementDBO;
import co.je.thesis.common.dbos.rules.RuleDBO;
import co.je.thesis.common.dbos.stocks.BaseStockDBO;
import co.je.thesis.common.dbos.stocks.HistoricalStockDBO;
import co.je.thesis.server.persistence.dbos.AnalysisResultsStorageDBO;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * Class which establishes the connection and handles the interactions with the database.
 * 
 * @author Julian Espinel
 */
public class DBManager {

	/**
	 * The DB name.
	 */
	public static final String DB_NAME = "project_bourse_db";
	
	/**
	 * The name of the collection in which the base stocks are stored.
	 */
	public static final String BASE_STOCKS_COLLECTION = "base_stocks";
	
	/**
	 * The name of the collection in which the analysis results are stored.
	 */
	public static final String ANALYSIS_RESULTS_COLLECTION = "analysis_results";

	/**
	 * Attribute that represents the DB connection.
	 */
	private static Mongo mongoInstance;
	
	/**
	 * Attribute that represents the system's DB instance.
	 */
	private static DB projectBourseDBInstance;

	/**
	 * DBManager constructor.
	 */
	private DBManager() {
	}

	/**
	 * Returns the unique instance of the system's DB.
	 * 
	 * @return the unique instance of the system's DB.
	 */
	private static DB getProjectBourseInstance() {

		if (mongoInstance == null) {

			try {
				
//				MongoOptions mongoOptions = new MongoOptions();
//				mongoOptions.setConnectionsPerHost(100);
//				mongoOptions.setThreadsAllowedToBlockForConnectionMultiplier(100);
//				mongoInstance = new Mongo("localhost", mongoOptions);
				
				mongoInstance = new Mongo();

			} catch (UnknownHostException e) {

				e.printStackTrace();
			}
		}

		if (projectBourseDBInstance == null) {

			projectBourseDBInstance = mongoInstance.getDB(DB_NAME);
		}

		return projectBourseDBInstance;
	}

	// -----------------------------------------------------------------------------------
	// DSL
	// -----------------------------------------------------------------------------------

	/**
	 * Returns the collection in which the DSL's version number is stored.
	 * 
	 * @return the collection in which the DSL's version number is stored.
	 */
	public static DBCollection getDSLVersionCollection() {

		String collectionName = DSLCollectionNames.VERSION_COLLECTION_NAME;
		DBCollection dslVersionCollection = getProjectBourseInstance()
				.getCollection(collectionName);
		dslVersionCollection.setObjectClass(BasicDBObject.class);

		return dslVersionCollection;
	}

	/**
	 * Returns any collection related with the system's DSL.
	 * 
	 * @param collectionName the name of the collection we are looking for.
	 * @return any collection related with the system's DSL.
	 */
	public static DBCollection getDSLCollection(String collectionName) {

		DBCollection dslCollection = getProjectBourseInstance().getCollection(collectionName);
		dslCollection.setObjectClass(DSLElementDBO.class);

		return dslCollection;
	}

	// -----------------------------------------------------------------------------------
	// Rules
	// -----------------------------------------------------------------------------------

	/**
	 * Returns the collection in which the valid rules version number is stored.
	 * 
	 * @return the collection in which the valid rules version number is stored.
	 */
	public static DBCollection getValidRulesVersionCollection() {

		String collectionName = RulesCollectionNames.VALID_RULES_VERSION_COLLECTION_NAME;
		DBCollection dslVersionCollection = getProjectBourseInstance()
				.getCollection(collectionName);
		dslVersionCollection.setObjectClass(BasicDBObject.class);

		return dslVersionCollection;
	}

	/**
	 * Returns the collection in which the valid rules are stored.
	 * 
	 * @return the collection in which the valid rules are stored.
	 */
	public static DBCollection getValidRulesCollection() {

		String collectionName = RulesCollectionNames.VALID_RULES_COLLECTION_NAME;
		DBCollection anyCollection = getProjectBourseInstance().getCollection(collectionName);
		anyCollection.setObjectClass(RuleDBO.class);

		return anyCollection;
	}

	// -----------------------------------------------------------------------------------
	// Stocks
	// -----------------------------------------------------------------------------------

	/**
	 * Returns the base stocks collection. The base stocks collection is the
	 * collection which contains the base set of stocks.
	 * 
	 * @return Object of type DBCollection. This object will store BaseStock
	 *         objects.
	 */
	public static DBCollection getBaseStocksCollection() {

		DBCollection baseStocks = getProjectBourseInstance().getCollection(BASE_STOCKS_COLLECTION);
		baseStocks.setObjectClass(BaseStockDBO.class);

		return baseStocks;
	}

	/**
	 * Returns the stock collection identified by its symbol.
	 * 
	 * @param stockSymbol The the symbol of the stock for which we want to get its
	 *            		  collection.
	 * @return The collection of the stock of the given symbol.
	 */
	public static DBCollection getStockCollection(String stockSymbol) {

		DBCollection baseStocks = getProjectBourseInstance().getCollection(stockSymbol);
		baseStocks.setObjectClass(HistoricalStockDBO.class);

		return baseStocks;
	}

	// -----------------------------------------------------------------------------------
	// Analysis
	// -----------------------------------------------------------------------------------
	
	/**
	 * Returns the collection in which all the analysis results are stored.
	 * 
	 * @return the collection in which all the analysis results are stored.
	 */
	public static DBCollection getAnalysisResultsCollection() {
		
		DBCollection analysisResults = getProjectBourseInstance().getCollection(ANALYSIS_RESULTS_COLLECTION);
		analysisResults.setObjectClass(AnalysisResultsStorageDBO.class);

		return analysisResults;
	}
}