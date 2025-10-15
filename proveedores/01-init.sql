-- Crear base de datos para proveedores
\connect proveedoresdb;

CREATE TABLE IF NOT EXISTS proveedores (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    contacto VARCHAR(150),
    telefono VARCHAR(50),
    direccion VARCHAR(255),
    correo VARCHAR(150),
    activo BOOLEAN DEFAULT TRUE
);
