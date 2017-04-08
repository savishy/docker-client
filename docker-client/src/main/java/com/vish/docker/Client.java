package com.vish.docker;
import java.io.IOException;
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
	/** input param names */
	private static enum INPUTS {
		dockerhub,
		pull,
		run
	}
	
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
		h = new Helper();
		try {
			log4jprops.load(this.getClass().getResourceAsStream("/log4j.properties"));
			PropertyConfigurator.configure(log4jprops);
		} catch (Exception e) {
			logger.error("Error building the Docker client!",e);
		}
	}
	
	/**
	 * Parse the commandline arguments, print usage as needed.
	 * @param args
	 */
	private void parseArgs(String[] args) throws IOException {
		Options options = new Options();
		Option useDockerHub = new Option("d",INPUTS.dockerhub.toString(),false,"use docker hub instead of private registry");
		Option pullImage = new Option("p", INPUTS.pull.toString(), true, "pull a Docker image without running a container");
		Option runContainer = new Option("r", INPUTS.run.toString(), true, "run a Docker container [might pull image if needed]");

		options.addOption(useDockerHub);
		options.addOption(pullImage);
		options.addOption(runContainer);

		CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("OPTIONS", options);

            System.exit(1);
            return;
        }

        String outputFilePath = cmd.getOptionValue(INPUTS.run.toString());

        execute(cmd);
        

    }
	
	/**
	 * Execute the action appropriate to commandline options
	 * @param cmd
	 * @throws IOException
	 */
	private void execute(CommandLine cmd) throws IOException {
        if(cmd.hasOption(INPUTS.dockerhub.toString())) {
    		h.createClient(false);				
        } else 		h.createClient(true);				
		
        if(cmd.hasOption(INPUTS.pull.toString())) {
            String imgName = cmd.getOptionValue(INPUTS.pull.toString());
            h.pullImage(imgName);
        }
	}
	
	public static void main( String[] args ) {
		Client dockerClient = new Client();
		try {
			dockerClient.parseArgs(args);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

	}

}
