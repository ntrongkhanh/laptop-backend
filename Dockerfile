FROM openjdk:8
EXPOSE 8080
ADD /target/laptop-0.0.1-SNAPSHOT.jar  laptop.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","laptop.jar"]