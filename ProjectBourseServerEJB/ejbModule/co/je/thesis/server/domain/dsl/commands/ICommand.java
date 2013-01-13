package co.je.thesis.server.domain.dsl.commands;

/**
 * A common interface for all the commands derived from an analysis rule.
 * 
 * @author Julian Espinel
 */
public interface ICommand {
	
	/**
	 * Returns the name of the command.
	 * 
	 * @return the name of the command.
	 */
	public String getCommandName();
	
	/**
	 * Determines if the command is basic or composite.
	 * 
	 * @return if the command is a basic command, then returns true, else returns false.
	 */
	public boolean isBasicCommand();
}
