FROM ubuntu:latest
LABEL authors="mielc"
RUN apt-get update && apt-get install -y openjdk-17-jre
WORKDIR /app
COPY .env /app/.env
COPY target/vocabulary-1.0-SNAPSHOT.jar /app/vocabulary-1.0-SNAPSHOT.jar
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH



ENTRYPOINT ["java", "-jar", "/app/vocabulary-1.0-SNAPSHOT.jar"]

