package co.je.thesis.common.dtos.dsl;

import java.util.ArrayList;

import co.je.thesis.common.constants.DSLCategories;

/**
 * This class models the DSL.
 * 
 * @author Julian Espinel
 */
public class DSLDataTransferObject {

	/**
	 * The DSL current version.
	 */
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

	/**
	 * Constructor with parameters.
	 * 
	 * @param version the current version of the DSL.
	 * @param subjects an ArrayList with the DSL elements that belong to the subjects category.
	 * @param properties an ArrayList with the DSL elements that belong to the properties category.
	 * @param mathOperations an ArrayList with the DSL elements that belong to the mathOperations category.
	 * @param specificMathOperations an ArrayList with the DSL elements that belong to the specificMathOperations category.
	 * @param conditionalOperators an ArrayList with the DSL elements that belong to the conditionalOperators category.
	 * @param logicalOperators an ArrayList with the DSL elements that belong to the logicalOperators category.
	 * @param units an ArrayList with the DSL elements that belong to the units category.
	 */
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
	
	/**
	 * Returns a DSLElementDTO object given it value.
	 * 
	 * @param value the value stored by the object we want to find. 
	 * @return The DSLElementDTO that stores the given value.
	 */
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

	/**
	 * Returns all the values of the DSL elements given an ArrayList of DSLElementDTO objects. 
	 * 
	 * @param dslElements an ArrayList of DSLElementDTO objects
	 * @return all the values of the DSL elements given an ArrayList of DSLElementDTO objects.
	 */
	private ArrayList<String> getCategoryValues(ArrayList<DSLElementDTO> dslElements) {

		ArrayList<String> categoryValues = new ArrayList<String>();

		for (int i = 0; i < dslElements.size(); i++) {

			DSLElementDTO dslElementDTO = dslElements.get(i);
			String value = dslElementDTO.getValue();

			categoryValues.add(value);
		}

		return categoryValues;
	}

	/**
	 * Returns all the values grouped under a given category.
	 * 
	 * @param category the name of the category from which we want to get its values.
	 * @return all the values grouped under a given category.
	 */
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