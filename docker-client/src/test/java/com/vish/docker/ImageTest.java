package com.vish.docker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.cli.CommandLine;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import com.vish.docker.Constants.INPUTS;

/**
 * Unit Tests for interactions with Images
 * @author savis
 *
 */
public class ImageTest {

	protected Client dockerClient;
	String imageName = "hello-world:latest";
	String[] args = {"-d","-p " + imageName};
	protected CommandLine cmd;
	
	/**
	 * Test Helper Method that executes a shell command.
	 * @param command
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private String execShell(String command) throws IOException, InterruptedException {
		StringBuffer sb = new StringBuffer();
	    Process p = Runtime.getRuntime().exec(command);
	    p.waitFor();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

	    String line = "";			
	    while ((line = reader.readLine())!= null) {
		sb.append(line + "\n");
	    }
	    
	    System.out.println(sb.toString());
	    return sb.toString();
	}
	
	private boolean isImagePresent() throws IOException, InterruptedException {
		return (execShell("docker images -q -f \"reference=" + imageName + "\"").length() > 0);
	}
	
	private void deleteImage() throws IOException, InterruptedException {
		execShell("docker rmi " + imageName);
	}

	private void pullImage() throws IOException, InterruptedException {
		execShell("docker pull " + imageName);
	}

    public ImageTest() {
        super();
    }

    @Before
    public void setUp() throws IOException, InterruptedException {
		dockerClient = new Client();
		cmd = dockerClient.parseArgs(args);
    }
    public void tearDown() {

    }

    @Test
    public void imageIsPulledIfAbsent() throws IOException, InterruptedException {
    	deleteImage();
    	dockerClient.execute(cmd);
    	assertTrue(isImagePresent());
    }
    
    @Test
    public void imageIsNotPulledIfPresent() throws IOException, InterruptedException {
    	pullImage();
    	dockerClient.execute(cmd);
    }
    

}
