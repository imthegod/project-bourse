package co.je.thesis.mobile.logic.portfolioManager;

import java.util.ArrayList;

import android.content.Context;
import co.je.thesis.mobile.logic.businessObjects.Portfolio;
import co.je.thesis.mobile.logic.businessObjects.Stock;
import co.je.thesis.mobile.persistence.portfolioPersistence.InvestorPersistence;

public class PortfolioManager {

	private InvestorPersistence investorPersistence;

	public PortfolioManager(Context context) {

		investorPersistence = new InvestorPersistence(context);
	}

	// -----------------------------------------------------------------------------------
	// Portfolio CRUD
	// -----------------------------------------------------------------------------------

	public void createPortfolio(String portfolioName) {

		Portfolio portfolio = new Portfolio(portfolioName);
		investorPersistence.createPortfolio(portfolio);
	}

	public void createPortfolio(String portfolioName, ArrayList<Stock> stocks) {

		Portfolio portfolio = new Portfolio(portfolioName);
		portfolio.addMultipleStocks(stocks);

		investorPersistence.createPortfolio(portfolio);
	}

	public Portfolio getSinglePortfolio(String portfolioName) {

		Portfolio portfolio = investorPersistence.readSinglePortfolio(portfolioName);
		return portfolio;
	}

	public ArrayList<Portfolio> getAllPortfolios() {

		ArrayList<Portfolio> portfolios = investorPersistence.readAllPortfolios();
		return portfolios;
	}

	public void updatePortfolioStocksNumber() {

	}

	public void deletePortfolio(String portfolioName) {

		investorPersistence.deletePortfolio(portfolioName);
	}

	// -----------------------------------------------------------------------------------
	// Stock CRUD
	// -----------------------------------------------------------------------------------

	public void addStockToPortfolio(String portfolioName, Stock stock) {

		investorPersistence.createStock(portfolioName, stock);
		// Updates portafolio's numberOfStocks field.
		investorPersistence.updatePortfolio(portfolioName);
	}

	public Stock getSingleStock(String portfolioName, String stockSymbol) {

		Stock stock = investorPersistence.readSingleStock(portfolioName, stockSymbol);
		return stock;
	}

	public ArrayList<Stock> getMultipleStocks(String portfolioName) {

		ArrayList<Stock> portfolioStocks = investorPersistence.readMultipleStocks(portfolioName);
		return portfolioStocks;
	}

	public void updateStockFromPortfolio(String portfolioName, Stock stock) {

		investorPersistence.updateStock(portfolioName, stock);
	}

	public void deleteStockFromPortfolio(String portfolioName, String stockSymbol) {

		investorPersistence.deleteStock(portfolioName, stockSymbol);
	}

	// -----------------------------------------------------------------------------------
	// Extra methods
	// -----------------------------------------------------------------------------------

	public boolean investorHasThisStock(String stockSymbol) {

		boolean answer = investorPersistence.investorHasThisStock(stockSymbol);
		return answer;
	}

	public ArrayList<String> getPortfolioNamesWhereThisStockIsPresent(String stockSymbol) {

		ArrayList<String> portfolioNames = investorPersistence
				.getPortfolioNameWhereIsThisStock(stockSymbol);
		return portfolioNames;
	}
	
	public boolean portfolioAlreadyExists(String portfolioName) {
		
		boolean answer = investorPersistence.portfolioAlreadyExists(portfolioName);
		return answer;
	}

	public ArrayList<String> getAllPortfolioNames() {
		
		ArrayList<String> portfolioNames = investorPersistence.getAllPortfolioNames();
		return portfolioNames;
	}
	
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