# 🛍️ Taller 4 - Gestión de Productos (Docker + Spring Boot + PostgreSQL)


Este proyecto implementa un **sistema de gestión de productos** con:
- **Backend en Spring Boot** (API REST)
- **Base de datos PostgreSQL** configurada en Docker
- **Frontend HTML + JavaScript** servido con Nginx
- **Orquestación completa** con Docker Compose

---

## 🚀 Requisitos Previos

Antes de empezar, asegúrate de tener instalado:

- 🐳 [Docker](https://www.docker.com/get-started)
- 🔧 [Docker Compose](https://docs.docker.com/compose/)
---

## ⚙️ Configuración de Entorno

Crea un archivo `.env` en la raíz del proyecto con el siguiente contenido:
```env
POSTGRES_USER=postgres_user
POSTGRES_PASSWORD=tu_password_segura
POSTGRES_DB=productosdb_name
```

## Datos iniciales 
Al iniciar la configuración inicial de docker, se carga la base de datos postgreSQL con **init.sql** y se cargan productos (datos quemados) con **data.sql**. Es importante tener el archivo **.env** con las credenciales de postgreSQL

## Fases del proyecto - Instalación 
### Fase Inicial: Construir Todo el Proyecto
Levantar todos los servicios desde la raíz del proyecto 

```env
docker-compose up --build
```

Verifique que todo esté funcionando con los siguientes endpoints 
1. [http://localhost:8080/api/productos](http://localhost:8080/api/productos "Title") "Módulo Backend"
2. [ http://localhost:3000]( http://localhost:3000 "Title") "Módulo Frontend"

También verifique que los contenedores estén activos 
```env
docker ps
```

### Pruebas proyecto
Para hacer pruebas debe usar POSTMAN o THUNDERCLIENT (Extensión de VS Code). Y pasar los siguientes parámetros:

1. El endpoint configurado para hacer los pagos es 
[http://localhost:8080/api/pagos](http://localhost:8080/api/pagos).
2. Para esto, debe usar el siguiente body formato JSON (Recuerde modificar el email).
```env
{
  "cliente": "David Cuadros",
  "email": "cuadrosdavid01@gmail.com",
  "metodoPago": "Tarjeta de crédito",
  "monto": 250000.0,
  "moneda": "COP",
  "usuarioId": 1,
  "idTransaccion": "TXN-123456",
  "direccionEnvio": "Carrera 10 #25-50",
  "ciudadEnvio": "Bucaramanga",
  "fechaPago": "2025-10-15T17:30:00",
  "productos": [
    {
      "productoId": 1,
      "proveedorId": 2,
      "cantidad": 1,
      "precioUnitario": 250000.0
    },
    {
      "productoId": 2,
      "proveedorId": 2,
      "cantidad": 1,
      "precioUnitario": 25000.0
    }
  ]
}
```
3. Revise el correo diligenciado y allí encontrará la factura e información de la compra.

### Actualizaciones o cambios 
En dado caso que tenga que actualizar o cambiar algunas partes del código, tendrá que volver a desplegar con lo siguientes comandos 
1. Detener contenedores y eliminar volúmenes para evitar caché

```env
docker-compose down -v
```
2. Reconstruir y levantar nuevamente
```env
docker-compose up --build
```

En caso de que sea un cambio solo del frontend, reconstruya la imagen con los siguientes comandos:
1. Detiene y elimina todos los contenedores
```bash
docker-compose down

```
2. Reconstruye la imagen Docker solo del servicio "frontend". 
```bash
docker-compose build frontend
```

3. Vuelve y iniciar todos los servicios 
```bash
docker-compose up
```

En caso de que sea Backend o la base de datos, haga los mismos pasos anteriores, pero en en el **Comando 2** ejecute de acuerdo alos cambios:
* Reconstruir la imagen Docker solo del servicio "backend".
```bash
docker-compose build backend
```

* Reconstruye la imagen Docker solo del servicio "base de datos".
```bash
docker-compose build db
```
### Eliminar contenedores 
Si desea eliminar los contenedores: 
```bash
docker rm -f postgres_productos productos_frontend spring_productos_app
```
