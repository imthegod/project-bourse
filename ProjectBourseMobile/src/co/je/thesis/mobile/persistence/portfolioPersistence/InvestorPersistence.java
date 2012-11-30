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

public class InvestorPersistence {

	// -----------------------------------------------------------------------------------
	// Constants
	// -----------------------------------------------------------------------------------

	// -----------------------------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------------------------

	private DatabaseOpenHelper dbOpenHelper;
	private SQLiteDatabase liteDb;

	// -----------------------------------------------------------------------------------
	// Methods
	// -----------------------------------------------------------------------------------

	public InvestorPersistence(Context context) {

		dbOpenHelper = new DatabaseOpenHelper(context);
		liteDb = dbOpenHelper.getDbInstance();
	}

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

	private ArrayList<StockDBO> translateMultipleStocksToDBO(ArrayList<Stock> stocks) {

		ArrayList<StockDBO> stocksDBO = new ArrayList<StockDBO>();

		for (int i = 0; i < stocks.size(); i++) {

			Stock stock = stocks.get(i);
			StockDBO stockDBO = StockTranslator.toDBO(stock);
			stocksDBO.add(stockDBO);
		}

		return stocksDBO;
	}

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

	public void updatePortfolio(String portfolioName) {

		// Updates portfolio's numberOfStocks field.

		Portfolio portfolio = readSinglePortfolio(portfolioName);

		PortfolioPersistence portfolioPersistence = new PortfolioPersistence(liteDb);

		PortfolioDBO updatedPortfolio = PortfolioTranslator.toDBO(portfolio);
		portfolioPersistence.updatePortfolio(liteDb, portfolioName, updatedPortfolio);
	}

	public void deletePortfolio(String portfolioName) {

		PortfolioPersistence portfolioPersistence = new PortfolioPersistence(liteDb);
		portfolioPersistence.deletePortfolio(liteDb, portfolioName);

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);
		stockPersistence.deletePortFolioStocks(liteDb, portfolioName);
	}

	// -----------------------------------------------------------------------------------
	// Stock CRUD
	// -----------------------------------------------------------------------------------

	public void createStock(String portfolioName, Stock stock) {

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);

		StockDBO stockDBO = StockTranslator.toDBO(stock);
		stockPersistence.createSingleStock(liteDb, stockDBO);
	}

	public Stock readSingleStock(String portfolioName, String stockSymbol) {

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);

		StockDBO stockDBO = stockPersistence.readSingleStock(liteDb, stockSymbol);
		Stock stock = StockTranslator.toBusinessObjetc(stockDBO);

		return stock;
	}

	public ArrayList<Stock> readMultipleStocks(String portfolioName) {

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);
		ArrayList<StockDBO> stocksDBO = stockPersistence.readPortfolioStocks(liteDb, portfolioName);
		ArrayList<Stock> portfolioStocks = translateMultipleStocksToBO(stocksDBO);

		return portfolioStocks;
	}

	public void updateStock(String portfolioName, Stock stock) {

		StockPersistence stockPersistence = new StockPersistence(liteDb, portfolioName);

		String stockSymbol = stock.getSymbol();
		StockDBO updatedStockDBO = StockTranslator.toDBO(stock);
		stockPersistence.updateStock(liteDb, stockSymbol, updatedStockDBO);
	}

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
	 * @param stockSymbol
	 *            , the symbol of the stock
	 * @return true if the investor has a stock at least one time in any of his
	 *         portfolios; else return false;
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
	 * Returns an arraylist with the names of portfolios which has the stock
	 * 
	 * @param stockSymbol
	 *            , symbol of the stock
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
	
	public boolean portfolioAlreadyExists(String portfolioName) {
		
		boolean answer = PortfolioPersistence.portfolioAlreadyExists(liteDb, portfolioName);
		return answer;
	}

	public ArrayList<String> getAllPortfolioNames() {
		
		ArrayList<String> portfolioNames = PortfolioPersistence.getAllPortfolioNames(liteDb);
		return portfolioNames;
	}
}