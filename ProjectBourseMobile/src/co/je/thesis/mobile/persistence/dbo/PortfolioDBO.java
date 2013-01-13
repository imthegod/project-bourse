package co.je.thesis.mobile.persistence.dbo;

/**
 * This class models an object that allow us to manage portfolio records stored into the DB.
 * 
 * @author Julian Espinel
 */
public class PortfolioDBO {

	// -----------------------------------------------------------------------------------
	// Constants
	// -----------------------------------------------------------------------------------

	// -----------------------------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------------------------

	/**
	 * The name of the portfolio.
	 */
	private String name;
	
	/**
	 * The number of stocks that compounds the portfolio.
	 */
	private int numberOfStocks;

	// -----------------------------------------------------------------------------------
	// Methods
	// -----------------------------------------------------------------------------------

	/**
	 * PortfolioDBO constructor method.
	 * 
	 * @param name the name of the portfolio.
	 * @param numberOfStocks the number of stocks that compounds the portfolio. 
	 */
	public PortfolioDBO(String name, int numberOfStocks) {

		this.name = name;
		this.numberOfStocks = numberOfStocks;
	}

	/**
	 * Returns the name of the portfolio.
	 * 
	 * @return the name of the portfolio.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the number of stocks that compounds the portfolio. 
	 * 
	 * @return the number of stocks that compounds the portfolio.
	 */
	public int getNumberOfStocks() {
		return numberOfStocks;
	}
}