package co.je.thesis.mobile.persistence.portfolioPersistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import co.je.thesis.mobile.persistence.dbo.PortfolioDBO;
import co.je.thesis.mobile.persistence.translators.PortfolioTranslator;

/**
 * This class is responsible for the persistence of the portfolios data.
 * 
 * @author Julian Espinel
 */
public class PortfolioPersistence {

	// -----------------------------------------------------------------------------------
	// Constants
	// -----------------------------------------------------------------------------------

	/**
	 * Constant to model the portfolios table name.
	 */
	public static final String PORTFOLIOS = "portfolios";
	
	/**
	 * Constant for logging purposes.
	 */
	public static final String TAG = "PortfolioPersistence";

	// Table fields

	/**
	 * Table column named "name".
	 */
	public static final String NAME = "name";
	
	/**
	 * Table column named "number_of_stocks".
	 */
	public static final String NUMBER_OF_STOCKS = "number_of_stocks";

	// -----------------------------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------------------------

	// -----------------------------------------------------------------------------------
	// Methods
	// -----------------------------------------------------------------------------------

	/**
	 * PortfolioPersistence constructor.
	 * 
	 * @param db SQLiteDatabase object. It exposes methods to manage a SQLite database.
	 */
	public PortfolioPersistence(SQLiteDatabase db) {
		
		createTable(db);
	}

	/**
	 * Creates the portfolios table.
	 * 
	 * @param db SQLiteDatabase object. It exposes methods to manage a SQLite database.
	 */
	private void createTable(SQLiteDatabase db) {

		String createQuery = getCreatePortfolioTableQuery();
		db.execSQL(createQuery);
	}

	/**
	 * Returns the query needed to create the portfolios table.
	 * 
	 * @return the query needed to create the portfolios table.
	 */
	private String getCreatePortfolioTableQuery() {

		String createPortfolioTableQuery = "create table if not exists "
				+ PORTFOLIOS + " (" + NAME + " text primary key, "
				+ NUMBER_OF_STOCKS + " integer)";

		return createPortfolioTableQuery;
	}

	// -----------------------------------------------------------------------------------
	// Portfolio CRUD
	// -----------------------------------------------------------------------------------

	/**
	 * Creates a new portfolio into the DB. If a portfolio with the same name already exists
	 * into the DB, then throws a new SQLiteException.
	 * 
	 * @param db SQLiteDatabase object. It exposes methods to manage a SQLite database.
	 * @param portfolioDBO the portfolio to be created into the DB.
	 */
	public void createPortfolio(SQLiteDatabase db, PortfolioDBO portfolioDBO) {

		ContentValues values = new ContentValues();
		
		values.put(NAME, portfolioDBO.getName());
		values.put(NUMBER_OF_STOCKS, portfolioDBO.getNumberOfStocks());

		long result = db.insert(PORTFOLIOS, null, values);

		if (result == -1) {
			
			Log.e(TAG, "Insert error");
			String exceptionMessage = "Portfolio with name: " + portfolioDBO.getName() + " already exists in the DB.";
			throw new SQLiteException(exceptionMessage);
		}
	}

	/**
	 * Retrieves a specific portfolio from the DB.
	 * 
	 * @param db SQLiteDatabase object. It exposes methods to manage a SQLite database.
	 * @param portfolioName the name of the portfolio we want to retrieve.
	 * @return a specific portfolio from the DB.
	 */
	public PortfolioDBO readSinglePortfolio(SQLiteDatabase db, String portfolioName) {

		String correctedPortfolioNameForDB = PortfolioTranslator.getCorrectedNameForDb(portfolioName);
		
		String[] columns = null; // all columns
		String selection = "name = ?";
		String[] selectionArgs = { correctedPortfolioNameForDB };
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor result = db.query(PORTFOLIOS, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);

		PortfolioDBO foundPortfolio = null;

		for (int i = 0; i < result.getCount(); i++) {

			result.moveToPosition(i);

			String name = result.getString(0);
			int numberOfStocks = result.getInt(1);

			foundPortfolio = new PortfolioDBO(name, numberOfStocks);
		}

		return foundPortfolio;
	}
	
	/**
	 * Returns all the portfolios stored into the DB.
	 * 
	 * @param db SQLiteDatabase object. It exposes methods to manage a SQLite database.
	 * @return all the portfolios stored into the DB.
	 */
	public ArrayList<PortfolioDBO> readAllPortfolios(SQLiteDatabase db) {
		
		String[] columns = null; // all columns
		String selection = null; // all rows for the given table
		String[] selectionArgs = null; // no selection
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor result = db.query(PORTFOLIOS, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);
		
		ArrayList<PortfolioDBO> portfoliosDBO = new ArrayList<PortfolioDBO>();

		for (int i = 0; i < result.getCount(); i++) {

			result.moveToPosition(i);

			String name = result.getString(0);
			int numberOfStocks = result.getInt(1);

			PortfolioDBO portfolioDBO = new PortfolioDBO(name, numberOfStocks);
			portfoliosDBO.add(portfolioDBO);
		}
		
		return portfoliosDBO;
	}

	/**
	 * Updates a specific portfolio.
	 * 
	 * @param db SQLiteDatabase object. It exposes methods to manage a SQLite database.
	 * @param portfolioName the name of the portfolio we want to update.
	 * @param updatedPortfolio the PortfolioDBO object that contains the information we want to update.
	 */
	public void updatePortfolio(SQLiteDatabase db, String portfolioName,
			PortfolioDBO updatedPortfolio) {

		String correctedPortfolioNameForDB = PortfolioTranslator.getCorrectedNameForDb(portfolioName);
		
		String[] columns = null; // all columns
		String selection = NAME + " = ?";
		String[] selectionArgs = { correctedPortfolioNameForDB };
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor result = db.query(PORTFOLIOS, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);

		// If the portfolio with ID = idPortfolio exists
		if (result.getCount() > 0) {

			// Values to be updated
			ContentValues values = new ContentValues();
			// Cannot change the portfolio name
			values.put(NUMBER_OF_STOCKS, updatedPortfolio.getNumberOfStocks());

			String whereClause = "name = ?";
			String[] whereArgs = { correctedPortfolioNameForDB };

			int rowsAffected = db.update(PORTFOLIOS, values, whereClause,
					whereArgs);

			if (rowsAffected != 1) {
				
				String exceptionMessage = "Update error.";
				Log.e(TAG, exceptionMessage);
				throw new SQLiteException(exceptionMessage);
			}

		} else {

			Log.e(TAG, "it doesn't exists a portfolio with name = " + portfolioName
					+ "into the db");
		}
	}

	/**
	 * Deletes a portfolio from the database.
	 * 
	 * @param db SQLiteDatabase object. It exposes methods to manage a SQLite database.
	 * @param portfolioName the name of the portfolio we want to delete.
	 */
	public void deletePortfolio(SQLiteDatabase db, String portfolioName) {

		String correctedPortfolioNameForDB = PortfolioTranslator.getCorrectedNameForDb(portfolioName);
		
		String whereClause = "name = ?";
		String[] whereArgs = { correctedPortfolioNameForDB };
		int rowsAffected = db.delete(PORTFOLIOS, whereClause, whereArgs);

		if (rowsAffected != 1) {
			
			String exceptionMessage = "Delete error.";
			Log.e(TAG, exceptionMessage);
			throw new SQLiteException(exceptionMessage);
		}
	}

	// -----------------------------------------------------------------------------------
	// Extra methods
	// -----------------------------------------------------------------------------------
	
	/**
	 * Determines if a portfolio with a given name exists into the DB.
	 * 
	 * @param db SQLiteDatabase object. It exposes methods to manage a SQLite database.
	 * @param portfolioName the name of the portfolio we are looking for.
	 * @return if a portfolio with a given name exists into the DB, then returns true, else
	 * 		   returns false.
	 */
	public static boolean portfolioAlreadyExists(SQLiteDatabase db, String portfolioName) {
		
		String correctedPortfolioNameForDB = PortfolioTranslator.getCorrectedNameForDb(portfolioName);
		
		String[] columns = null; // all columns
		String selection = "name = ?";
		String[] selectionArgs = { correctedPortfolioNameForDB };
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor cursor = db.query(PORTFOLIOS, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);

		boolean portfolioExists = false;
		
		if (cursor.getCount() == 1) {
			
			cursor.moveToPosition(0);
			String portfolioNameFromCursor = cursor.getString(0);
			
			if (portfolioNameFromCursor.equalsIgnoreCase(correctedPortfolioNameForDB)) {
				
				portfolioExists = true;
			}
		}

		return portfolioExists;
	}
	
	/**
	 * Returns an ArrayList with the names of all the portfolios that are stored into the DB.
	 * 
	 * @param db SQLiteDatabase object. It exposes methods to manage a SQLite database.
	 * @return an ArrayList with the names of all the portfolios that are stored into the DB.
	 */
	public static ArrayList<String> getAllPortfolioNames(SQLiteDatabase db) {
		
		String[] columns = null; // all columns
		String selection = null; // all rows for the given table
		String[] selectionArgs = null; // no selection
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor result = db.query(PORTFOLIOS, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);
		
		ArrayList<String> portfolioNames = new ArrayList<String>();

		for (int i = 0; i < result.getCount(); i++) {

			result.moveToPosition(i);
			String portfolioName = result.getString(0);
			
			portfolioNames.add(portfolioName);
		}
		
		return portfolioNames;
	}
}