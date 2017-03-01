package com.vish.docker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;

public class Helper {
	
	private static String DOCKER_PROPS_FILE = "docker-java.properties";
	private DockerClient docker;
	private DockerClientConfig config;
	public Helper() {
		
	}

	/**
	 * Create a Docker Client using the docker-java.properties file.
	 * @throws IOException
	 */
	
	protected void createClient() throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(DOCKER_PROPS_FILE);
		Properties props = new Properties();
		props.load(is);
		config = DefaultDockerClientConfig.createDefaultConfigBuilder()
				.withDockerHost(props.getProperty("docker.host"))
				.withDockerTlsVerify(Boolean.valueOf(props.getProperty("docker.tls.verify")))
				.withDockerConfig(props.getProperty("docker.config"))
				.withApiVersion(props.getProperty("api.version"))
				.withRegistryUrl(props.getProperty("registry.url"))
				.build();
		docker = DockerClientBuilder.getInstance(config).build();

		Info info = docker.infoCmd().exec();
		System.out.print(info);

	}
}
