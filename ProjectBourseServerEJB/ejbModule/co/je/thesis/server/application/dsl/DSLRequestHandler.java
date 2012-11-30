package co.je.thesis.server.application.dsl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.je.thesis.common.dtos.dsl.DSLDataTransferObject;
import co.je.thesis.server.domain.dsl.DSLManager;

/**
 * Session Bean implementation class DSLRequestHandler
 */
@Stateless
@LocalBean
public class DSLRequestHandler {
	
	private DSLManager dslManager;

    /**
     * Default constructor. 
     */
    public DSLRequestHandler() {
    	
    	dslManager = new DSLManager();
    }
    
    public boolean isDSLUpToDate(int dslClientVersion) {
    	
    	System.out.println("EJB: DSLRequestHandler: isUpToDate()");
    	boolean answer = dslManager.isDSLUpToDate(dslClientVersion);
    	
    	return answer;
    }
    
    public int getDSLVersion() {
    	
    	System.out.println("EJB: DSLRequestHandler: getDSLVersion()");
    	int dslVersion = dslManager.getDSLVersion();
    	
    	return dslVersion;
    }
    
    public DSLDataTransferObject getUpdatedDSL() {
    	
    	System.out.println("EJB: DSLRequestHandler: getUpdatedDSL()");
    	DSLDataTransferObject dsl = dslManager.getDSL();
    	return dsl;
    }
}
