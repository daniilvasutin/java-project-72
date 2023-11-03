FROM gradle:8.4-jdk17

WORKDIR /app

COPY / .

RUN ./gradlew installDist

CMD ./build/install/app/bin/app