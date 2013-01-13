package co.je.thesis.mobile.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A helper class to manage database creation and version management.
 * 
 * @author Julian Espinel
 * @see http://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

	/**
	 * Constant that stores the DB's name.
	 */
	public static final String DB_NAME = "project_bourse";
	
	/**
	 * Initial version of the DB.
	 */
	public static final int DB_VERSION = 1;
	
	/**
	 * Attribute that exposes methods to manage a SQLite database.
	 * This attribute can be initialized only once.
	 */
	private static SQLiteDatabase dbInstance;
	
	/**
	 * Returns the dbInstance attribute. If it is already initialized then just return it,
	 * else the method initializes and return the dbInstance.  
	 * 
	 * @return the dbInstance attribute.
	 */
	public SQLiteDatabase getDbInstance() {
		
		if (dbInstance == null) {
			
			dbInstance = getWritableDatabase();
		}
		
		return dbInstance;
	}
	
	/**
	 * DatabaseOpenHelper constructor.
	 * 
	 * @param context the Android App context.
	 * @see http://developer.android.com/reference/android/content/Context.html
	 */
	public DatabaseOpenHelper(Context context) {
		
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}