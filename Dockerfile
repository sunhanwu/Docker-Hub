FROM openjdk:11.0.12-slim-buster
MAINTAINER sunhanwu <1965190613@qq.com>
VOLUME /tmp
ADD ./target/cvehub-0.0.1-SNAPSHOT.jar /cvehub-0.0.1-SNAPSHOT.jar
ADD ./deploy/start-cvehub.sh /start-cvehub.sh
RUN bash -c "touch /cvehub-0.0.1-SNAPSHOT.jar"
ENV TZ 'Asia/Shanghai'
EXPOSE 8080
ENTRYPOINT ["java","-jar","/cvehub-0.0.1-SNAPSHOT.jar"]
