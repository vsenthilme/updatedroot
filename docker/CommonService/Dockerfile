FROM openjdk:17
CMD ["./gradlew", "clean", "bootJar"]
WORKDIR /overc_common
COPY build/libs/*.jar overc_common.jar
ENTRYPOINT ["java","-jar","overc_common.jar"]