package co.je.thesis.mobile.persistence.translators;

import co.je.thesis.mobile.logic.businessObjects.Portfolio;
import co.je.thesis.mobile.persistence.dbo.PortfolioDBO;

public class PortfolioTranslator {

	public static String getCorrectedNameForDb(String originalPortfolioName) {

		String correctedName = originalPortfolioName;

		if (originalPortfolioName.contains(" ")) {

			correctedName = originalPortfolioName.replace(" ", "_");
		}

		return correctedName;
	}

	public static String getOriginalPortfolioName(String correctedName) {
		
		String originalPortfolioName = correctedName;
		
		if (correctedName.contains("_")) {
			
			originalPortfolioName = correctedName.replace("_", " ");
		}
		
		return originalPortfolioName;
	}

	public static PortfolioDBO toDBO(Portfolio portfolio) {

		String name = portfolio.getName();
		String correctedName = getCorrectedNameForDb(name);
		int numberOfStocks = portfolio.getStocks().size();
		PortfolioDBO portfolioDBO = new PortfolioDBO(correctedName, numberOfStocks);

		return portfolioDBO;
	}

	public static Portfolio toBusinessObject(PortfolioDBO portfolioDBO) {

		String name = portfolioDBO.getName();
		String originalPortfolioName = getOriginalPortfolioName(name);
		Portfolio portfolio = new Portfolio(originalPortfolioName);

		return portfolio;
	}
}