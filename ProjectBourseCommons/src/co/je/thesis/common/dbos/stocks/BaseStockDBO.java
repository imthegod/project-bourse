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
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param symbol the stock symbol.
	 * @param name the stock name.
	 */
	public BaseStockDBO (String symbol, String name) {

		put(SYMBOL, symbol);
		put(NAME, name);
	}

	/**
	 * Returns the stock symbol.
	 * 
	 * @return the stock symbol.
	 */
	public String getSymbol() {

		String symbol = getString(SYMBOL);
		return symbol;
	}

	/**
	 * Returns the stock name.
	 * 
	 * @return the stock name.
	 */
	public String getName() {

		String name = getString(NAME);
		return name;
	}
}