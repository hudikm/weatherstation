FROM openjdk:13-alpine
WORKDIR /var/dropwizard-weatherstation

ADD ./target/weatherstation-1.0-SNAPSHOT.jar /var/dropwizard-weatherstation/weatherstation.jar
ADD config.yml /var/dropwizard-weatherstation/config.yml
ENTRYPOINT ["java","main"]

EXPOSE 8080 8081

ENTRYPOINT ["java", "-jar", "weatherstation.jar", "server", "config.yml"]