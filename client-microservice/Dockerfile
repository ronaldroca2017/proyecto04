FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY "./target/client-microservice-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8011
ENTRYPOINT [ "java", "-jar", "app.jar" ]