package co.je.thesis.server.infrastructure.translators.dsl;

import com.mongodb.BasicDBObject;

import co.je.thesis.common.dbos.dsl.DSLElementDBO;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;

/**
 * This class knows how to translate DSLElementDTO objects to DSLElementDBO and vice versa.
 * 
 * @author Julian Espinel
 */
public class DSLElementTranslator {
	
	/**
	 * Translates a DSLElementDBO object to a DSLElementDTO object.
	 * 
	 * @param elementDBO the object we want to translate.
	 * @return a DSLElementDTO object with the information contained by 
	 * 		   the given DSLElementDBO object.
	 */
	public DSLElementDTO toDTO(DSLElementDBO elementDBO) {
		
		String category = elementDBO.getCategory();
		String value = elementDBO.getValue();
		DSLElementDTO elementDTO = new DSLElementDTO(category, value);
		
		return elementDTO;
	}
	
	/**
	 * Translates a DSLElementDTO object to a DSLElementDBO object.
	 * 
	 * @param elementDTO the object we want to translate.
	 * @return a DSLElementDBO object with the information contained by 
	 * 		   the given DSLElementDTO object.
	 */
	public DSLElementDBO toDBO(DSLElementDTO elementDTO) {
		
		String category = elementDTO.getCategory();
		String value = elementDTO.getValue();
		DSLElementDBO elementDBO = new DSLElementDBO(category, value);
		
		return elementDBO;
	}
	
	/**
	 * Translates a BasicDBObject to a DSLElementDBO object.
	 * 
	 * @param basicDBO the object we want to translate.
	 * @return a DSLElementDBO with the information contained by the BasicDBObject.
	 */
	public DSLElementDBO toDBO(BasicDBObject basicDBO) {
		
		String category = basicDBO.getString(DSLElementDBO.CATEGORY);
		String value = basicDBO.getString(DSLElementDBO.VALUE);
		DSLElementDBO dslElementDBO = new DSLElementDBO(category, value);
		
		return dslElementDBO;
	}
}