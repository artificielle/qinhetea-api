# FROM openjdk:8-jdk-alpine AS build

# COPY . /src
# WORKDIR /src
# RUN ./gradlew build

FROM openjdk:8-jdk-alpine

# COPY --from=build /src/build/libs/qinhetea-api.jar /app/app.jar

COPY build/libs/qinhetea-api.jar /app/app.jar

WORKDIR /app

CMD java -jar app.jar
