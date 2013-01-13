package co.je.thesis.server.infrastructure.translators.dsl;

import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import co.je.thesis.common.dbos.dsl.DSLElementDBO;
import co.je.thesis.common.dbos.rules.RuleDBO;
import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;

/**
 * This class translates RuleDTO objects into RuleDBO objects.
 * 
 * @author Julian Espinel
 */
public class RuleTranslator {

	/**
	 * Translates BasicDBObject to RuleDBO objects.
	 * 
	 * @param basicDBO the object we want to translate.
	 * @return a RuleDBO object with the information contained by the BasicDBObject.
	 */
	public RuleDBO toDBO(BasicDBObject basicDBO) {

		BasicDBList basicElementsDBO = (BasicDBList) basicDBO.get(RuleDBO.ELEMENTS_DBO);
		ArrayList<DSLElementDBO> elementsDBO = new ArrayList<DSLElementDBO>();
		DSLElementTranslator translator = new DSLElementTranslator();
		
		for (int i = 0; i < basicElementsDBO.size(); i++) {
			
			BasicDBObject basicElementDBO = (BasicDBObject) basicElementsDBO.get(i);
			DSLElementDBO elementDBO = translator.toDBO(basicElementDBO);
			elementsDBO.add(elementDBO);
		}
		
		RuleDBO ruleDBO = new RuleDBO(elementsDBO);

		return ruleDBO;
	}

	/**
	 * Translates RuleDTO objects to RuleDBO objects.
	 * 
	 * @param ruleDTO the object we want to translate.
	 * @return a RuleDBO object with the information contained by the RuleDTO object.
	 */
	public RuleDBO toDBO(RuleDTO ruleDTO) {

		ArrayList<DSLElementDTO> elementsDTO = ruleDTO.getElements();
		ArrayList<DSLElementDBO> elementsDBO = new ArrayList<DSLElementDBO>();

		DSLElementTranslator translator = new DSLElementTranslator();

		for (int i = 0; i < elementsDTO.size(); i++) {

			DSLElementDTO elementDTO = elementsDTO.get(i);
			DSLElementDBO elementDBO = translator.toDBO(elementDTO);

			elementsDBO.add(elementDBO);
		}

		RuleDBO ruleDBO = new RuleDBO(elementsDBO);

		return ruleDBO;
	}

	/**
	 * Translates RuleDBO objects to RuleDTO objects.
	 * 
	 * @param ruleDBO the object we want to translate.
	 * @return a RuleDTO object with the information contained by the RuleDBO object.
	 */
	public RuleDTO toDTO(RuleDBO ruleDBO) {

		BasicDBList basicElementsDBO = ruleDBO.getBasicElementsDBO();
		ArrayList<DSLElementDTO> elementsDTO = new ArrayList<DSLElementDTO>();

		DSLElementTranslator translator = new DSLElementTranslator();

		for (int i = 0; i < basicElementsDBO.size(); i++) {

			BasicDBObject basicElementDBO = (BasicDBObject) basicElementsDBO.get(i);
			DSLElementDBO elementDBO = translator.toDBO(basicElementDBO);
			DSLElementDTO elementDTO = translator.toDTO(elementDBO);

			elementsDTO.add(elementDTO);
		}

		RuleDTO ruleDTO = new RuleDTO(elementsDTO);

		return ruleDTO;
	}

	/**
	 * Translates a RuleDBO object into a ValidRule object.
	 * 
	 * @param ruleDBO the object we want to translate.
	 * @return a ValidRule object with the information contained by the RuleDBO object.
	 */
	public ValidRule toDomainObject(RuleDBO ruleDBO) {

		BasicDBList basicElementsDBO = ruleDBO.getBasicElementsDBO();
		ArrayList<DSLElementDTO> elementsDTO = new ArrayList<DSLElementDTO>();
		
		DSLElementTranslator translator = new DSLElementTranslator();
		
		for (int i = 0; i < basicElementsDBO.size(); i++) {
			
			BasicDBObject basicElementDBO = (BasicDBObject) basicElementsDBO.get(i);
			DSLElementDBO elementDBO = translator.toDBO(basicElementDBO);
			DSLElementDTO elementDTO = translator.toDTO(elementDBO);
			elementsDTO.add(elementDTO);
		}

		ValidRule rule = new ValidRule(elementsDTO);

		return rule;
	}
}
