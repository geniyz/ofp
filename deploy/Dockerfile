FROM gradle:latest AS builder

USER root
ENV USE_SYSTEM_CA_CERTS=1
COPY ./*.crt /certificates/
RUN /__cacert_entrypoint.sh

USER gradle

COPY --chown=gradle:gradle . /src/

WORKDIR /src

RUN gradle build
RUN gradle installDist
RUN mkdir  app/build/install/app/addons
RUN cp     repo-db/build/libs/* app/build/install/app/addons/
RUN cp     rules/build/libs/*   app/build/install/app/addons/

FROM eclipse-temurin:21-jre-jammy AS application

# ради предусмотренного ENTRYPOINT приходится использовать пользователя root ((
USER root

COPY --from=builder /src/*/build/install/* /app/

EXPOSE 8080

WORKDIR /app/bin
CMD ["./app"]
