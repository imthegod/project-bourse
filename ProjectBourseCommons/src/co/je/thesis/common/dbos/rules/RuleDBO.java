package co.je.thesis.common.dbos.rules;

import java.util.ArrayList;

import co.je.thesis.common.dbos.dsl.DSLElementDBO;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class RuleDBO extends BasicDBObject {
	
	public static final String ELEMENTS_DBO = "elementsDBO";
	
	public RuleDBO() {
		// Required in order to create a new instance of this class
	}
	
	public RuleDBO(ArrayList<DSLElementDBO> dslElementsDBO) {
		
		BasicDBList elementsDBO = new BasicDBList();
		elementsDBO.addAll(dslElementsDBO);
		this.put(ELEMENTS_DBO, elementsDBO);
	}
	
	public BasicDBList getBasicElementsDBO() {
		
		BasicDBList basicDBList = (BasicDBList) this.get(ELEMENTS_DBO);
		return basicDBList;
	}
}
