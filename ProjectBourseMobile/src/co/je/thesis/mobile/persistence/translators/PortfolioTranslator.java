package co.je.thesis.mobile.persistence.translators;

import co.je.thesis.mobile.logic.businessObjects.Portfolio;
import co.je.thesis.mobile.persistence.dbo.PortfolioDBO;

/**
 * This class knows how to translate objects of type Portfolio, to PortfolioDBO objects 
 * and vice versa.
 * 
 * @author Julian Espinel
 */
public class PortfolioTranslator {

	/**
	 * Corrects the name of the portfolio in order to be able to store it into the DB.
	 * This method replaces the spaces of the name with underscores.
	 * 
	 * @param originalPortfolioName the original portfolio name.
	 * @return the name of the portfolio with no spaces.
	 */
	public static String getCorrectedNameForDb(String originalPortfolioName) {

		String correctedName = originalPortfolioName;

		if (originalPortfolioName.contains(" ")) {

			correctedName = originalPortfolioName.replace(" ", "_");
		}

		return correctedName;
	}

	/**
	 * Returns the portfolio name to its original value. Replaces underscores for spaces.
	 * 
	 * @param correctedName the name of the portfolio with underscores instead of spaces.
	 * @return the portfolio name to its original value. Replaces underscores for spaces.
	 */
	public static String getOriginalPortfolioName(String correctedName) {
		
		String originalPortfolioName = correctedName;
		
		if (correctedName.contains("_")) {
			
			originalPortfolioName = correctedName.replace("_", " ");
		}
		
		return originalPortfolioName;
	}

	/**
	 * Translates a Portfolio object into a PortfolioDBO object.
	 * 
	 * @param portfolio the Portfolio object we want to translate.
	 * @return a PortfolioDBO object with the information stored by the Portfolio object.
	 */
	public static PortfolioDBO toDBO(Portfolio portfolio) {

		String name = portfolio.getName();
		String correctedName = getCorrectedNameForDb(name);
		int numberOfStocks = portfolio.getStocks().size();
		PortfolioDBO portfolioDBO = new PortfolioDBO(correctedName, numberOfStocks);

		return portfolioDBO;
	}

	/**
	 * Translates a PortfolioDBO object into a Portfolio object.
	 * 
	 * @param portfolioDBO the PortfolioDBO object we want to translate.
	 * @return a Portfolio object with the information stored by the PortfolioDBO object.
	 */
	public static Portfolio toBusinessObject(PortfolioDBO portfolioDBO) {

		String name = portfolioDBO.getName();
		String originalPortfolioName = getOriginalPortfolioName(name);
		Portfolio portfolio = new Portfolio(originalPortfolioName);

		return portfolio;
	}
}