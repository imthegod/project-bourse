package co.je.thesis.setup.rules.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import co.je.thesis.common.dbos.dsl.DSLElementDBO;
import co.je.thesis.common.dbos.rules.RuleDBO;

public class ValidRulesParser {
	
	/**
	 * Builds a rule with the elements in the parameter array.
	 * 
	 * @param ruleElements, An array which contains the rule elements.
	 * @return A RuleDBO object with its elements.
	 */
	private RuleDBO BuildRuleDBO(Cell[] ruleElements) {
		
		ArrayList<DSLElementDBO> dslElementsDBO = new ArrayList<DSLElementDBO>();
		
		// The element 0 is the rule header
		for (int i = 1; i < ruleElements.length; i++) {
			
			String category = ruleElements[i].getContents();
			DSLElementDBO elementDBO = new DSLElementDBO(category, "");
			dslElementsDBO.add(elementDBO);
		}
		
		RuleDBO ruleDBO = new RuleDBO(dslElementsDBO);
		
		return ruleDBO;
	}
	
	/**
	 * Returns an ArrayList of RuleDBO objects. Each object into the ArrayList
	 * represents a valid rule.
	 * 
	 * @param excelFile, The file from the valid rules will be retrieved.
	 * @return Returns an ArrayList of RuleDBO objects.
	 */
	public ArrayList<RuleDBO> buildValidRulesDBOs(File excelFile) {
		
		ArrayList<RuleDBO> validRules = new ArrayList<RuleDBO>();
		
		try {
			
			Workbook book = Workbook.getWorkbook(excelFile);
			Sheet sheet = book.getSheet(0);
			
			int numberOfColumns = sheet.getColumns();
			
			// Each column into the Excel file represents a valid rule.
			for (int i = 0; i < numberOfColumns; i++) {
				
				Cell[] ruleElements = sheet.getColumn(i);
				RuleDBO ruleDBO = BuildRuleDBO(ruleElements);
				validRules.add(ruleDBO);
			}
			
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return validRules;
	}
}
