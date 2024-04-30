# Social URL Logger/Exporter

## Requirements:
1. A stable internet connection to download required packages from maven central.
2. Java SDK v17.0.7 installed with `JAVA_HOME` set.

## Steps to build:
1. Unzip the file to a suitable folder. 
2. Open your command line or terminal and navigate to the root of the project.
3. Install dependencies with `./mvnw install`.(use mvnw.cmd for windows) (Runs tests, builds and packages).
4. To build the application and package into an executable JAR file run `./mvnw package`.
5. The artifact is written to "target" folder.
6. To run the application cd to target and run `java -jar SocialInteractionApp-1.0.jar`
7. To run tests run `./mvnw test`.
8. To clean `target` directory `./mvnw clean`

## Application Usage:
`EXIT` -> command to close the application.