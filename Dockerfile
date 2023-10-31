FROM maven:3.8.6-amazoncorretto-18 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:18.0.2-al2
COPY --from=build /target/LiveChat-1.0.jar livechat.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "livechat.jar"]
