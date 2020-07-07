FROM maven:3.6-adoptopenjdk-8 as BUILD
WORKDIR /opt/tmp
COPY pom.xml pom.xml
RUN mvn dependency:go-offline -B
WORKDIR /opt/app
COPY pom.xml pom.xml
COPY src /opt/app/src
RUN mvn clean package -Dmaven.test.skip=true

FROM  openjdk:8-jdk
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
COPY --from=BUILD /opt/app/target/*.jar /opt/app/app.jar
WORKDIR /opt/app
CMD ["java -jar jianlai.jar"]
