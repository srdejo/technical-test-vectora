# Prueba Técnica - Backend Developer (Java + Spring Boot)

Este proyecto utiliza Docker Compose para levantar un entorno con Kafka, PostgreSQL y varios microservicios (account-service, transaction-service y bff-service).

## 📌 Prerrequisitos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes componentes en tu máquina:

- **Docker**: [Instalar Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Instalar Docker Compose](https://docs.docker.com/compose/install/)

Verifica que están instalados ejecutando:

```sh
docker --version
```

```sh
docker-compose --version
```

## 🚀 Cómo ejecutar el proyecto

1. Clonar el repositorio del proyecto:

```sh
git clone https://github.com/srdejo/technical-test-vectora.git
cd technical-test-vectora
```

2. Construir y levantar los contenedores en segundo plano:

```sh
sudo docker-compose up --build -d
```

3. Ver los logs de los servicios en tiempo real:

```sh
sudo docker-compose logs -f
```

4. Para detener todos los servicios:

```sh
sudo docker-compose down
```

## 🛠️ Servicios y Puertos

Todos los servicios son internos y se comunican dentro de la red de Docker. No hay exposición de puertos externos.

- **Kafka Broker** → Interno en la red Docker 
- **PostgreSQL** → Interno en la red Docker 
- **Account Service** → Interno en la red Docker
- **Transaction Service** → Interno en la red Docker
- **BFF Service** → `localhost:8080`

## 📄 Variables de Entorno

Los servicios utilizan las siguientes variables de entorno configuradas en el `docker-compose.yml`:

- **Base de datos PostgreSQL**
  - `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/bank_bd`
  - `SPRING_DATASOURCE_USERNAME=bank_user`
  - `SPRING_DATASOURCE_PASSWORD=bank_pass`

Si necesitas modificar credenciales, edita `docker-compose.yml` antes de levantar los contenedores.

## 🧹 Limpieza

Si deseas eliminar todos los contenedores, volúmenes y redes creadas:

```sh
sudo docker-compose down -v
```

---

Ahora el proyecto está listo para ser probado en cualquier máquina con Docker y Docker Compose instalados. 🚀

## Colección de Postman

Para facilitar las pruebas, hemos incluido una colección de Postman con las principales peticiones de la API.

### Cómo importar la colección en Postman

1. Descarga el archivo [VectoraTest.postman_collection.json](VectoraTest.postman_collection.json).
2. Abre Postman y selecciona **File > Import**.
3. Selecciona la opción **File** y carga el archivo JSON descargado.
4. Una vez importado, podrás ejecutar las peticiones directamente en Postman.

La colección incluye endpoints para:
- Crear cuenta (`POST /accounts/accounts`)
- Obtener cuenta (`GET /accounts/accounts/{id}`)
- Crear transacción (`POST /transactions/transactions`)
- Obtener transacción (`GET /transactions/transactions/{id}`)
- Registro de usuario (`POST /auth/register`)
- Inicio de sesión (`POST /auth/login`)

Recuerda configurar la variable `base_url` en Postman si es necesario.
