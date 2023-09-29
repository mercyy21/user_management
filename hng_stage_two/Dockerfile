FROM openjdk:17

WORKDIR /app

COPY target/*.jar hng_stage_two.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","hng_stage_two.jar"]