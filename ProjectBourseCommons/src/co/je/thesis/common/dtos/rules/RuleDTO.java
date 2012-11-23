package co.je.thesis.common.dtos.rules;

import java.io.Serializable;
import java.util.ArrayList;

import co.je.thesis.common.dtos.dsl.DSLElementDTO;

public class RuleDTO implements Serializable {

	private ArrayList<DSLElementDTO> elements;
	
	/*
	 * Don't remove this, it is necessary for the rest services.
	 */
	public RuleDTO() {
		
		elements = new ArrayList<DSLElementDTO>();
	}

	public RuleDTO(ArrayList<DSLElementDTO> elements) {
		this.elements = elements;
	}

	public ArrayList<DSLElementDTO> getElements() {
		return elements;
	}

	public void addElement(DSLElementDTO dslElement) {

		elements.add(dslElement);
	}
}