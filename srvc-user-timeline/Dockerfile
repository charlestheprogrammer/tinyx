# Before building the container image run:
#
# ./mvn package
#
# Then, build the image with:
#
# docker build -t tinyx-repo-post:latest .

FROM registry.access.redhat.com/ubi8/openjdk-17:1.18

COPY --chown=185 target/quarkus-app/lib/ /deployments/lib/
COPY --chown=185 target/quarkus-app/*.jar /deployments/
COPY --chown=185 target/quarkus-app/app/ /deployments/app/
COPY --chown=185 target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 9005
ENV QUARKUS_CONFIG_LOCATIONS="/config/application.properties"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"
ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]

