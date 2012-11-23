package co.je.thesis.mobile.logic.businessObjects;

import java.util.ArrayList;

public class Portfolio {

	private String name;
	private ArrayList<Stock> stocks;

	public Portfolio(String name) {

		this.name = name;
		stocks = new ArrayList<Stock>();
	}

	public String getName() {
		return name;
	}

	public void changeName(String name) {

		this.name = name;
	}

	public ArrayList<Stock> getStocks() {
		return stocks;
	}
	
	public int getNumberOfStocks() {
		
		int numberOfStocks = getStocks().size();
		return numberOfStocks;
	}

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

	public void addStock(Stock stock) {

		String stockSymbol = stock.getSymbol();

		if (!stockExistsIntoPortfolio(stockSymbol)) {

			stocks.add(stock);
		}
	}
	
	public void addMultipleStocks(ArrayList<Stock> newStocks) {
		
		for (int i = 0; i < newStocks.size(); i++) {
			
			Stock stock = newStocks.get(i);
			addStock(stock);
		}
	}

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

	public void removeStock(String stockSymbol) {

		for (int i = 0; i < stocks.size(); i++) {

			Stock stock = stocks.get(i);
			if (stock.getSymbol().equals(stockSymbol)) {
				
				stocks.remove(stock);
			}
		}
	}
}