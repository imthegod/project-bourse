package co.je.thesis.common.dbos.dsl;

import com.mongodb.BasicDBObject;

/**
 * Class that models a DSL element DBO (Database object). The object that models a DSL element, and also
 * could be stored in a DB collection.
 * 
 * @author Julian Espinel
 */
public class DSLElementDBO extends BasicDBObject {
	
	/**
	 * Constant that models the key "category".
	 */
	public static final String CATEGORY = "category";
	
	/**
	 * Constant that models the key "value".
	 */
	public static final String VALUE = "value";
	
	/**
	 * Constructor
	 */
	public DSLElementDBO() {
		// Required in order to create a new instance of this class
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param category the name of the category to which this element belongs.
	 * @param value the value represented by this element.
	 */
	public DSLElementDBO(String category, String value) {
		
		put(CATEGORY, category);
		put(VALUE, value);
	}
	
	/**
	 * Returns the name of the category to which this element belongs.
	 * 
	 * @return the name of the category to which this element belongs.
	 */
	public String getCategory() {
		
		String category = getString(CATEGORY);
		return category;
	}
	
	/**
	 * Returns the value represented by this element.
	 * 
	 * @return the value represented by this element.
	 */
	public String getValue() {
		
		String value = getString(VALUE);
		return value;
	}
}
