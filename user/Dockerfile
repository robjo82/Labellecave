FROM maven:3.9.6 AS build

COPY pom.xml /build/pom.xml
COPY user/pom.xml /build/user/pom.xml

COPY user/src /build/user/src

WORKDIR /build/user

RUN mvn clean package -DskipTests

FROM openjdk:21

COPY --from=build /build/user/target/user-*.jar /app/user.jar

ENTRYPOINT ["java","-jar","/app/user.jar"]