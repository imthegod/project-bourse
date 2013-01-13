package co.je.thesis.mobile.logic.businessObjects;

import java.io.Serializable;

/**
 * This class models a stock.
 * 
 * @author Julian Espinel
 */
public class Stock implements Serializable {

	/**
	 * The symbol of the stock.
	 */
	private String symbol;
	
	/**
	 * The name of the stock
	 */
	private String name;
	
	/**
	 * The number of shares of this stock, that the investor have bought.
	 */
	private int numberOfShares;
	
	/**
	 * The name of the portfolio this stock belongs to.
	 */
	private String portfolioName;
	
	/**
	 * The price of the stock when the investor bought the shares.
	 */
	private double basePrice;

	/*
	 * Limits to minimize the losses.
	 */
	
	/**
	 * First limit to minimize the losses.
	 */
	private double stopLoss1;
	
	/**
	 * Second limit to minimize the losses.
	 */
	private double stopLoss2;
	
	/**
	 * Third limit to minimize the losses.
	 */
	private double stopLoss3;

	/*
	 * Limits to maximize the earnings.
	 */
	
	/**
	 * First limit to maximize the earnings.
	 */
	private double takeProfit1;
	
	/**
	 * Second limit to maximize the earnings.
	 */
	private double takeProfit2;
	
	/**
	 * Third limit to maximize the earnings.
	 */
	private double takeProfit3;

	public Stock() {
		
	}

	/**
	 * Stock constructor method.
	 * 
	 * @param symbol the symbol of the stock.
	 * @param name the name of the stock.
	 * @param numberOfShares the number of shares of this stock, that the investor bought.
	 * @param portfolioName the name of the portfolio this stock belongs to.
	 * @param basePrice the price of the stock when the investor bought the shares.
	 * @param stopLoss1 first limit to minimize the losses.
	 * @param stopLoss2 second limit to minimize the losses.
	 * @param stopLoss3 third limit to minimize the losses.
	 * @param takeProfit1 first limit to maximize the earnings.
	 * @param takeProfit2 second limit to maximize the earnings.
	 * @param takeProfit3 third limit to maximize the earnings.
	 */
	public Stock(String symbol, String name, int numberOfShares, String portfolioName,
			double basePrice, double stopLoss1, double stopLoss2, double stopLoss3,
			double takeProfit1, double takeProfit2, double takeProfit3) {

		this.symbol = symbol.toUpperCase();
		this.name = name;
		this.numberOfShares = numberOfShares;
		this.portfolioName = portfolioName;
		this.basePrice = basePrice;
		this.stopLoss1 = stopLoss1;
		this.stopLoss2 = stopLoss2;
		this.stopLoss3 = stopLoss3;
		this.takeProfit1 = takeProfit1;
		this.takeProfit2 = takeProfit2;
		this.takeProfit3 = takeProfit3;
	}

	/**
	 * Returns the stock's symbol.
	 * 
	 * @return the stock's symbol.
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Returns the stock's name.
	 * 
	 * @return the stock's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the number of shares bought by the investor.
	 * 
	 * @return the number of shares bought by the investor.
	 */
	public int getNumberOfShares() {
		return numberOfShares;
	}

	/**
	 * Returns the name of the portfolio this stock belongs to.
	 * 
	 * @return the name of the portfolio this stock belongs to.
	 */
	public String getPortfolioName() {
		return portfolioName;
	}

	/**
	 * Returns the price of the stock when the investor bought the shares.
	 * 
	 * @return the price of the stock when the investor bought the shares.
	 */
	public double getBasePrice() {
		return basePrice;
	}

	/**
	 * Returns the first stop loss
	 * 
	 * @return the first stop loss
	 */
	public double getStopLoss1() {
		return stopLoss1;
	}

	/**
	 * Returns the second stop loss
	 * 
	 * @return the second stop loss
	 */
	public double getStopLoss2() {
		return stopLoss2;
	}

	/**
	 * Returns the third stop loss
	 * 
	 * @return the third stop loss
	 */
	public double getStopLoss3() {
		return stopLoss3;
	}

	/**
	 * Returns the first take profit.
	 * 
	 * @return the first take profit.
	 */
	public double getTakeProfit1() {
		return takeProfit1;
	}

	/**
	 * Returns the second take profit.
	 * 
	 * @return the second take profit.
	 */
	public double getTakeProfit2() {
		return takeProfit2;
	}

	/**
	 * Returns the third take profit.
	 * 
	 * @return the third take profit.
	 */
	public double getTakeProfit3() {
		return takeProfit3;
	}
}