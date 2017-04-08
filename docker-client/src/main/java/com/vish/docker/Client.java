package com.vish.docker;
import java.util.*;
import org.apache.commons.cli.*;
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
			h.createClient(false);			
		} catch (Exception e) {
			logger.error("Error building the Docker client!",e);
		}
	}

	private void parseArgs(String[] args) {
		Options options = new Options();
		Option useDockerHub = new Option("d","dockerhub",false,"use docker hub instead of private registry");
		options.addOption(useDockerHub);

		CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
        }

        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = cmd.getOptionValue("output");

        System.out.println(inputFilePath);
        System.out.println(outputFilePath);

    }
	
	public static void main( String[] args ) {
		Client dockerClient = new Client();
	}

}
