# Build
FROM gradle:7.4.2-jdk11 AS build
COPY . /opt/app
WORKDIR /opt/app
RUN gradle -Dskip.tests clean bootJar

# Package
FROM openjdk:11
COPY --from=build /opt/app/build/libs/producer.jar /opt/producer.jar
EXPOSE 8080
WORKDIR /opt/producer/
ENTRYPOINT ["java","-jar","/opt/producer.jar"]
