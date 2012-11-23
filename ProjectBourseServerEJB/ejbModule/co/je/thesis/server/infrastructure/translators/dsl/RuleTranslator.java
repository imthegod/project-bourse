package co.je.thesis.server.infrastructure.translators.dsl;

import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import co.je.thesis.common.dbos.dsl.DSLElementDBO;
import co.je.thesis.common.dbos.rules.RuleDBO;
import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;

public class RuleTranslator {

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
