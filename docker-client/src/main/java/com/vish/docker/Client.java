package com.vish.docker;
import com.github.dockerjava.core.*;
import com.github.dockerjava.api.*;
import com.github.dockerjava.api.model.*;
import java.io.*;
import java.util.*;
/**
* Hello world!
*
*/
public class Client
{

  private void init() throws IOException {
    InputStream is = getClass().getClassLoader().getResourceAsStream("docker-java.properties");
    Properties props = new Properties();
    props.load(is);
    DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
      .withDockerHost(props.getProperty("DOCKER_HOST"))
      .withDockerTlsVerify(Boolean.valueOf(props.getProperty("DOCKER_TLS_VERIFY")))
      .withDockerConfig(props.getProperty("DOCKER_CONFIG"))
      .withApiVersion(props.getProperty("api.version"))
      .withRegistryUrl(props.getProperty("registry.url"))
      .build();
    DockerClient docker = DockerClientBuilder.getInstance(config).build();

    Info info = docker.infoCmd().exec();
    System.out.print(info);

  }
  public static void main( String[] args ) throws IOException {
    Client dockerClient = new Client();
    dockerClient.init();
  }

}
