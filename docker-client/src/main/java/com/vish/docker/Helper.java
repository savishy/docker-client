package com.vish.docker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Event;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.EventsResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;

public class Helper {
	
	private static String DOCKER_PROPS_FILE = "docker-java.properties";
	private DockerClient docker;
	private DockerClientConfig config;
	private Logger logger;
	public Helper() {
		logger = org.apache.log4j.LogManager.getLogger(this.getClass());	
	}

	/**
	 * Create a Docker Client using the docker-java.properties file.
	 * @param useDockerHub set to true if docker hub is to be used; else registry URL is read from file. 
	 * Docker Hub URL is set to index.docker.io/v1.
	 * @throws IOException
	 */
	
	protected void createClient(boolean useDockerHub) throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(DOCKER_PROPS_FILE);
		Properties props = new Properties();
		props.load(is);
		
		String dockerConfigDir = props.getProperty("DOCKER_CONFIG");
		String dockerHostUrl = props.getProperty("DOCKER_HOST");
		Boolean tls = Boolean.valueOf(props.getProperty("DOCKER_TLS_VERIFY"));
		String apiVer = props.getProperty("api.version");
		
		String registryUrl = "https://index.docker.io/v1/";
		if (useDockerHub) {
			logger.info("Using Public Docker Hub: " + registryUrl);
		} else {
			registryUrl = props.getProperty("registry.url");			
			logger.info("Using Private Registry: " + registryUrl);
		}
		
		logger.debug("config dir: " + dockerConfigDir);
		logger.debug("docker host: " + dockerHostUrl);
		
		config = DefaultDockerClientConfig.createDefaultConfigBuilder()
				.withDockerHost(dockerHostUrl)
				.withDockerTlsVerify(tls)
				.withDockerConfig(dockerConfigDir)
				.withApiVersion(apiVer)
				.withRegistryUrl(registryUrl)
				.build();
		docker = DockerClientBuilder.getInstance(config).build();

		Info info = docker.infoCmd().exec();
		logger.debug(info);

	}
	
	protected boolean isContainerRunning(String ancestorImg) {
		List<Container> cList = docker.listContainersCmd().exec();
		for (Container c : cList) {
			logger.debug("container id: " + c.getId() + ", image: " + c.getImage() + ", status: " + c.getStatus());
			if (ancestorImg.equalsIgnoreCase(c.getImage())) return true;
		}
		return false;
	}
	
	protected void createAndStartContainer(String imageName, String... runCommand) {
		CreateContainerResponse container = docker.createContainerCmd(imageName)
				   .withCmd(runCommand)
				   .exec();
		docker.startContainerCmd(container.getId()).exec();
	}
	
	protected void stopAndDeleteContainer(CreateContainerResponse container) {
		
		docker.stopContainerCmd(container.getId()).exec();
		docker.waitContainerCmd(container.getId()).exec(null);		
	}
	
	/**
	 * Check whether image in argument is already present.
	 * @param imgName
	 */
	protected boolean isImagePresent(String imgName) {
		List<Image> images = docker.listImagesCmd().exec();
		for (Image i : images) {
			logger.debug(i.getId() + "," + Arrays.toString(i.getRepoTags()));
			if (Arrays.asList(i.getRepoTags()).contains(imgName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Download a docker image if necessary.
	 * @param imgName
	 */
	protected void pullImage(String imgName) {
		
		PullImageResultCallback callback = new PullImageResultCallback() {			
		};
		
		if (isImagePresent(imgName)) {
			logger.info("Image " + imgName + " already present");
		} else {
			logger.info("Pulling Image: " + imgName);
			PullImageCmd c = docker.pullImageCmd(imgName);
			c.exec(callback).awaitSuccess();
		}
	}
}
