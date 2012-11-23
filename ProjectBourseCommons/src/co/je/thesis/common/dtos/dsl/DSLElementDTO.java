package co.je.thesis.common.dtos.dsl;

import java.io.Serializable;

public class DSLElementDTO implements Serializable {

	private String category;
	private String value;

	/*
	 * Don't remove this, it is necessary for the rest services.
	 */
	public DSLElementDTO() {
	}
	
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

	public boolean belongToTheSameCategory(DSLElementDTO dslElement) {

		boolean answer = false;
		
		if (category.equalsIgnoreCase(dslElement.getCategory())) {

			answer = true;
		}

		return answer;
	}
}