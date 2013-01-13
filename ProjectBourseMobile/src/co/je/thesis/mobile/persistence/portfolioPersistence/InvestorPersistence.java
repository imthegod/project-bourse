package co.je.thesis.mobile.persistence.portfolioPersistence;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import co.je.thesis.mobile.logic.businessObjects.Portfolio;
import co.je.thesis.mobile.logic.businessObjects.Stock;
import co.je.thesis.mobile.persistence.DatabaseOpenHelper;
import co.je.thesis.mobile.persistence.dbo.PortfolioDBO;
import co.je.thesis.mobile.persistence.dbo.StockDBO;
import co.je.thesis.mobile.persistence.translators.PortfolioTranslator;
import co.je.thesis.mobile.persistence.translators.StockTranslator;

/**
 * Class that contains the logic necessary to persist portfolio and stock related data.
 * 
 * @author Julian Espinel
 */
public class InvestorPersistence {

	// -----------------------------------------------------------------------------------
	// Constants
	// -----------------------------------------------------------------------------------

	// -----------------------------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------------------------

	/**
	 * Attribute that allow us to connect to the SQLiteDB.
	 */
	private DatabaseOpenHelper dbOpenHelper;
	
	/**
	 * Attribute that exposes methods to manage a SQLite database. 
	 */
	private SQLiteDatabase liteDb;

	// -----------------------------------------------------------------------------------
	// Methods
	// -----------------------------------------------------------------------------------

	/**
	 * InvestorPersistence constructor.
	 * 
	 * @param context the Android App context.
	 * @see http://developer.android.com/reference/android/content/Context.html
	 */
	public InvestorPersistence(Context context) {

		dbOpenHelper = new DatabaseOpenHelper(context);
		liteDb = dbOpenHelper.getDbInstance();
	}

	/**
	 * Translate an ArrayList of PortfolioDBO objects, to an ArrayList of Portfolio objects.
	 * 
	 * @param portfoliosDBO an ArrayList of PortfolioDBO objects.
	 * @return an ArrayList of Portfolio objects.
	 */
	private ArrayList<Portfolio> translateMultiplePortfoliosToBO(
			ArrayList<PortfolioDBO> portfoliosDBO) {

		ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();

		for (int i = 0; i < portfoliosDBO.size(); i++) {

			PortfolioDBO portfolioDBO = portfoliosDBO.get(i);
			Portfolio portfolio = PortfolioTranslator.toBusinessObject(portfolioDBO);

			portfolios.add(portfolio);
		}

		return portfolios;
	}

	/**
	 * Translate an ArrayList of Stock objects, to an ArrayList of StockDBO objects.
	 * 
	 * @param stocks an ArrayList of Stock objects.
	 * @return an ArrayList of StockDBO objects.
	 */
	private ArrayList<StockDBO> translateMultipleStocksToDBO(ArrayList<Stock> stocks) {

		ArrayList<StockDBO> stocksDBO = new ArrayList<StockDBO>();

		for (int i = 0; i < stocks.size(); i++) {

			Stock stock = stocks.get(i);
			StockDBO stockDBO = StockTranslator.toDBO(stock);
			stocksDBO.add(stockDBO);
		}

		return stocksDBO;
	}

	/**
	 * Translate an ArrayList of StockDBO objects, to an ArrayList of Stock objects.
	 * 
	 * @param stocksDBO an ArrayList of StockDBO objects
	 * @return an ArrayList of Stock objects.
	 */
	private ArrayList<Stock> translateMultipleStocksToBO(ArrayList<StockDBO> stocksDBO) {

		ArrayList<Stock> stocks = new ArrayList<Stock>();

		for (int i = 0; i < stocksDBO.size(); i++) {

			StockDBO stockDBO = stocksDBO.get(i);
			Stock stock = StockTranslator.toBusinessObjetc(stockDBO);

			stocks.add(stock);
		}

		return stocks;
	}

	// -----------------------------------------------------------------------------------
	// Portfolio CRUD
	// -----------------------------------------------------------------------------------

	/**
	 * Create a portfolio into the DB.
	 * 
	 * @param portfolio the portfolio to be created into the DB.
	 */
	public void createPortfolio(Portfolio portfolio) {

		String portfolioName = portfolio.getName();
		PortfolioPersistence portfolioPersistence = new PortfolioPersistence(liteDb);

		PortfolioDBO portfolioDBO = PortfolioTranslator.toDBO(portfolio);
		portfolioPersistence.createPortfolio(liteDb, portfolioDBO);

		
		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);

		ArrayList<Stock> portfolioStocks = portfolio.getStocks();
		ArrayList<StockDBO> stocksDBO = translateMultipleStocksToDBO(portfolioStocks);

		stockPersistence.createMultipleStocks(liteDb, stocksDBO);
	}

	/**
	 * Retrieves a specific portfolio from the DB.
	 * 
	 * @param portfolioName the name of the portfolio we want to retrieve.
	 * @return a specific portfolio.
	 */
	public Portfolio readSinglePortfolio(String portfolioName) {

		PortfolioPersistence portfolioPersistence = new PortfolioPersistence(liteDb);

		PortfolioDBO portfolioDBO = portfolioPersistence.readSinglePortfolio(liteDb, portfolioName);
		Portfolio portfolio = PortfolioTranslator.toBusinessObject(portfolioDBO);

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);

		ArrayList<StockDBO> stocksDBO = stockPersistence.readPortfolioStocks(liteDb, portfolioName);
		ArrayList<Stock> stocks = translateMultipleStocksToBO(stocksDBO);

		// Adds stocks to portfolio
		portfolio.addMultipleStocks(stocks);

		return portfolio;
	}

	/**
	 * Returns an ArrayList with all the portfolios stored into the DB.
	 * 
	 * @return an ArrayList with all the portfolios stored into the DB.
	 */
	public ArrayList<Portfolio> readAllPortfolios() {

		PortfolioPersistence portfolioPersistence = new PortfolioPersistence(liteDb);
		ArrayList<PortfolioDBO> portfoliosDBO = portfolioPersistence.readAllPortfolios(liteDb);
		ArrayList<Portfolio> portfolios = translateMultiplePortfoliosToBO(portfoliosDBO);

		for (int i = 0; i < portfolios.size(); i++) {

			Portfolio portfolio = portfolios.get(i);
			String portfolioName = portfolio.getName();

			StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);
			ArrayList<StockDBO> stocksDBO = stockPersistence.readPortfolioStocks(liteDb,
					portfolioName);
			ArrayList<Stock> stocks = translateMultipleStocksToBO(stocksDBO);

			portfolio.addMultipleStocks(stocks);
		}

		return portfolios;
	}

	/**
	 * Updates the number of stock of a specific portfolio.
	 * 
	 * @param portfolioName the name of the portfolio we want to update.
	 */
	public void updatePortfolio(String portfolioName) {

		// Updates portfolio's numberOfStocks field.

		Portfolio portfolio = readSinglePortfolio(portfolioName);

		PortfolioPersistence portfolioPersistence = new PortfolioPersistence(liteDb);

		PortfolioDBO updatedPortfolio = PortfolioTranslator.toDBO(portfolio);
		portfolioPersistence.updatePortfolio(liteDb, portfolioName, updatedPortfolio);
	}

	/**
	 * Deletes a portfolio given its name.
	 * 
	 * @param portfolioName the name of the portfolio we want to delete.
	 */
	public void deletePortfolio(String portfolioName) {

		PortfolioPersistence portfolioPersistence = new PortfolioPersistence(liteDb);
		portfolioPersistence.deletePortfolio(liteDb, portfolioName);

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);
		stockPersistence.deletePortFolioStocks(liteDb, portfolioName);
	}

	// -----------------------------------------------------------------------------------
	// Stock CRUD
	// -----------------------------------------------------------------------------------

	/**
	 * Creates a new stock into a specific portfolio.
	 * 
	 * @param portfolioName the name of the portfolio in which the stock will created.
	 * @param stock the stock to be created into the given portfolio.
	 */
	public void createStock(String portfolioName, Stock stock) {

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);

		StockDBO stockDBO = StockTranslator.toDBO(stock);
		stockPersistence.createSingleStock(liteDb, stockDBO);
	}

	/**
	 * Retrieves a specific stock from a given portfolio.
	 * 
	 * @param portfolioName the name of the portfolio from which we want to retrieve the stock.
	 * @param stockSymbol the symbol of the stock we want to retrieve.
	 * @return a specific stock from a given portfolio.
	 */
	public Stock readSingleStock(String portfolioName, String stockSymbol) {

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);

		StockDBO stockDBO = stockPersistence.readSingleStock(liteDb, stockSymbol);
		Stock stock = StockTranslator.toBusinessObjetc(stockDBO);

		return stock;
	}

	/**
	 * Returns an ArrayList with all the stocks that compound a given portfolio.
	 * 
	 * @param portfolioName the name of the portfolio from which we want to retrieve the stocks.
	 * @return an ArrayList with all the stocks that compound a given portfolio.
	 */
	public ArrayList<Stock> readMultipleStocks(String portfolioName) {

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);
		ArrayList<StockDBO> stocksDBO = stockPersistence.readPortfolioStocks(liteDb, portfolioName);
		ArrayList<Stock> portfolioStocks = translateMultipleStocksToBO(stocksDBO);

		return portfolioStocks;
	}

	/**
	 * Updates a stock that belongs to a given portfolio.
	 * 
	 * @param portfolioName the name of the portfolio that contains the stock.
	 * @param stock the stock we want to update.
	 */
	public void updateStock(String portfolioName, Stock stock) {

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);

		String stockSymbol = stock.getSymbol();
		StockDBO updatedStockDBO = StockTranslator.toDBO(stock);
		stockPersistence.updateStock(liteDb, stockSymbol, updatedStockDBO);
	}

	/**
	 * Deletes a stock from a given portfolio.
	 * 
	 * @param portfolioName the name of the portfolio that contains the stock.
	 * @param stockSymbol the symbol of the stock that we want to delete.
	 */
	public void deleteStock(String portfolioName, String stockSymbol) {

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);
		stockPersistence.deleteSingleStock(liteDb, stockSymbol);
	}

	// -----------------------------------------------------------------------------------
	// Extra methods
	// -----------------------------------------------------------------------------------

	/**
	 * Determines if the investor has a stock at least one time in any of his
	 * portfolios.
	 * 
	 * @param stockSymbol the symbol of the stock
	 * @return true if the investor has a stock at least one time in any of his
	 *         portfolios, else return false.
	 */
	public boolean investorHasThisStock(String stockSymbol) {

		boolean stockBelongsToInvestor = false;

		ArrayList<Portfolio> portfolios = readAllPortfolios();

		for (int i = 0; i < portfolios.size() && !stockBelongsToInvestor; i++) {

			Portfolio portfolio = portfolios.get(i);
			String portfolioName = portfolio.getName();

			if (StockPersistence.isStockIntoPortfolio(liteDb, stockSymbol, portfolioName)) {

				stockBelongsToInvestor = true;
			}
		}

		return stockBelongsToInvestor;
	}

	/**
	 * Returns an ArrayList with the names of portfolios which has the stock
	 * 
	 * @param stockSymbol symbol of the stock
	 * @return arraylist with the names of portfolios which has the stock
	 */
	public ArrayList<String> getPortfolioNameWhereIsThisStock(String stockSymbol) {

		ArrayList<String> portfolioNames = new ArrayList<String>();
		ArrayList<Portfolio> portfolios = readAllPortfolios();

		for (int i = 0; i < portfolios.size(); i++) {

			Portfolio portfolio = portfolios.get(i);
			String portfolioName = portfolio.getName();

			if (StockPersistence.isStockIntoPortfolio(liteDb, stockSymbol, portfolioName)) {

				portfolioNames.add(portfolioName);
			}
		}

		return portfolioNames;
	}
	
	/**
	 * Determines if a portfolio with the given name exists into the DB or not.
	 * 
	 * @param portfolioName the name of the portfolio we are looking for.
	 * @return if a portfolio with the given name exists into the DB returns true, else
	 * 		   returns false.
	 */
	public boolean portfolioAlreadyExists(String portfolioName) {
		
		boolean answer = PortfolioPersistence.portfolioAlreadyExists(liteDb, portfolioName);
		return answer;
	}

	/**
	 * Returns an ArrayList with the names of all the portfolios stored into the DB.
	 * 
	 * @return an ArrayList with the names of all the portfolios stored into the DB.
	 */
	public ArrayList<String> getAllPortfolioNames() {
		
		ArrayList<String> portfolioNames = PortfolioPersistence.getAllPortfolioNames(liteDb);
		return portfolioNames;
	}
}