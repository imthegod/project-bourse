package co.je.thesis.mobile.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "project_bourse";
	public static final int DB_VERSION = 1;
	
	private static SQLiteDatabase dbInstance;
	
	public SQLiteDatabase getDbInstance() {
		
		if (dbInstance == null) {
			
			dbInstance = getWritableDatabase();
		}
		
		return dbInstance;
	}
	
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
