FROM gradle:8.4-jdk17

COPY ./ .

RUN gradlew installDist

CMD build/install/app/bin/app