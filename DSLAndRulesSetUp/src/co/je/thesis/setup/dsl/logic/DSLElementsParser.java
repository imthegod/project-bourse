package co.je.thesis.setup.dsl.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import co.je.thesis.common.dbos.dsl.DSLElementDBO;

public class DSLElementsParser {
	
	/**
	 * Builds an ArrayList of DSLElementDBO objects. Each object of the ArrayList
	 * is created with the information of the categoryCells and the valueCells
	 * array.
	 * 
	 * @param categoryCells, Array which contains the category values for the
	 * 		  DSLElementDBO objects.
	 * @param valueCells, Array which contains the value values for the
	 * 		  DSLElementDBO objects.
	 * @return Builds an ArrayList of DSLElementDBO objects.
	 */
	private ArrayList<DSLElementDBO> buildElementsDBO(Cell[] categoryCells,
			Cell[] valueCells) {
		
		ArrayList<DSLElementDBO> dslElementsDBO = new ArrayList<DSLElementDBO>();
		
		if (categoryCells.length == valueCells.length) {
			
			// i begins at 1, because 0 is the header
			for (int i = 1; i < categoryCells.length; i++) {
				
				Cell categoryCell = categoryCells[i];
				String category = categoryCell.getContents();
				
				Cell valueCell = valueCells[i];
				String value = valueCell.getContents();
				
				DSLElementDBO elementDBO = new DSLElementDBO(category, value);
				dslElementsDBO.add(elementDBO);
			}
		}
		
		return dslElementsDBO;
	}
	
	/**
	 * Returns the DSL elements according to the file name. For example if the file
	 * name is "dsl_properties.xls", then will return an ArrayList with the 
	 * DSLElementsDBO objects of properties elements of the DSL.
	 * 
	 * @param fileName, the name of the file from which the data will be retrieved.
	 * @return Returns the DSL elements according to the file name.
	 */
	public ArrayList<DSLElementDBO> buildDSLElements(File excelFile) {
		
		ArrayList<DSLElementDBO> dslElementsDBO = new ArrayList<DSLElementDBO>();
		
		try {
			
			Workbook book = Workbook.getWorkbook(excelFile);
			Sheet sheet = book.getSheet(0);
			
			Cell[] categoryCells = sheet.getColumn(0);
			Cell[] valueCells = sheet.getColumn(1);
			
			System.out.println("categoryCells: " + categoryCells.length);
			
			dslElementsDBO = buildElementsDBO(categoryCells, valueCells);
			
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dslElementsDBO;
	}
}
