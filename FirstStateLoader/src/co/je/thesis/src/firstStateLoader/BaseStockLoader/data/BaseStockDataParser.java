package co.je.thesis.src.firstStateLoader.BaseStockLoader.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.src.firstStateLoader.common.exceptions.StringHasParenthesesException;

/**
 * Class to parse data
 * 
 * @author Julian Espinel
 */
public class BaseStockDataParser {

	/**
	 * Given an array of Cells this method returns an ArrayList. Each position of
	 * the ArrayList, contains a String object with the information of one cell of
	 * the cells array.
	 * 
	 * @param cells, Array of objects of type Cell
	 * @return An ArrayList of String objects with the information of each cell
	 */
	private ArrayList<String> addNamesToArray(Cell[] cells) {

		ArrayList<String> names = new ArrayList<String>();

		/*
		 * i Begins from 1, because cells[i] when i == 0 are the columns headers
		 */
		for (int i = 1; i < cells.length; i++) {

			Cell cell = cells[i];
			String name = cell.getContents();

			names.add(name);
		}

		return names;
	}

	/**
	 * Returns an ArrayList of String objects with the name of the stocks contained
	 * in the Excel file
	 * 
	 * @param excelFile, The representation of an Excel file. See jxl.jar library
	 * @return An ArrayList of String objects with the name of the stocks contained
	 *         in the Excel file
	 */
	public ArrayList<String> getStockNamesFromExcelFile(File excelFile) {

		ArrayList<String> names = new ArrayList<String>();

		try {
			Workbook book = Workbook.getWorkbook(excelFile);

			Sheet[] sheets = book.getSheets();
			for (int i = 0; i < sheets.length; i++) {

				Sheet sheet = sheets[i];
				Cell[] columnCells = sheet.getColumn(1);

				ArrayList<String> cellsNames = addNamesToArray(columnCells);
				names.addAll(cellsNames);
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return names;
	}

	/**
	 * Verifies that a given String object, doesn't contains parentheses
	 * 
	 * @param word String object to be verified
	 * @throws StringHasParenthesesException This exception is thrown when the
	 *         parameter word contains parentheses
	 */
	private void verifyStringDoesntContainParentheses(String word) throws StringHasParenthesesException {

		boolean hasRightParentheses = word.contains("(");
		boolean hasLeftParentheses = word.contains(")");

		boolean hasParentheses = hasRightParentheses || hasLeftParentheses;

		if (hasParentheses) {
			String message = "The word: " + word + " has parentheses";
			throw new StringHasParenthesesException(message);
		}
	}

	/**
	 * Given the stockInfo in the following format: "company name (symbol)", returns
	 * a String object formatted as follows: "company name;symbol"
	 * 
	 * @param stockInfo The stock information in this format:
	 *        "company name (symbol)"
	 * @return A String object formatted as follows: "company name;symbol"
	 */
	private String getSymbolAndName(String stockInfo) {

		String regex = " ";
		String[] array = stockInfo.split(regex);
		
		String stockName = "";
		// because array[array.length - 1] is the stock symbol
		int stockSymbolPosition = array.length - 1; 
		for (int i = 0; i < stockSymbolPosition; i++) {
			
			stockName += array[i] + regex;
		}
		
		stockName = stockName.trim();

		String stockSymbol = array[stockSymbolPosition];
		int lastPosition = stockSymbol.length();
		// Remove the initial and last parentheses from the stock symbol
		stockSymbol = stockSymbol.substring(1, lastPosition - 1);

		String symbolAndName = stockSymbol + ";" + stockName;
		return symbolAndName;
	}

	/**
	 * Returns an ArrayList which contains BaseStock objects
	 * 
	 * @param stocksSymbolAndName An ArrayList that contains String objects. Each
	 *        String in the ArrayList follows the next format: "company name (symbol)"
	 * @return An ArrayList which contains BaseStock objects
	 */
	public List<BaseStock> getBaseStocks(List<String> stocksSymbolAndName) {

		ArrayList<BaseStock> baseStocks = new ArrayList<BaseStock>();

		String regex = ";";
		for (int i = 0; i < stocksSymbolAndName.size(); i++) {

			String stockInfo = stocksSymbolAndName.get(i);
			String symbolAndName = getSymbolAndName(stockInfo);

			String[] array = symbolAndName.split(regex);
			String stockSymbol = array[0];
			String stockName = array[1];

			try {
				
				verifyStringDoesntContainParentheses(stockSymbol);

			} catch (StringHasParenthesesException e) {

				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			System.out.println("symbo: " + stockSymbol + ", name: " + stockName);
			
			BaseStock baseStock = new BaseStock(stockSymbol, stockName);
			baseStocks.add(baseStock);
		}

		return baseStocks;
	}
}