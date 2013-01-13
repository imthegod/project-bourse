package co.je.server.services.test.load.analysis.concurrency;

import static com.jayway.restassured.RestAssured.given;
import co.je.thesis.common.dtos.analysis.AnalysisDTO;

/**
 * This class models a task to be executed by a thread.
 * The task this class executes is create an analysis request into the system's server.
 * 
 * @author Julian Espinel
 */
public class AnalysisTask implements Runnable {

	/**
	 * This attribute stores the analysis request we want to create into the server.
	 */
	private AnalysisDTO analysisDTO;
	
	/**
	 * AnalysisTask constructor.
	 * 
	 * @param analysisDTO the analysis request we want to create into the server.
	 */
	public AnalysisTask(AnalysisDTO analysisDTO) {
		
		this.analysisDTO = analysisDTO;
	}
	
	/**
	 * Makes the http post request to the server in order to create the analysis.
	 */
	private void executeAnalysisRequest() {
		
		String url = "http://localhost:8080/ProjectBourseServerWeb/rest/analysis";

		given().header("Accept", "application/json").header("Content-Type", "application/json")
				.body(analysisDTO).expect().statusCode(202).when().post(url);
	}
	
	/**
	 * This method is called when we submit an AnalysisTask object into a thread pool.
	 * for example: threadPool.submit(analysisTask);
	 * 
	 * This method calls the executeAnalysisRequest method.
	 */
	@Override
	public void run() {
		
		executeAnalysisRequest();
	}
}