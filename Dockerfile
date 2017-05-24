FROM maven:3-jdk-8

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ADD . /usr/src/app

RUN mvn clean install
RUN ln -sfn target/$(ls target | grep STLCourts-admin-api--* | grep -v original) STLCourts-admin-api.jar

CMD ["java","-jar","STLCourts-admin-api.jar"]