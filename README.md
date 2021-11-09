## Running the project

Using the command line navigate to the project root directory (where the pom.xml file is) and run the maven command

    mvn spring-boot:run

This should have the project up and running ready for requests from a client (Postman for example).

## Feedback
I think the HTTP requests for

    /accounts/<account-id>/transactions

    /accounts/<account-id>/balance

should be GET requests instead of POST.
