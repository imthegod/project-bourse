package co.je.thesis.mobile.communication.rules;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.mobile.communication.constants.CommunicationsConstants;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This class contains the logic necessary to consume the rules REST services, 
 * exposed by the server.
 * 
 * @author Julian Espinel
 */
public class RulesServicesConsumer {
	
	/**
	 * Path that points to rules services.
	 */
	public static final String VALID_RULES_SERVICES_URL = "/valid-rules";

	/**
	 * Retrieves the valid rules from the system's server.
	 * 
	 * @return an ArrayList with the system's valid rules.
	 */
	public ArrayList<ValidRule> getValidRules() {

		String url = CommunicationsConstants.BASE_URL + VALID_RULES_SERVICES_URL;
		
		String[] params = { url };

		GetValidRulesAsyncTask getValidRulesAsyncTask = new GetValidRulesAsyncTask();
		getValidRulesAsyncTask.execute(params);

		String jsonValidRules = "";

		try {

			jsonValidRules = getValidRulesAsyncTask.get();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		ArrayList<ValidRule> validRules = null;

		if (!jsonValidRules.equalsIgnoreCase("")) {

			Type validRulesType = new TypeToken<ArrayList<ValidRule>>(){}.getType();
			
			Gson gson = new Gson();
			validRules = gson.fromJson(jsonValidRules, validRulesType);
		}

		return validRules;
	}
	
	/**
	 * Returns the current version of the valid rules.
	 * 
	 * @return the current version of the valid rules.
	 */
	public int getValidRulesVersion() {
		
		String url = CommunicationsConstants.BASE_URL + VALID_RULES_SERVICES_URL + "/version";
		
		String[] params = { url };

		GetValidRulesVersionAsyncTask getValidRulesVersionAsyncTask = new GetValidRulesVersionAsyncTask();
		getValidRulesVersionAsyncTask.execute(params);

		String jsonValidRulesVersion = "";

		try {

			jsonValidRulesVersion = getValidRulesVersionAsyncTask.get();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		int validRulesVersion = -1;

		if (!jsonValidRulesVersion.equalsIgnoreCase("")) {
			
			validRulesVersion = Integer.parseInt(jsonValidRulesVersion);
		}

		return validRulesVersion;
	}
}