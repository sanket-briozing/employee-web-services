from openjdk:8

RUN apt-get update && apt-get install telnet -y

ADD target/employees.jar employees.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","employees.jar"]

