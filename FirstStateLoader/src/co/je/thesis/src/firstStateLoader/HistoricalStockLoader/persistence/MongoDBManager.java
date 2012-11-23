package co.je.thesis.src.firstStateLoader.HistoricalStockLoader.persistence;

import java.net.UnknownHostException;

import co.je.thesis.common.dbos.stocks.HistoricalStockDBO;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * Class which establishes the connection and handles the interactions with the
 * Mongo database
 * 
 * @author Julian Espinel
 */
public class MongoDBManager {
	
	public static final String DB_NAME = "project_bourse_db";

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
	 * Returns the stock collection identified by its symbol
	 * 
	 * @param stockSymbol The the symbol of the stock for which we want to get its
	 *        collection
	 * @return The collection of the stock of the given symbol
	 */
	public static DBCollection getStockCollection(String stockSymbol) {

		DBCollection baseStocks = getProjectBourseInstance().getCollection(stockSymbol);
		baseStocks.setObjectClass(HistoricalStockDBO.class);

		return baseStocks;
	}
}