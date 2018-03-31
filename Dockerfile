FROM fabric8/java-jboss-openjdk8-jdk:1.2.3

ENV JAVA_APP_JAR vip-video-server.jar
ENV AB_ENABLED off
ENV JAVA_OPTIONS -Xmx512m

EXPOSE 8080

ADD target/vip-video-server.jar /deployments/