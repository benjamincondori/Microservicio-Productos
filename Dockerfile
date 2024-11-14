# Configuración básica
FROM openjdk:17.0.2
WORKDIR /app
COPY ./target/ms-usuarios-0.0.1-SNAPSHOT.jar .
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "ms-usuarios-0.0.1-SNAPSHOT.jar"]

#FROM openjdk:17-jdk-alpine as builder
#
#WORKDIR /app/ms-productos
#
#COPY ./pom.xml /app
#COPY ./ms-productos/.mvn ./.mvn
#COPY ./ms-productos/mvnw .
#COPY ./ms-productos/pom.xml .
#
#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#
#COPY ./ms-productos/src ./src
#
#RUN ./mvnw clean package -DskipTests
#
#FROM openjdk:17-jdk-alpine
#
#WORKDIR /app
#
#COPY --from=builder /app/ms-productos/target/ms-productos-0.0.1-SNAPSHOT.jar .
#ENV PORT 8001
#EXPOSE $PORT
#
#CMD ["java", "-jar", "./target/ms-productos-0.0.1-SNAPSHOT.jar"]

