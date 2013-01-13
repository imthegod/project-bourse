package co.je.server.services.tests.analysis;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;

import org.junit.Test;

import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;

/**
 * This class provides a method to verify that the REST service offered by the server, that
 * allows clients to create analysis requests is working properly.
 * 
 * @author Julian Espinel
 */
public class TestAnalysisRestServices {

	/**
	 * Returns a defined AnalysisDTO object.
	 * 
	 * @return a defined AnalysisDTO object.
	 */
	private AnalysisDTO getAnalysisDTOSample() {

		ArrayList<RuleDTO> rulesDTO = new ArrayList<RuleDTO>();

		ArrayList<DSLElementDTO> ruleElements = new ArrayList<DSLElementDTO>();
		
		ruleElements.add(new DSLElementDTO(DSLCategories.SUBJECT, "acciones"));
		ruleElements.add(new DSLElementDTO(DSLCategories.PROPERTY, "close"));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, "07-11-2012"));

		ruleElements.add(new DSLElementDTO(DSLCategories.CONDITIONAL_OPERATOR, ">"));

		ruleElements.add(new DSLElementDTO(DSLCategories.MATH_OPERATION, "multiplicacion"));
		ruleElements.add(new DSLElementDTO(DSLCategories.NUMBER, "97"));
		ruleElements.add(new DSLElementDTO(DSLCategories.UNIT, "%"));
		ruleElements.add(new DSLElementDTO(DSLCategories.AVERAGE, "average"));
		ruleElements.add(new DSLElementDTO(DSLCategories.PROPERTY, "close"));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, "10-10-2012"));
		ruleElements.add(new DSLElementDTO(DSLCategories.TIME_FRAME, "07-11-2012"));

		RuleDTO ruleDTO = new RuleDTO(ruleElements);
		RuleDTO ruleDTO2 = new RuleDTO(ruleElements);
		
		rulesDTO.add(ruleDTO);
		rulesDTO.add(ruleDTO2);
		
		ArrayList<DSLElementDTO> logicalOperatorsDTO = new ArrayList<DSLElementDTO>();
		String category = DSLCategories.LOGICAL_OPERATOR;
		String value = "AND";
		DSLElementDTO logicalOperator = new DSLElementDTO(category, value);
		logicalOperatorsDTO.add(logicalOperator);

		String ownerUserName = "owner";
		
		AnalysisDTO analysisDTO = new AnalysisDTO(ownerUserName, rulesDTO, logicalOperatorsDTO);

		return analysisDTO;
	}

	/**
	 * Test the REST service offered by the server, that allows clients to create a new
	 * analysis request.
	 */
	@Test
	public void testCreateAnalysisRequest() {

		String url = "http://localhost:8080/ProjectBourseServerWeb/rest/analysis";

		AnalysisDTO analysisDTO = getAnalysisDTOSample();

		given().header("Accept", "application/json").header("Content-Type", "application/json")
				.body(analysisDTO).expect().statusCode(202).when().post(url);
	}
}