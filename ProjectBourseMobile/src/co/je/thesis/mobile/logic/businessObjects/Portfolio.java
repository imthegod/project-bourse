package co.je.thesis.mobile.logic.businessObjects;

import java.util.ArrayList;

/**
 * This class models a portfolio.
 * 
 * @author Julian Espinel
 */
public class Portfolio {

	/**
	 * The name of the portfolio.
	 */
	private String name;
	
	/**
	 * The stocks that compound the portfolio. 
	 */
	private ArrayList<Stock> stocks;

	/**
	 * Portofolio constructor.
	 * 
	 * @param name the name of the portfolio.
	 */
	public Portfolio(String name) {

		this.name = name;
		stocks = new ArrayList<Stock>();
	}

	/**
	 * Returns the portfolio name.
	 * 
	 * @return the portfolio name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Changes the portfolio name.
	 * 
	 * @param name the new portfolio name.
	 */
	public void changeName(String name) {

		this.name = name;
	}

	/**
	 * Returns the stocks of the portfolio.
	 * 
	 * @return the stocks of the portfolio.
	 */
	public ArrayList<Stock> getStocks() {
		return stocks;
	}
	
	/**
	 * Returns the number of stocks that compounds the portofio.
	 * 
	 * @return the number of stocks that compounds the portofio.
	 */
	public int getNumberOfStocks() {
		
		int numberOfStocks = getStocks().size();
		return numberOfStocks;
	}

	/**
	 * Verifies if a stock is already into the portfolio.
	 * 
	 * @param stockSymbol the symbol of the stock we want to verify is into the portfolio.
	 * @return if the stock is already into the portfolio, then returns true, else returns false.
	 */
	private boolean stockExistsIntoPortfolio(String stockSymbol) {

		boolean answer = false;

		for (int i = 0; i < stocks.size(); i++) {

			Stock stock = stocks.get(i);

			if (stock.getSymbol().equals(stockSymbol)) {

				answer = true;
			}
		}

		return answer;
	}

	/**
	 * Adds a stock to the portfolio.
	 * 
	 * @param stock the stock we want to add to the portfolio.
	 */
	public void addStock(Stock stock) {

		String stockSymbol = stock.getSymbol();

		if (!stockExistsIntoPortfolio(stockSymbol)) {

			stocks.add(stock);
		}
	}
	
	/**
	 * Adds multiple stocks to the portfolio.
	 * 
	 * @param newStocks an ArrayList with the stocks we to add to the portfolio.
	 */
	public void addMultipleStocks(ArrayList<Stock> newStocks) {
		
		for (int i = 0; i < newStocks.size(); i++) {
			
			Stock stock = newStocks.get(i);
			addStock(stock);
		}
	}

	/**
	 * Returns a stock if it is into the portfolio.
	 * 
	 * @param stockSymbol the symbol of the stock we want to find.
	 * @return if a stock with the given symbol is into the portfolio, then returns the stock, else
	 * 		   returns null.
	 */
	public Stock findStock(String stockSymbol) {

		Stock foundedStock = null;

		for (int i = 0; i < stocks.size(); i++) {

			Stock stock = stocks.get(i);
			if (stock.getSymbol().equals(stockSymbol)) {

				foundedStock = stock;
			}
		}

		return foundedStock;
	}

	/**
	 * Removes a stock from the portfolio.
	 * 
	 * @param stockSymbol the symbol of the stock we want to remove from the portfolio.
	 */
	public void removeStock(String stockSymbol) {

		for (int i = 0; i < stocks.size(); i++) {

			Stock stock = stocks.get(i);
			if (stock.getSymbol().equals(stockSymbol)) {
				
				stocks.remove(stock);
			}
		}
	}
}