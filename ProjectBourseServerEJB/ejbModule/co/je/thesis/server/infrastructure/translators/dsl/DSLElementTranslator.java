package co.je.thesis.server.infrastructure.translators.dsl;

import com.mongodb.BasicDBObject;

import co.je.thesis.common.dbos.dsl.DSLElementDBO;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

public class DSLElementTranslator {
	
	public DSLElementDTO toDTO(DSLElementDBO elementDBO) {
		
		String category = elementDBO.getCategory();
		String value = elementDBO.getValue();
		DSLElementDTO elementDTO = new DSLElementDTO(category, value);
		
		return elementDTO;
	}
	
	public DSLElementDBO toDBO(DSLElementDTO elementDTO) {
		
		String category = elementDTO.getCategory();
		String value = elementDTO.getValue();
		DSLElementDBO elementDBO = new DSLElementDBO(category, value);
		
		return elementDBO;
	}
	
	public DSLElementDBO toDBO(BasicDBObject basicDBO) {
		
		String category = basicDBO.getString(DSLElementDBO.CATEGORY);
		String value = basicDBO.getString(DSLElementDBO.VALUE);
		DSLElementDBO dslElementDBO = new DSLElementDBO(category, value);
		
		return dslElementDBO;
	}
}
