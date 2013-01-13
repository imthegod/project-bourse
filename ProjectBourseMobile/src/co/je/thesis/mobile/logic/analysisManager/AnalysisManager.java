package co.je.thesis.mobile.logic.analysisManager;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import co.je.thesis.mobile.persistence.analysisPersistence.AnalysisPersistence;
import co.je.thesis.mobile.persistence.userPersistence.UserPersistence;

/**
 * Class that is responsible for all the business tasks related with analysis requests.
 * 
 * @author Julian Espinel
 */
public class AnalysisManager {

	/**
	 * Attribute which handles the user's data.
	 */
	private UserPersistence userPersistence;
	
	/**
	 * Attribute which handles the data of the created analysis.
	 */
	private AnalysisPersistence analysisPersistence;

	/**
	 * AnalysisManager constructor.
	 * 
	 * @param context the Android App context.
	 * @see http://developer.android.com/reference/android/content/Context.html
	 */
	public AnalysisManager(Context context) {

		userPersistence = new UserPersistence(context);
		analysisPersistence = new AnalysisPersistence(context);
	}

	/**
	 * Determines if the user name has been set and stored into the app's DB or not. 
	 * 
	 * @return if the user name has been set then returns true, else returns false.
	 */
	public boolean userNameHasBeenSet() {

		boolean answer = userPersistence.userNameHasBeenSet();
		return answer;
	}

	/**
	 * Sets and stores the user name into the app's DB.
	 * 
	 * @param userName the user name to be stored into the DB.
	 */
	public void setUserName(String userName) {

		if (!userPersistence.userNameHasBeenSet()) {

			userPersistence.setUserName(userName);

		} else {

			String exceptionMessage = "There is already a username in the DB.";
			throw new SQLiteException(exceptionMessage);
		}
	}

	/**
	 * Gets the stored user name from the app's DB.
	 * 
	 * @return the stored user name from the app's DB.
	 */
	public String getUserName() {

		String userName = userPersistence.getUserName();
		return userName;
	}

	/**
	 * Determines if there are pending analysis in the local DB. An analysis is a pending analysis
	 * if its results are not been retrieved yet from the system's server.
	 * 
	 * @return if there are more than one pending analysis into the DB, then returns true, 
	 * 		   else returns false.
	 */
	public boolean existsPengingAnalysis() {

		boolean answer = analysisPersistence.existsPendingAnalysis();
		return answer;
	}

	/**
	 * Adds a pending analysis to the app's DB. 
	 * 
	 * @param uuid the UUID of the pending analysis.
	 */
	public void addPendingAnalysis(String uuid) {

		analysisPersistence.addPendingAnalysis(uuid);
	}

	/**
	 * Returns an ArrayList with the UUID of each one of the pending analysis stored into the DB.
	 * 
	 * @return an ArrayList with the UUID of each one of the pending analysis stored into the DB.
	 */
	public ArrayList<String> getAllPendingAnalysisUuids() {
		
		ArrayList<String> pendingAnalysis = analysisPersistence.getAllPendingAnalysisUuids();
		return pendingAnalysis;
	}

	/**
	 * Returns an ArrayList with the creation date of each one of the pending analysis stored 
	 * into the DB.
	 * 
	 * @return an ArrayList with the creation date of each one of the pending analysis stored 
	 * 		   into the DB.
	 */
	public ArrayList<String> getAllPendingAnalysisDates() {
		
		ArrayList<String> pendingAnalysis = analysisPersistence.getAllPendingAnalysisDates();
		return pendingAnalysis;
	}
	
	/**
	 * Returns the first pending analysis UUID that corresponds to the given date.
	 * 
	 * @param date the requested date.
	 * @return the first pending analysis UUID that corresponds to the given date.
	 */
	public String getUuidByDate(String date) {
		
		String uuid = analysisPersistence.getUuidByDate(date);
		return uuid;
	}

	/**
	 * Returns the first date that corresponds to the given pending analysis UUID.
	 * 
	 * @param uuid the requested UUID.
	 * @return the first date that corresponds to the given pending analysis UUID.
	 */
	public String getDateByUuid(String uuid) {

		String date = analysisPersistence.getDateByUuid(uuid);
		return date;
	}

	/**
	 * Deletes the given pending analysis UUID from the DB. 
	 * 
	 * @param uuidParam the UUID of the analysis we want to delete from the DB.
	 */
	public void deletePendingAnalysis(String uuidParam) {

		analysisPersistence.removePendingAnalysis(uuidParam);
	}
}