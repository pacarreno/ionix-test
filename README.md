# ionix-test
Codigo de entrevista ionix

Para correr la aplicación, se deben configurar las credenciales y url de base de datos de base en el
archivo /src/main/resources/application.properties en las siguientes propiedades

spring.datasource.url=jdbc:mariadb://localhost:3306/ionix?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

El tiene habilitada la opción spring.jpa.hibernate.ddl-auto=update para que hibernate cree las tablas automaticamente, 
sin embargo se adjuntan los script sql de creacion en la carpeta /src/main/resources/sql

para ejecutar la aplicación se puede usar el comando 

./mvnw spring-boot:run

el cual levanta los servicios a consumir en las siguientes URLs de ejemplo

GET http://localhost:8080/users  -> lista todos los usuarios
GET http://localhost:8080/users/email  -> lista el usuario con el email especificado
POST http://localhost:8080/users -> recibe un content-type application/json y crea el usuario.
Ejemplo json :
{
  "id": 1,
  "name": "Pablo",
  "username": "pcarreno",
  "email": "pcarreno@gmail.com",
  "phone": "445566"
}

GET http://localhost:8080/apicall?param=1-9 -> llama a la api externa

