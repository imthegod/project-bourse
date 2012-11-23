package co.je.thesis.mobile.persistence.dbo;

public class PortfolioDBO {

	// -----------------------------------------------------------------------------------
	// Constants
	// -----------------------------------------------------------------------------------

	// -----------------------------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------------------------

	private String name;
	private int numberOfStocks;

	// -----------------------------------------------------------------------------------
	// Methods
	// -----------------------------------------------------------------------------------

	public PortfolioDBO(String name, int numberOfStocks) {

		this.name = name;
		this.numberOfStocks = numberOfStocks;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfStocks() {
		return numberOfStocks;
	}
}