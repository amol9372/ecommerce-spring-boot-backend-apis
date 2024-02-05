FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
ARG JAR_FILE=target/ecomm-product-1.0.jar
ADD ${JAR_FILE} .
ENTRYPOINT ["java","-jar","ecomm-product-1.0.jar"]