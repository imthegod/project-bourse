package co.je.thesis.mobile.communication.constants;

/**
 * Class that stores the constants needed to communicate with the system's server and be
 * able to consume its exposed services.
 * 
 * @author Julian Espinel
 */
public class CommunicationsConstants {
	
	/*
	 * TODO:
	 * 
	 * In a future release these constants should be stored into the mobile app's database.
	 * This will allow us to change (for example) the server ip address on runtime. Therefore, 
	 * it will not be necessary to update the app in order to change these values.
	 */
	
	/**
	 * The system's server IP address.
	 */
	/*
	 * 10.0.2.2 is the ip of the local machine. If you put localhost or 127.0.0.1
	 * the android emulator will try to reach the ip of its own vm.
	 */
	public static final String IP_ADDRESS = "10.0.2.2";
	
	/**
	 * The system's server port.
	 */
	public static final String PORT = "8080";
	
	/**
	 * The URL where all the REST services are located.
	 */
	public static final String BASE_URL = "http://" + IP_ADDRESS + ":" + PORT + "/ProjectBourseServerWeb/rest";
}
