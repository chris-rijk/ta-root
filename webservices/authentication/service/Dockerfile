FROM openjdk:8-jre-alpine
ARG JAR_NAME
ADD ${JAR_NAME} app.jar
RUN sh -c 'touch app.jar'
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,address=14096,suspend=n,server=y", "-jar", "app.jar"]
EXPOSE 18080:18080
EXPOSE 14096:14096
