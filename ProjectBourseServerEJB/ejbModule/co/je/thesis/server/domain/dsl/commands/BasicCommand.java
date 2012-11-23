package co.je.thesis.server.domain.dsl.commands;

import co.je.thesis.common.dtos.dsl.DSLElementDTO;

public abstract class BasicCommand implements ICommand {
	
	@Override
	public boolean isBasicCommand() {
		return true;
	}
	
	protected static boolean elementValueIsValid(DSLElementDTO dslElementDTO) {

		boolean valueIsNotEmpty = !(dslElementDTO.getValue().isEmpty());
		boolean valueIsNotNull = (dslElementDTO.getValue() != null);
		boolean answer = valueIsNotEmpty && valueIsNotNull;

		return answer;
	}
}