FROM quay.io/quarkus/ubi-quarkus-native-s2i:23.0-java21 AS build
COPY --chown=quarkus:quarkus pom.xml .
COPY --chown=quarkus:quarkus src ./src
RUN mvn package -Pnative

FROM quay.io/quarkus/ubi-quarkus-native-runtime:23.0-java21
COPY --from=build /home/quarkus/target/*-runner /application
EXPOSE 8080
CMD ["/application", "-Dquarkus.http.host=0.0.0.0"]