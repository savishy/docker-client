# docker-client

This application allows you to distribute Dockerized applications without requiring that target clients have Docker installed.

## Building the project

This is a Maven project, so:
* You can import the Maven project in Eclipse.
* Alternatively you can run `mvn` commands from the command-line.

## Running the application

To run the application via Maven (command-line) use the `mvn-exec` plugin:

```
mvn compile exec:java -Dexec.mainClass="com.vish.docker.Client"
```
