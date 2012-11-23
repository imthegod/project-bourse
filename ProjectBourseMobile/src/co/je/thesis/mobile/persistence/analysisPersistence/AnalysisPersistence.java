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

public class AnalysisPersistence {

	public static final String TAG = "AnalysisPersistence";

	public static final String PENDINGS_ANALYSIS = "pendings_analysis";
	public static final String UUID = "uuid";
	public static final String DATE = "date";

	private DatabaseOpenHelper dbOpenHelper;
	private SQLiteDatabase liteDb;

	public AnalysisPersistence(Context context) {

		dbOpenHelper = new DatabaseOpenHelper(context);
		liteDb = dbOpenHelper.getDbInstance();

		createTable(liteDb);
	}

	private void createTable(SQLiteDatabase db) {

		String createQuery = getCreatePendingsAnalysisTableQuery();
		db.execSQL(createQuery);
	}

	private String getCreatePendingsAnalysisTableQuery() {

		String createUserTableQuery = "create table if not exists " + PENDINGS_ANALYSIS + " ("
				+ UUID + " text primary key, " + DATE + " text)";

		return createUserTableQuery;
	}

	private String getCurrentDate() {

		String pattern = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Date date = new Date();
		String currentDate = sdf.format(date);

		return currentDate;
	}

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
