package co.je.thesis.server.application.validRules;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.server.domain.dsl.DSLManager;

/**
 * Session Bean implementation class ValidRulesRequestHandler
 */
@Stateless
@LocalBean
public class ValidRulesRequestHandler {

	private DSLManager dslManager;

	/**
	 * Default constructor.
	 */
	public ValidRulesRequestHandler() {

		dslManager = new DSLManager();
	}

	public boolean areRulesUpToDate(int clientValidRulesVersion) {

		System.out.println("EJB: ValidRulesRequestHandler: areRulesUpToDate()");
		boolean areUpToDate = dslManager.areRulesUpToDate(clientValidRulesVersion);
		return areUpToDate;
	}
	
	public int getValidRulesVersion() {
		
		System.out.println("EJB: ValidRulesRequestHandler: getValidRulesVersion()");
		int validRulesVersion = dslManager.getValidRulesVersion();
		return validRulesVersion;
	}

	public ArrayList<ValidRule> getValidRules() {

		System.out.println("EJB: ValidRulesRequestHandler: getValidRules()");
		ArrayList<ValidRule> validRules = dslManager.getValidRules();
		return validRules;
	}
}
