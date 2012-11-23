package co.je.thesis.src.firstStateLoader.BaseStockLoader.persistence;

import java.net.UnknownHostException;

import co.je.thesis.common.dbos.stocks.BaseStockDBO;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * Class which establishes the connection and handles the interactions with the Mongo
 * database
 * 
 * @author Julian Espinel
 */
public class MongoDBManager {

	/**
	 * Database name
	 */
	public static final String DB_NAME = "project_bourse_db";
	
	/**
	 * Name of the collection which will contain the base stock set
	 */
	public static final String BASE_STOCKS_COLLECTION = "base_stocks";

	private static Mongo mongoInstance;
	private static DB projectBourseDBInstance;

	private MongoDBManager() {
	}
	
	private static DB getProjectBourseInstance() {

		if (mongoInstance == null) {

			try {

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

	/**
	 * Returns the base stocks collection. The base stocks collection is the
	 * collection which contains the base set of stocks.
	 * 
	 * @return Object of type DBCollection. This object will store BaseStock
	 * 		   objects.
	 */
	public static DBCollection getBaseStocksCollection() {

		DBCollection baseStocks = getProjectBourseInstance().getCollection(BASE_STOCKS_COLLECTION);
		baseStocks.setObjectClass(BaseStockDBO.class);

		return baseStocks;
	}
}
