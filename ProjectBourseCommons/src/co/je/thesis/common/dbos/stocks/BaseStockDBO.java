package co.je.thesis.common.dbos.stocks;

import com.mongodb.BasicDBObject;

/**
 * Class which models a BaseStock object, an in addition extends BasicDBObject
 * in order to be able to save objects of this class into MongoDB.
 * 
 * @author Julian Espinel
 */
public class BaseStockDBO extends BasicDBObject {
	
	public static final String SYMBOL = "symbol";
	public static final String NAME = "name";

	/*
	 * Do not delete this constructor. It is needed by the MongoDB java Driver.
	 */
	public BaseStockDBO() {
	}
	
	public BaseStockDBO (String symbol, String name) {

		put(SYMBOL, symbol);
		put(NAME, name);
	}

	public String getSymbol() {

		String symbol = getString(SYMBOL);
		return symbol;
	}

	public String getName() {

		String name = getString(NAME);
		return name;
	}
}