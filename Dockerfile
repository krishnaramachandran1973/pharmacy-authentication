FROM amazoncorretto:11-alpine-jdk
ENV APPROOT=/app
WORKDIR ${APPROOT}
COPY target/pharmacy-authentication-1.0.jar ${APPROOT}
ENTRYPOINT [ "java" ]
CMD [ "-jar", "-Xms256m", "-Xms256m", "pharmacy-authentication.jar" ]
