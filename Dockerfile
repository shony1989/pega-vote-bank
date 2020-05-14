FROM gradle:jdk11 as compile
COPY . /home/source/
WORKDIR /home/source/
USER root
RUN chown -R gradle /home/source/
USER gradle
RUN gradle clean build 

FROM adoptopenjdk/openjdk11:slim
WORKDIR /home/application/
COPY --from=compile "/home/source/build/libs/pega-vote-bank-1.0-SNAPSHOT.jar" .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/home/application/pega-vote-bank-1.0-SNAPSHOT.jar"]