FROM amazoncorretto:18.0.2-al2
COPY target/LiveChat-1.0.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "LiveChat-1.0.jar"]
