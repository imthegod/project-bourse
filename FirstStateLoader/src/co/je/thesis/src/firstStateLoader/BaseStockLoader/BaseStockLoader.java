package co.je.thesis.src.firstStateLoader.BaseStockLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.src.firstStateLoader.BaseStockLoader.data.BaseStockDataParser;
import co.je.thesis.src.firstStateLoader.BaseStockLoader.data.BaseStockDataRetriever;
import co.je.thesis.src.firstStateLoader.BaseStockLoader.persistence.BaseStockMapper;

/**
 * Class which knows how to load the base stocks with which the system will work.
 * 
 * @author Julian Espinel
 */
public class BaseStockLoader {

	/**
	 * Constructor of the class
	 */
	public BaseStockLoader() {

	}

	/**
	 * Adds the String objects of the temporalNames array to the baseStocksInfo
	 * array, if and only if, they are not already in this last.
	 * 
	 * @param baseStocksInfo An ArrayList which represents and accumulates the
	 *        information of the base stocks with which the system will work.
	 * @param temporalInfo An ArrayList with the names to be added to the
	 *        baseStocksInfo ArrayList
	 */
	private void mergeNoRepeatedStocks(ArrayList<String> baseStocksInfo, ArrayList<String> temporalInfo) {

		for (int i = 0; i < temporalInfo.size(); i++) {

			boolean foundSameName = false;
			String temporalName = temporalInfo.get(i);

			for (int j = 0; j < baseStocksInfo.size(); j++) {

				String stockName = baseStocksInfo.get(j);

				if (temporalName.equalsIgnoreCase(stockName)) {
					foundSameName = true;
				}
			}

			if (! foundSameName) {
				baseStocksInfo.add(temporalName);
			}
		}
	}

	/**
	 * Loads the files and fills an ArrayList with String objects. Each String
	 * object in the ArrayList, contains information of one stock, and follows this
	 * format: "company name (symbol)"
	 * 
	 * @return An ArrayList with String objects. Each String object in the
	 *         ArrayList, contains information of one stock
	 */
	private ArrayList<String> getBaseStockInfo() {

		BaseStockDataRetriever dataRetriever = new BaseStockDataRetriever();
		BaseStockDataParser dataParser = new BaseStockDataParser();

		File[] files = dataRetriever.loadStockBaseExcelFiles();

		ArrayList<String> baseStocksInfo = new ArrayList<String>();

		for (int i = 0; i < files.length; i++) {

			File archivo = files[i];
			ArrayList<String> temporalNames = new ArrayList<String>();
			temporalNames = dataParser.getStockNamesFromExcelFile(archivo);

			mergeNoRepeatedStocks(baseStocksInfo, temporalNames);
		}

		return baseStocksInfo;
	}

	/**
	 * Returns a List of BaseStock objects. Each BaseStock object contains the
	 * information of one String object in the baseStocksInfo parameter.
	 * 
	 * @param baseStocksInfo A List of String object. Each String object within this
	 *        List contains stock info in the following format:
	 *        "company name (symbol)".
	 * @return A List of BaseStock objects.
	 */
	private List<BaseStock> getBaseStocks(List<String> baseStocksInfo) {

		BaseStockDataParser dataParser = new BaseStockDataParser();
		List<BaseStock> baseStocks = dataParser.getBaseStocks(baseStocksInfo);

		return baseStocks;
	}

	/**
	 * Creates multiple BaseStock objects into the database.
	 * 
	 * @param baseStocks A List of BaseStock objects.
	 */
	private void saveBaseStocksInDB(List<BaseStock> baseStocks) {

		BaseStockMapper mapper = new BaseStockMapper();
		mapper.create(baseStocks);
	}

	/**
	 * Returns a List with all the BaseStock objects stored into the database
	 * 
	 * @return A List with all the BaseStock objects stored into the database
	 */
	public List<BaseStock> getBaseStocksFromDB() {

		BaseStockMapper mapper = new BaseStockMapper();
		List<BaseStock> baseStocks = mapper.readAll();

		return baseStocks;
	}

	/**
	 * This method can be considered as the main function of the BaseStockLoader
	 * module.
	 * 
	 * This method executes the followings steps:
	 * 
	 * 1. Loads the stock information from the excel files located at the data
	 * directory.
	 * 
	 * 2. Parses the information retrieved on step 1, and returns a List of
	 * BaseStocks objects.
	 * 
	 * 3. Saves all the BaseStock objects, contained in the List of the step 2,
	 * into the database
	 */
	public void loadBaseStocks() {

		ArrayList<String> baseStocksInfo = getBaseStockInfo();
		List<BaseStock> baseStocks = getBaseStocks(baseStocksInfo);
		saveBaseStocksInDB(baseStocks);
	}
}