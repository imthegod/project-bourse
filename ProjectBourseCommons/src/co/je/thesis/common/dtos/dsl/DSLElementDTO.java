package co.je.thesis.common.dtos.dsl;

import java.io.Serializable;

/**
 * This class models a Data transfer object of a DSL element.
 * 
 * @author Julian Espinel
 */
public class DSLElementDTO implements Serializable {

	/**
	 * The category of this DSL element.
	 */
	private String category;
	
	/**
	 * The value stored by this DSL element.
	 */
	private String value;

	/*
	 * Don't remove this, it is necessary for the rest services.
	 */
	public DSLElementDTO() {
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param category the category of this DSL element.
	 * @param value the value stored by this DSL element.
	 */
	public DSLElementDTO(String category, String value) {
		this.category = category;
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public String getValue() {
		return value;
	}

	/**
	 * Verifies if a given DSL element belongs to the same category.
	 * 
	 * @param dslElement the given DSL element.
	 * @return if the category of the given DSL element and the category of this DSL element are equal,
	 * 		   then returns true, else returns false.
	 */
	public boolean belongToTheSameCategory(DSLElementDTO dslElement) {

		boolean answer = false;
		
		if (category.equalsIgnoreCase(dslElement.getCategory())) {

			answer = true;
		}

		return answer;
	}
}