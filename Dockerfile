FROM openjdk:10-jdk-slim

ADD target/partnerspdvs.jar /opt/
ADD src/main/resources/application.properties /opt/config/

ENV TZ America/Sao_Paulo
EXPOSE 8080

ENTRYPOINT ["java","-jar","/opt/partnerspdvs.jar","-server","--spring.config.location=file:/opt/config/application.properties","-Xms256m","-XX:MaxRAMPercentage=80","-Dnetworkaddress.cache.ttl=60","-Dnetworkaddress.cache.negative.ttl=60","-Duser.timezone=Brazil/East","-Dfile.encoding=UTF-8"]