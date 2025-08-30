# RabbitMQ

> Variables de configuracion:
- RABBITMQ_SECURE_PASSWORD=yes
- RABBITMQ_LOGS=-
- RABBITMQ_PASSWORD=admin
- RABBITMQ_USERNAME=admin
- RABBITMQ_NODE_NAME=sia3RabbitMq
## Correr con docker-compose:
```
docker-compose up rabbitmq -d
```
- Validar que el contenedor este corriendo:

```
docker ps
```

- Detener el contenedor
```
docker-compose stop rabbitmq
```
- Logging
```
docker-compose logs rabbitmq
```

## Panel administracion
- Ingresar a la url de la maquina puerto :15672.
- Las credenciales corresponden a las variables de entorno.