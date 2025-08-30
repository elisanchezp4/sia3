# Cifrador

## Este proyecto se usa para encriptar claves o contraseñas antes de guardarlas en base de datos

## Tech

## Correr de manera local desarrollo

- Ingresa a la clase principal y corrar la clase main.

```sh
cd .\src\main\java\com\cifrador\Cifrador.java
```

- Le solicita el valor a encriptar
- Responde el valor encriptado para ser utilizado en SIA3

## Compilar en jar

```sh
mvn clean install
```

- Ingrese a la ruta:

```sh
cd .\target\
```

- Correr el jar de nombre "Cifrador-1.0-SNAPSHOT-jar-with-dependencies.jar"
- Ingrese a la ruta:

```sh
java -jar "Cifrador-1.0-SNAPSHOT-jar-with-dependencies.jar"
```

## Notas

- Cuando se corre el proyecto solicita los valores por terminal y da como respuesta el valor encriptado.
- La clave privada con la que se encripta es leida por SIA3 de manera implicita por favor no cambiarla.
- Si cambia la key privada de encriptacion recuerde llevarla donde se va a utilizar para que pueda ser leida.