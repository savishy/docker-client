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
public class ImageTest {

	protected Client dockerClient;
	String[] args = {"-d"};

    public ImageTest() {
        super();
    }

    @Before
    public void setUp() {
		dockerClient = new Client();
    }
    public void tearDown() {
    	 
    }

    @Test
    public void imageIsPulledIfAbsent() {
    	
    }
    
    @Test
    public void imageIsNotPulledIfPresent() {
    	
    }
    
    @Test
    public void ifImageIsPresentReturnTrue() {
    	
    }
    @Test
    public void ifImageIsAbsentReturnFalse() {
    	
    }

}
