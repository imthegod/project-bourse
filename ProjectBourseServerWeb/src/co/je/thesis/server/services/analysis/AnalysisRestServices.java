package co.je.thesis.server.services.analysis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.server.application.analysis.AnalysisRequestHandler;
import co.je.thesis.server.application.analysis.AsyncAnalysisRequestHandler;

/**
 * This class exposes the REST related with analysis requests.
 * 
 * @author Julian Espinel
 */
@Path("/analysis")
public class AnalysisRestServices {
	
	/**
	 * Verifies a String object is not null and not empty.
	 * 
	 * @param param the String object to evaluate.
	 * @return if the String object is not null and not empty, then returns true, else
	 * 		   returns false.
	 */
	private boolean isValidString(String param) {
		
		boolean answer = false;
		
		if (param != null) {
			
			if (!param.isEmpty()) {
				
				answer = true;
			}
		}
		
		return answer;
	}
	
	/**
	 * Creates a new analysis request.
	 * 
	 * @param analysisDTO the analysis we want to create.
	 * @return a Response object. If the action was completed successfully the returns http status
	 * 		   code 202 (Accepted), else returns http status code 400 (Bad Request).
	 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
	 */
	@POST
	@Consumes("application/json")
	public Response createAnalysisRequest(AnalysisDTO analysisDTO) {
		
		Response response = Response.status(400).build();
		
		if (analysisDTO != null) {
			
			String ownerUserName = analysisDTO.getOwnerUserName();
			String uuid = analysisDTO.getUuid();
			
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			
			if (isValidString(ownerUserName) && isValidString(uuid)) {
				
				AsyncAnalysisRequestHandler asyncAnalysisRequestHandler = new AsyncAnalysisRequestHandler(analysisDTO);
				executorService.execute(asyncAnalysisRequestHandler);
				
				response = Response.status(202).build();
			}
			
			executorService.shutdown();
		}
		
		return response;
	}
	
	/**
	 * Returns the results of a specific analysis. The analysis request is identified
	 * by its UUID and the user name of the investor who created that analysis request.
	 * 
	 * @param ownerUserName the user name of the investor who created the analysis.
	 * @param uuid the UUID of the analysis we are looking for.
	 * @return the results of a specific analysis request.
	 */
	@GET
	@Produces("application/json")
	public Response getAnalysisResults(@QueryParam("username") String ownerUserName, 
			@QueryParam("uuid") String uuid) {
		
		Response response = Response.status(400).build();
		
		if (isValidString(ownerUserName) && isValidString(uuid)) {
			
			AnalysisRequestHandler analysisRequestHandler = new AnalysisRequestHandler();
			AnalysisResultsStorageDTO analysisResultsStorageDTO = analysisRequestHandler.getAnalysisResults(ownerUserName, uuid);
			
			response = Response.status(200).entity(analysisResultsStorageDTO).build();
		}
		
		return response;
	}
}