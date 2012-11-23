package co.je.thesis.mobile.persistence.dbo;

public class StockDBO {

	// -----------------------------------------------------------------------------------
	// Constants
	// -----------------------------------------------------------------------------------

	// -----------------------------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------------------------

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

	// -----------------------------------------------------------------------------------
	// Methods
	// -----------------------------------------------------------------------------------

	public StockDBO(String symbol, String name, int numberOfShares,
			String portfolioName, double basePrice, double stopLoss1,
			double stopLoss2, double stopLoss3, double takeProfit1,
			double takeProfit2, double takeProfit3) {

		this.symbol = symbol;
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

	public void changeStopLoss1(double newStopLoss1) {

		stopLoss1 = newStopLoss1;
	}

	public void changeStopLoss2(double newStopLoss2) {

		stopLoss1 = newStopLoss2;
	}

	public void changeStopLoss3(double newStopLoss3) {

		stopLoss1 = newStopLoss3;
	}

	public void changeTakeProfit1(double newTakeProfit1) {

		takeProfit1 = newTakeProfit1;
	}

	public void changeTakeProfit2(double newTakeProfit2) {

		takeProfit1 = newTakeProfit2;
	}

	public void changeTakeProfit3(double newTakeProfit3) {

		takeProfit1 = newTakeProfit3;
	}
}