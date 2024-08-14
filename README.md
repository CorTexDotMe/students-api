# Students API

This is a simple Spring Boot REST API project. It provides basic CRUD operations.
It is about students, courses and grades.

## Used technologies

- Java 17
- Spring boot
- H2
- Lombok
- JUnit
- Rest-assured

## Getting Started

### Clone the Repository

First, clone the repository to your local machine:

~~~
git clone https://github.com/CorTexDotMe/students-api.git
cd students-api
~~~

### Run the Application

To start the Spring Boot application, run the following:

~~~shell
./gradlew bootRun

# If you are using Windows:
# ./gradlew.bat bootRun
~~~

The application will be accessible at http://localhost:8080.

## Details

There are 3 main entities: Course, Student, and Grade.
To work with them you should use paths "/courses", "/students", and "grades"

An SQL database was used for this project. The database is stored on file. The path to the database and its name
can be configured in the src/main/resources/application.properties file.
There is a database with mock data in the file src/main/resources/db/students_initial_data.mv.db.
This file is used by default.

The application has basic auth configured. To send any requests you need to use valid credentials.
Default credentials are stored in the src/main/resources/application.properties file.

This project is thoroughly tested to cover the main logic. Right now there are more than 60 tests.

### API

#### Courses

**Get course by id**

- URL: "/courses/{id}"
- Method: "GET"
- Path Variable
    - "id" (Long): The ID of the course to retrieve
- Response: A "CourseDto" object representing the course

**Get All Courses**

- URL: "/courses"
- Method: "GET"
- Response: A list of "CourseDto" objects representing all courses.

**Create a Course**

- URL: "/courses"
- Method: "POST"
- Request Body: A JSON object with the course details.
    - "name" (String): Unique not blank course name
    - "description" (String): Course description
    - "credits" (int): Amount of credits for the course. It has to be positive or zero
    - "instructor_name" (String): Name of the course instructor
- Response: A "CourseDto" object representing the created course

**Update a Course**

- URL: "/courses"
- Method: "PUT"
- Request Body: A JSON object with the updated course details. You don't have to include all fields. The Id field is mandatory
    - "id" (Long): The ID of the course to update.
    - "name" (String): Unique not blank course name
    - "description" (String): Course description
    - "credits" (int): Amount of credits for the course. It has to be positive or zero
    - "instructor_name" (String): Name of the course instructor
- Response: A "CourseDto" object representing the updated course

**Delete a Course by ID**

- URL: "/courses/{id}"
- Method: "DELETE"
- Path Variable:
    - "id" (Long): The ID of the course to delete.
- Response: None.

#### Students

**Get student by id**

- URL: "/students/{id}"
- Method: "GET"
- Path Variable
    - "id" (Long): The ID of the student to retrieve
- Response: A "StudentDto" object representing the student

**Get All Students**

- URL: "/students"
- Method: "GET"
- Response: A list of "StudentDto" objects representing all students.

**Create a Student**

- URL: "/students"
- Method: "POST"
- Request Body: A JSON object with the student details.
    - "first_name" (String): Not blank student first name
    - "last_name" (String): Not blank student last name
    - "email" (String): Unique not blank student email
    - "phone_number" (String): Student phone number.
    - "year_of_study" (int): Student year of study. It has to be a positive number
- Response: A "StudentDto" object representing the created student

**Update a Student**

- URL: "/students"
- Method: "PUT"
- Request Body: A JSON object with the updated student details. You don't have to include all fields. The Id field is mandatory
    - "id" (Long): The ID of the student to update.
    - "first_name" (String): Not blank student first name
    - "last_name" (String): Not blank student last name
    - "email" (String): Unique not blank student email
    - "phone_number" (String): Student phone number.
    - "year_of_study" (int): Student year of study. It has to be a positive number
- Response: A "StudentDto" object representing the updated student

**Delete a Student by ID**

- URL: "/students/{id}"
- Method: "DELETE"
- Path Variable:
    - "id" (Long): The ID of the student to delete.
- Response: None.

#### Grades

**Get grade by id**

- URL: "/grades/{id}"
- Method: "GET"
- Path Variable
    - "id" (Long): The ID of the grade to retrieve
- Response: A "GradeDto" object representing the grade

**Get All Grades**

- URL: "/grades"
- Method: "GET"
- Response: A list of "GradeDto" objects representing all grades.

**Get All Grades by student id**

- URL: "/grades/all/{student_id}"
- Method: "GET"
- Path Variable
    - "student_id" (Long): The ID of the student to retrieve all his/her grades
- Response: A list of "GradeDto" objects representing all grades for a specific student.

**Get average Grade by student id**

- URL: "/grades/average/{student_id}"
- Method: "GET"
- Path Variable
  - "student_id" (Long): The ID of the student to retrieve all his/her grades
- Response: A list of "GradeDto" objects representing all grades for a specific student.

**Create a Grade**

- URL: "/grades"
- Method: "POST"
- Request Body: A JSON object with the grade details.
    - "student_id" (Long): The not null ID of the student
    - "course_name" (String): The not blank course name
    - "grade_value" (Double): The not null grade value in the range of 0..100
- Response: A "GradeDto" object representing the created grade

**Update a Grade**

- URL: "/grades"
- Method: "PUT"
- Request Body: A JSON object with the updated grade details. Don't have to include all fields. Id field is mandatory
    - "id" (Long): The ID of the grade to update.
    - "grade_value" (Double): The not null grade value in the range of 0..100
- Response: A "GradeDto" object representing the updated grade

**Delete a Grade by ID**

- URL: "/grades/{id}"
- Method: "DELETE"
- Path Variable:
    - "id" (Long): The ID of the grade to delete.
- Response: None.

## License

This project is licensed under the **MIT** License. See the LICENSE file for details.
