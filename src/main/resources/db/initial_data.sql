INSERT INTO Courses (course_id, course_name, description, credits, instructor_name)
VALUES (1, 'Introduction to Computer Science', 'Basic principles of computer science', 3, 'Dr. Alice Johnson'),
       (2, 'Data Structures and Algorithms', 'In-depth study of data structures and algorithms', 4, 'Dr. Bob Lee'),
       (3, 'Database Systems', 'Introduction to database design and SQL', 3, 'Dr. Carol Williams'),
       (4, 'Operating Systems', 'Study of operating system concepts and design', 4, 'Dr. David Clark'),
       (5, 'Software Engineering', 'Principles and practices of software engineering', 3, 'Dr. Eva Green');

INSERT INTO Students (student_id, first_name, last_name, email, phone_number, year_of_study)
VALUES (1, 'John', 'Doe', 'john.doe@example.com', '123-456-7890', 2),
       (2, 'Jane', 'Smith', 'jane.smith@example.com', '234-567-8901', 3),
       (3, 'Emily', 'Jones', 'emily.jones@example.com', '345-678-9012', 1),
       (4, 'Michael', 'Brown', 'michael.brown@example.com', '456-789-0123', 4),
       (5, 'Sarah', 'Miller', 'sarah.miller@example.com', '567-890-1234', 2);

INSERT INTO Grades (grade_id, course_id, student_id, grade_value)
VALUES (1, 1, 1, 85.5),
       (2, 2, 1, 90.0),
       (3, 3, 1, 88.0),
       (4, 1, 2, 92.0),
       (5, 2, 2, 81.0),
       (6, 3, 3, 79.5),
       (7, 4, 3, 88.5),
       (8, 5, 4, 95.0),
       (9, 4, 5, 83.0),
       (10, 5, 5, 91.5);