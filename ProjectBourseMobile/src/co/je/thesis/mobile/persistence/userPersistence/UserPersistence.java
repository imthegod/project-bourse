package co.je.thesis.mobile.persistence.userPersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import co.je.thesis.mobile.persistence.DatabaseOpenHelper;

/**
 * This class is responsible for handling the user data persistence.
 * 
 * @author Julian Espinel
 */
public class UserPersistence {

	/**
	 * Constant for logging purposes.
	 */
	public static final String TAG = "UserPersistence";

	/**
	 * Constant to model the users table name.
	 */
	public static final String USERS = "users";
	
	/**
	 * Table column named "user_name"
	 */
	public static final String USER_NAME = "user_name";

	/**
	 * Attribute that allow us to connect to the SQLiteDB.
	 */
	private DatabaseOpenHelper dbOpenHelper;
	
	/**
	 * Attribute that exposes methods to manage a SQLite database.
	 */
	private SQLiteDatabase liteDb;

	/**
	 * UserPersistence constructor.
	 * 
	 * @param context the Android App context.
	 * @see http://developer.android.com/reference/android/content/Context.html
	 */
	public UserPersistence(Context context) {

		dbOpenHelper = new DatabaseOpenHelper(context);
		liteDb = dbOpenHelper.getDbInstance();

		createTable(liteDb);
	}

	/**
	 * Creates the users table into the DB.
	 * 
	 * @param db SQLiteDatabase object. It exposes methods to manage a SQLite database.
	 */
	private void createTable(SQLiteDatabase db) {

		String createQuery = getCreateUserTableQuery();
		db.execSQL(createQuery);
	}

	/**
	 * Returns the query needed to create the users table into the DB.
	 * 
	 * @return the query needed to create the users table into the DB.
	 */
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

	/**
	 * Determines if the user name is already set into the DB or not.
	 * 
	 * @return if the user name is already set into the DB, then returns true, else returns false.
	 */
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

	/**
	 * Returns the user name stored into the DB.
	 * 
	 * @return the user name stored into the DB.
	 */
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