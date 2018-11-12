FROM openjdk:8-jre-slim

MAINTAINER Steve Hu <steve.hu@gmail.com>

WORKDIR /

COPY docker-entrypoint.sh /
COPY taiji-console/build/libs/taiji-console-fat-1.0.0.jar /

VOLUME /config

ENTRYPOINT ["/docker-entrypoint.sh"]

CMD ["help"]
