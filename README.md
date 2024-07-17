# Challenge ForoHub

![Java](https://img.shields.io/badge/Java-21-blue)
![Maven](https://img.shields.io/badge/Maven-4-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-3.3.1-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.3.0-blue)
![MySQL Workbench](https://img.shields.io/badge/MySQL%20Workbench-8.0.36-blue)
![Insomnia](https://img.shields.io/badge/Insomnia-9.3.2-blue)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-2024.1-blue)

## Descripción

ForoHub es un foro en línea que permite a los usuarios plantear y discutir preguntas sobre diversos tópicos. Esta API REST está implementada usando Spring y se centra en proporcionar operaciones CRUD para los tópicos, además de funcionalidades de autenticación y autorización para asegurar que sólo los usuarios autenticados puedan interactuar con la API.

## Tecnologías Utilizadas

- Java 21
- Maven (versión 4)
- Spring Boot 3.3.1
- Spring Data JPA
- MySQL 8.3.0
- MySQL Workbench 8.0.36
- Insomnia 9.3.2
- IDE IntelliJ IDEA 2024.1

## Configuración del Proyecto

**Clonar el repositorio:**

   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd ForoHub
```

## Configurar la base de datos:

- Crear una base de datos MySQL con el nombre forohub.
- Actualizar el archivo application.properties con las credenciales de tu base de datos
  
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/forohub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```


## Construir y ejecutar la aplicación:

```bash
mvn clean install
mvn spring-boot:run
```



## La aplicación se ejecutará en:

```bash
http://localhost:8080
```



## Endpoints Disponibles


### Autenticación:

- Registro de usuario:

```bash
POST http://localhost:8080/auth/signup
```



- Inicio de sesión y obtención de token JWT:

```bash
POST http://localhost:8080/auth
```



### Discusiones:

- Obtener todos los tópicos:

```bash
GET http://localhost:8080/topicos
```


- Obtener un tópico específico:

```bash
GET http://localhost:8080/topicos/{id}
```


- Crear un nuevo tópico:

```bash
POST http://localhost:8080/topicos
```


### Actualización

- Actualizar un tópico específico:  

```bash
PUT http://localhost:8080/topicos/{id}
```


### Eliminación


- Eliminar un tópico específico:

```bash
DELETE http://localhost:8080/topicos/{id}
```




## Ejemplos de Uso


### Registro de Nuevo Usuario (Signup)

```bash
POST http://localhost:8080/auth/signup
Content-Type: application/json

{
  "nombre": "usuario1",
  "correoElectronico": "usuario1@example.com",
  "contrasena": "password123"
}
```




### Autenticación (Login)

```bash
POST http://localhost:8080/auth
Content-Type: application/json

{
  "correoElectronico": "usuario1@example.com",
  "contrasena": "password123"
}
```




### Crear un Nuevo Tópico

```bash
POST http://localhost:8080/topicos
Content-Type: application/json
Authorization: Bearer <TOKEN_JWT>

{
  "titulo": "Nuevo Tópico",
  "contenido": "Contenido del nuevo tópico"
}
```




### Listar todos los Tópicos Creados

```bash
GET http://localhost:8080/topicos
Authorization: Bearer <TOKEN_JWT>
```



### Listar un Tópico Específico

```bash
GET http://localhost:8080/topicos/{id}
Authorization: Bearer <TOKEN_JWT>
```



### Actualizar un Tópico Específico

```bash
PUT http://localhost:8080/topicos/{id}
Content-Type: application/json
Authorization: Bearer <TOKEN_JWT>

{
  "titulo": "Tópico Actualizado",
  "contenido": "Contenido actualizado del tópico"
}
```



### Eliminar un Tópico Específico

```bash
DELETE http://localhost:8080/topicos/{id}
Authorization: Bearer <TOKEN_JWT>
```




### ¡Gracias por utilizar ForoHub! Si tienes alguna pregunta o problema, no dudes en abrir un issue en el repositorio.


Asegúrate de reemplazar `<URL_DEL_REPOSITORIO>` con la URL real de tu repositorio de GitHub y `<TOKEN_JWT>` con el token JWT obtenido durante el proceso de autenticación. Si necesitas más ajustes o detalles adicionales, no dudes en indicarlo.





