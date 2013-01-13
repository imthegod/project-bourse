package co.je.thesis.server.services.validRules;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.server.application.validRules.ValidRulesRequestHandler;

/**
 * This class exposes the REST related with valid rules.
 * 
 * @author Julian Espinel
 */
@Path("/valid-rules")
public class ValidRulesRestServices {
	
	/**
	 * Determines if a given version number of the valid rules is up to date.
	 * 
	 * @param clientValidRulesVersion the given version number.
	 * @return if the given version number of the valid rules is up to date, the returns true,
	 * 		   else returns false.
	 */
	@GET
	@Path("/{param}")
	@Produces("application/json")
	public Response areRulesUpToDate(@PathParam("param") int clientValidRulesVersion) {
		
		ValidRulesRequestHandler validRulesRequestHandler = new ValidRulesRequestHandler();
		boolean areUpToDate = validRulesRequestHandler.areRulesUpToDate(clientValidRulesVersion);
		
		Response response = Response.status(200).entity(areUpToDate).build();
		
		return response;
	}
	
	/**
	 * Returns the current (and last) valid rules version.
	 * 
	 * @return the current (and last) valid rules version.
	 */
	@GET
	@Path("/version")
	@Produces("application/json")
	public Response getValidRulesVersion() {
		
		ValidRulesRequestHandler validRulesRequestHandler = new ValidRulesRequestHandler();
		int validRulesVersion = validRulesRequestHandler.getValidRulesVersion();
		
		Response response = Response.status(200).entity(validRulesVersion).build();
		
		return response;
	}
	
	/**
	 * Returns an ArrayList with all the valid rules on its current (and last) version.
	 * 
	 * @return an ArrayList with all the valid rules on its current (and last) version.
	 */
	@GET
	@Produces("application/json")
	public Response getValidRules() {
		
		ValidRulesRequestHandler validRulesRequestHandler = new ValidRulesRequestHandler();
		ArrayList<ValidRule> validRules = validRulesRequestHandler.getValidRules();
		
		Response response = Response.status(200).entity(validRules).build();
		
		return response;
	}
}