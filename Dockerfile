FROM sunrdocker/jdk21-jre-font-openssl-alpine
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]