package co.je.server.services.test.load.analysis.concurrency;

import static com.jayway.restassured.RestAssured.given;
import co.je.thesis.common.dtos.analysis.AnalysisDTO;

public class AnalysisTask implements Runnable {

	private AnalysisDTO analysisDTO;
	
	public AnalysisTask(AnalysisDTO analysisDTO) {
		
		this.analysisDTO = analysisDTO;
	}
	
	private void executeAnalysisRequest() {
		
		String url = "http://localhost:8080/ProjectBourseServerWeb/rest/analysis";

		given().header("Accept", "application/json").header("Content-Type", "application/json")
				.body(analysisDTO).expect().statusCode(202).when().post(url);
	}
	
	@Override
	public void run() {
		
		executeAnalysisRequest();
	}
}
