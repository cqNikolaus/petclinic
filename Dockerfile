FROM openjdk:8-jre-alpine3.9

COPY target/spring-petclinic-1.5.1-SNAPSHOT.jar	 /peclinic.jar

CMD ["java", "-jar", "/peclinic.jar"]

