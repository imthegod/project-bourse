package co.je.thesis.server.domain.dsl.commands;

import co.je.thesis.common.dtos.dsl.DSLElementDTO;

/**
 * Abstract class to be extended by the all the commands except by the composite commands.
 * 
 * @author Julian Espinel
 */
public abstract class BasicCommand implements ICommand {
	
	/**
	 * Determines if it is a basic command or not.
	 */
	@Override
	public boolean isBasicCommand() {
		return true;
	}
	
	/**
	 * Determines if a DSL element is valid or not.
	 * 
	 * @param dslElementDTO the DSL element.
	 * @return id the DSL element is not empty and is not null, then returns true, 
	 * 		   else returns false.
	 */
	protected static boolean elementValueIsValid(DSLElementDTO dslElementDTO) {

		boolean valueIsNotEmpty = !(dslElementDTO.getValue().isEmpty());
		boolean valueIsNotNull = (dslElementDTO.getValue() != null);
		boolean answer = valueIsNotEmpty && valueIsNotNull;

		return answer;
	}
}