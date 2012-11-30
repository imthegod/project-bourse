package co.je.thesis.mobile.communication.rules;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.mobile.communication.constants.CommunicationsConstants;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RulesServicesConsumer {
	
	public static final String VALID_RULES_SERVICES_URL = "/valid-rules";

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