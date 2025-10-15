\connect facturaciondb;

CREATE TABLE IF NOT EXISTS facturas (
    id SERIAL PRIMARY KEY,
    descripcion_pedido VARCHAR(255) NOT NULL,
    cliente VARCHAR(100) NOT NULL,
    producto_id BIGINT NOT NULL,
    proveedor_id BIGINT NOT NULL,
    cantidad INTEGER NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total NUMERIC(10,2) NOT NULL,
    estado VARCHAR(50) DEFAULT 'generada'
);
