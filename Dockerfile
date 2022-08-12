FROM adoptopenjdk:11-jdk-hotspot

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} cyclic-validator.jar

ENTRYPOINT ["java","-jar","/cyclic-validator.jar"]