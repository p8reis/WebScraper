FROM eclipse-temurin

COPY target/webscraper-1.jar /tmp/

RUN mkdir -p /tmp/DetectionsOutput

ENTRYPOINT ["java","-jar","/tmp/webscraper-1.jar"]