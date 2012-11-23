package co.je.thesis.mobile.persistence.userPersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import co.je.thesis.mobile.persistence.DatabaseOpenHelper;

public class UserPersistence {

	public static final String TAG = "UserPersistence";

	public static final String USERS = "users";
	public static final String USER_NAME = "user_name";

	private DatabaseOpenHelper dbOpenHelper;
	private SQLiteDatabase liteDb;

	public UserPersistence(Context context) {

		dbOpenHelper = new DatabaseOpenHelper(context);
		liteDb = dbOpenHelper.getDbInstance();

		createTable(liteDb);
	}

	private void createTable(SQLiteDatabase db) {

		String createQuery = getCreateUserTableQuery();
		db.execSQL(createQuery);
	}

	private String getCreateUserTableQuery() {

		String createUserTableQuery = "create table if not exists " + USERS + " (" + USER_NAME
				+ " text primary key)";

		return createUserTableQuery;
	}

	public void setUserName(String userName) {

		if (userName != null && !userName.isEmpty()) {

			if (!userNameHasBeenSet()) {

				ContentValues values = new ContentValues();
				values.put(USER_NAME, userName);

				long result = liteDb.insert(USERS, null, values);

				if (result == -1) {

					Log.e(TAG, "Insert error");
					String exceptionMessage = "User with username: " + userName
							+ " already exists in the DB.";
					throw new SQLiteException(exceptionMessage);
				}

			}
		}
	}

	public boolean userNameHasBeenSet() {

		String[] columns = null; // all columns
		String selection = null; // all rows for the given table
		String[] selectionArgs = null; // no selection
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor cursor = liteDb.query(USERS, columns, selection, selectionArgs, groupBy, having,
				orderBy, limit);

		boolean answer = false;

		if (cursor.getCount() > 0) {

			cursor.moveToPosition(0);
			String userName = cursor.getString(0);

			if (!userName.isEmpty()) {

				answer = true;
			}
		}

		return answer;
	}

	public String getUserName() {

		String[] columns = null; // all columns
		String selection = null; // all rows for the given table
		String[] selectionArgs = null; // no selection
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor cursor = liteDb.query(USERS, columns, selection, selectionArgs, groupBy, having,
				orderBy, limit);

		String userNameAnswer = "";

		if (cursor.getCount() > 0) {

			cursor.moveToPosition(0);
			String userName = cursor.getString(0);

			if (!userName.isEmpty()) {

				userNameAnswer = userName;
			}
		}

		return userNameAnswer;
	}
}
