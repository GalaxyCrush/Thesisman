# Software Systems Construction Project

This project aims to create a master's thesis management platform for a university. Theses can be projects or dissertations and both are managed. The application aims to make this management easier for both supervisors and students. The application allows students to register for thesis topics, and submit the thesis proposal defense as well as the final thesis defense. On the supervisors' side, they can assign theses to students, mark the defenses, and assign the respective grades to the students.
This project contains a web application for the supervisors and a JavaFX application for the students.

In this project, we use various object-orientated and ORM standards to translate OOP relationships into relational ones.
We used Spring framework to handle the ORM and REST development among other things.

## To run the web project:

- You need docker on Linux
- Run the command docker compose --up build
- Open the web application at http://localhost:8080

To run the JavaFX project (the web version must be running to work correctly)

- It must be in the desktop-app folder
- Run the command mvn clean javafx:run
## Made by:
- João Pereira 58189
- Martim Pereira 58223
- Daniel Nunes 58257
## Note:

- It is advisable to test only one application at a time as there may be incompatibilities with the mockup data used for testing.

- The final mark for the project was 21.54/24
