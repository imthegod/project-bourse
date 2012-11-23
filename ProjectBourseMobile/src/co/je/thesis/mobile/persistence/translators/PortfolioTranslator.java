package co.je.thesis.mobile.persistence.translators;

import co.je.thesis.mobile.logic.businessObjects.Portfolio;
import co.je.thesis.mobile.persistence.dbo.PortfolioDBO;

public class PortfolioTranslator {
	
	public static PortfolioDBO toDBO(Portfolio portfolio) {
		
		String name = portfolio.getName();
		int numberOfStocks = portfolio.getStocks().size();
		PortfolioDBO portfolioDBO = new PortfolioDBO(name, numberOfStocks);
		
		return portfolioDBO;
	}
	
	public static Portfolio toBusinessObject(PortfolioDBO portfolioDBO) {
		
		String name = portfolioDBO.getName();
		Portfolio portfolio = new Portfolio(name);
		
		return portfolio;
	}
}