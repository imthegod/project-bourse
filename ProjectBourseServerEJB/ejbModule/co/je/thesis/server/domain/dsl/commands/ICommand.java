package co.je.thesis.server.domain.dsl.commands;


public interface ICommand {
	
	public String getCommandName();
	public boolean isBasicCommand();
}
