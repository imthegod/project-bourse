package co.je.thesis.common.dtos.stocks;

import java.util.Date;

/**
 * Class to represent the stock historical information.
 * 
 * One HistoricalStock object represents the basic information of a stock in a
 * specific day. In this way, in order to know (i.e.) the historical prices of a
 * stock, we will need many (a lot!) of HistoricalStock objects per Stock.
 * 
 * @author Julian Espinel
 */
public class HistoricalStock implements Comparable<HistoricalStock> {

	/**
	 * The date when the information contained in this object was produced by the
	 * stock market
	 */
	private Date date;

	/**
	 * Opening price of the stock on the day indicated by the date field
	 */
	private double open;

	/**
	 * Highest price of the stock on the day indicated by the date field
	 */
	private double high;

	/**
	 * Lowest price of the stock on the day indicated by the date field
	 */
	private double low;

	/**
	 * Closing price of the stock on the day indicated by the date field
	 */
	private double close;

	/**
	 * Negotiated volume of the stock on the day indicated by the date field
	 */
	private double volume;

	public HistoricalStock(Date date, double open, double high, double low, double close, double volume) {

		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}

	public Date getDate() {

		return date;
	}

	public double getOpen() {

		return open;
	}

	public double getHigh() {

		return high;
	}

	public double getLow() {

		return low;
	}

	public double getClose() {

		return close;
	}

	public double getVolume() {

		return volume;
	}

	@Override
	public String toString() {

		String stockInfo = "date: " + date + ", close: " + close + ", volume: " + volume;
		return stockInfo;
	}

	/**
	 * Defines if the object is empty based on its date field. If the date field is
	 * null, then the object is empty, else it is not
	 * 
	 * @return True if the date field is null, else, returns false
	 */
	public boolean isEmpty() {

		boolean isDateNull = (date == null);
		return isDateNull;
	}

	@Override
	public int compareTo(HistoricalStock historicalStock) {

		int answer = - 1;

		boolean hasSameDate = (date.equals(historicalStock.getDate()));
		boolean hasSameOpen = (open == historicalStock.getOpen());
		boolean hasSameHigh = (high == historicalStock.getHigh());
		boolean hasSameLow = (low == historicalStock.getLow());
		boolean hasSameClose = (close == historicalStock.getClose());
		boolean hasSameVolume = (volume == historicalStock.getVolume());

		boolean areTheSameObject = hasSameDate && hasSameOpen && hasSameHigh && hasSameLow && hasSameClose && hasSameVolume;

		if (areTheSameObject) {

			answer = 0;
		}

		return answer;
	}
}