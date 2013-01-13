package co.je.thesis.server.services.dsl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import co.je.thesis.common.dtos.dsl.DSLDataTransferObject;
import co.je.thesis.server.application.dsl.DSLRequestHandler;

/**
 * This class exposes the REST related with the system's DSL.
 * 
 * @author Julian Espinel
 */
@Path("/dsl")
public class DSLRestServices {
	
	/**
	 * Determines if a given DSL's version number is up to date.
	 * 
	 * @param dslVersion the version number we want to know if it is up to date or not.
	 * @return if the given DSL's version number is up to date, then returns true, else returns false.
	 */
	@GET
	@Path("/{param}")
	@Produces("application/json")
	public Response isDSLUpToDate(@PathParam("param") int dslVersion) {
		
		DSLRequestHandler dslRequestHandler = new DSLRequestHandler();
		boolean isUpToDate = dslRequestHandler.isDSLUpToDate(dslVersion);
		
		Response response = Response.status(200).entity(isUpToDate).build();
		
		return response;
	}
	
	/**
	 * Returns the current (and last) DSL version number.
	 * 
	 * @return the current (and last) DSL version number.
	 */
	@GET
	@Path("/version")
	@Produces("application/json")
	public Response getDSLVersion() {
		
		DSLRequestHandler dslRequestHandler = new DSLRequestHandler();
		int dslVersion = dslRequestHandler.getDSLVersion();
		
		Response response = Response.status(200).entity(dslVersion).build();
		
		return response;
	}
	
	/**
	 * Returns the whole DSL on its current (and last) version.
	 * 
	 * @return the whole DSL on its current (and last) version.
	 */
	@GET
	@Produces("application/json")
	public Response getUpdatedDSL() {
		
		DSLRequestHandler dslRequestHandler = new DSLRequestHandler();
		DSLDataTransferObject dsl = dslRequestHandler.getUpdatedDSL();
		
		Response response = Response.status(200).entity(dsl).build();
		
		return response;
	}
}
