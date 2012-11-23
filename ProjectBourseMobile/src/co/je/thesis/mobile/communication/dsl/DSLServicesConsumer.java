package co.je.thesis.mobile.communication.dsl;

import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;

import co.je.thesis.common.dtos.dsl.DSLDataTransferObject;
import co.je.thesis.mobile.communication.constants.CommunicationsConstants;

public class DSLServicesConsumer {

	public static final String DSL_SERVICES_URL = "/dsl";

	public DSLDataTransferObject getDSL() {

		String url = CommunicationsConstants.BASE_URL + DSL_SERVICES_URL;

		String[] params = { url };
		GetDSLAsyncTask getDSLAsyncTask = new GetDSLAsyncTask();
		getDSLAsyncTask.execute(params);

		String jsonDSL = "";

		try {

			jsonDSL = getDSLAsyncTask.get();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		DSLDataTransferObject dsl = null;

		if (!jsonDSL.equalsIgnoreCase("")) {

			Gson gson = new Gson();
			dsl = gson.fromJson(jsonDSL, DSLDataTransferObject.class);
		}

		return dsl;
	}

	public int getDSLVersion() {
		
		String url = CommunicationsConstants.BASE_URL + DSL_SERVICES_URL + "/version";

		String[] params = { url };
		GetDSLVersionAsyncTask getDSLVersionAsyncTask = new GetDSLVersionAsyncTask();
		getDSLVersionAsyncTask.execute(params);

		String jsonDSLVersion = "";

		try {

			jsonDSLVersion = getDSLVersionAsyncTask.get();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		int dslVersion = -1;

		if (!jsonDSLVersion.equalsIgnoreCase("")) {

			dslVersion = Integer.parseInt(jsonDSLVersion);
		}

		return dslVersion;
	}
}
