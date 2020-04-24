from openjdk:8

ADD target/employees.jar employees.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","employees.jar"]

