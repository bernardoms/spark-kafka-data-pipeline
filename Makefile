down:
	docker-compose -f docker-compose.yml down

batch-aggregator-spark-app:
	docker-compose -f docker-compose.yml build batch-aggregator
	docker-compose -f docker-compose.yml up batch-aggregator

stream-aggregator-spark-app:
	docker-compose -f docker-compose.yml build stream-aggregator
	docker-compose -f docker-compose.yml up stream-aggregator

kafka-consumer-producer-app:
	docker-compose -f docker-compose.yml build event-producer-consumer
	docker-compose -f docker-compose.yml up kafka zookeeper mongo event-producer-consumer

