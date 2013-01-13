package co.je.thesis.server.persistence.stocks;

import java.util.ArrayList;

import co.je.thesis.common.dbos.stocks.BaseStockDBO;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.server.infrastructure.translators.stocks.BaseStockTranslator;
import co.je.thesis.server.persistence.DBManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

/**
 * This class is responsible for handling the stock data persistence.
 * 
 * @author Julian Espinel
 */
public class StockPersistence {

	/**
	 * StockPersistence constructor.
	 */
	public StockPersistence() {
	}

	/**
	 * Returns an ArrayList which contains the system's base stocks.
	 * The base stocks are the stocks of the NYSE that are supported by this system.
	 * 
	 * @return an ArrayList which contains the system's base stocks.
	 */
	public ArrayList<BaseStock> getBaseStocks() {

		DBCollection baseStocksCollection = DBManager.getBaseStocksCollection();
		DBCursor cursor = baseStocksCollection.find();

		BaseStockTranslator translator = new BaseStockTranslator();
		ArrayList<BaseStock> baseStocks = new ArrayList<BaseStock>();

		while (cursor.hasNext()) {

			BasicDBObject basicDBO = (BasicDBObject) cursor.next();
			BaseStockDBO stockDBO = translator.translateToDBO(basicDBO);
			BaseStock baseStock = translator.translateToDomainObject(stockDBO);

			baseStocks.add(baseStock);
		}

		return baseStocks;
	}

	/**
	 * Returns the stock collection identified by its symbol.
	 * 
	 * @param stockSymbol The the symbol of the stock for which we want to get its
	 *        collection.
	 * @return The collection of the stock of the given symbol.
	 */
	public DBCollection getStockCollection(String stockSymbol) {

		DBCollection stockCollection = DBManager.getStockCollection(stockSymbol);

		return stockCollection;
	}
}