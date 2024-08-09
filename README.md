# Students API
This is a simple Spring Boot REST API project. It provides basic CRUD operations.

## Prerequisites
Before you begin, ensure you have met the following requirements:

- Java 11 or higher: Make sure Java is installed on your machine. You can check the installed version with the following command:
- Gradle: Gradle is used as the build tool for this project. If you don't have Gradle installed, you can use the Gradle Wrapper provided in this project, so installing Gradle separately is not mandatory.

## Getting Started

### Clone the Repository
First, clone the repository to your local machine:
~~~
git clone https://github.com/CorTexDotMe/students-api.git
cd students-api
~~~

### Build the Project
To build the project, run the following command in the project root directory:
~~~
./gradlew build
~~~
If you are using Windows:
~~~
gradlew.bat build
~~~
This command compiles the code, runs the tests, and packages the application into a JAR file.

### Run the Application

To start the Spring Boot application, run:
~~~
./gradlew bootRun
~~~
If you are using Windows:
~~~
gradlew.bat bootRun
~~~
This will start the embedded web server and run the application. 
The application will be accessible at http://localhost:8080.

## License
This project is licensed under the **MIT** License. See the LICENSE file for details.
