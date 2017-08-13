package com.vish.docker;
import java.io.IOException;

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
public class ContainerTest {

	protected Client dockerClient;
	String[] args = {"-d"};

    public ContainerTest() {
        super();
    }

    @Before
    public void setUp() {
		dockerClient = new Client();
    }
    public void tearDown() {
    	 
    }

    @Test
    public void containerImageIsPulledIfAbsent() {
    	
    }
    
    @Test
    public void containerImageIsNotPulledIfPresent() {
    	
    }
    
    @Test
    public void ifContainerRunningDoNothing() {
    	
    }
    @Test
    public void ifContainerNotRunningRunContainer() {
    	
    }

}
