package co.je.thesis.server.services.dsl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import co.je.thesis.common.dtos.dsl.DSLDataTransferObject;
import co.je.thesis.server.application.DSLRequestHandler;

@Path("/dsl")
public class DSLRestServices {
	
	@GET
	@Path("/{param}")
	@Produces("application/json")
	public Response isDSLUpToDate(@PathParam("param") int dslVersion) {
		
		DSLRequestHandler dslRequestHandler = new DSLRequestHandler();
		boolean isUpToDate = dslRequestHandler.isDSLUpToDate(dslVersion);
		
		Response response = Response.status(200).entity(isUpToDate).build();
		
		return response;
	}
	
	@GET
	@Path("/version")
	@Produces("application/json")
	public Response getDSLVersion() {
		
		DSLRequestHandler dslRequestHandler = new DSLRequestHandler();
		int dslVersion = dslRequestHandler.getDSLVersion();
		
		Response response = Response.status(200).entity(dslVersion).build();
		
		return response;
	}
	
	@GET
	@Produces("application/json")
	public Response getUpdatedDSL() {
		
		DSLRequestHandler dslRequestHandler = new DSLRequestHandler();
		DSLDataTransferObject dsl = dslRequestHandler.getUpdatedDSL();
		
		Response response = Response.status(200).entity(dsl).build();
		
		return response;
	}
}
