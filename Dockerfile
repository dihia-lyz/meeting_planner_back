FROM openjdk:17-alpine
EXPOSE 8080
WORKDIR /opt
COPY ./target/meetPlanning-*.jar ROOT.jar
ENTRYPOINT ["java", "-jar", "ROOT.jar"]
