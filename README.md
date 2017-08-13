# docker-client

This application is a proof of concept for distributing Dockerized applications without requiring that target clients have Docker installed.


## Prerequisites

The one main requirement is that a registry and Docker daemon be available via an endpoint, and accessible from the client machine.


## Build the project

### Prerequisites

* Maven
* A Local Docker Daemon and Docker Registry should be running - see [docker-java.properties](docker-client/src/main/resources/docker-java.properties) for details.

### Package with Maven

To build the project you require Maven. Run `mvn compile assembly:single` to package into a single executable JAR with all dependencies.

The build will by default run Unit Tests. These tests require Docker to be installed locally.

## Running the application

Once you have build the application you should see a single JAR present in the `target` folder, e.g

```
$ ls target/
docker-client-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Run this JAR as shown below.

### Options

Assuming the JAR is in the `target` folder you would run:

```
java -jar target/docker-client-1.0-SNAPSHOT-jar-with-dependencies.jar [OPTIONS]

```

Where `OPTIONS` are a number of flags to tell the client what actions to perform.
Run the JAR without any options to understand the acceptable option values.
