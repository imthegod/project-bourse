package co.je.thesis.mobile.persistence.analysisPersistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import co.je.thesis.mobile.persistence.DatabaseOpenHelper;

/**
 * This class is responsible for the persistence of the analysis requests data.
 * 
 * @author Julian Espinel
 */
public class AnalysisPersistence {

	/**
	 * Constant for logging purposes.
	 */
	public static final String TAG = "AnalysisPersistence";

	/**
	 * The name of the table that stores the pending analyses.
	 */
	public static final String PENDINGS_ANALYSIS = "pendings_analysis";
	
	/**
	 * Constant to represent an analysis UUID.
	 */
	public static final String UUID = "uuid";
	
	/**
	 * Constant to represent an analysis date.
	 */
	public static final String DATE = "date";

	/**
	 * Attribute that allow us to connect to the SQLiteDB.
	 */
	private DatabaseOpenHelper dbOpenHelper;
	
	/**
	 * Attribute that exposes methods to manage a SQLite database. 
	 */
	private SQLiteDatabase liteDb;

	/**
	 * AnalysisPersistence constructor.
	 * 
	 * @param context the Android App context.
	 * @see http://developer.android.com/reference/android/content/Context.html
	 */
	public AnalysisPersistence(Context context) {

		dbOpenHelper = new DatabaseOpenHelper(context);
		liteDb = dbOpenHelper.getDbInstance();

		createPendingAnalysisTable();
	}

	/**
	 * Creates the pending analysis table.
	 */
	private void createPendingAnalysisTable() {

		String createQuery = getCreatePendingsAnalysisTableQuery();
		liteDb.execSQL(createQuery);
	}

	/**
	 * Returns the query necessary to create the pending analysis table.
	 * 
	 * @return the query necessary to create the pending analysis table.
	 */
	private String getCreatePendingsAnalysisTableQuery() {

		String createUserTableQuery = "create table if not exists " + PENDINGS_ANALYSIS + " ("
				+ UUID + " text primary key, " + DATE + " text)";

		return createUserTableQuery;
	}

	/**
	 * Returns the current date in the following format:"dd-MM-yyyy HH:mm:ss" 
	 * 
	 * @return the current date in the following format:"dd-MM-yyyy HH:mm:ss"
	 */
	private String getCurrentDate() {

		String pattern = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Date date = new Date();
		String currentDate = sdf.format(date);

		return currentDate;
	}

	/**
	 * Adds a new pending analysis to the DB.
	 * 
	 * @param uuid the UUID of the analysis we want to add to the DB.
	 */
	public void addPendingAnalysis(String uuid) {

		ContentValues values = new ContentValues();
		values.put(UUID, uuid);
		values.put(DATE, getCurrentDate());

		if (uuid != null && !uuid.isEmpty()) {

			long result = liteDb.insert(PENDINGS_ANALYSIS, null, values);

			if (result == -1) {

				Log.e(TAG, "Insert error");
				String exceptionMessage = "Analysis with uuid: " + uuid
						+ " already exists in the DB.";
				throw new SQLiteException(exceptionMessage);
			}
		}
	}

	/**
	 * Determines if there are pending analyses.
	 * 
	 * @return if there are pending analyses, then returns true, else returns false.
	 */
	public boolean existsPendingAnalysis() {

		String[] columns = null; // all columns
		String selection = null; // all rows for the given table
		String[] selectionArgs = null; // no selection
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor cursor = liteDb.query(PENDINGS_ANALYSIS, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);

		boolean answer = false;

		if (cursor.getCount() > 0) {

			cursor.moveToPosition(0);
			String uuid = cursor.getString(0);

			if (!uuid.isEmpty()) {

				answer = true;
			}
		}

		return answer;
	}

	/**
	 * Returns a pending analysis given its UUID.
	 * 
	 * @param uuidParam the UUID of the pending analysis we are looking for. 
	 * @return a pending analysis given its UUID.
	 */
	public String getPendingAnalysis(String uuidParam) {

		String[] columns = null; // all columns
		String selection = UUID + " = ?";
		String[] selectionArgs = { uuidParam };
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor cursor = liteDb.query(PENDINGS_ANALYSIS, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);

		String uuidAnswer = "";

		if (cursor.getCount() > 0) {

			cursor.moveToPosition(0);
			String uuidFromCursor = cursor.getString(0);

			if (!uuidFromCursor.isEmpty()) {

				uuidAnswer = uuidFromCursor;
			}
		}

		return uuidAnswer;
	}

	/**
	 * Returns an ArrayList with the UUIDs of all the pending analysis stored into the DB.
	 * 
	 * @return an ArrayList with the UUIDs of all the pending analysis stored into the DB.
	 */
	public ArrayList<String> getAllPendingAnalysisUuids() {

		String[] columns = null; // all columns
		String selection = null; // all rows for the given table
		String[] selectionArgs = null; // no selection
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor cursor = liteDb.query(PENDINGS_ANALYSIS, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);

		ArrayList<String> pendingAnalysis = new ArrayList<String>();

		for (int i = 0; i < cursor.getCount(); i++) {

			cursor.moveToPosition(i);

			String uuidFromCursor = cursor.getString(0);
			pendingAnalysis.add(uuidFromCursor);
		}

		return pendingAnalysis;
	}

	/**
	 * Returns an ArrayList with the dates of all the pending analysis stored into the DB.
	 * 
	 * @return an ArrayList with the dates of all the pending analysis stored into the DB.
	 */
	public ArrayList<String> getAllPendingAnalysisDates() {

		String[] columns = null; // all columns
		String selection = null; // all rows for the given table
		String[] selectionArgs = null; // no selection
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor cursor = liteDb.query(PENDINGS_ANALYSIS, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);

		ArrayList<String> pendingAnalysis = new ArrayList<String>();

		for (int i = 0; i < cursor.getCount(); i++) {

			cursor.moveToPosition(i);

			String dateFromCursor = cursor.getString(1);
			pendingAnalysis.add(dateFromCursor);
		}

		return pendingAnalysis;
	}

	/**
	 * Returns the UUID of a pending analysis with the given date.
	 * 
	 * @param dateParam the date of the pending analysis that we want looking for.
	 * @return the UUID of a pending analysis with the given date.
	 */
	public String getUuidByDate(String dateParam) {
		
		String[] columns = null; // all columns
		String selection = DATE + " = ?";
		String[] selectionArgs = { dateParam };
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor cursor = liteDb.query(PENDINGS_ANALYSIS, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);

		String uuidAnswer = "";

		if (cursor.getCount() > 0) {

			cursor.moveToPosition(0);
			String uuidFromCursor = cursor.getString(0);
			String dateFromCursor = cursor.getString(1);

			if (!uuidFromCursor.isEmpty() && !dateFromCursor.isEmpty()) {

				uuidAnswer = uuidFromCursor;
			}
		}

		return uuidAnswer;
	}
	
	/**
	 * Returns the date of a pending analysis given its UUID.
	 * 
	 * @param uuidParam the UUID of the pending analysis we are looking for.
	 * @return the date of a pending analysis given its UUID.
	 */
	public String getDateByUuid(String uuidParam) {

		String[] columns = null; // all columns
		String selection = UUID + " = ?";
		String[] selectionArgs = { uuidParam };
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor cursor = liteDb.query(PENDINGS_ANALYSIS, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);

		String uuidAnswer = "";

		if (cursor.getCount() > 0) {

			cursor.moveToPosition(0);
			String uuidFromCursor = cursor.getString(0);
			String dateFromCursor = cursor.getString(1);

			if (!uuidFromCursor.isEmpty() && !dateFromCursor.isEmpty()) {

				uuidAnswer = dateFromCursor;
			}
		}

		return uuidAnswer;
	}

	/**
	 * Removes the pending analysis of the given UUID from the DB.
	 * 
	 * @param uuidParam the UUID of the pending analysis that we want to remove from the DB.
	 */
	public void removePendingAnalysis(String uuidParam) {

		String uuid = getPendingAnalysis(uuidParam);

		if (!uuid.isEmpty()) {

			String whereClause = UUID + " = ?";
			String[] whereArgs = { uuid };
			int rowsAffected = liteDb.delete(PENDINGS_ANALYSIS, whereClause, whereArgs);

			if (rowsAffected != 1) {

				String exceptionMessage = "Delete error.";
				Log.e(TAG, exceptionMessage);
				throw new SQLiteException(exceptionMessage);
			}
		}
	}
}