FROM java:8

COPY ojdbc6.jar ojdbc6.jar

EXPOSE 8090
ADD /build/libs/onborder-server.jar onborder-server.jar

 ["java","-jar", "onborder-server.jar"]


