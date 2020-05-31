FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/ad-service-0.0.1-SNAPSHOT.jar ad-service-0.0.1.jar
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]
