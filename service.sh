kill -9 $(lsof -t -i:8888 -sTCP:LISTEN)

mvn spring-boot:run &