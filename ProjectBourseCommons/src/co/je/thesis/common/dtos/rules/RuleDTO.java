package co.je.thesis.common.dtos.rules;

import java.io.Serializable;
import java.util.ArrayList;

import co.je.thesis.common.dtos.dsl.DSLElementDTO;

/**
 * Class that models a Rule Data transfer object.
 * 
 * @author Julian Espinel
 */
public class RuleDTO implements Serializable {

	/**
	 * The DSL elements that compound the rule.
	 */
	private ArrayList<DSLElementDTO> elements;
	
	/**
	 * Constructor
	 */
	public RuleDTO() {
		
		elements = new ArrayList<DSLElementDTO>();
	}

	/**
	 * Constructor with parameters.
	 * 
	 * @param elements the DSL elements that compound the rule.
	 */
	public RuleDTO(ArrayList<DSLElementDTO> elements) {
		this.elements = elements;
	}

	public ArrayList<DSLElementDTO> getElements() {
		return elements;
	}

	/**
	 * Adds a DSL element to the rule.
	 * 
	 * @param dslElement the DSL element we want to add to the rule.
	 */
	public void addElement(DSLElementDTO dslElement) {

		elements.add(dslElement);
	}
}