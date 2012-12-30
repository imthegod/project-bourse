package co.je.thesis.common.dtos.stocks;

/**
 * Class that models a base stock. A base stock its an object which just has a
 * symbol and a name.
 * 
 * @author Julian Espinel
 */
public class BaseStock implements Comparable<BaseStock> {
	
	public static final String SEPARATOR = ", ";

	private String symbol;
	private String name;

	/**
	 * BaseStock Constructor method
	 * 
	 * @param symbol the stock symbol
	 * @param name the stock name
	 */
	public BaseStock(String symbol, String name) {

		this.symbol = symbol;
		this.name = name;
	}

	/**
	 * Returns the symbol of the BaseStock
	 * 
	 * @return the symbol of the BaseStock
	 */
	public String getSymbol() {

		return symbol;
	}

	/**
	 * Returns the name of the baseStock
	 * 
	 * @return the name of the baseStock
	 */
	public String getName() {

		return name;
	}
	
	/**
	 * Overwritten compateTo method.
	 * 
	 * @param baseStock a BaseStock object to be compared with this.
	 * @return if the baseStock symbol and name, are equals to this stock symbol and name, then
	 * 		   returns true, else returns false.
	 */
	@Override
	public int compareTo(BaseStock baseStock) {

		int answer = - 1;

		boolean hasSameSymbol = symbol.equals(baseStock.getSymbol());
		boolean hasSameName = name.equals(baseStock.getName());

		if (hasSameSymbol && hasSameName) {

			answer = 0;
		}

		return answer;
	}
	
	@Override
	public String toString() {
		
		String baseStockString = getSymbol() + SEPARATOR + getName();
		return baseStockString;
	}
}