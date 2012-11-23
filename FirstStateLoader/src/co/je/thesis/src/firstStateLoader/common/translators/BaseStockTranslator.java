package co.je.thesis.src.firstStateLoader.common.translators;

import co.je.thesis.common.dbos.stocks.BaseStockDBO;
import co.je.thesis.common.dtos.stocks.BaseStock;

/**
 * Class to know how to translate from BaseStock objects to BaseStockDBO objects,
 * and vice versa.
 * 
 * @author Julian Espinel
 */
public class BaseStockTranslator {

	/**
	 * Translates a given BaseStockDBO object to a BaseStock object.
	 * 
	 * @param baseStockDBO The BaseStockDBO object we want to to be a
	 * 		  BaseStock object. 
	 * @return A BaseStock object with the information contained by the
	 * 		   parameter.
	 */
	public BaseStock translateToDomainObject(BaseStockDBO baseStockDBO) {

		String symbol = baseStockDBO.getSymbol();
		String name = baseStockDBO.getName();
		BaseStock baseStock = new BaseStock(symbol, name);

		return baseStock;
	}

	/**
	 * Translates a given BaseStock object into a BaseStockDBO object.
	 * 
	 * @param baseStock The BaseStock object we want to to be a
	 * 		  BaseStockDBO object.
	 * @return A BaseStockDBO object with the information contained by the
	 * 		   parameter.
	 */
	public BaseStockDBO translateToDBO(BaseStock baseStock) {

		String symbol = baseStock.getSymbol();
		String name = baseStock.getName();
		BaseStockDBO baseStockDBO = new BaseStockDBO(symbol, name);

		return baseStockDBO;
	}
}
