package co.je.thesis.src.firstStateLoader.BaseStockLoader.persistence;

import java.util.ArrayList;
import java.util.List;

import co.je.thesis.common.dbos.stocks.BaseStockDBO;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.src.firstStateLoader.common.translators.BaseStockTranslator;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * Class which models a data mapper. 
 * See: http://martinfowler.com/eaaCatalog/dataMapper.html
 * 
 * @author Julian Espinel
 *
 */
public class BaseStockMapper {

	private BaseStockTranslator translator;

	/**
	 * Constructor method.
	 */
	public BaseStockMapper() {

		translator = new BaseStockTranslator();
	}

	/**
	 * Creates a new BaseStock object in the database.
	 * 
	 * @param baseStock The BaseStock object which will be created in the database.
	 */
	public void create(BaseStock baseStock) {

		BaseStockDBO baseStockDBO = translator.translateToDBO(baseStock);
		DBCollection baseStockCollection = MongoDBManager.getBaseStocksCollection();
		baseStockCollection.insert(baseStockDBO);
	}

	/**
	 * Creates multiple BaseStock objects into the database.
	 * 
	 * @param baseStockList A List of BaseStock objects. The objects within this List
	 * 		  will be created into the database.
	 */
	public void create(List<BaseStock> baseStockList) {

		DBCollection baseStockCollection = MongoDBManager.getBaseStocksCollection();

		for (int i = 0; i < baseStockList.size(); i++) {
			
			BaseStock baseStock = baseStockList.get(i);
			BaseStockDBO baseStockDBO = translator.translateToDBO(baseStock);
			baseStockCollection.insert(baseStockDBO);
		}
	}

	/**
	 * Retrieves a StockObject inside the database.
	 * 
	 * @param baseStock The BaseStock object that we are looking for.
	 * @return A StockObject inside the database.
	 */
	public BaseStock read(BaseStock baseStock) {

		BaseStockDBO baseStockDBO = translator.translateToDBO(baseStock);
		DBCollection baseStockCollection = MongoDBManager.getBaseStocksCollection();
		DBObject dbObject = baseStockCollection.findOne(baseStockDBO);

		BaseStockDBO foundBaseStockDBO = (BaseStockDBO) dbObject;
		BaseStock foundBaseStock = translator.translateToDomainObject(foundBaseStockDBO);

		return foundBaseStock;
	}

	/**
	 * Reads all the documents stored in the BaseStockCollection and returns a List
	 * of BaseStock objects. Each BaseStock object in the List represents one
	 * document from the collection.
	 * 
	 * @return A List of BaseStock objects. Each BaseStock object in the List
	 *         represents one document from the collection.
	 */
	public List<BaseStock> readAll() {

		DBCollection baseStockCollection = MongoDBManager.getBaseStocksCollection();
		DBCursor cursor = baseStockCollection.find();

		List<BaseStock> baseStocks = new ArrayList<BaseStock>();

		while (cursor.hasNext()) {

			DBObject dbObject = cursor.next();
			BaseStockDBO baseStockDBO = (BaseStockDBO) dbObject;
			BaseStock baseStock = translator.translateToDomainObject(baseStockDBO);

			baseStocks.add(baseStock);
		}

		return baseStocks;
	}

	/**
	 * Deletes one BaseStock object from the database.
	 * 
	 * @param baseStock The BaseStock object we want to delete from the database.
	 */
	public void delete(BaseStock baseStock) {

		BaseStockDBO baseStockDBO = translator.translateToDBO(baseStock);
		DBCollection baseStockCollection = MongoDBManager.getBaseStocksCollection();
		baseStockCollection.remove(baseStockDBO);
	}

	/**
	 * Returns the number of documents stored into the BaseStock collection
	 * 
	 * @return the number of documents stored into the BaseStock collection
	 */
	public long getNumberOfDocumentsInBaseStockCollection() {

		DBCollection baseStockCollection = MongoDBManager.getBaseStocksCollection();
		long numberOfDocuments = baseStockCollection.count();

		return numberOfDocuments;
	}
}
