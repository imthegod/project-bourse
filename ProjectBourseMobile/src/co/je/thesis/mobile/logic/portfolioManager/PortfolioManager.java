package co.je.thesis.mobile.logic.portfolioManager;

import java.util.ArrayList;

import android.content.Context;
import co.je.thesis.mobile.logic.businessObjects.Portfolio;
import co.je.thesis.mobile.logic.businessObjects.Stock;
import co.je.thesis.mobile.persistence.portfolioPersistence.InvestorPersistence;

/**
 * Class that is responsible for all the business tasks related with portfolios.
 * 
 * @author Julian Espinel
 */
public class PortfolioManager {

	/**
	 * Handles the data of the investor's portfolios and stocks.
	 */
	private InvestorPersistence investorPersistence;

	/**
	 * PortfolioManager constructor
	 * 
	 * @param context the Android App context.
	 * @see http://developer.android.com/reference/android/content/Context.html 
	 */
	public PortfolioManager(Context context) {

		investorPersistence = new InvestorPersistence(context);
	}

	// -----------------------------------------------------------------------------------
	// Portfolio CRUD
	// -----------------------------------------------------------------------------------

	/**
	 * Creates a portfolio given its name.
	 * 
	 * @param portfolioName the name of the new portfolio.
	 */
	public void createPortfolio(String portfolioName) {

		Portfolio portfolio = new Portfolio(portfolioName);
		investorPersistence.createPortfolio(portfolio);
	}

	/**
	 * Create a new portfolio given its name and its stocks.
	 * 
	 * @param portfolioName the name of the new portfolio.
	 * @param stocks an ArrayList which contains the portfolio's stocks. 
	 */
	public void createPortfolio(String portfolioName, ArrayList<Stock> stocks) {

		Portfolio portfolio = new Portfolio(portfolioName);
		portfolio.addMultipleStocks(stocks);

		investorPersistence.createPortfolio(portfolio);
	}

	/**
	 * Returns a portfolio given its name.
	 * 
	 * @param portfolioName the name of the porfolio we want to retrieve.
	 * @return the portfolio of a given name.
	 */
	public Portfolio getSinglePortfolio(String portfolioName) {

		Portfolio portfolio = investorPersistence.readSinglePortfolio(portfolioName);
		return portfolio;
	}

	/**
	 * Returns a list with all the portfolios of the investor.
	 * 
	 * @return a list with all the portfolios of the investor.
	 */
	public ArrayList<Portfolio> getAllPortfolios() {

		ArrayList<Portfolio> portfolios = investorPersistence.readAllPortfolios();
		return portfolios;
	}

	/**
	 * Deletes a portfolio given its name.
	 * 
	 * @param portfolioName the name of the portfolio we want to delete.
	 */
	public void deletePortfolio(String portfolioName) {

		investorPersistence.deletePortfolio(portfolioName);
	}

	// -----------------------------------------------------------------------------------
	// Stock CRUD
	// -----------------------------------------------------------------------------------

	/**
	 * Adds a stock to a portfolio.
	 * 
	 * @param portfolioName the name of the portfolio in which we want to add the stock.
	 * @param stock the stock to be added to the portfolio.
	 */
	public void addStockToPortfolio(String portfolioName, Stock stock) {

		investorPersistence.createStock(portfolioName, stock);
		// Updates portafolio's numberOfStocks field.
		investorPersistence.updatePortfolio(portfolioName);
	}

	/**
	 * Returns a stock from a given portfolio.
	 * 
	 * @param portfolioName the name of the portfolio from which we want to retrieve the stock.
	 * @param stockSymbol the symbol of the stock we want to retrieve.
	 * @return a stock from a given portfolio.
	 */
	public Stock getSingleStock(String portfolioName, String stockSymbol) {

		Stock stock = investorPersistence.readSingleStock(portfolioName, stockSymbol);
		return stock;
	}

	/**
	 * Returns the stocks that compound a given portfolio.
	 * 
	 * @param portfolioName the name of the portfolio from which we want to retrieve its stocks.
	 * @return the stocks that compound a given portfolio.
	 */
	public ArrayList<Stock> getMultipleStocks(String portfolioName) {

		ArrayList<Stock> portfolioStocks = investorPersistence.readMultipleStocks(portfolioName);
		return portfolioStocks;
	}

	/**
	 * Updates a stock from a given portfolio.
	 * 
	 * @param portfolioName the name of the portfolio that contains the stock we want to update.
	 * @param stock the updated stock object.
	 */
	public void updateStockFromPortfolio(String portfolioName, Stock stock) {

		investorPersistence.updateStock(portfolioName, stock);
	}

	/**
	 * Deletes a stock from a given portfolio. 
	 * 
	 * @param portfolioName the name of the portfolio from which we want to delete the stock.
	 * @param stockSymbol the symbol of the stock we want to delete.
	 */
	public void deleteStockFromPortfolio(String portfolioName, String stockSymbol) {

		investorPersistence.deleteStock(portfolioName, stockSymbol);
	}

	// -----------------------------------------------------------------------------------
	// Extra methods
	// -----------------------------------------------------------------------------------

	/**
	 * Determines if the investor has bought a given stock.
	 * 
	 * @param stockSymbol the symbol of the stock that we want to know if the investor already 
	 * 		  bought it or not.
	 * @return If the investor has a stock with the given symbol, then returns true, else returns
	 * 		   false.
	 */
	public boolean investorHasThisStock(String stockSymbol) {

		boolean answer = investorPersistence.investorHasThisStock(stockSymbol);
		return answer;
	}

	/**
	 * Returns an ArrayList with the name of the portfolios which contains the given stock.
	 * 
	 * @param stockSymbol the symbol of the stock we are looking for.
	 * @return an ArrayList with the name of the portfolios which contains the given stock.
	 */
	public ArrayList<String> getPortfolioNamesWhereThisStockIsPresent(String stockSymbol) {

		ArrayList<String> portfolioNames = investorPersistence
				.getPortfolioNameWhereIsThisStock(stockSymbol);
		return portfolioNames;
	}
	
	/**
	 * Determines if already exists a portfolio with the given name within the DB.
	 * 
	 * @param portfolioName the name of the portfolio we are looking for.
	 * @return if already exists a portfolio with the given name within the DB, then returns true,
	 * 		   else returns false.
	 */
	public boolean portfolioAlreadyExists(String portfolioName) {
		
		boolean answer = investorPersistence.portfolioAlreadyExists(portfolioName);
		return answer;
	}

	/**
	 * Returns all the portfolio names that are stored into the DB.
	 * 
	 * @return all the portfolio names that are stored into the DB.
	 */
	public ArrayList<String> getAllPortfolioNames() {
		
		ArrayList<String> portfolioNames = investorPersistence.getAllPortfolioNames();
		return portfolioNames;
	}
	
	/**
	 * Determines if a specific stock is into the given portfolio.
	 * 
	 * @param portfolioName the name of the portfolio we want to know if contains a stock. 
	 * @param stockSymbol the symbol of the stock we are looking for.
	 * @return if the stock is into the portfolio, then returns true, else returns false. 
	 */
	public boolean isStockInPortfolio(String portfolioName, String stockSymbol) {
		
		boolean foundPortfolio = false;
		
		ArrayList<String> portfoliosWithStock = getPortfolioNamesWhereThisStockIsPresent(stockSymbol);
		
		for (int i = 0; i < portfoliosWithStock.size() && !foundPortfolio; i++) {
			
			String portfolioNameFromArray = portfoliosWithStock.get(i);
			
			if (portfolioNameFromArray.equalsIgnoreCase(portfolioName)) {
				
				foundPortfolio = true;
			}
		}
		
		return foundPortfolio;
	}
}