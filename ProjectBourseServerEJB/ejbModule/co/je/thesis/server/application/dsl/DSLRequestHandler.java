package co.je.thesis.server.application.dsl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.je.thesis.common.dtos.dsl.DSLDataTransferObject;
import co.je.thesis.server.domain.dsl.DSLManager;

/**
 * This class represents an EJB that handles requests related with the system's DSL.
 * 
 * @author Julian Espinel
 */
@Stateless
@LocalBean
public class DSLRequestHandler {
	
	/**
	 * This attribute knows how to execute DSL related requests. 
	 */
	private DSLManager dslManager;

    /**
     * DSLRequestHandler constructor. 
     */
    public DSLRequestHandler() {
    	
    	dslManager = new DSLManager();
    }
    
    /**
     * Determines if the version of the DSL that a client has is up to date.
     * 
     * @param dslClientVersion the version of the DSL that the client has.
     * @return if the version of the DSL that a client has is up to date, then returns true,
     * 		   else returns false.
     */
    public boolean isDSLUpToDate(int dslClientVersion) {
    	
    	System.out.println("EJB: DSLRequestHandler: isUpToDate()");
    	boolean answer = dslManager.isDSLUpToDate(dslClientVersion);
    	
    	return answer;
    }
    
    /**
     * Returns the current (and last) version number of the system's DSL.
     * 
     * @return the current (and last) version number of the system's DSL.
     */
    public int getDSLVersion() {
    	
    	System.out.println("EJB: DSLRequestHandler: getDSLVersion()");
    	int dslVersion = dslManager.getDSLVersion();
    	
    	return dslVersion;
    }
    
    /**
     * Returns the whole DSL on  its current (and last) version.
     * 
     * @return the whole DSL on  its current (and last) version.
     */
    public DSLDataTransferObject getUpdatedDSL() {
    	
    	System.out.println("EJB: DSLRequestHandler: getUpdatedDSL()");
    	DSLDataTransferObject dsl = dslManager.getDSL();
    	return dsl;
    }
}