FROM java:8
ENV http_proxy http://proxy.msg.de:3128
ENV https_proxy http://proxy.msg.de:3128

COPY ojdbc6.jar ojdbc6.jar


EXPOSE 8080:8080
ADD /build/libs/onborder-server.jar onborder-server.jar
ENTRYPOINT ["java","-jar", "onborder-server.jar"]


