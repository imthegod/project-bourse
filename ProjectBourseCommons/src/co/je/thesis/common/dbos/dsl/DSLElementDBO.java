package co.je.thesis.common.dbos.dsl;

import com.mongodb.BasicDBObject;

public class DSLElementDBO extends BasicDBObject {
	
	public static final String CATEGORY = "category";
	public static final String VALUE = "value";
	
	public DSLElementDBO() {
		// Required in order to create a new instance of this class
	}
	
	public DSLElementDBO(String category, String value) {
		
		put(CATEGORY, category);
		put(VALUE, value);
	}
	
	public String getCategory() {
		
		String category = getString(CATEGORY);
		return category;
	}
	
	public String getValue() {
		
		String value = getString(VALUE);
		return value;
	}
}
