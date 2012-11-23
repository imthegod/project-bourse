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

	public Date getDate() {

		Date date = (Date) get(DATE);
		return date;
	}

	public double getOpen() {

		double open = getDouble(OPEN);
		return open;
	}

	public double getHigh() {

		double high = getDouble(HIGH);
		return high;
	}

	public double getLow() {

		double low = getDouble(LOW);
		return low;
	}

	public double getClose() {

		double close = getDouble(CLOSE);
		return close;
	}

	public double getVolume() {

		double volume = getDouble(VOLUME);
		return volume;
	}
}