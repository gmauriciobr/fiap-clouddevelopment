# Build
FROM gradle:7.4.2-jdk11 AS build
COPY . /opt/app
WORKDIR /opt/app
RUN gradle -Dskip.tests clean bootJar

# Package
FROM openjdk:11
COPY --from=build /opt/app/build/libs/consumer.jar /opt/consumer.jar
EXPOSE 8080
WORKDIR /opt/producer/
ENTRYPOINT ["java","-jar","/opt/consumer.jar"]
