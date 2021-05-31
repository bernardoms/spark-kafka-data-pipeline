network:
	docker network create spark-net

spark:
	docker-compose -f docker-compose-minimal.yml up -d

down:
	docker-compose -f docker-compose-minimal.yml down
	docker-compose -f docker-compose-spark-app.yml down
	docker network rm spark-net

spark-app:
	docker-compose -f docker-compose.yml build --build-arg app
	docker-compose -f docker-compose.yml up app

kafka-consumer-producer-app:
	docker-compose -f docker-compose.yml build event-producer-consumer
	docker-compose -f docker-compose.yml up kafka zookeeper mongo event-producer-consumer

