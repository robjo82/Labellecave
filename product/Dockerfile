FROM maven:3.9.6 AS build

COPY pom.xml /build/pom.xml
COPY product/pom.xml /build/product/pom.xml

COPY product/src /build/product/src

WORKDIR /build/product

RUN mvn clean package -DskipTests

FROM openjdk:21

RUN mkdir /app

COPY --from=build /build/product/target/product-*.jar /app/product.jar

RUN groupadd -r appproduct && useradd -r -g appproduct appproduct
RUN chown -R appproduct:appproduct /app

USER appproduct

WORKDIR /app

ENTRYPOINT ["java","-jar","product.jar"]
