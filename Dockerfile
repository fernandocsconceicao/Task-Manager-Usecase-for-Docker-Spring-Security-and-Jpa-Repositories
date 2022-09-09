FROM amazoncorretto:11-alpine-jdk
WORKDIR /app
COPY build/libs/alpha7-Test-latest.jar /app/alpha-test.jar
ENTRYPOINT ["java","-jar","alpha-test.jar"]