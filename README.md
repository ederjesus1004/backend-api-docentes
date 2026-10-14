# API de Docentes

API REST para registrar a los docentes de un colegio. Tiene un CRUD completo con validaciones, reglas de negocio y manejo de errores en un solo lugar.

## Tecnologías

- Java 21
- Spring Boot 4 (Web MVC, Data JPA, Validation)
- PostgreSQL
- Lombok
- Maven

## Endpoints

| Método | Ruta | Descripción | Respuesta |
|---|---|---|---|
| GET | `/api/docentes` | Lista todos los docentes | 200 |
| GET | `/api/docentes/{id}` | Busca un docente por id | 200 / 404 |
| POST | `/api/docentes` | Registra un docente | 201 / 400 / 409 |
| PUT | `/api/docentes/{id}` | Actualiza un docente | 200 / 400 / 404 / 409 |
| DELETE | `/api/docentes/{id}` | Elimina un docente | 204 / 404 |

## Reglas

- El DNI tiene 8 dígitos y no se puede repetir.
- El email no se puede repetir. Se guarda en minúsculas, así que `Juan@Mail.com` y `juan@mail.com` cuentan como el mismo.
- Nombres, apellidos y curso son obligatorios.
- Las horas semanales van de 1 a 40.
- Al actualizar, un docente puede conservar su propio DNI y email, pero no usar los de otro docente.

## Errores

Todos los errores responden con el mismo formato:

| Código | Cuándo |
|---|---|
| 400 | Datos que no cumplen las validaciones, o JSON mal escrito |
| 404 | El docente no existe |
| 409 | El DNI o el email ya están registrados |
| 500 | Error inesperado (el detalle queda en el log) |

```json
{
  "fecha": "2026-10-13T22:30:00",
  "estado": 400,
  "mensaje": "Datos inválidos",
  "errores": {
    "dni": "El DNI debe tener 8 dígitos",
    "horasSemanales": "Máximo 40 horas semanales"
  }
}
```

## Estructura

```
src/main/java/dev/eder/colegio/
├── entity/         Docente (la tabla)
├── repository/     consultas a la base de datos
├── dto/            DocenteRequest (entrada) y DocenteResponse (salida)
├── exception/      excepciones propias, ErrorResponse y GlobalExceptionHandler
├── mapper/         convierte entre DTO y entidad
├── service/        interface + implementación con las reglas de negocio
└── controller/     endpoints REST
```

## Cómo correrlo

1. Crea la base de datos en PostgreSQL:

   ```sql
   CREATE DATABASE docente_db;
   ```

2. Configura tu contraseña de PostgreSQL como variable de entorno. En IntelliJ: *Run → Edit Configurations → Environment variables*:

   ```
   DB_PASSWORD=tu_contraseña
   ```

   Si no la defines, el proyecto usa `postgres`.

3. Ejecuta el proyecto:

   ```bash
   ./mvnw spring-boot:run
   ```

La API queda en `http://localhost:8080` y la tabla `docentes` se crea sola al arrancar.

## Ejemplo

**POST** `/api/docentes`

```json
{
  "dni": "41234567",
  "nombres": "Juan",
  "apellidos": "Pérez Soto",
  "email": "Juan.Perez@Mail.com",
  "curso": "Matemática",
  "horasSemanales": 20
}
```

Respuesta **201 Created**:

```json
{
  "id": 1,
  "dni": "41234567",
  "nombres": "Juan",
  "apellidos": "Pérez Soto",
  "descripcion": "Juan Pérez Soto - Matemática",
  "email": "juan.perez@mail.com",
  "curso": "Matemática",
  "horasSemanales": 20,
  "fechaRegistro": "2026-10-13T22:30:00"
}
```

## Autor

Eder Cuaresma · [GitHub](https://github.com/ederjesus1004)
