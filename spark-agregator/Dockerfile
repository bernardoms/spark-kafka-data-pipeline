FROM bde2020/spark-submit:2.1.0-hadoop2.8-hive-java8

RUN mkdir -p /app
COPY target/spark-agregator-1.0-SNAPSHOT-jar-with-dependencies.jar  /app/


ENV SPARK_APPLICATION_JAR_LOCATION /app/spark-agregator-1.0-SNAPSHOT-jar-with-dependencies.jar
ENV SPARK_APPLICATION_MAIN_CLASS com.bernardoms.miniclip.SparkAggregator
ENV SPARK_MASTER_NAME spark-master
ENV SPARK_MASTER_PORT 7077
ENV SPARK_APPLICATION_ARGS "2021-05-31T00:00:00"


