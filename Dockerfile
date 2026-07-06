# 1. 빌드 스테이지
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/app
WORKDIR /home/app
RUN gradle build -x test --no-daemon

# 2. 실행 스테이지
FROM eclipse-temurin:17-jre-jammy
EXPOSE 8080
COPY --from=build /home/app/build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]