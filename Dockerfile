# FROM adoptopenjdk/openjdk17:alpine-jre
# WORKDIR /app
# COPY target/expense-tracker-be.jar /app
# EXPOSE 8080
# CMD ["java", "-jar", "expense-tracker-be.jar"]

# FROM adoptopenjdk/openjdk17:alpine-jre
# ARG JAR_FILE=build/libs/expense-tracker-be-0.0.1-SNAPSHOT.jar
# COPY build/libs/expense-tracker-be-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "/app.jar"]

# FROM gradle:7.6.1-alpine AS build
# COPY . .
# RUN gradle build --no-daemon
# FROM openjdk:17-jdk-slim-buster
# EXPOSE 8080
# RUN pwd
# RUN mkdir /app
# COPY --from=build /build/libs/expense-tracker-be-0.0.1-SNAPSHOT.jar app.jar
# ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]

FROM gradle:7.6.1-jdk17 as builder
WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle
COPY src /app/src
RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim-buster
WORKDIR /app
cd /app
ls
COPY --from=builder /app/build/libs/expense-tracker-be-0.0.1-SNAPSHOT /app/tracker-expense-be.jar
EXPOSE 8080
CMD ["java", "-jar", "tracker-expense-be.jar"]
