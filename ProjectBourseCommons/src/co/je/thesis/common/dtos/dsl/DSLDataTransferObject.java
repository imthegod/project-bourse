package co.je.thesis.common.dtos.dsl;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;

public class DSLDataTransferObject {

	private int version;

	/**
	 * These attributes are the DSL categories
	 */
	private ArrayList<DSLElementDTO> subjects;
	private ArrayList<DSLElementDTO> properties;
	private ArrayList<DSLElementDTO> mathOperations;
	private ArrayList<DSLElementDTO> specificMathOperations;
	private ArrayList<DSLElementDTO> conditionalOperators;
	private ArrayList<DSLElementDTO> logicalOperators;
	private ArrayList<DSLElementDTO> units;

	public DSLDataTransferObject(int version, ArrayList<DSLElementDTO> subjects,
			ArrayList<DSLElementDTO> properties, ArrayList<DSLElementDTO> mathOperations,
			ArrayList<DSLElementDTO> specificMathOperations,
			ArrayList<DSLElementDTO> conditionalOperators,
			ArrayList<DSLElementDTO> logicalOperators, ArrayList<DSLElementDTO> units) {

		this.version = version;
		this.subjects = subjects;
		this.properties = properties;
		this.mathOperations = mathOperations;
		this.specificMathOperations = specificMathOperations;
		this.conditionalOperators = conditionalOperators;
		this.logicalOperators = logicalOperators;
		this.units = units;
	}

	public int getVersion() {
		return version;
	}

	public ArrayList<DSLElementDTO> getSubjects() {
		return subjects;
	}

	public ArrayList<DSLElementDTO> getProperties() {
		return properties;
	}

	public ArrayList<DSLElementDTO> getMathOperations() {
		return mathOperations;
	}
	
	public ArrayList<DSLElementDTO> getSpecificMathOperations() {
		return specificMathOperations;
	}

	public ArrayList<DSLElementDTO> getConditionalOperators() {
		return conditionalOperators;
	}

	public ArrayList<DSLElementDTO> getLogicalOperators() {
		return logicalOperators;
	}

	public ArrayList<DSLElementDTO> getUnits() {
		return units;
	}
	
	public DSLElementDTO getDSLElementFromStringValue(String value) {
		
		ArrayList<DSLElementDTO> allDSLElements = new ArrayList<DSLElementDTO>();
		allDSLElements.addAll(subjects);
		allDSLElements.addAll(properties);
		allDSLElements.addAll(mathOperations);
		allDSLElements.addAll(specificMathOperations);
		allDSLElements.addAll(conditionalOperators);
		allDSLElements.addAll(logicalOperators);
		allDSLElements.addAll(units);
		
		boolean foundElement = false;
		DSLElementDTO answer = new DSLElementDTO("", "");
		
		for (int i = 0; i < allDSLElements.size() && !foundElement; i++) {
			
			DSLElementDTO dslElementDTO = allDSLElements.get(i);
			String elementValue = dslElementDTO.getValue();
			
			if (elementValue.equalsIgnoreCase(value)) {
				
				foundElement = true;
				answer = dslElementDTO;
			}
		}
		
		return answer;
	}

	private ArrayList<String> getCategoryValues(ArrayList<DSLElementDTO> dslElements) {

		ArrayList<String> categoryValues = new ArrayList<String>();

		for (int i = 0; i < dslElements.size(); i++) {

			DSLElementDTO dslElementDTO = dslElements.get(i);
			String value = dslElementDTO.getValue();

			categoryValues.add(value);
		}

		return categoryValues;
	}

	public ArrayList<String> getValuesOfAGivenCategory(String category) {

		ArrayList<String> categoryValues = new ArrayList<String>();

		if (category.equalsIgnoreCase(DSLCategories.SUBJECT)) {

			categoryValues.addAll(getCategoryValues(subjects));

		} else if (category.equalsIgnoreCase(DSLCategories.PROPERTY)) {

			categoryValues.addAll(getCategoryValues(properties));

		} else if (category.equalsIgnoreCase(DSLCategories.MATH_OPERATION)) {

			categoryValues.addAll(getCategoryValues(mathOperations));

		} else if (category.equalsIgnoreCase(DSLCategories.SUMMATION)) {
			
			categoryValues.add(DSLCategories.SUMMATION);

		} else if (category.equalsIgnoreCase(DSLCategories.AVERAGE)) {
			
			categoryValues.add(DSLCategories.AVERAGE);

		} else if (category.equalsIgnoreCase(DSLCategories.CONDITIONAL_OPERATOR)) {

			categoryValues.addAll(getCategoryValues(conditionalOperators));

		} else if (category.equalsIgnoreCase(DSLCategories.LOGICAL_OPERATOR)) {

			categoryValues.addAll(getCategoryValues(logicalOperators));

		} else if (category.equalsIgnoreCase(DSLCategories.TIME_FRAME)) {
			
			categoryValues.add(DSLCategories.TIME_FRAME);
		
		} else if (category.equalsIgnoreCase(DSLCategories.NUMBER)) {
			
			categoryValues.add(DSLCategories.NUMBER);
		
		} else if (category.equalsIgnoreCase(DSLCategories.UNIT)) {

			categoryValues.addAll(getCategoryValues(units));
		}

		return categoryValues;
	}
}