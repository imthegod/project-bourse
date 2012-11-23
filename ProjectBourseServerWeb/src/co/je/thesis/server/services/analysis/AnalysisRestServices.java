package co.je.thesis.server.services.analysis;

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

@Path("/analysis")
public class AnalysisRestServices {
	
	private boolean isValidString(String param) {
		
		boolean answer = false;
		
		if (param != null) {
			
			if (!param.isEmpty()) {
				
				answer = true;
			}
		}
		
		return answer;
	}
	
	@POST
	@Consumes("application/json")
	public Response createAnalysisRequest(AnalysisDTO analysisDTO) {
		
		Response response = Response.status(400).build();
		
		if (analysisDTO != null) {
			
			String ownerUserName = analysisDTO.getOwnerUserName();
			String uuid = analysisDTO.getUuid();
			
			if (isValidString(ownerUserName) && isValidString(uuid)) {
				
				AsyncAnalysisRequestHandler asyncAnalysisRequestHandler = new AsyncAnalysisRequestHandler(analysisDTO);
				asyncAnalysisRequestHandler.start();
				
				response = Response.status(202).build();
			}
		}
		
		return response;
	}
	
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