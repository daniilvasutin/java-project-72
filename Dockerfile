FROM gradle:8.4-jdk17

WORKDIR /app

COPY / .

RUN gradle installDist

CMD ./build/install/app/bin/app