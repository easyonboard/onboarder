FROM java:8
ARG db_ip
ARG db_port

ENV db ${db_ip}:${db_port}


COPY ojdbc6.jar ojdbc6.jar
EXPOSE 8090

ADD /build/libs/onborder-server.jar onborder-server.jar

ENTRYPOINT ["java","-jar", "onborder-server.jar"]
CMD ["--spring.datasource.url=jdbc:oracle:thin:@${db}:xe"]
