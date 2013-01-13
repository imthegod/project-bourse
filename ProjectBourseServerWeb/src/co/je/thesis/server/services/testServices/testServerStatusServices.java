package co.je.thesis.server.services.testServices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import co.je.thesis.server.application.dsl.DSLRequestHandler;

/**
 * This class provides a service that test all the server components in a fast and efficient way.
 * 
 * @author Julian Espinel
 */
@Path("test")
public class testServerStatusServices {
	
	/**
	 * A method that calls and EJB's method. That EJB method needs the DB.
	 * So if this method doesn't throw and exception, the EBJs and the DB 
	 * are working properly.
	 */
	private void ejbAndDBAreWorkingProperly() {
		
		DSLRequestHandler dslRequestHandler = new DSLRequestHandler();
		dslRequestHandler.getDSLVersion();
	}
	
	/**
	 * Tests if all the components of the server are working properly.
	 * 
	 * @return if all the components of the server are working properly, returns an http status code
	 * 		   200 (OK), else returns an http status code 503 (Service Unavailable).
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
	 */
	@GET
	@Path("/status")
	@Produces("application/json")
	public Response isServerAlive() {
		
		// If its here is because server is alive
		Response response = Response.status(200).build();
		
		try {
			
			ejbAndDBAreWorkingProperly();
			
		} catch (Exception e) {
			
			response = Response.status(503).build();
		}
		
		return response;
	}
}