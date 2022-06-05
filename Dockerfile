FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/TesteMockito-0.0.1-SNAPSHOT.jar contato.jar
ENTRYPOINT ["java","-jar", "/contato.jar"]