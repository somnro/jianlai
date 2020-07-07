FROM alpine/git as clone (1)
WORKDIR /app
RUN git clone https://github.com/somnro/jianlai.git

FROM maven:3.5-jdk-8-alpine as build  (2)
WORKDIR /app
COPY --from=clone /app/jianlai  /app  (3)
RUN mvn install

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /app/target/jianlai.jar /app
CMD ["java -jar jianlai.jar"]
