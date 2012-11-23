package co.je.thesis.mobile.logic.businessObjects;

import java.io.Serializable;

public class Stock implements Serializable {

	private String symbol;
	private String name;
	private int numberOfShares;
	private String portfolioName;
	private double basePrice;

	private double stopLoss1;
	private double stopLoss2;
	private double stopLoss3;

	private double takeProfit1;
	private double takeProfit2;
	private double takeProfit3;

	public Stock() {
		
	}
	
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

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfShares() {
		return numberOfShares;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public double getStopLoss1() {
		return stopLoss1;
	}

	public double getStopLoss2() {
		return stopLoss2;
	}

	public double getStopLoss3() {
		return stopLoss3;
	}

	public double getTakeProfit1() {
		return takeProfit1;
	}

	public double getTakeProfit2() {
		return takeProfit2;
	}

	public double getTakeProfit3() {
		return takeProfit3;
	}
	
	public void setNumberOfShares(int numberOfShares) {
		this.numberOfShares = numberOfShares;
	}

	public void setStopLoss1(double stopLoss1) {
		this.stopLoss1 = stopLoss1;
	}

	public void setStopLoss2(double stopLoss2) {
		this.stopLoss2 = stopLoss2;
	}

	public void setStopLoss3(double stopLoss3) {
		this.stopLoss3 = stopLoss3;
	}

	public void setTakeProfit1(double takeProfit1) {
		this.takeProfit1 = takeProfit1;
	}

	public void setTakeProfit2(double takeProfit2) {
		this.takeProfit2 = takeProfit2;
	}

	public void setTakeProfit3(double takeProfit3) {
		this.takeProfit3 = takeProfit3;
	}
}