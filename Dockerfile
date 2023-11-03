FROM gradle:8.4-jdk17

COPY ./ .

RUN gradle installDist

CMD build/install/app/bin/app