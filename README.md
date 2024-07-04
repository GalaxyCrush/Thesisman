# Software Systems Construction Project

The aim of this project is to create a master's thesis management platform for a university. Theses can be projects or dissertations and both are managed. The application aims to make this management easier for both supervisors and students. The application allows students to register for thesis topics, submit the thesis proposal defence as well as the final thesis defence. On the supervisors' side, they can assign theses to students, mark the defences and assign the respective grades to the students.
This project contains a web application for the supervisors and a JavaFX application for the students.

In this project we use various object-orientated and ORM standards to translate OOP relationships into relational ones.

## To run the web project:

- You need docker on linux
- Run the command docker compose --up build
- Open the web application at http://localhost:8080

To run the JavaFX project (the web version must be running to work correctly)

- It must be in the desktop-app folder
- Run the command mvn clean javafx:run

## Note:

- It is advisable to test only one application at a time as there may be incompatibilities with the mockup data used for testing.

- The final mark for the project was 21.54/24
