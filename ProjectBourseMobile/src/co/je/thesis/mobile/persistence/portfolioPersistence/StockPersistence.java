package co.je.thesis.mobile.persistence.portfolioPersistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import co.je.thesis.mobile.persistence.dbo.StockDBO;
import co.je.thesis.mobile.persistence.translators.PortfolioTranslator;

public class StockPersistence {

	// -----------------------------------------------------------------------------------
	// Constants
	// -----------------------------------------------------------------------------------

	public static final String STOCKS = "stocks";
	public static final String TAG = "StockPersistence";

	// Table fields

	public static final String SYMBOL = "symbol";
	public static final String NAME = "name";
	public static final String NUMBER_OF_SHARES = "number_of_shares";
	public static final String PORTFOLIO_NAME = "portfolio_name";
	public static final String BASE_PRICE = "base_price";

	public static final String STOP_LOSS_1 = "stop_loss_1";
	public static final String STOP_LOSS_2 = "stop_loss_2";
	public static final String STOP_LOSS_3 = "stop_loss_3";

	public static final String TAKE_PROFIT_1 = "take_profit_1";
	public static final String TAKE_PROFIT_2 = "take_profit_2";
	public static final String TAKE_PROFIT_3 = "take_profit_3";

	// -----------------------------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------------------------
	
	private String stocksTableName;

	// -----------------------------------------------------------------------------------
	// Methods
	// -----------------------------------------------------------------------------------

	public StockPersistence(SQLiteDatabase db, String portfolioName) {
		
		String correctedPortfolioNameForDB = PortfolioTranslator.getCorrectedNameForDb(portfolioName);
		stocksTableName = STOCKS + "_" + correctedPortfolioNameForDB;
		createTable(db);
	}

	private void createTable(SQLiteDatabase db) {

		String createQuery = getCreateStocksTableQuery();
		db.execSQL(createQuery);
	}

	private String getCreateStocksTableQuery() {

		String createStocksTableQuery = "create table if not exists " + stocksTableName
				+ " (" + SYMBOL + " text primary key, " + NAME + " text, "
				+ NUMBER_OF_SHARES + " integer, " + PORTFOLIO_NAME + " text, "
				+ BASE_PRICE + " real, " + STOP_LOSS_1 + " real, " + STOP_LOSS_2
				+ " real, " + STOP_LOSS_3 + " real, " + TAKE_PROFIT_1 + " real, "
				+ TAKE_PROFIT_2 + " real, " + TAKE_PROFIT_3 + " real, " + "foreign key ("
				+ PORTFOLIO_NAME + ") references " + PortfolioPersistence.PORTFOLIOS
				+ " (" + PortfolioPersistence.NAME + "))";

		// foreign key statement should be the last

		return createStocksTableQuery;
	}

	// -----------------------------------------------------------------------------------
	// Stock CRUD
	// -----------------------------------------------------------------------------------

	public void createSingleStock(SQLiteDatabase db, StockDBO stockDBO) {

		ContentValues values = new ContentValues();
		values.put(SYMBOL, stockDBO.getSymbol());
		values.put(NAME, stockDBO.getName());
		values.put(NUMBER_OF_SHARES, stockDBO.getNumberOfShares());
		values.put(PORTFOLIO_NAME, stockDBO.getPortfolioName());
		values.put(BASE_PRICE, stockDBO.getBasePrice());

		values.put(STOP_LOSS_1, stockDBO.getStopLoss1());
		values.put(STOP_LOSS_2, stockDBO.getStopLoss2());
		values.put(STOP_LOSS_3, stockDBO.getStopLoss3());

		values.put(TAKE_PROFIT_1, stockDBO.getTakeProfit1());
		values.put(TAKE_PROFIT_2, stockDBO.getTakeProfit2());
		values.put(TAKE_PROFIT_3, stockDBO.getTakeProfit3());

		long result = db.insert(stocksTableName, null, values);

		if (result == -1) {

			Log.e(TAG, "Insert error");
			String exceptionMessage = "Stock with symbol: " + stockDBO.getSymbol()
					+ " already exists in the DB.";
			throw new SQLiteException(exceptionMessage);
		}
	}

	public void createMultipleStocks(SQLiteDatabase db, ArrayList<StockDBO> stocksDBO) {

		for (int i = 0; i < stocksDBO.size(); i++) {

			StockDBO stockDBO = stocksDBO.get(i);
			createSingleStock(db, stockDBO);
		}
	}
	
	public static boolean isStockIntoPortfolio(SQLiteDatabase db, String stockSymbol, String portfolioName) {

		String correctedPortfolioNameForDB = PortfolioTranslator.getCorrectedNameForDb(portfolioName);
		
		// Use this local variable, not the attribute
		String stocksTableName = STOCKS + "_" + correctedPortfolioNameForDB;
		
		String[] columns = null; // all columns
		String selection = SYMBOL + " = ?";
		String[] selectionArgs = { stockSymbol + "" };
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor result = db.query(stocksTableName, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);

		boolean answer = false;

		if (result.getCount() == 1) {
			
			result.moveToPosition(0);
			String symbol = result.getString(0);
			
			if (symbol != null) {
				
				if (!symbol.isEmpty()) {
					
					answer = true;
				}
			}
		}

		return answer;
	}

	public StockDBO readSingleStock(SQLiteDatabase db, String stockSymbol) {

		String[] columns = null; // all columns
		String selection = SYMBOL + " = ?";
		String[] selectionArgs = { stockSymbol + "" };
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor result = db.query(stocksTableName, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);

		StockDBO foundStock = null;

		if (result.getCount() > 1) {

			String exceptionMessage = "An error has occured, there are more than one stock with the symbol: "
					+ stockSymbol;
			Log.e(TAG, exceptionMessage);
			throw new SQLiteException(exceptionMessage);

		} else {

			for (int i = 0; i < result.getCount(); i++) {

				result.moveToPosition(i);

				String symbol = result.getString(0);
				String name = result.getString(1);
				int numberOfShares = result.getInt(2);
				String portfolioName = result.getString(3);
				double basePrice = result.getDouble(4);

				double stopLoss1 = result.getDouble(5);
				double stopLoss2 = result.getDouble(6);
				double stopLoss3 = result.getDouble(7);

				double takeProfit1 = result.getDouble(8);
				double takeProfit2 = result.getDouble(9);
				double takeProfit3 = result.getDouble(10);

				foundStock = new StockDBO(symbol, name, numberOfShares, portfolioName,
						basePrice, stopLoss1, stopLoss2, stopLoss3, takeProfit1,
						takeProfit2, takeProfit3);
			}
		}

		return foundStock;
	}

	public ArrayList<StockDBO> readPortfolioStocks(SQLiteDatabase db,
			String portfolioNameParam) {

		String correctedPortfolioNameForDB = PortfolioTranslator.getCorrectedNameForDb(portfolioNameParam);
		
		String[] columns = null; // all columns
		String selection = PORTFOLIO_NAME + " = ?";
		String[] selectionArgs = { correctedPortfolioNameForDB };
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor result = db.query(stocksTableName, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);

		ArrayList<StockDBO> portfolioStocksDBO = new ArrayList<StockDBO>();

		for (int i = 0; i < result.getCount(); i++) {

			result.moveToPosition(i);

			String symbol = result.getString(0);
			String name = result.getString(1);
			int numberOfShares = result.getInt(2);
			String portfolioName = result.getString(3);
			double basePrice = result.getDouble(4);

			double stopLoss1 = result.getDouble(5);
			double stopLoss2 = result.getDouble(6);
			double stopLoss3 = result.getDouble(7);

			double takeProfit1 = result.getDouble(8);
			double takeProfit2 = result.getDouble(9);
			double takeProfit3 = result.getDouble(10);

			StockDBO stockDBO = new StockDBO(symbol, name, numberOfShares, portfolioName,
					basePrice, stopLoss1, stopLoss2, stopLoss3, takeProfit1, takeProfit2,
					takeProfit3);

			portfolioStocksDBO.add(stockDBO);
		}

		return portfolioStocksDBO;
	}

	public void updateStock(SQLiteDatabase db, String stockSymbol, StockDBO updatedStock) {

		String[] columns = null; // all columns
		String selection = SYMBOL + " = ?";
		String[] selectionArgs = { stockSymbol + "" };
		String groupBy = null; // not grouped
		String having = null; // all row groups to be included
		String orderBy = null; // default sort order
		String limit = null; // no limit

		Cursor result = db.query(stocksTableName, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);

		if (result.getCount() > 0) {

			// Values to be updated
			ContentValues values = new ContentValues();
			values.put(NAME, updatedStock.getName());
			values.put(NUMBER_OF_SHARES, updatedStock.getNumberOfShares());
			values.put(PORTFOLIO_NAME, updatedStock.getPortfolioName());
			values.put(BASE_PRICE, updatedStock.getBasePrice());

			values.put(STOP_LOSS_1, updatedStock.getStopLoss1());
			values.put(STOP_LOSS_2, updatedStock.getStopLoss2());
			values.put(STOP_LOSS_3, updatedStock.getStopLoss3());

			values.put(TAKE_PROFIT_1, updatedStock.getTakeProfit1());
			values.put(TAKE_PROFIT_2, updatedStock.getTakeProfit2());
			values.put(TAKE_PROFIT_3, updatedStock.getTakeProfit3());

			String whereClause = SYMBOL + " = ?";
			String[] whereArgs = { stockSymbol };

			int rowsAffected = db.update(stocksTableName, values, whereClause, whereArgs);

			if (rowsAffected != 1) {
				String exceptionMessage = "Update error.";
				Log.e(TAG, exceptionMessage);
				throw new SQLiteException(exceptionMessage);
			}

		} else {

			Log.e(TAG, "it doesn't exists a stock with symbol = " + stockSymbol
					+ "into the db");
		}
	}

	public void deleteSingleStock(SQLiteDatabase db, String stockSymbol) {

		String whereClause = SYMBOL + " = ?";
		String[] whereArgs = { stockSymbol };
		int rowsAffected = db.delete(stocksTableName, whereClause, whereArgs);

		if (rowsAffected != 1) {
			String exceptionMessage = "Delete error.";
			Log.e(TAG, exceptionMessage);
			throw new SQLiteException(exceptionMessage);
		}
	}

	public void deletePortFolioStocks(SQLiteDatabase db, String portfolioName) {

		String correctedPortfolioNameForDB = PortfolioTranslator.getCorrectedNameForDb(portfolioName);
		
		String whereClause = PORTFOLIO_NAME + " = ?";
		String[] whereArgs = { correctedPortfolioNameForDB };
		db.delete(stocksTableName, whereClause, whereArgs);
	}
}
