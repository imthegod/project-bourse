package co.je.thesis.mobile.logic.analysisManager;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import co.je.thesis.mobile.persistence.analysisPersistence.AnalysisPersistence;
import co.je.thesis.mobile.persistence.userPersistence.UserPersistence;

public class AnalysisManager {

	private UserPersistence userPersistence;
	private AnalysisPersistence analysisPersistence;

	public AnalysisManager(Context context) {

		userPersistence = new UserPersistence(context);
		analysisPersistence = new AnalysisPersistence(context);
	}

	public boolean userNameHasBeenSet() {

		boolean answer = userPersistence.userNameHasBeenSet();
		return answer;
	}

	public void setUserName(String userName) {

		if (!userPersistence.userNameHasBeenSet()) {

			userPersistence.setUserName(userName);

		} else {

			String exceptionMessage = "There is already a username in the DB.";
			throw new SQLiteException(exceptionMessage);
		}
	}

	public String getUserName() {

		String userName = userPersistence.getUserName();
		return userName;
	}

	public boolean existsPengingAnalysis() {

		boolean answer = analysisPersistence.existsPendingAnalysis();
		return answer;
	}

	public void addPendingAnalysis(String uuid) {

		analysisPersistence.addPendingAnalysis(uuid);
	}

	public String getPendingAnalysis(String uuidParam) {

		String uuid = analysisPersistence.getPendingAnalysis(uuidParam);
		return uuid;
	}

	public ArrayList<String> getAllPendingAnalysisUuids() {
		
		ArrayList<String> pendingAnalysis = analysisPersistence.getAllPendingAnalysisUuids();
		return pendingAnalysis;
	}

	public ArrayList<String> getAllPendingAnalysisDates() {
		
		ArrayList<String> pendingAnalysis = analysisPersistence.getAllPendingAnalysisDates();
		return pendingAnalysis;
	}
	
	public String getUuidByDate(String date) {
		
		String uuid = analysisPersistence.getUuidByDate(date);
		return uuid;
	}

	public String getDateByUuid(String uuid) {

		String date = analysisPersistence.getDateByUuid(uuid);
		return date;
	}

	public void deletePendingAnalysis(String uuidParam) {

		analysisPersistence.removePendingAnalysis(uuidParam);
	}
}