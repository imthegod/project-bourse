package co.je.thesis.src.firstStateLoader.HistoricalStockLoader.persistence;

import java.util.Date;
import java.util.List;

import co.je.thesis.common.dbos.stocks.HistoricalStockDBO;
import co.je.thesis.common.dtos.stocks.HistoricalStock;
import co.je.thesis.src.firstStateLoader.common.translators.HistoricalStockTranslator;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * Class that moves data between objects and a database while keeping them
 * independent See: http://martinfowler.com/eaaCatalog/dataMapper.html
 * 
 * @author Julian Espinel
 */
public class HistoricalStockMapper {

	private String stockSymbol;
	private HistoricalStockTranslator translator;

	/**
	 * Constructor method. Initializes the MongoDB manager, the translator, and the
	 * field symbol. This last inform for which stock this mapper is working for.
	 * 
	 * For example if the symbol field has the value "AAPL", it means that this
	 * mapper is working for Apple. Thus, this mapper will handle the historical
	 * information of Apple Inc ONLY.
	 * 
	 * @param stockSymbol The symbol of the stock for which this mapper will work
	 *        for
	 */
	public HistoricalStockMapper(String stockSymbol) {

		this.stockSymbol = stockSymbol;
		translator = new HistoricalStockTranslator();
	}

	/**
	 * Inserts a new HistoricalStock object into the database
	 * 
	 * @param historicalStock The object to be stored into the database
	 */
	public void create(HistoricalStock historicalStock) {

		HistoricalStockDBO historicalStockDBO = translator.translateToDBO(historicalStock);
		DBCollection historicalStockCollection = MongoDBManager.getStockCollection(stockSymbol);
		historicalStockCollection.insert(historicalStockDBO);
	}

	/**
	 * Inserts a list of HistoricalStock objects into the database
	 * 
	 * @param historicalStockList The list which contains the HistoricalStock
	 *        objects to be stored into the database
	 */
	public void create(List<HistoricalStock> historicalStockList) {

		DBCollection historicalStockCollection = MongoDBManager.getStockCollection(stockSymbol);

		for (int i = 0; i < historicalStockList.size(); i++) {

			HistoricalStock historicalStock = historicalStockList.get(i);
			HistoricalStockDBO historicalStockDBO = translator.translateToDBO(historicalStock);

			historicalStockCollection.insert(historicalStockDBO);
		}
	}

	/**
	 * Retrieve a HistoricalStock object, given the value of its date field
	 * 
	 * @param queryObject An object which contains the same date value of the
	 *        HistoricalStock object we want to retrieve
	 * @return A HistoricalStock object whose date field matches the date contained
	 *         by the parameter object
	 */
	public HistoricalStock read(HistoricalStock historicalStock) {

		DBCollection historicalStockCollection = MongoDBManager.getStockCollection(stockSymbol);

		HistoricalStockDBO queryObject = translator.translateToDBO(historicalStock);
		DBObject dbObject = historicalStockCollection.findOne(queryObject);
		HistoricalStockDBO historicalStockDBO = (HistoricalStockDBO) dbObject;

		if (historicalStockDBO == null) {

			Date date = queryObject.getDate();
			String message = "The HistoricalStockDBO object with date: " + date + " doesn't exists into the database";

			throw new NullPointerException(message);
		}

		HistoricalStock foundHistoricalStock = translator.translateToDomainObject(historicalStockDBO);

		return foundHistoricalStock;
	}

	/**
	 * Deletes an object within the database, given a query object which matches its
	 * date field value
	 * 
	 * @param queryObject An object which contains the same date value of the
	 *        HistoricalStock object we want to delete
	 */
	public void delete(HistoricalStock historicalStock) {

		DBCollection historicalStockCollection = MongoDBManager.getStockCollection(stockSymbol);
		HistoricalStockDBO queryObject = translator.translateToDBO(historicalStock);
		historicalStockCollection.remove(queryObject);
	}

	/**
	 * Returns the number of documents stored into the HistoricalStock collection
	 * 
	 * @return Number of documents stored into the HistoricalStock collection
	 */
	public long getNumberOfDocumentsInTheCollection() {

		DBCollection historicalStockCollection = MongoDBManager.getStockCollection(stockSymbol);
		long numberOfDocuments = historicalStockCollection.count();

		return numberOfDocuments;
	}
}