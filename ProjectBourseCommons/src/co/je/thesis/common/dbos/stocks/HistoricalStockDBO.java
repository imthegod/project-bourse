package co.je.thesis.common.dbos.stocks;

import java.util.Date;

import com.mongodb.BasicDBObject;

/**
 * Class which models a HistoricalStock object, an in addition extends BasicDBObject
 * in order to be able to save objects of this class into MongoDB.
 * 
 * @author Julian Espinel
 */
public class HistoricalStockDBO extends BasicDBObject {
	
	public static final String DATE = "date";
	public static final String OPEN = "open";
	public static final String HIGH = "high";
	public static final String LOW = "low";
	public static final String CLOSE = "close";
	public static final String VOLUME = "volume";

	/*
	 * Do not delete this constructor. It is needed by the MongoDB java Driver.
	 */
	public HistoricalStockDBO() {
	}

	/**
	 * Constructor with parameters.
	 * 
	 * @param date the date when the data stored in this object were taken.
	 * @param open the open price of the stock on this date.
	 * @param high the highest price of the stock on this date.
	 * @param low the lowest price of the stock on this date.
	 * @param close the close price of the stock on this date.
	 * @param volume the traded volume of the stock on this date.
	 */
	public HistoricalStockDBO(Date date, double open, double high, double low, double close, double volume) {

		put(DATE, date);
		put(OPEN, open);
		put(HIGH, high);
		put(LOW, low);
		put(CLOSE, close);
		put(VOLUME, volume);
	}

	/**
	 * Creates a HistoricalStockDBO just with a date field. This object is created
	 * in order to use it as a query object. Mongo DB will use this query object to
	 * find an object in the database which has the same date and retrieve the full
	 * DBO.
	 * 
	 * @param date The date of the query object
	 * @return a HistoricalStockDBO just with a date field. This object will work as
	 *         a query object
	 */
	public static HistoricalStockDBO createQueryDBO(Date date) {
		
		HistoricalStockDBO queryDbo = new HistoricalStockDBO();
		queryDbo.put(DATE, date);

		return queryDbo;
	}
	
	/**
	 * Return the date when the data stored in this object were taken.
	 * 
	 * @return the date when the data stored in this object were taken.
	 */
	public Date getDate() {

		Date date = (Date) get(DATE);
		return date;
	}

	/**
	 * Returns the open price of the stock on this date.
	 * 
	 * @return the open price of the stock on this date.
	 */
	public double getOpen() {

		double open = getDouble(OPEN);
		return open;
	}

	/**
	 * Returns the highest price of the stock on this date.
	 * 
	 * @return the highest price of the stock on this date.
	 */
	public double getHigh() {

		double high = getDouble(HIGH);
		return high;
	}

	/**
	 * Returns the lowest price of the stock on this date.
	 * 
	 * @return the lowest price of the stock on this date.
	 */
	public double getLow() {

		double low = getDouble(LOW);
		return low;
	}

	/**
	 * Returns the close price of the stock on this date.
	 * 
	 * @return the close price of the stock on this date.
	 */
	public double getClose() {

		double close = getDouble(CLOSE);
		return close;
	}

	/**
	 * Returns the traded volume of the stock on this date.
	 * 
	 * @return the traded volume of the stock on this date.
	 */
	public double getVolume() {

		double volume = getDouble(VOLUME);
		return volume;
	}
}