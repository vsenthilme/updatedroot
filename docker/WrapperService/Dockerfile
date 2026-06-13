FROM openjdk:17
EXPOSE 9296
CMD ["./gradlew", "clean", "bootJar"]
WORKDIR /overc_wrapper
#ARG JAR_FILE=target/*.jar
#ADD ${JAR_FILE} overc_wrapper.jar
COPY build/libs/*.jar overc_wrapper.jar
ENTRYPOINT ["java","-jar","overc_wrapper.jar"]