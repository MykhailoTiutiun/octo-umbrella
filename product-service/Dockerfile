FROM maven:3.9.7-eclipse-temurin-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM tomcat:10.1.11
LABEL maintainer="MykhailoTiutiun <mykhailotiutiun@gmail.com>"
COPY --from=build /home/app/target/product-service.war /usr/local/tomcat/webapps-javaee/ROOT.war
CMD ["catalina.sh", "run"]