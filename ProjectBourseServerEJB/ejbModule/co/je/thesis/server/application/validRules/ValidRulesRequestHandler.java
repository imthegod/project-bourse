package co.je.thesis.server.application.validRules;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.server.domain.dsl.DSLManager;

/**
 * This class represents an EJB that handles requests related with valid rules.
 * 
 * @author Julian Espinel
 */
@Stateless
@LocalBean
public class ValidRulesRequestHandler {

	/**
	 * This attribute knows how to execute valid rules related requests. 
	 */
	private DSLManager dslManager;

	/**
	 * ValidRulesRequestHandler constructor.
	 */
	public ValidRulesRequestHandler() {

		dslManager = new DSLManager();
	}

	/**
	 * Determines if the valid rules's version that a client has is up to date.
	 * 
	 * @param clientValidRulesVersion the valid rules's version that the client has.
	 * @return if the valid rules's version that a client has is up to date, then returns true,
	 * 		   else returns false.
	 */
	public boolean areRulesUpToDate(int clientValidRulesVersion) {

		System.out.println("EJB: ValidRulesRequestHandler: areRulesUpToDate()");
		boolean areUpToDate = dslManager.areRulesUpToDate(clientValidRulesVersion);
		return areUpToDate;
	}
	
	/**
	 * Returns the current (and last) version number of the valid rules.
	 * 
	 * @return the current (and last) version number of the valid rules.
	 */
	public int getValidRulesVersion() {
		
		System.out.println("EJB: ValidRulesRequestHandler: getValidRulesVersion()");
		int validRulesVersion = dslManager.getValidRulesVersion();
		return validRulesVersion;
	}

	/**
	 * Returns an ArrayList with all the valid rules on its current (and last) version.
	 * 
	 * @return an ArrayList with all the valid rules on its current (and last) version.
	 */
	public ArrayList<ValidRule> getValidRules() {

		System.out.println("EJB: ValidRulesRequestHandler: getValidRules()");
		ArrayList<ValidRule> validRules = dslManager.getValidRules();
		return validRules;
	}
}