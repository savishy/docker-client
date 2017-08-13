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
	 * Parse the commandline arguments, print usage as needed, return {@link CommandLine} object.
	 * @param args
	 */
	protected CommandLine parseArgs(String[] args) throws IOException {
		
		Options options = new Options();
		Option useDockerHub = new Option("d",Constants.INPUTS.dockerhub.toString(),false,
				"if this flag is set, use docker hub instead of private registry");
		Option pullImage = new Option("p", Constants.INPUTS.pull.toString(), true, 
				"pull a Docker image only without running a container");
		Option runContainer = new Option("r", Constants.INPUTS.run.toString(), true, 
				"run a Docker container [pulls image if needed]");

		options.addOption(useDockerHub);
		options.addOption(pullImage);
		options.addOption(runContainer);

		CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            //print help if options are empty
            if (cmd.getOptions().length == 0) {
                formatter.printHelp("OPTIONS", options);
            }            
        } catch (ParseException e) {
            formatter.printHelp("OPTIONS", options);
            throw new IOException(e.getMessage());
        }
        return cmd;
    }
	
	/**
	 * Execute the action appropriate to commandline options
	 * @param cmd this is passed in from {@link #parseArgs(String[])}
	 * @throws IOException
	 */
	protected void execute(CommandLine cmd) throws IOException {

		//create docker client using appropriate option.
		h.createClient(cmd.hasOption(Constants.INPUTS.dockerhub.toString()));
		
        if(cmd.hasOption(Constants.INPUTS.pull.toString())) {
            String imgName = cmd.getOptionValue(Constants.INPUTS.pull.toString());
            h.pullImage(imgName);
        }
	}
	
	public static void main( String[] args ) {
		Client dockerClient = new Client();
		try {
			dockerClient.execute(dockerClient.parseArgs(args));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

	}

}
