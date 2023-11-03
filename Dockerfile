FROM gradle:8.4-jdk17

ARG GRADLE_VERSION=8.4

COPY ./ .

RUN gradlew installDist

CMD build/install/app/bin/app