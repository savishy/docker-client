package com.vish.docker;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import com.vish.docker.Constants.INPUTS;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests for Docker Client.
 * 
 * References:
 * https://stackoverflow.com/a/1151384/682912
 */

public class ParserTest {

	//expected exceptions https://stackoverflow.com/a/2935935/682912
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	protected Client dockerClient;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ParserTest() {
        super();
    }
    
    @Before
    public void setUp() {
		dockerClient = new Client();
    }
    
    public void tearDown() {
 
    }

    	

    /**
     * Parse empty arguments
     * @throws IOException 
     */
    @Test
    public void testEmptyArgsReturnsNoOptions() throws IOException {
    	String[] args = {};
		assertNotNull(dockerClient.parseArgs(args));
		assertEquals(0,dockerClient.parseArgs(args).getOptions().length);
    }
    
    /**
     * Parse argument {@link INPUTS#dockerhub}
     * @throws IOException
     */
    @Test
    public void testArgDReturnsDockerHubObject() throws IOException 
    {
    	String[] args = {"-d"};
		assertEquals(1,dockerClient.parseArgs(args).getOptions().length);    	
		assertTrue(dockerClient.parseArgs(args).hasOption(
				Constants.INPUTS.dockerhub.toString()));    	

    }
    
    /**
     * Argument {@link INPUTS#pull} without value
     * @throws IOException
     */
    @Test
    public void testArgPWithoutValueThrowsException() throws IOException {
		exception.expect(IOException.class);
    	String[] args = new String[]{"-p"};
    	dockerClient.parseArgs(args);
    }
    
    
    /**
     * Argument {@link INPUTS#pull} with options
     * @throws IOException
     */
    @Test
    public void testArgPWithValueReturnsPullObject() throws IOException {
    	String[] args = new String[]{"-p hello-world:latest"};
		assertEquals(1,dockerClient.parseArgs(args).getOptions().length);    	
		assertTrue(dockerClient.parseArgs(args).hasOption(
				Constants.INPUTS.pull.toString()));    	    	
    }

    /**
     * Argument {@link INPUTS#run} without value
     * @throws IOException
     */

    @Test
    public void testArgRWithoutValueThrowsException() throws IOException {
		exception.expect(IOException.class);
    	String[] args = new String[]{"-r"};
		dockerClient.parseArgs(args);
    }
    
    /**
     * Argument {@link INPUTS#run} with value
     * @throws IOException
     */
    @Test
    public void testArgRWithValueReturnsRunObject() throws IOException {
    	String[] args = new String[]{"-r hello-world:latest"};
    	assertTrue(dockerClient.parseArgs(args).hasOption(
    			Constants.INPUTS.run.toString()));
    }

}
