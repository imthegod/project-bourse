package co.je.thesis.mobile.communication.constants;

public class CommunicationsConstants {
	
	/*
	 * 10.0.2.2 is the ip of the local machine. If you put localhost or 127.0.0.1
	 * the android emulator will try to reach the ip of its own vm.
	 */
	public static final String IP_ADDRESS = "10.0.2.2";
	
	public static final String PORT = "8080";
	
	public static final String BASE_URL = "http://" + IP_ADDRESS + ":" + PORT + "/ProjectBourseServerWeb/rest";
	
//	private String ipAddress;
//	private String port;
//	
//	public CommunicationsConstants(String ipAddress, String port) {
//		
//		this.ipAddress = ipAddress;
//		this.port = port;
//	}
//	
//	public String getBaseUrl() {
//		
//		String baseUrl = "http://" + ipAddress + ":" + port + "/ProjectBourseServerWeb/rest";;
//		
//		return baseUrl;
//	}
}
