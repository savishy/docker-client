package com.vish.docker;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * Docker Client main class.
 *
 */
public class Client
{
	private Helper h;
	private Logger logger;
	
	/**
	 * The constructor for the class does the following:
	 * <ul>
	 * <li>Configure logger</li>
	 * <li>Initialize the {@link Helper} class</li>
	 * </ul>
	 */
	public Client() {
		logger = org.apache.log4j.LogManager.getLogger(this.getClass());
		Properties log4jprops = new Properties();
		logger.info("hey");
		h = new Helper();
		try {
			log4jprops.load(this.getClass().getResourceAsStream("/log4j.properties"));
			PropertyConfigurator.configure(log4jprops);
			h.createClient();			
		} catch (Exception e) {
			logger.error("Error building the Docker client!",e);
		}
	}

	public static void main( String[] args ) {
		Client dockerClient = new Client();
	
	}

}
