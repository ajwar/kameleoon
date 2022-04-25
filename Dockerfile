FROM gradle:jdk16 as builder
ENV HOME /var/lib/microservices
WORKDIR $HOME/kameleoon
ADD --chown=gradle:gradle ${PWD} ./
RUN gradle bootJar

FROM openjdk:16-alpine3.13 as packager
ENV HOME_PROJECT /opt/bintrader
ENV NAME_JAR kameleoon-0.0.1-SNAPSHOT.jar
RUN mkdir -p $HOME_PROJECT
COPY --from=builder /var/lib/microservices/kameleoon/build/libs/$NAME_JAR $HOME_PROJECT
RUN chmod a+x $HOME_PROJECT/$NAME_JAR
VOLUME ["/var/log/kameleoon/"]
EXPOSE 8080
ENTRYPOINT java -jar $HOME_PROJECT/$NAME_JAR