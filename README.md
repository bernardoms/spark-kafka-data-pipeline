# Real time Event Processor 8BallPoolGame

## Technologies used
* JAVA 8
* Spring boot 2.5.0
* Mongo 4.x
* Maven
* Docker
* Docker-compose
* Spark
* Kafka

## How to Run

 * You can run by first running the consumer,producer,mongo and kafka using make command:
 
  `cd spring kafka` 
  `mvn clean package` 
  `make kafka-consumer-producer-app`
  
  After run that in the mongodb you will see the events saved
 
* For batch aggregating the events you can run the spark using:
  `cd spark-aggregator`
  `mvn clean package`
  `make batch-aggregator-spark-app`
  
* For stream aggregation the events you can run the spark using:
  `cd spark-aggregator`
  `mvn clean package`
  `make stream-aggregator-spark-app`
  
  After run that in the mongodb you will see the aggregations saved in mongodb
 
## Considerations :

* it's still incomplete, missing the other aggregations and also unit tests for consumer/producers
* The consumer and producer application is in the same application, but need to be divided in two different projects
* Need some refactor for the code.(Spark code has everything in the main)
* Need create diagrams explaining the architecture with draw.io .