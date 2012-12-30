package co.je.thesis.common.dbos.rules;

import java.util.ArrayList;

import co.je.thesis.common.dbos.dsl.DSLElementDBO;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

/**
 * Class that models a Rule DBO (Database object).
 * 
 * @author Julian Espinel
 */
public class RuleDBO extends BasicDBObject {
	
	/**
	 * Constant the models the key "elementsDBO", the DSL elements that compound the rule.
	 */
	public static final String ELEMENTS_DBO = "elementsDBO";
	
	/**
	 * constructor
	 */
	public RuleDBO() {
		// Required in order to create a new instance of this class
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param dslElementsDBO an ArrayList of DSLElementDBO objects. These DSLElementDBO objects
	 * 		  compound the rule.
	 */
	public RuleDBO(ArrayList<DSLElementDBO> dslElementsDBO) {
		
		BasicDBList elementsDBO = new BasicDBList();
		elementsDBO.addAll(dslElementsDBO);
		this.put(ELEMENTS_DBO, elementsDBO);
	}
	
	/**
	 * Returns a BasicDBList of DSLElementDBO objects.
	 * 
	 * @return a BasicDBList with the DSLElementDBO objects that compound the rule.
	 */
	public BasicDBList getBasicElementsDBO() {
		
		BasicDBList basicDBList = (BasicDBList) this.get(ELEMENTS_DBO);
		return basicDBList;
	}
}
