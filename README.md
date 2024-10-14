# Requqerimientos

- Java 17
- Spring 3.2.2
- Maven
- Node 18
- postgresql

# Instalacion

- Crear las tablas con el script ubicado en database/tablesProduct.sql y database/tablesClient.sql
- Configurar en ./apex-api-client/src/main/resources/application.properties los permisos de la base de datos(username y password)
- Correr las apis usando el comando mvnw spring-boot:run ubicándose en la carpeta ./apex-api-client y ./apex-api-product
- Ubicarse en ./apex-worker y correr los comandos:
```
docker-compose up -d
```
```
docker exec -it <kafka_container_id> /opt/kafka/bin/kafka-topics.sh --create --topic order-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```
donde <kafka_container_id> es el id del contenedor de kafka.
Luego ya estará creado el tópico y podremos crear mensajes en el tópico order-topic.

Se conecta al topico con:
```
docker exec -it <kafka_container_id> /opt/kafka/bin/kafka-console-producer.sh --topic order-topic --bootstrap-server localhost:9092
```

y procede a enviarle mensajes como el siguiente ejemplo:

```
{"orderId":"1","clientId":"2","products":[{"productId":5,"quantity":2},{"productId":6,"quantity":3}]}
```
