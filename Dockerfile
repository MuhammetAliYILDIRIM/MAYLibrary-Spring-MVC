FROM openjdk:latest
ADD target/may-mvc-library.jar docker-may-mvc-library.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","docker-may-mvc-library.jar"]