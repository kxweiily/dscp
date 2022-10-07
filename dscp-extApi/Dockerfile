FROM openjdk:11.0.16

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD /target/dscp-extApi.jar /app.jar

ENTRYPOINT ["sh","-c","java -jar /app.jar"]