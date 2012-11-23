package co.je.thesis.server.persistence.stocks;

import java.util.ArrayList;

import co.je.thesis.common.dbos.stocks.BaseStockDBO;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.server.infrastructure.translators.stocks.BaseStockTranslator;
import co.je.thesis.server.persistence.DBManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class StockPersistence {

	public StockPersistence() {
	}

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

	public DBCollection getStockCollection(String stockSymbol) {

		DBCollection stockCollection = DBManager.getStockCollection(stockSymbol);

		return stockCollection;
	}
}